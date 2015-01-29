package managedbean;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * Security filter to avoid access to protected USER views without authentication!!
 * 
 * Thanks to: http://stackoverflow.com/questions/1470591/basic-security-in-jsf
 * 
 */
public class AuthenticationUserFilter implements Filter {
	@SuppressWarnings("unused")
	private FilterConfig config;
	  
	  
	  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
	    
		  //System.out.println("FILTER:" + ((HttpServletRequest) req).getSession().getAttribute("superadmin"));
		  
		  if (((HttpServletRequest) req).getSession().getAttribute("user") == null) {
	      ((HttpServletResponse) resp).sendRedirect("welcomeView.xhtml");
	    } else {
	      chain.doFilter(req, resp);
	    }
	  }
	
	  public void init(FilterConfig config) throws ServletException {
	    this.config = config;
	  }
	
	  public void destroy() {
	    config = null;
	  }
}
