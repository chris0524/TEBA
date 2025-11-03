package filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import soa.client.utils.DataUtil;
import soa.client.utils.SSOUtil;
import soa.model.SoaUser;
import sys.ap.SYSAP003F_Permission;
import util.Database;
import util.Datetime;
import util.TCGHCommon;
import util.User;
import util.Validate;

public class SSOFilter implements Filter {

	public static final String SESSION_SSO_TOKEN1 = "token1";
	public static final String SESSION_EIP_INDEX_PAGE = "eipIndexPage";
	
	public static String eipIndexPage;//eip首頁
	public static String appCodeNameFromEip;//eip傳給本系統appCode的參數名稱
	public static String userIdNameFromEip;//eip傳給本系統使用者帳號的參數名稱
	public static String ipNameFromEip;//eip傳給本系統使用者IP的參數名稱
	public static String token1NameFromEip;//eip傳給本系統使用者token1的參數名稱
	public static int validateSSOToken1Type;//如何驗證token1。0:不驗證、1：即時呼叫SSO驗證、2：搭配intervalForValidingSSOToken1呼叫SSO驗證
	public static long intervalForValidingSSOToken1;//距離上一次透過SSO驗證token1有效性的間隔
	private Logger logger = Logger.getLogger(this.getClass());

	protected HttpServletRequest getHttpServletRequest(ServletRequest request){
		return (HttpServletRequest)request;
	}
	
	protected HttpServletResponse getHttpServletResponse(ServletResponse response){
		return (HttpServletResponse)response;
	}
	
	protected HttpSession getHttpSession(ServletRequest request){
		HttpServletRequest httpReq = (HttpServletRequest)request;
		return httpReq.getSession();
	}
	
	protected void setSessionAttribute(ServletRequest request, String key, Object value){
		getHttpSession(request).setAttribute(key, value);
	}
	
	protected Object getSessionAttribute(ServletRequest request, String key){
		return getHttpSession(request).getAttribute(key);
	}
	
	protected String getRequestParamter(ServletRequest request, String key){
		return getHttpServletRequest(request).getParameter(key);
	}
	
	protected void RedirectToSsoErrorPage(ServletRequest request, ServletResponse response, String errMsg) 
			throws ServletException, IOException{
		HttpSession hs = getHttpSession(request);
		
		hs.setAttribute("errMsg", errMsg);
		hs.setAttribute("eipHomePage", eipIndexPage);
		getHttpServletResponse(response).sendRedirect("/TCGH/home/ssoError.jsp");
	}
	
	public void init(FilterConfig config) throws ServletException {
		eipIndexPage = config.getInitParameter("eipIndexPage");
		appCodeNameFromEip = config.getInitParameter("appCodeNameFromEip");
		userIdNameFromEip = config.getInitParameter("userIdNameFromEip");
		ipNameFromEip = config.getInitParameter("ipNameFromEip");
		token1NameFromEip = config.getInitParameter("token1NameFromEip");
		intervalForValidingSSOToken1 = Long.parseLong(config.getInitParameter("intervalForValidingSSOToken1"));
		validateSSOToken1Type = Integer.parseInt(config.getInitParameter("validateSSOToken1Type"));
		SSOUtil.setWsdlUrl(config.getInitParameter("ssoServiceWsdlUrl"));
		DataUtil.setWsdlUrl(config.getInitParameter("dataServiceWsdlUrl"));
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {				
		//handle Session Variable Poisoning
		String eip1 = "";
		if(!Validate.checkSpecialChar(token1NameFromEip)){
			eip1 = token1NameFromEip;
			System.out.println(" *** token1NameFromEip *** : " + token1NameFromEip);
		}
		
		String eipToken1 = getRequestParamter(request, eip1);
		if (StringUtils.isEmpty(eipToken1)){
			RedirectToSsoErrorPage(request, response, "請透過EIP單一簽入方式登入本系統！");
			return;
		}
		
		String userIp = getRequestParamter(request, ipNameFromEip);
		if (StringUtils.isEmpty(userIp)){
			RedirectToSsoErrorPage(request, response, "無法取得使用者IP");
			return;
		}
		String userID = "";
		if(!Validate.checkSpecialChar(userIdNameFromEip)){
			userID = userIdNameFromEip;
		}
		String eipUserID = getRequestParamter(request, userID);
		SoaUser soaUser = getToken2(eipToken1, userIp, eipUserID);
		if (soaUser == null){
			RedirectToSsoErrorPage(request, response, "帳號或密碼錯誤！");
			return;
		}
		
		//驗證帳號是否有權限登入TCGH系統
//		String appCode = getRequestParamter(request, appCodeNameFromEip);
//		String eipAppCode = soaUser.govCode + appCode;
//		if (!(appCode == null || "M".equals(soaUser.govCode) || "S".equals(soaUser.govCode))) {
//			if (!isValidToken1Authority(eipToken1, userIp, eipAppCode)){
//				RedirectToSsoErrorPage(request, response, "無使用本系統之權限！");
//				return;
//			}
//		}
		
		String empid = ""; 
		System.out.println("SSO 133 govCode :" + soaUser.govCode);
		if ("N".equals(soaUser.govCode)) { 	//竹科管局使用公號登入
			empid = getRequestParamter(request, "SSOChooseUID");
		} else { 							//科技部、中科管局、南科管局則使用EIP帳號登入
			empid = soaUser.userId;
		}
		System.out.println("SSO 139 empid :" + empid);
		
//		if ("0".equals(GetOrgUserInfo(eipUserID).userType)) { //如果離職則設為停用
//			invalidDbUser(soaUser,empid);//將SYSAP_EMP.isstop設定為停用
//			RedirectToSsoErrorPage(request, response, "此帳號已離職停用！");
//			return;
//		} 
		
		//注意：此部分需再確認新增USER到[SYSAP_EMP]時，其groupid,organid,roleid怎麼設定
		updateOrCreateDbUser(soaUser,empid);
		
		//String userID = soaUser.userId;
		
		//M:科技部 N:竹科  C:中科  S:南科 
		String organid = "M".equals(soaUser.govCode)? TCGHCommon.getSYSCODEName("01", "ETO") :"N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO");
		User user = User.getUser(empid,organid);
		System.out.println("SSO Filter 154-empid :" +empid + " , " + "organid : " + organid);
		if (user == null){
			RedirectToSsoErrorPage(request, response, "帳號或密碼錯誤！");
			return;
		}
		
	    
		this.saveUserToSession(request, user);
		System.out.println("SSO Filter 161-user :" +user.toString());
		setSessionAttribute(request, SESSION_EIP_INDEX_PAGE, eipIndexPage);
		setSessionAttribute(request, SESSION_SSO_TOKEN1, eipToken1);		
		getHttpServletResponse(response).sendRedirect("/TCGH/home/frame.jsp");
	}
	
	private void invalidDbUser(SoaUser soaUser,String empid) {
		Database db = new Database();
		Connection conn = db.getConnection();

		String sql = "update SYSAP_EMP set isstop = 'Y' where empid = ? and organid = ?";
		QueryRunner run = new QueryRunner();
		try {
			run.update(conn, sql, empid, "M".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"));
			if ("N".equals(soaUser.govCode)) { //竹科需同步保管人104/08/21
				String sql1 = "update UNTMP_KEEPER set incumbencyyn = 'N' where keeperno = ? and enterorg = ?";
				run.update(conn, sql1, empid, "M".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"));
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			db.closeAll();
		}
	}

	private void updateOrCreateDbUser(SoaUser soaUser,String empid) {
		Database db = new Database();
		Connection conn = db.getConnection();
		try{
			int count = updateDbUser(conn, soaUser, empid);
			if (count == 0) {
				if ("N".equals(soaUser.govCode)) {
					createDbUser_N(conn, soaUser, empid);
					int countkeeper = updateDbKeeper(conn, soaUser, empid);//竹科需同步保管人104.08.21
					if (countkeeper == 0) {//竹科需同步保管人104.08.21
						createDbKeeper(conn, soaUser, empid);
					}
				} else {
					createDbUser(conn, soaUser, empid);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			db.closeAll();
		}
	}
	
	//for 南科管理局及中科管理局
	private void createDbUser(Connection conn, SoaUser soaUser, String empid) {
		QueryRunner run = new QueryRunner();
		String sql = "INSERT INTO SYSAP_EMP (empid, emppwd, empname, organid, groupid, empemail, isstop, isadminmanager, roleid) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			run.update(conn, sql,
					empid,
					empid,
					soaUser.userName,
					"M".equals(soaUser.govCode)? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"),
					"keeper01",
//					soaUser.userDuty,
//					soaUser.userTel,
//					soaUser.userFax,
					soaUser.userEmail,
					"N",
					"N",
					"1");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	//for 竹科管理局
	private void createDbUser_N(Connection conn, SoaUser soaUser, String empid) {
		QueryRunner run = new QueryRunner();
		String sql = "INSERT INTO SYSAP_EMP (empid, emppwd, empname, organid, groupid, empemail, isstop, isadminmanager, roleid, keeperno) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			run.update(conn, sql,
					empid,
					empid,
					soaUser.userName,
					"M".equals(soaUser.govCode)? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"),
					"keeper01",
//					soaUser.userDuty,
//					soaUser.userTel,
//					soaUser.userFax,
					soaUser.userEmail,
					"N",
					"N",
					"1",
					empid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int updateDbUser(Connection conn, SoaUser soaUser,String empid) {
		QueryRunner run = new QueryRunner();
		String sql = "update SYSAP_EMP set isstop='N' where empid = ? and organid = ?";
		try {
			return run.update(conn, sql, empid, "M".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	//竹科需同步保管人104.08.21
	private void createDbKeeper(Connection conn, SoaUser soaUser, String empid) {
		QueryRunner run = new QueryRunner();
		String sql = "INSERT INTO UNTMP_KEEPER (enterorg, keeperno, keepname, incumbencyyn, editdate, edittime) values(?, ?, ?, ?, ?, ?)";
		try {
			run.update(conn, sql,
					"M".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"),
					empid,
					soaUser.userName,
					"Y",
					Datetime.getYYYYMMDD(),
					Datetime.getHHMMSS()
					);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private int updateDbKeeper(Connection conn, SoaUser soaUser,String empid) {
		QueryRunner run = new QueryRunner();
		String sql = "update UNTMP_KEEPER set incumbencyyn='Y' where keeperno = ? and enterorg = ?";
		try {
//			return run.update(conn, sql, soaUser.userId);
			return run.update(conn, sql, empid, "M".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("01", "ETO") : "N".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("02", "ETO") : "C".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("03", "ETO") : "S".equals(soaUser.govCode) ? TCGHCommon.getSYSCODEName("04", "ETO") : TCGHCommon.getSYSCODEName("01", "ETO"));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean isValidToken1Authority(String eipToken1, String userIp, String eipAppCode) {
		return SSOUtil.isValidToken1Authority(eipToken1, eipAppCode, userIp);
	}

	private SoaUser getToken2(String eipToken1, String userIp,String eipUserID) {
		soa.model.res.sso.GetToken2ActXML.RsInfo.User rsUser = SSOUtil.getToken2(eipToken1, userIp);
		System.out.println("SSO 303 rsUser.UserId :" + rsUser.UserId + " , " + "rsUser.Uid :" + rsUser.Uid);
		return SoaUser.convertBy(rsUser);
	}

	private void saveUserToSession(ServletRequest request, User user) {
		HttpSession hs = getHttpSession(request);
		hs.setAttribute("editID", user.getUserID());
		System.out.println("SSO Filter 310 editID :" + user.getUserID());
		SYSAP003F_Permission[] permission = user.getPermission();
		hs.setAttribute("CurrentPermission",permission);
		hs.setAttribute("user",user);
		System.out.println("hs :" + hs.getAttribute("user").toString());
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	private soa.model.res.data.GetOrgUserInfoActXML.RsInfo.User GetOrgUserInfo(String UserId) {
		return DataUtil.getOrgUserInfo(UserId);
	}
	
	public static int getValidateSSOToken1Type() {
		return validateSSOToken1Type;
	}

}
