package util.dtree;

import java.sql.ResultSet;
import java.util.ArrayList;

import util.Common;
import util.Database;
import util.SuperBean;

public class TreeEntry extends SuperBean {
	private String icon;
	private String iconOpen;
	private String id;
	private String name;
	private String opened;
	private String pid;
	private String target;
	private String title;
	private String url;
	private String btnRead;
	private String btnWrite;
	private String sorted;
	private String includeChild;
	private String pname;
	private String hasChild;
	
	public String getHasChild() { return checkGet(hasChild); }
	public void setHasChild(String s) { hasChild = checkSet(s); } 
	public String getPname() { return checkGet(pname); }
	public void setPname(String s) { pname = checkSet(s); }
	public String getIcon() { return checkGet(icon); }
	public void setIcon(String s) { icon = checkSet(s); }
	public String getIconOpen() { return checkGet(iconOpen); }
	public void setIconOpen(String s) { iconOpen = checkSet(s);}
	public String getId() {return checkGet(id);}
	public void setId(String s) { id = checkSet(s);}
	public String getName() { return checkGet(name); }
	public void setName(String s) {name = checkSet(s);}
	public String getOpened() {return checkGet(opened);}
	public void setOpened(String s) {opened = checkSet(s);}
	public String getPid() {return checkGet(pid);}
	public void setPid(String s) {pid = checkSet(s);}
	public String getTarget() {return checkGet(target);}
	public void setTarget(String s) {target = checkSet(s);}
	public String getTitle() {return checkGet(title);}
	public void setTitle(String s) {title = checkSet(s);}
	public String getUrl() {return checkGet(url);}
	public void setUrl(String s) {url = checkSet(s);}
	
	public void setBtnRead(String s) {btnRead = checkSet(s);}	
	public String getBtnRead() {return checkGet(btnRead);}	
	
	public void setBtnWrite(String s) {btnWrite = checkSet(s);}	
	public String getBtnWrite() {return checkGet(btnWrite);}
	
	public void setSorted(String s) {sorted = checkSet(s);}	
	public String getSorted() {return checkGet(sorted);}		
	
	public void setIncludeChild(String s) {includeChild = checkSet(s);}	
	public String getIncludeChild() {return checkGet(includeChild);}		
	
	protected String[][] getInsertCheckSQL(){	
		String[][] checkSQLArray = new String[1][4];
	 	checkSQLArray[0][0]=" select count(*) as checkresult from SYSAP_DTREE where id= '" + id + "'";
		checkSQLArray[0][1]=">";
		checkSQLArray[0][2]="0";
		checkSQLArray[0][3]="該id己重複，請重新輸入！";
		
		return checkSQLArray;
	}

	protected String[] getInsertSQL(){
		String[] execSQLArray = new String[1];
		//select id, pid, name, url, title, target, icon, iconOpen, opened, btnRead, btnWrite
		execSQLArray[0]=" insert into SYSAP_DTREE(" +
		   " id,"+
           " pid,"+
           " name,"+
           " url,"+
           " title,"+
           " target,"+
           " icon,"+
           " iconopen,"+
           " opened,"+
           " btnread,"+
           " btnwrite,"+
           " sorted"+
       " )Values(" +
           Common.sqlChar(id) + "," +
           Common.sqlChar(pid) + "," +
           Common.sqlChar(name) + "," +
           Common.sqlChar(url) + "," +
           Common.sqlChar(title) + "," +
           Common.sqlChar(target) + "," +
           Common.sqlChar(icon) + "," +
           Common.sqlChar(iconOpen) + "," +
           Common.sqlChar(opened) + "," +
           Common.sqlChar(btnRead) + "," +
           Common.sqlChar(btnWrite) + "," +
           Common.sqlInt(sorted) + ")" ;
		return execSQLArray;
	}	

	protected String[] getUpdateSQL(){
		String[] execSQLArray = new String[1];
		
	    execSQLArray[0]=" update SYSAP_DTREE set " +
//	        " id = " + Common.sqlChar(id) + "," +
	        " name = " + Common.sqlChar(name) + "," +
	        " pid = " + Common.sqlChar(pid) + "," +
	        " url = " + Common.sqlChar(url) + "," +
	        " title = " + Common.sqlChar(title) + "," +
	        " target = " + Common.sqlChar(target) + "," +
	        " icon = " + Common.sqlChar(icon) + "," +
	        " iconopen = " + Common.sqlChar(iconOpen) + "," +
	        " opened = " + Common.sqlChar(opened) + "," +
	        " btnread = " + Common.sqlChar(btnRead) + "," +
	        " btnwrite = " + Common.sqlChar(btnWrite) + "," +
	        " sorted = " + Common.sqlInt(sorted) +	        
	    " where id = '" + id + "'";		
		return execSQLArray;
	}		
	
	protected String[] getDeleteSQL(){
		String[] execSQLArray = null;
		Database db = new Database();
		try {
			ArrayList list = new ArrayList();
			list = getSQL(id, list, db);
			execSQLArray = new String[list.size()+1];
			execSQLArray[0] = "delete from SYSAP_DTREE where id = '" + id + "'";			
			java.util.Iterator it = list.iterator();
			int i = 1;
			while (it.hasNext()) {
				execSQLArray[i] = (String)it.next();
				i = i + 1;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return execSQLArray;
	}
	
	public ArrayList getSQL(String parentID, ArrayList list, util.Database db) {		
		String sSQL = " select id, pid from SYSAP_DTREE where pid = '" + parentID  + "' ";
		try {
			ResultSet rs = db.querySQL(sSQL);
			String id = "";
			while (rs.next()) {
				id = rs.getString("id");
				list.add("delete from SYSAP_DTREE where id='"+id+"'");
				list = getSQL(id, list, db);
			}
			rs.getStatement().close();
			//rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//db.closeAll();
		}
		return list;
	}
	
	public ArrayList getParentNode(String nId, ArrayList list, util.Database db) {
		if (nId.equals("0") || nId.equals("-1")) {
			//list.add(nId);
			return list;
		} else {
			String sSQL = " select id, pid from SYSAP_DTREE where id = '" + nId  + "' ";
			try {
				ResultSet rs = db.querySQL(sSQL);
				String pid = "";
				while (rs.next()) {
					pid = Common.checkGet(rs.getString("pid"));
					list.add(pid);
					list = getParentNode(pid, list, db);
				}
				rs.getStatement().close();
				//rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//db.closeAll();
			}
			return list;			
		}
	}	
	
	public TreeEntry queryOne() {
		Database db = new Database();
		TreeEntry obj= this;
		try{
			String sql = " select id, pid, name, url, title, target, icon, iconopen, opened, btnread, btnwrite, sorted from SYSAP_DTREE where  id = '" + id  + "' ";
			ResultSet rs = db.querySQL(sql);			
			if(rs.next()){
				obj.setId(Common.checkGet(rs.getString("id")));
				obj.setPid(Common.checkGet(rs.getString("pid")));
				obj.setName(Common.checkGet(rs.getString("name")));
				
				sql = " select name from SYSAP_DTREE where id = '" + obj.getPid() + "' ";
				obj.setPname(util.View.getLookupField(sql));
				
				obj.setUrl(Common.checkGet(rs.getString("url")));
				obj.setTitle(Common.checkGet(rs.getString("title")));
				obj.setTarget(Common.checkGet(rs.getString("target")));
				obj.setIcon(Common.checkGet(rs.getString("icon")));
				obj.setIconOpen(Common.checkGet(rs.getString("iconOpen")));
				obj.setOpened(Common.checkGet(rs.getString("opened")));
				obj.setBtnRead(Common.checkGet(rs.getString("btnRead")));
				obj.setBtnWrite(Common.checkGet(rs.getString("btnWrite")));	
				obj.setSorted(Common.checkGet(rs.getString("sorted")));
				//obj.setHasChild(setCS(Common.checkGet(rs.getString("id"))));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	    return obj;
	}
	
	public String getNodeName(String id) {
		id = Common.get(id);
		if (id.equals("0")) return "功能選單";
		else {
			Database db = new Database();
			try{
				String sql = " select name from SYSAP_DTREE where  id = '" + id  + "' ";
				ResultSet rs = db.querySQL(sql);			
				if(rs.next()){
					return Common.checkGet(rs.getString("name"));
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				db.closeAll();
			}
		}
		return "";
	}
	
	public ArrayList getTreeEntry() {
		ArrayList list=new ArrayList();
		String sql="select * from SYSAP_DTREE order by sorted, pid, id";
		Database db = new Database();		  
		try{
			ResultSet rs = db.querySQL(sql);
			while(rs.next()){
				TreeEntry entity=new TreeEntry();
				entity.setId(Common.checkGet(rs.getString("id")));
				entity.setPid(Common.checkGet(rs.getString("pid")));
				entity.setName(Common.checkGet(rs.getString("name")));
				entity.setUrl(Common.checkGet(rs.getString("url")));
				entity.setTitle(Common.checkGet(rs.getString("title")));
				entity.setTarget(Common.checkGet(rs.getString("target")));
				entity.setIcon(Common.checkGet(rs.getString("icon")));
				entity.setIconOpen(Common.checkGet(rs.getString("iconOpen")));
				entity.setOpened(Common.checkGet(rs.getString("opened")));
				entity.setBtnRead(Common.checkGet(rs.getString("btnRead")));
				entity.setBtnWrite(Common.checkGet(rs.getString("btnWrite")));
				entity.setSorted(Common.checkGet(rs.getString("sorted")));
				//entity.setHasChild(setCS(rs.getString("id")));
				list.add(entity);
			}
		    return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return null;
	}	
	
	public ArrayList getTreeEntry(String organID, String groupID) {
		ArrayList list=new ArrayList();
		String sql="select a.* from SYSAP_DTREE a, SYSAP_AUTHORITY b where b.groupid=" + Common.sqlChar(groupID) + " and b.organid = " + Common.sqlChar(organID) + " and a.id=b.progid order by a.sorted, a.pid, a.id";
		Database db = new Database();		  
		try{
			ResultSet rs = db.querySQL(sql);
			while(rs.next()){
				TreeEntry entity=new TreeEntry();
				entity.setId(Common.checkGet(rs.getString("id")));
				entity.setPid(Common.checkGet(rs.getString("pid")));
				entity.setName(Common.checkGet(rs.getString("name")));
				entity.setUrl(Common.checkGet(rs.getString("url")));
				entity.setTitle(Common.checkGet(rs.getString("title")));
				entity.setTarget(Common.checkGet(rs.getString("target")));
				entity.setIcon(Common.checkGet(rs.getString("icon")));
				entity.setIconOpen(Common.checkGet(rs.getString("iconOpen")));
				entity.setOpened(Common.checkGet(rs.getString("opened")));
				entity.setBtnRead(Common.checkGet(rs.getString("btnRead")));
				entity.setBtnWrite(Common.checkGet(rs.getString("btnWrite")));
				entity.setSorted(Common.checkGet(rs.getString("sorted")));
				//entity.setHasChild(setCS(rs.getString("id")));
				list.add(entity);
			}
		    return list;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		return null;
	}		
	
	public String setCS(String s) {
		Database db = new Database();
		try {
			ResultSet rs = db.querySQL("select count(*) as num from SYSAP_DTREE where pid="+Common.sqlChar(s));
			if (rs.next() && rs.getInt("num")>0) return "Y";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.closeAll();
		}
		return "N";
	}

}
