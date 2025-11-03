package sys.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.request.RequestContextListener;

public class MyServletContextListener extends RequestContextListener implements ServletContextListener {

	public static final String NL = "\r\n";
	
	private Logger logger = LogManager.getLogger(this.getClass());
    
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext servletContext = sce.getServletContext();
		MyServletContext.getInstance().setServletContext(servletContext);
		
		String message = NL;
		message += "##############################################################" + NL;
		message += "############### " + servletContext.getServletContextName() + " Started!" + NL;
		message += "##############################################################" + NL;
//		System.out.println(message);
		logger.info(message);
		
		//建立網路磁碟機，可以改到設定檔中
		File efile = new File("Z:\\");
		if (!efile.exists()) {
			try {
				File bfile = new File("/z.bat");    //<= 啟動Tomcat的根目錄
				if(bfile.exists()){
					logger.info("建立Z Drive");
					Process process = Runtime.getRuntime().exec("/z.bat");    //<= 啟動Tomcat的根目錄
					//z.bat
					//net use z: \\172.16.30.55\專案工作區\property PASSWORD /user:kduser
					Thread.sleep(2000);
					logger.info("建立Z Drive 完成");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			logger.info("已有Z Drive");
		}
		efile = null;
	}
	
	
	public void contextDestroyed(ServletContextEvent sce) {
		
		String message = NL;
		message += "##############################################################" + NL;
		message += "############### " + MyServletContext.getInstance().getServletContext().getServletContextName() + " Shutdown!" + NL;
		message += "##############################################################" + NL;
//		System.out.println(message);
		logger.info(message);
		
		MyServletContext.getInstance().setServletContext(null);
		
	}	 

	
}
