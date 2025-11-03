package soa.model;

public class SoaUser {
	public String userId;//人員登入帳號
	public String userName;//人員中文姓名
	public String userTelH;//人員家中電話聯絡資訊
	public String userTelExt;//人員分機
	public String modifyDate;
	public String userEmail;//人員的電子郵件位址
	public String userFax;
	public String userDutyOfficial;
	public String bid;
	public String note;
	public String needEmail;
	public String onservice;
	public String pid;
	public String pId_bId;
	public UserType userType;//人員的使用者類別
	public String entryId;//該人員資料的建立者
	public String mboxStatus;
	public String ondutyStatus;
	public String gender;//性別
	public String sn;
	public String entryDate;
	public String userDesc;//人員描述
	public String tryLoginCount;//人員嘗試登入錯誤次數
	public String userAddr;//住址
	public String orgUserId;//人員流水號
	public String userTel;//人員聯絡電話
	public String userEng;//人員英文姓名
	public String modifyId;//修改者的ID
	public String active;//人員是否啟用
	public String idno;//身分證字號
	public String titleId;//保留欄位2
	public String userDuty;//職稱
	public String mobile;//手機
	public String mboxSize;
	public String orgCode;//人員所屬單位代碼
	public String serialNumber;//人員憑證號碼
	public String govCode;//機關別(M=本部,A=核安署,N=竹科,C=中科,S=南科,R=偵測分署)
	public String orgMasterId;//直屬單位主管帳號(ex：userid)
	public String orgMasterame;//直屬單位主管姓名(ex：userid)
	public String orgViceId;//直屬單位副主管帳號
	public String orgViceName;//直屬單位副主管姓名
	public String orgCatalog;//階層關係(ex：MOST/mostI/Q0/Q1
	public String orgParentPath;//上層節點(ex：1/3/78)
	public String birth;//生日
	
	
	public static SoaUser convertBy(
			soa.model.res.sso.GetToken2ActXML.RsInfo.User rsUser) {
		
		SoaUser soaUser = new SoaUser();
		
		soaUser.userName = rsUser.CName;
		soaUser.userEmail = rsUser.Email;
		soaUser.gender = rsUser.Gender;
		soaUser.govCode = rsUser.GovCode;		
		soaUser.idno = rsUser.Idno;
		soaUser.sn = rsUser.Sn;
		soaUser.userId = rsUser.UserId;
		
		return soaUser;
	}
}
