package util;

import java.sql.ResultSet;


public class TCGHCommon 
{

	private TCGHCommon() 
	{
		//avoid instantiation...
	}


	/**
	 * 取得SYSCA_CODE的Map
	 * @param codeKindid
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_Code(String codeKindid)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select codeid,codename from SYSCA_CODE  ";
		       sql+=" where codekindid="+Common.sqlChar(codeKindid);
		
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("codeid"), rs.getString("codename"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
		
	/**
	 * 取得SYSCA_ORGAN(機關名稱)的Map
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_Organ() {
		return getSysca_Organ(null, false);
	}
	
	public static java.util.Map<String,String> getSysca_Organ(java.util.Map<String, String> cccMap, boolean ismanager) {
		if(cccMap == null){
			cccMap = new java.util.HashMap<String, String>();
		}
		
		Database db = null;
		try{
			db = new Database();
			
			String sql = "  select organid, organaname from SYSCA_ORGAN ";
			if(ismanager){
				sql += " where ismanager = 'Y' ";
			}
			
			ResultSet rs = db.querySQL(sql);
			while(rs.next()){
				cccMap.put(rs.getString("organid"), rs.getString("organaname"));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
		
		return cccMap;
	}
	
	// 取得公務用 - 機關上層單位資料
	public static java.util.Map<String, String> getSYSCA_ORGAN_SUPERIOR1(java.util.Map<String, String> cccMap, String selectOrgan, boolean ismanager) {
		if(cccMap == null){
			cccMap  = new java.util.HashMap<String, String>();
		}
		
		Database db = null;
		try{
			db = new Database();
			
			String sql = "  select organid, organsuperior1 from SYSCA_ORGAN where 1 = 1 ";
			if(ismanager){
				sql += " and ismanager = 'Y' ";
			}
			if(!"".equals(Common.get(selectOrgan))){
				sql += " and organid in ('-1'," + Common.get(selectOrgan) + ")";
			}
			
			ResultSet rs = db.querySQL(sql);
			while (rs.next()){
				cccMap.put(Common.get(rs.getString("organid")), Common.get(rs.getString("organsuperior1")));
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}

	/**
	 * 取得SYSCA_DEPRPARK(園區別名稱)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprPark(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select deprparkno,deprparkname from SYSCA_DEPRPARK  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("deprparkno"), rs.getString("deprparkname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRUNIT(部門別)Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprUnit(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select deprunitno,deprunitname from SYSCA_DEPRUNIT  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("deprunitno"), rs.getString("deprunitname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRUNIT1(部門別單位)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprUnit1(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select deprunit1no,deprunit1name from SYSCA_DEPRUNIT1  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("deprunit1no"), rs.getString("deprunit1name"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRACCOUNTS(會計科目)的Map
	 * @param enterorg 機關別
	 * @param deprunit 部門別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprAccounts(String enterorg,String deprunit)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql=" select a.depraccountsno,a.depraccountsname from SYSCA_DEPRACCOUNTS a,SYSCA_DEPRUNITACCOUNTS b";
		sql+=" where a.enterorg = b.enterorg" +
			" and a.depraccountsno = b.depraccountsno" +
			" and b.enterorg="+Common.sqlChar(enterorg) +
			" and b.deprunitno="+Common.sqlChar(deprunit);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("depraccountsno"), rs.getString("depraccountsname"));
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRACCOUNTS(會計科目)的Map
	 * @param enterorg 機關別
	 * @return Map  Key : depraccountsno    Value:  depraccountsname
	 */
	public static java.util.Map<String,String> getSysca_DeprAccounts(String enterorg) {
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql=" select a.depraccountsno ,a.depraccountsname from SYSCA_DEPRACCOUNTS a,SYSCA_DEPRUNITACCOUNTS b";
		sql+=" where a.enterorg = b.enterorg" +
			" and a.depraccountsno = b.depraccountsno" +
			" and b.enterorg="+Common.sqlChar(enterorg);
		ResultSet rs = null;
		try {
			rs = db.querySQL(sql);
			
			while (rs.next()) {
				cccMap.put(rs.getString("depraccountsno"), rs.getString("depraccountsname"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRUNIT.untdp019rnotes的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprUnit_Untdp019Notes(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select deprunitno,isnull(untdp019rnotes,'') as untdp019rnotes from SYSCA_DEPRUNIT  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("deprunitno"), rs.getString("untdp019rnotes"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSCA_DEPRUNIT1.untdp019rnotes的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_DeprUnit1__Untdp019Notes(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select deprunit1no,isnull(untdp019rnotes,'') as untdp019rnotes from SYSCA_DEPRUNIT1  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("deprunit1no"), rs.getString("untdp019rnotes"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	

	/**
	 * 取得SYSPK_PROPERTYCODE(財產名稱)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSyspk_PropertyCode(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		String sql="  select propertyno,propertyname from SYSPK_PROPERTYCODE  ";
		sql+=" where enterorg in ('000000000A',"+Common.sqlChar(enterorg)+")";
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("propertyno"), rs.getString("propertyname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSPK_PROPERTYCODE(財產名稱)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSyspk_PropertyCodeAll()
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		String sql=" select enterorg,propertyno,propertyname from SYSPK_PROPERTYCODE  ";
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("enterorg")+"-"+rs.getString("propertyno"), rs.getString("propertyname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得SYSPK_PROPERTYCODE2(物品名稱)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSyspk_PropertyCode2(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select propertyno,propertyname from SYSPK_PROPERTYCODE2  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("propertyno"), rs.getString("propertyname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	
	/**
	 * 取得SYSPK_PROPERTYCODE2.propertyunit(物品單位)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getPropertyUnit(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select propertyno,propertyunit from SYSPK_PROPERTYCODE2  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("propertyno"), rs.getString("propertyunit"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得UNTMP_KEEPUNIT(保管單位、使用單位)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getUntmp_KeepUnitCode(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select unitno, unitname from UNTMP_KEEPUNIT  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("unitno"), rs.getString("unitname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	/**
	 * 取得UNTMP_KEEPER(保管人、使用人)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getUntmp_KeeperCode(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select keeperno, keepername from UNTMP_KEEPER  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("keeperno"), rs.getString("keepername"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	
	/**
	 * 取得SYSCA_PLACE(存置地點)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_PlaceCode(String enterorg)
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select placeno, placename from SYSCA_PLACE  ";
		sql+=" where enterorg="+Common.sqlChar(enterorg);
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("placeno"), rs.getString("placename"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	
	/**
	 * 取得SYSCA_SIGN(縣市鄉鎮街)的Map
	 * @param enterorg 機關別
	 * @return Map
	 */
	public static java.util.Map<String,String> getSysca_SignCode()
	{
		java.util.Map<String, String> cccMap = new java.util.HashMap<String, String>();
		
		Database db = new Database();
		
		String sql="  select signno, signname from SYSCA_SIGN  ";
		ResultSet rs=null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			while (rs.next())
			{
				cccMap.put(rs.getString("signno"), rs.getString("signname"));		
			}
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return cccMap;
	}
	
	
	// 取得權屬
	public static String getOwnerShipDefault(String enterorg){
		String r = null;
		if(!"".equals(Common.get(enterorg))){
			String sql = "select ownership from SYSCA_ORGARGU where enterorg = " + Common.sqlChar(enterorg);
			
			Database db = null;
			try{
				db = new Database();
				ResultSet rs = db.querySQL(sql);
				if(rs.next()){
					r = rs.getString("ownership");
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(db != null){
					db.closeAll();
				}
			}
		}
		
		return Common.get(r);
	}
	
	// 取得代碼中文名稱
	public static String getSYSCODEName(String codeId, String codekindId){
		String r = null;
		if(!"".equals(Common.get(codeId)) && !"".equals(Common.get(codekindId))){
			String sql = " select codename from SYSCA_CODE where codekindid = " + Common.sqlChar(codekindId) + 
						 " and codeid = " + Common.sqlChar(codeId);
			Database db = null;
			try{
				db = new Database();
				ResultSet rs = db.querySQL(sql);
				if(rs.next()){
					r = rs.getString("codename");
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(db != null){
					db.closeAll();
				}
			}
		}
		
		return Common.get(r);
	}
	
	// 查詢單一資料用
	public static String getLookup(String sql){
		String r = null;
		if(!"".equals(Common.get(sql))){
			Database db = null;
			try{
				db = new Database();
				ResultSet rs = db.querySQL(sql);
				if(rs.next()){
					r = rs.getString(1);
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(db != null){
					db.closeAll();
				}
			}
		}
		
		return Common.get(r);
	}
	
	/**
	 * 取得財產編號之String array, 若長度不足該項則為空白, 長度超過11碼則會噴錯
	 * <br/>StringArray[0]:類
	 * <br/>StringArray[1]:項
	 * <br/>StringArray[2]:目
	 * <br/>StringArray[3]:節
	 * <br/>StringArray[4]:號
	 * <br/>EX: 若傳入[123] 三碼  則StringArray[0] = 1 ; StringArray[1] = 23 , 其餘空白
	 * @param propertyNo : StringArray
	 * @return
	 * @throws Exception 
	 */
	public static String[] getFormatedPropertyNo(String propertyNo) throws Exception {
		if (propertyNo.length() > 11) {
			throw new Exception("財產編號超過11碼");
		}
		String[] formatedPropertyno = new String[]{"", "", "", "", ""};
		char[] charArray = propertyNo.toCharArray();
		for (int index = 0; index < charArray.length; index++) {
			char tmpChar = charArray[index];
			switch (index) {
			case 0: 
				// 類
				formatedPropertyno[0] = String.valueOf(tmpChar);
				break;
			case 1: 
			case 2:
				// 項
				formatedPropertyno[1] += String.valueOf(tmpChar);
				break;
			case 3:
			case 4:
				// 目
				formatedPropertyno[2] += String.valueOf(tmpChar);
				break;
			case 5:
			case 6:
				// 節
				formatedPropertyno[3] += String.valueOf(tmpChar);
				break;
			default:
				// 號
				formatedPropertyno[4] += String.valueOf(tmpChar);
				break;
			}


		}

		return formatedPropertyno;
	}
	
	
	
	/**
	 * 取得UNTPD_CHECKNONEXP最大的serialno1
	 * @param enterorg 機關別
	 * @return int
	 */
	public static int getMaxserialno1(String enterorg)
	{
		int maxserialno1 = 0;
		
		Database db = new Database();
		
		String sql=" select ISNULL(max(serialno1),'0')+1 as maxserialno1 from UNTPD_CHECKNONEXP where 1=1 and enterorg = '" + enterorg + "' ";

		ResultSet rs = null;
		
		try 
		{
			rs = db.querySQL(sql);
			
			if (rs.next())
			{
				maxserialno1  = rs.getInt("maxserialno1");		
			}
			rs.close();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if(db != null){
				db.closeAll();
			}
		}
			
		return maxserialno1;
	}
	
}
