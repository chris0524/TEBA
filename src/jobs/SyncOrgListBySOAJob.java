package jobs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import soa.client.utils.DataUtil;
import soa.model.res.data.GetOrgInfoActXML.RsInfo.Org.User;
import soa.model.res.data.GetOrgListActXML.RsInfo.Org;
import util.Database;

public class SyncOrgListBySOAJob implements Job {
	private Map<String, String> topGovAndNscuMap = new HashMap<String, String>(){{
		put("N", "U0");//本部
	}};
	
	private String level = "5";
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		Database db = new Database();
		Connection con = db.getConnection();
		
		Set<Entry<String, String>> set = topGovAndNscuMap.entrySet();
		String govCode = null;
		String nscuCode = null;
		for(Entry entry: set){
			govCode = (String) entry.getKey();
			nscuCode = (String) entry.getValue();
			
			List<Org> orgList = DataUtil.getOrgList(govCode, nscuCode, level);
			try{
				con.setAutoCommit(false);
				for(Org org: orgList){
					syncOrgToDatabase(con, org);
					syncOrgUsers(con, org);
				}
				con.commit();
			}catch(Exception e){
				try {
					con.rollback();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		if (con != null)
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		if (db != null)
			db.closeAll();			
	}

	private void syncOrgToDatabase(Connection con, Org org) {
		// TODO Auto-generated method stub
		
	}
	private void syncOrgUsers(Connection con, Org org) {
		soa.model.res.data.GetOrgInfoActXML.RsInfo.Org orgInfo = DataUtil.getOrgInfo(org.govCode, org.nscuCode, "ALL");
		List<soa.model.res.data.GetOrgInfoActXML.RsInfo.Org.User> users = orgInfo.Users;
		if (CollectionUtils.isNotEmpty(users)){
			for(soa.model.res.data.GetOrgInfoActXML.RsInfo.Org.User user: users){
				syncUserToDatabase(con, user);
			}
		}
	}
	private void syncUserToDatabase(Connection con, User user) {
		// TODO Auto-generated method stub
		
	}

}
