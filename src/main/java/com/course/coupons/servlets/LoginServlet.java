package com.course.coupons.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import com.project.coupons.dao.DaoException;
import com.project.coupons.facade.AdminFacade;
import com.project.coupons.facade.ClientType;
import com.project.coupons.facade.CompanyFacade;
import com.project.coupons.facade.CouponClientFacade;
import com.project.coupons.facade.CustomerFacade;
import com.project.coupons.system.CouponSystem;
import com.project.coupons.system.PasswordHashing;
import com.course.coupons.services.Constans;

/**
 * Servlet implementation class Login
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		PrintWriter output = response.getWriter();
		output.append("Served at: ").append(request.getContextPath());
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String loginAs = request.getParameter("loginAs");
		System.out.println(username);
		System.out.println(password);
		System.out.println(loginAs);
		CouponSystem singletonCouponSystem = CouponSystem.getInstance();
		System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
		
		PasswordHashing genHashPassword = new PasswordHashing();
		String hashPassword = genHashPassword.generateSaltHash(password);
		CouponClientFacade facade = null;
		try {
			if (loginAs.equalsIgnoreCase("Administrator")){
				facade = (AdminFacade) singletonCouponSystem.login(username, hashPassword,ClientType.ADMINISTRATOR );	
			}
			if (loginAs.equalsIgnoreCase("Company")){
				facade = (CompanyFacade) singletonCouponSystem.login(username, hashPassword,ClientType.COMPANY );	
			}
			if (loginAs.equalsIgnoreCase("Customer")){
				facade = (CustomerFacade) singletonCouponSystem.login(username, hashPassword,ClientType.CUSTOMER );	
			}
			
		} catch (DaoException e) {
			//login failed
			e.printStackTrace();
			// a flag value of login failed from  servlet
			request.setAttribute("loginResult", false);
			response.sendRedirect(Constans.LOGINPAGE);
			return ;
		}
		System.out.println(facade);
        
		// place the facade on the Session
		request.getSession().setAttribute("facade", facade);
		request.getSession().setAttribute("loginName", username);
		request.getSession().setAttribute("loginAs", loginAs);
		request.getSession().setMaxInactiveInterval(120*60); // set timeout of 120 minutes
		//facade =(AdminFacade) request.getSession().getAttribute("facade");
		System.out.println(facade);
		// now you can redirect to the correct page (if the login was successful)
		response.sendRedirect(Constans.HOMEPAGE);
		
		
	}

}
