package com.fx.mobile.user.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter{
    private String cross="";
    private String cross1="";
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest)servletrequest;
		HttpServletResponse hsp = (HttpServletResponse)servletresponse;
	    if(!cross.equals(hsr.getServletPath()) && !cross1.equals(hsr.getServletPath()))
	    {
	    	if(hsr.getSession().getAttribute("username") == null || "".equals(hsr.getSession().getAttribute("username")))
	    	{
	    		//hsr.getRequestDispatcher("/login.html").forward(servletrequest, servletresponse);
	    		hsp.sendRedirect(hsr.getContextPath()+"/login.html");
	    		return;
	    	}
	    }
		filterchain.doFilter(servletrequest, servletresponse);
		
	}

	public void init(FilterConfig filterconfig) throws ServletException {
		cross = filterconfig.getInitParameter("cross");
	    cross1 = filterconfig.getInitParameter("cross1");
	}


	
}
