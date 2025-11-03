package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.owasp.esapi.ESAPI;

import util.User;

public class AuthenticateFilter implements Filter {

	public interface KEY {
		public String REQUEST_STARTTIME = "request_start_time";
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = null;
		HttpServletRequest myRequest = null;
		HttpServletResponse myResponse = null;

		if (request instanceof HttpServletRequest) {
			myRequest = (HttpServletRequest) request;
			session = myRequest.getSession();
		}
		if (response instanceof HttpServletResponse) {
			myResponse = (HttpServletResponse) response;
		}
		
		if (session != null) {
			if (this.isAuthenticated(session)) {
				chain.doFilter(request, response);
			} else {
				//清除Session
				this.clearHttpSession(session);
				//導向 no_session.jsp
				this.authenticateFail(myRequest,myResponse);
				return;
			}
		} else {
			//清除Session
			this.clearHttpSession(session);
			//導向 no_session.jsp
			this.authenticateFail(myRequest,myResponse);
			return;
		}
	}

	private boolean isAuthenticated(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null && !"".equals(user.getUserID())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 清除SESSION
	 * @param session
	 */
	private void clearHttpSession(HttpSession session) {
		if (null != session) session.invalidate();
	}

	/**
	 * 驗證失敗時，導向 首頁
	 */
	private void authenticateFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			ESAPI.httpUtilities().sendRedirect(response,request.getContextPath());
		} catch (Exception e) {
			//e.printStackTrace();
			//logger.error(e.getMessage(), e);				    
		}
	}

	public void destroy() {
		this.filterConfig = null;
	}

	private FilterConfig filterConfig = null;

	public FilterConfig getFilterConfig() {return filterConfig;}
	public void setFilterConfig(FilterConfig filterConfig) {this.filterConfig = filterConfig;}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}