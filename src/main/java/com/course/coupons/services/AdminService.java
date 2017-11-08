package com.course.coupons.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.coupons.beans.Company;
import com.project.coupons.beans.Customer;
import com.project.coupons.dao.DaoException;
import com.project.coupons.facade.AdminFacade;
import com.project.coupons.facade.ClientType;
import com.project.coupons.system.*;

import jdk.nashorn.internal.objects.annotations.Getter;

@Path("/admin") // change path to your liking
public class AdminService {
	
	@Context
	private HttpServletRequest request;
	private static final String FACADE_ATTR = "facade_attr";
	private String FACADE;
	private String USER ="admin";
	private final String  APP = "CouponSystem";
	private final String  LOGIN_FAILED = "Login Failed";
	private final String  LOGIN_SUCCEED = "Login Succeed";
	CouponSystem singletonCouponSystem = null;
	AdminFacade adminFacade = null;
	
	@GET
	@Path("/getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getAllCompanies() throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("getAllCompanies :" +adminFacade);
		return adminFacade.viewAllCompanies();
		
	}
	@POST
	@Path("/viewcompany")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes (MediaType.APPLICATION_JSON)
	public Company viewCompany(Company company) throws DaoException
	{
		adminFacade = getFacade();
		System.out.println("viewCompany :" +adminFacade);
		return adminFacade.viewCompany(company);
		
	}
	@POST
	@Path("findcompanyyname")
	@Produces(MediaType.APPLICATION_JSON)
	
	public Company findCompanyByName( String  companyName) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("findCompanyByName :" +adminFacade);
		return adminFacade.findCompanyByName(companyName);
		
	}
	
	@DELETE
	@Path("removecompany/{companyId}")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public void removeCompany(@PathParam("companyId") long  companyId) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("removeCompany :" +adminFacade);
		Company company = new Company();
		company.setID(companyId);
		adminFacade.removeCompany(company);
		
	}
	@POST
	@Path("addcompany")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public void addCompany(Company  company) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("addCompany :" +adminFacade);
		adminFacade.addCompany(company);
		
	}
	
	@PUT
	@Path("updatecompany")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCompany(Company  company) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("getallcompanies :" +adminFacade);
		adminFacade.updateCompany(company);
		return "Update completed";
		
	}
	
	
	@GET
	@Path("/getallcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers() throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("getAllCustomers :" +adminFacade);
		return adminFacade.viewAllCustomers();
	
	}
	
	
	@PUT
	@Path("updatecustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(Customer  customer) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("updateCustomer :" +adminFacade);
		adminFacade.updateCustomer(customer);
		return "Update completed";
	}
	@POST
	@Path("addcustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCustomer(Customer  customer) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("addCustomer :" +adminFacade);
		adminFacade.addCustomer(customer);
		return "Add customer completed";
	}
	@DELETE
	@Path("removecustomer/{customerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCustomer(@PathParam("customerId") long  customerId) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("removeCustomer :" +adminFacade);
		Customer customer = new Customer();
		customer.setID(customerId);
		adminFacade.removeCustomer(customer);
		System.out.println("removeCustomer completed");
		return "removeCustomer completed";
		
	}
	
	
	@GET
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(long id)
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		adminFacade = getFacade();
		System.out.println("getCustomer :" +adminFacade);
		return null;
	}
	
	@POST
	//@GET
	@Path("/login")
	@Produces(MediaType.TEXT_HTML)
	// TODO: this is not very secure
	public String login(@QueryParam("name")String username, 
			@QueryParam("password")String password)
	{
		String returnResult = LOGIN_SUCCEED;
		// do login
		 // returned from login operation
		//print the currnt jar path  class code base�s location:
		System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation());
		singletonCouponSystem = CouponSystem.getInstance();
		PasswordHashing genHashPassword = new PasswordHashing();
		String hashPassword = genHashPassword.generateSaltHash(password);
		HttpSession session = request.getSession();
		String loginStatus = LOGIN_FAILED;
		if (session.isNew())
		{
			session.setAttribute("APPNAME", APP);
			session.setAttribute("FACADE", ClientType.ADMINISTRATOR);
			session.setAttribute("USER", USER);
			//Set the session valid for 100 secs
			session.setMaxInactiveInterval(100);
			//give default value 
			
		}
		else
		{
			 loginStatus = (String)session.getAttribute("LOGIN");
		
		}
		//login again only if login failed last time
		if (loginStatus.equals(LOGIN_FAILED)){
		
			try {
				adminFacade = (AdminFacade) singletonCouponSystem.login(username, hashPassword,ClientType.ADMINISTRATOR );
				session.setAttribute("LOGIN", LOGIN_SUCCEED);
				session.setAttribute(FACADE_ATTR, adminFacade.getClass().toString());
				
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				returnResult = LOGIN_FAILED;	
			}
		}else{
			returnResult="session exsit AdminFacad:" + String.valueOf(session.getAttribute(FACADE_ATTR))+
					       " user: " + (String)session.getAttribute("USER") + " getId:"+ session.getId(); 
		}
		//System.out.println(singletonCouponSystem);
		// put the facade in the session!
		/*HttpSession session = request.getSession();
		if (!session.isNew()) { } //bug
		*/
		//session.setAttribute(FACADE_ATTR, adminFacade);
		System.out.println("username:"+username +" password:"+ password);
		
		return returnResult;
	}
	@GET
	@Path("/getloginas")
	@Produces(MediaType.TEXT_PLAIN)
	public String getLoginAs() throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		String loginAs= getLoginAsFromSession();
		System.out.println("getloginas :" +loginAs);
		return loginAs;
	}
	@GET
	@Path("/getusername")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUsername() throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		String username= getUsernameFromSession();
		System.out.println("getusername :" +username);
		return username;
	}
	
	private AdminFacade getFacade()
	{
		HttpSession session = request.getSession(); // Do not create new
		if (session == null){} // send error response - to login page
		return  (AdminFacade) session.getAttribute("facade");
	 }
	private String getLoginAsFromSession()
	{
		HttpSession session = request.getSession(); // Do not create new
		if (session == null){} // send error response - to login page
		return  (String) session.getAttribute("loginAs");
	 }
	private String getUsernameFromSession()
	{
		HttpSession session = request.getSession(); // Do not create new
		if (session == null){} // send error response - to login page
		return  (String) session.getAttribute("loginName");
	 }
	
}
