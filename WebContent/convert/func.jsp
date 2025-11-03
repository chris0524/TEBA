<%!
public String fullLeftZero(String s, int i) {
	if(s == null) {
		return fullLeftZero("0", i);
	} 
	if(s.length() >= i) {
        return s;
    } else {
        return fullLeftZero("0"+s, i);
    }
}
public String cutString(String s, int pos_s, int pos_e) {
	if (s == null || s.equals(""))
		return "";

	if (s.length() <= pos_e)
		return s.substring(pos_s, s.length());
	else
		return s.substring(pos_s, pos_e);
}
public void dropTable(util.Database db, String table) throws Exception {
	String tempSrc = db.getLookupField("select count(0) from all_tables where table_name = upper('"+table+"')");
	if(tempSrc.equals("1")) {
		db.exeSQL("DROP TABLE "+ table);
	}	
}
public boolean isValidDate(String date) {
	boolean rtnValue = false;
	try {
		int y = Integer.parseInt(date.substring(0,3));
		int m = Integer.parseInt(date.substring(3,5));
		int d = Integer.parseInt(date.substring(5,7));
		if((y>=1) && (m>=1&&m<=12) && (d>=1&&d<=31)) {
			rtnValue = true;
		}
	} catch(Exception e) {
		return false;
	}
	return rtnValue;
}
public int toInt(String s) {
	try {
		if (s == null || s.trim().equals(""))
			return 0;
		else
			return Integer.parseInt(s);
	} catch (Exception e) {
		e.printStackTrace();
		return 0;
	}
}
public void addcolumn(util.Database db, String table, String column, String type) throws Exception {
	String tempSrc = db.getLookupField("SELECT COUNT(0) FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = UPPER('"+table+"') AND COLUMN_NAME = UPPER('"+column+"') ");
	if(tempSrc.equals("0")) {
		db.exeSQL("ALTER TABLE "+table+" ADD "+column+" "+type);
	}	
}
public String genProgramLink(util.Database db, String table, String program) {
	String rtnStr = "";
	int count = toInt(db.getLookupField("select count(0) from "+table)) / 10000 +1;
    for(int i = 0; i < count; i++) {
	    int recNo_s = (i * 10000) + 1;
        int recNo_e = recNo_s + 9999;
        String newLine = ((i+1)%5==0)?"<br>":"";
        rtnStr +="<a href='#'onclick=\"go('"+program+"?recNo_s="+recNo_s+"&recNo_e="+recNo_e+"')\">("+(i+1)+")</a>"+ newLine +"\n";
 	}	
    return rtnStr+"<br>";
}
%>
