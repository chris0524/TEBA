package soa.model.res.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import soa.model.res.ActXML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ActXML")
public class GetOrgInfoActXML extends ActXML {
	private static XStream xs = createXStream(GetOrgInfoActXML.class);
	
	public RsInfo RsInfo;

	public static class RsInfo {
		public Org Org;
		
		@XStreamAlias("Org")
		public static class Org {
			public List<User> Users;
			
			public String nscuEmail;
			public String govCode;
			public String nscuCode2;
			public String childcount;
			public String modifyDate;
			public String nscuCode1;
			public String entryId;
			public String deptNo;
			public String nscuCode;
			public String oid;
			public String parentPath;
			public String nscuAddr;
			public String orglevel;
			public String nscuDesc;
			public String isVirtual;
			public String nscuTel;
			public String entryDate;
			public String sortBy;
			public String orgNscuId;
			public String nscuAbbr;
			public String modifyId;
			public String faxNo;
			public String active;
			public String OrgCatalog;
			public String OrgParentPath;
			
			@XStreamAlias("User")
			public static class User{
				public String userType;
				public String entryId;
				public String userId;
				public String userName;
				public String activeDate;
				public String entryDate;
				public String userEng;
				public String active;
				public String idno;
				public String mobile;
				public String userEmail;
				public String graph ;
				public String userFax;
				public String userDesc;
				public String orgUserId;
				public String userTel;
				public String userDuty;				
			}
		}
	}

	public static GetOrgInfoActXML fromXml(String xml) {
		return (GetOrgInfoActXML) xs.fromXML(xml);
	}
	
	public static void main(String[] args) {
		String xml = getXml();
		GetOrgInfoActXML x = GetOrgInfoActXML.fromXml(xml);
		System.out.println(x);
	}

	private static String getXml() {
		try {
			return FileUtils.readFileToString(new File("D:/kangda/TCGH_/docs/20140403_SOA維護廠商凌網提供科技部SOA web service介接文件/取得組織架構_範例/02_新竹科學工業園區管理局_直屬人員列表.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
