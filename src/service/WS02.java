package service; 

public interface WS02 {
	
	//暫時保留，待新版上線之後，將此功能拔除
	public  String Query_list(String enterorg,String useunit,String place1,String keepunit,String keeper,String userno,String propertyno,String Chktype,String AuthCode) throws Exception;
		
	public  String Query_list1(String enterorg,String useunit,String place1,String keepunit,String keeper,String userno,String propertyno,String Chktype,String PageIndex,String AuthCode) throws Exception;

}
