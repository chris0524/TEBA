package soa.model;

public enum UserType {
	ZERO("0", "離職"),
	ONE("1", "會內人員"),
	TWO("2", "附屬單位"),
	THREE("3", "駐外人員"),
	FOUR("4", "委外人員"),
	FIVE("5", "工讀生"),
	SIX("6", "顧問"),
	SEVEN("7", "廠商"),
	EIGHT("8", "其他"),
	NINE("9", "公用");
	

	private String code;
	private String desc;
	
	UserType(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static UserType getByCode(String code){
		for(UserType ut: UserType.values()){
			if (ut.code.equals(code)) return ut;
		}
		
		return null;
	}

}
