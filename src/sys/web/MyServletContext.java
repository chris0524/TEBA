package sys.web;

import javax.servlet.ServletContext;

public class MyServletContext {

	public static MyServletContext instance;
	
	public static MyServletContext getInstance() {
		if(instance==null) {
			instance = new MyServletContext();
		}
		return instance;
	}
	
	String contextPath;
	public String getContextPath() {
		if (contextPath!=null) return contextPath;
		return servletContext.getContextPath();
	}
	public void setContextPath(String contextPath) {this.contextPath = contextPath;}

	private ServletContext servletContext;
	public ServletContext getServletContext() { return servletContext; }
	public void setServletContext(ServletContext servletContext) { this.servletContext = servletContext; }
	
}
