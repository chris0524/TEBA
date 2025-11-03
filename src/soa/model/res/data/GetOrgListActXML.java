package soa.model.res.data;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import soa.model.res.ActXML;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("ActXML")
public class GetOrgListActXML extends ActXML {
	private static XStream xs = createXStream(GetOrgListActXML.class);
	
	public RsInfo RsInfo;

	public static class RsInfo {
		public List<Org> Orgs;
		
		@XStreamAlias("Org")
		public static class Org {
			public List<Org> Orgs;
			public String docDept;
			public String nscuEmail;
			public String govCode;
			public String nscuCode2;
			public String syncAD;
			public String childcount;
			public String modifyDate;
			public String nscuCode1;
			public String entryId;
			public String deptNo;
			public String nscuCode;
			public String oid;
			public String parentPath;
			public String orglevel;
			public String nscuDesc;
			public String isVirtual;
			public String nscuTel;
			public String entryDate;
			public String sortBy;
			public String orgNscuId;
			public String modifyId;
			public String faxNo;
			public String active;
			public String docCode;
			
			@XStreamAsAttribute
			public String Level;
			@XStreamAsAttribute
			public String ParentNscuId;
		}
	}

	public static GetOrgListActXML fromXml(String xml) {
		return (GetOrgListActXML) xs.fromXML(xml);
	}
	
	public static void main(String[] args) {
		String xml = getXml();
		GetOrgListActXML x = GetOrgListActXML.fromXml(xml);
		System.out.println(x);
	}

	private static String getXml() {
		try {
			return FileUtils.readFileToString(new File("D:/kangda/TCGH_/docs/20140403_SOA維護廠商凌網提供科技部SOA web service介接文件/取得組織架構_範例/01_新竹科學工業園區管理局-組織清單.txt"), "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
