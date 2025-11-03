package util.report;

import java.sql.Connection;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import util.Common;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class SQLReportGenerator extends ReportGenerator {

	
	static private Logger logger = Logger.getLogger(SQLReportGenerator.class);
	
	protected Connection conn;
	protected String dsQuerySQL;
	protected java.util.List dsQuerySQLList;
	protected boolean useDataSource;
	
	public SQLReportGenerator(SQLReportEnvironment env){
		super(env);
	}
	
	private void loadEnvironment(){
		useDataSource = ((SQLReportEnvironment)env).isUseDataSource();
		conn = ((SQLReportEnvironment)env).getConnection();
		dsQuerySQL = ((SQLReportEnvironment)env).getDataSourceSQL();
		dsQuerySQLList = ((SQLReportEnvironment)env).getDataSourceSQLList();
		StringBuffer msg = new StringBuffer();
		msg.append("Environment[");
		msg.append("Connection:").append(conn).append(",");
		msg.append("UseDataSource:").append(useDataSource).append(",");
		msg.append("DataSourceSQL:").append(dsQuerySQL).append(",");
		msg.append("]");
		logger.debug(msg.toString());		
	}
		
	protected JasperPrint getJasperPrint(java.util.Map parms) throws Exception{
		loadEnvironment();
		logger.debug("Parameter:"+parms.toString());
		if(jasperFile==null){
			throw new IllegalArgumentException("JasperFile is null");
		}		
		if(conn==null){	
			throw new IllegalArgumentException("Connection is null");
		}		
		if(useDataSource && dsQuerySQL==null){
			throw new IllegalArgumentException("Query SQL is null");
		}		
		JasperPrint print;		
		if(useDataSource){
			JRDataSource ds = getDataSource();
			parms.put(JRParameter.REPORT_DATA_SOURCE ,ds);
			if (Common.getFileExtension(jasperFile).equals("jrxml")) {
				print = JasperFillManager.fillReport(compileAndFillReport(jasperFile),parms,ds);
			} else {
				print = JasperFillManager.fillReport(jasperFile,parms,ds);
			}
		}else{
			parms.put(JRParameter.REPORT_CONNECTION ,conn);
			if (Common.getFileExtension(jasperFile).equals("jrxml")) {
				print = JasperFillManager.fillReport(compileAndFillReport(jasperFile),parms,conn);
			} else {
				print = JasperFillManager.fillReport(jasperFile,parms,conn);
			}			
		}
		return print;
		
	}
	protected java.util.List getJasperPrintList(java.util.Map parms) throws Exception {
		loadEnvironment();
		if(conn==null){	throw new IllegalArgumentException("Connection is null");	}		
		if(useDataSource && (dsQuerySQL==null || dsQuerySQLList==null)){throw new IllegalArgumentException("Query SQL is null");	}		
		
		java.util.List list = new java.util.ArrayList();
		if (jasperFileList!=null && jasperFileList.size()>0) {
			for (int i = 0; i < jasperFileList.size(); i++) {
				JasperPrint print;	
				String jasperFilePath = (String)jasperFileList.get(i);				
				java.util.Map p = parms;
				if (jasperParamList!=null && jasperParamList.size()>i) {
					p = (java.util.Map) jasperParamList.get(i);
					if (p!=null) {
						for (Object key : parms.keySet()) {
							if (p.get(key)==null) p.put(key, parms.get(key));
						}						
					} else {
						p = parms;
					}
				}				
				if(useDataSource && dsQuerySQLList!=null && dsQuerySQLList.size()>0){
					String sql = (String)dsQuerySQLList.get(i);
					JRDataSource ds = getDataSource(sql);
					parms.put(JRParameter.REPORT_DATA_SOURCE ,ds);
					
					if (Common.getFileExtension(jasperFile).equals("jrxml")) {
						print = JasperFillManager.fillReport(compileAndFillReport(jasperFilePath),p,ds);
					} else {
						print = JasperFillManager.fillReport(jasperFilePath,p,ds);
					}
					
				}else{
					parms.put(JRParameter.REPORT_CONNECTION ,conn);
					if (Common.getFileExtension(jasperFile).equals("jrxml")) {
						print = JasperFillManager.fillReport(compileAndFillReport(jasperFilePath),p,conn);
					} else {
						print = JasperFillManager.fillReport(jasperFilePath,p,conn);
					}
					
				}
				list.add(print);
			}			
		} else {
			JasperPrint print;		
			if(useDataSource){
				JRDataSource ds = getDataSource();
				parms.put(JRParameter.REPORT_DATA_SOURCE ,ds);
				if (Common.getFileExtension(jasperFile).equals("jrxml")) {
					print = JasperFillManager.fillReport(compileAndFillReport(jasperFile),parms,ds);
				} else {
					print = JasperFillManager.fillReport(jasperFile,parms,ds);
				}
				
			}else{
				parms.put(JRParameter.REPORT_CONNECTION ,conn);
				if (Common.getFileExtension(jasperFile).equals("jrxml")) {
					print = JasperFillManager.fillReport(compileAndFillReport(jasperFile),parms,conn);
				} else {
					print = JasperFillManager.fillReport(jasperFile,parms,conn);
				}				
			}	
			list.add(print);
		}
		return list;
	}	
	
	protected JRDataSource getDataSource() throws Exception{
		return getDataSource(dsQuerySQL);
	}
	protected JRDataSource getDataSource(String sql) throws Exception{
		ResultSet rs = conn.createStatement().executeQuery(sql);
		return new JRResultSetDataSource(rs);
		
	}	

}
