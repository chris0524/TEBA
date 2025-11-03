package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzFilter implements Filter {
	private Scheduler scheduler;
	public void init(FilterConfig config) throws ServletException {
		Boolean enabled = Boolean.valueOf(config.getInitParameter("enabled"));
		if (Boolean.TRUE != enabled) return;
		
		String properties = config.getInitParameter("properties");
		SchedulerFactory factory = null;
		try {
			factory = new StdSchedulerFactory(properties);
			this.scheduler = factory.getScheduler();
			this.scheduler.start();
		} catch (SchedulerException e) {
			throw new ServletException(e);
		}				
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	public void destroy() {
		if (this.scheduler != null)
			try {
				this.scheduler.shutdown();
			} catch (SchedulerException e) {
				throw new RuntimeException(e);
			}
	}
}
