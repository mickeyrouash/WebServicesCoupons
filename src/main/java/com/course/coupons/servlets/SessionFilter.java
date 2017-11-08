package com.course.coupons.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.coupons.facade.AdminFacade;
import com.project.coupons.facade.CompanyFacade;
import com.project.coupons.facade.CustomerFacade;

public class SessionFilter implements Filter {

	private ArrayList<String> urlList;
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		boolean allowedRequest = false;
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        
        if(urlList.contains(url)) {
       		allowedRequest = true;
		}
        HttpSession session = request.getSession(false);
        if  ( session == null ) {
        	response.sendRedirect("login/login.html");
        	//return;
        }else{
        	 Object facade = request.getSession(false).getAttribute("facade"); 
     		if (facade  instanceof AdminFacade  )
     		 {
     		    chain.doFilter(req, res);
     		 }else{
     			 response.sendRedirect("login/login.html");
     		 }
        }
        
       
		
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//		String url = request.getServletPath();
//		boolean allowedRequest = false; 
//		
//		if(urlList.contains(url)) {
//			allowedRequest = true;
//		}
//			
//		if (!allowedRequest) {
//			// don't create if it doesn't exist
//			HttpSession session = request.getSession(false);
//			if (null == session ) {
//				response.sendRedirect("index.html");
//			}
//		}
//		
//		chain.doFilter(req, res);
	}

	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");

		urlList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			urlList.add(token.nextToken());

		}
	}
}