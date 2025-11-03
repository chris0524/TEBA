package sys.ca;

import java.sql.ResultSet;

import util.Database;
import util.QueryBean;

public class SYSCA014F extends QueryBean{

	private String enterOrg;
	private String enterOrgName;
	private String ownership;
	private String propertyKind;
	private String proofKindPT;
	private String proofKindNE;
	private String fundDeprMethod;
	private String escrowDeprMethod;
	private String officialDeprMethod;
	private String deferredDeprMethod;
	private String localDeprMethod;
	private String proofNotes;
	private String reachedYearCalMethod;
	private String notes;
	private String editID;
	private String editDate;
	private String editTime;

	public String getEnterOrg() {return checkGet(enterOrg);}
	public void setEnterOrg(String enterOrg) {this.enterOrg = checkSet(enterOrg);}
	public String getEnterOrgName() {return checkGet(enterOrgName);}
	public void setEnterOrgName(String enterOrgName) {this.enterOrgName = checkSet(enterOrgName);}
	public String getOwnership() {return checkGet(ownership);}
	public void setOwnership(String ownership) {this.ownership = checkSet(ownership);}
	public String getPropertyKind() {return checkGet(propertyKind);}
	public void setPropertyKind(String propertyKind) {this.propertyKind = checkSet(propertyKind);}
	public String getProofKindPT() {return checkGet(proofKindPT);}
	public void setProofKindPT(String proofKindPT) {this.proofKindPT = checkSet(proofKindPT);}
	public String getProofKindNE() {return checkGet(proofKindNE);}
	public void setProofKindNE(String proofKindNE) {this.proofKindNE = checkSet(proofKindNE);}
	public String getFundDeprMethod() {return checkGet(fundDeprMethod);}
	public void setFundDeprMethod(String fundDeprMethod) {this.fundDeprMethod = checkSet(fundDeprMethod);}
	public String getEscrowDeprMethod() {return checkGet(escrowDeprMethod);}
	public void setEscrowDeprMethod(String escrowDeprMethod) {this.escrowDeprMethod = checkSet(escrowDeprMethod);}
	public String getOfficialDeprMethod() {return checkGet(officialDeprMethod);}
	public void setOfficialDeprMethod(String officialDeprMethod) {this.officialDeprMethod = checkSet(officialDeprMethod);}
	public String getDeferredDeprMethod() {return checkGet(deferredDeprMethod);}
	public void setDeferredDeprMethod(String deferredDeprMethod) {this.deferredDeprMethod = checkSet(deferredDeprMethod);}
	public String getLocalDeprMethod() {return checkGet(localDeprMethod);}
	public void setLocalDeprMethod(String localDeprMethod) {this.localDeprMethod = checkSet(localDeprMethod);}
	public String getProofNotes() {return checkGet(proofNotes);}
	public void setProofNotes(String proofNotes) {this.proofNotes = checkSet(proofNotes);}
	public String getReachedYearCalMethod() { return checkGet(reachedYearCalMethod); }
	public void setReachedYearCalMethod(String reachedYearCalMethod) { this.reachedYearCalMethod = checkSet(reachedYearCalMethod); }
	public String getNotes() {return checkGet(notes);}
	public void setNotes(String notes) {this.notes = checkSet(notes);}
	public String getEditID() {return checkGet(editID);}
	public void setEditID(String editID) {this.editID = checkSet(editID);}
	public String getEditDate() {return checkGet(editDate);}
	public void setEditDate(String editDate) {this.editDate = checkSet(editDate);}
	public String getEditTime() {return checkGet(editTime);}
	public void setEditTime(String editTime) {this.editTime = checkSet(editTime);}
	
	
	public void queryData(){
		
		String sql = "select" +
							" enterOrg, " +
							" (select organsname from SYSCA_ORGAN z where z.organid = enterOrg) as enterOrgName, " +
							" ownership, " +
							" propertyKind, " +
							" proofKindPT, " +
							" proofKindNE, " +
							" fundDeprMethod, " +
							" escrowDeprMethod, " +
							" officialDeprMethod, " +
							" deferredDeprMethod, " +
							" localDeprMethod, " +
							" proofNotes, " +
							" reachedyearcalmethod" +
						" from SYSCA_ORGARGU" + 
						" where enterorg = '" + getEnterOrg() + "'";
		
		Database db = null;
		ResultSet rs = null;
		try{
			db = new Database();			
			rs = db.querySQL(sql);
			while(rs.next()){
				this.setEnterOrg(rs.getString("enterOrg"));
				this.setEnterOrgName(rs.getString("enterOrgName"));
				this.setOwnership(rs.getString("ownership"));
				this.setPropertyKind(rs.getString("propertyKind"));
				this.setProofKindPT(rs.getString("proofKindPT"));
				this.setProofKindNE(rs.getString("proofKindNE"));
				this.setFundDeprMethod(rs.getString("fundDeprMethod"));
				this.setEscrowDeprMethod(rs.getString("escrowDeprMethod"));
				this.setOfficialDeprMethod(rs.getString("officialDeprMethod"));
				this.setDeferredDeprMethod(rs.getString("deferredDeprMethod"));
				this.setLocalDeprMethod(rs.getString("localDeprMethod"));
				this.setProofNotes(rs.getString("proofNotes"));	
				this.setReachedYearCalMethod(rs.getString("reachedyearcalmethod"));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
	}

	public void updateDate(){
		String sql = "update SYSCA_ORGARGU set" +
						" ownership = '" + getOwnership() + "'," +
						" propertyKind = '" + getPropertyKind() + "'," +
						" proofKindPT = '" + getProofKindPT() + "'," +
						" proofKindNE = '" + getProofKindNE() + "'," +
						" fundDeprMethod = '" + getFundDeprMethod() + "'," +
						" escrowDeprMethod = '" + getEscrowDeprMethod() + "'," +
						" officialDeprMethod = '" + getOfficialDeprMethod() + "'," +
						" deferredDeprMethod = '" + getDeferredDeprMethod() + "'," +
						" localDeprMethod = '" + getLocalDeprMethod() + "'," +
						" proofNotes = '" + getProofNotes() + "'," +
						" reachedyearcalmethod = '" + this.getReachedYearCalMethod() + "'," +
						" notes = '" + getNotes() + "'," +
						" editID = '" + getEditID() + "'," +
						" editDate = '" + getEditDate() + "'," +
						" editTime = '" + getEditTime() + "'" +
					" where enterOrg = '" + getEnterOrg() + "'";
		
		Database db = null;
		try{
			db = new Database();
			db.exeSQL(sql);
			this.setErrorMsg("修改完成");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			db.closeAll();
		}
		
	}

}


