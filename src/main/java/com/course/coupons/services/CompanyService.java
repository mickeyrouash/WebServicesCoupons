package com.course.coupons.services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.coupons.beans.Company;
import com.project.coupons.beans.Coupon;
import com.project.coupons.beans.Customer;
import com.project.coupons.dao.DaoException;
import com.project.coupons.facade.AdminFacade;
import com.project.coupons.facade.ClientType;
import com.project.coupons.facade.CompanyFacade;
import com.project.coupons.facade.CouponType;
import com.project.coupons.system.CouponSystem;

@Path("/company") // change path to your liking
public class CompanyService {
	
	@Context
	private HttpServletRequest request;
	private static final String FACADE_ATTR = "facade_attr";
	private String FACADE;
	//private String USER ="admin";
	private final String  APP = "CouponSystem";
	private final String  LOGIN_FAILED = "Login Failed";
	private final String  LOGIN_SUCCEED = "Login Succeed";
	CouponSystem singletonCouponSystem = null;
	CompanyFacade companyFacade = null;
	
	
	// TODO: Find if JAXB knows how to marshal a list to json. 
	// if not - find another solution (e.g. return Response)

	
	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Response  getAllCoupons()  
	{
		 
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("getallcoupons :" +companyFacade);
		List<Coupon> coupons = null;
		Response r;
		try {
			coupons = companyFacade.getCoupons();
		} catch (DaoException e) {
			r =Response.ok(e.getMessage()).header("someHeader", "someHeaderValue").status(501).build();
			e.printStackTrace();
			System.out.println(r);
			return r;
		}
		GenericEntity<List<Coupon>> genericEntity = new GenericEntity<List<Coupon>>(coupons) {
	      };//needs empty body to preserve generic type
	       r = Response.ok(genericEntity)
	                           .header("someHeader", "someHeaderValue").status(200)
	                           .build();
		return r;
		
	}
	

	@GET
	@Path("/getallcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers() throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		System.out.println("getAllCustomers :" +companyFacade);
		return companyFacade.viewAllCustomers();
	
	}
	
	
	@GET
	@Path("getcouponbytype/{couponType}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes (MediaType.TEXT_PLAIN)
	public List <Coupon>  getCouponByType(@PathParam("couponType") String couponType) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		return companyFacade.getCouponsByType(CouponType.valueOf(couponType));
	}
	
	@GET
	@Path("getcouponbydate/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes (MediaType.TEXT_PLAIN)
	public List <Coupon>  getCouponByDate(@PathParam("date") String date) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		return companyFacade.getCouponsByDate(date);
	}
	@GET
	@Path("getcouponbyprice/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Consumes (MediaType.TEXT_PLAIN)
	public List <Coupon>  getCouponByDate(@PathParam("price") double price) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		return companyFacade.getCouponsByPrice(price);
	}
	@POST
	@Path("addcoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	//@Consumes (MediaType.TEXT_PLAIN)
	public void  addCoupon(Coupon  coupon) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		companyFacade.addCoupon(coupon);
		
	}
	@PUT
	@Path("updatecoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCoupon(Coupon  coupon) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		companyFacade.updateCoupon(coupon);
		return "Update completed";
		
	}
	@DELETE
	@Path("removecoupon/{couponId}")
	@Consumes(MediaType.APPLICATION_JSON)
	
	public void removeCoupon(@PathParam("couponId") long  couponId) throws DaoException
	{
		CompanyFacade companyFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("removeCoupon :" +companyFacade);
		Coupon coupon = new Coupon();
		coupon.setID(couponId);
		companyFacade.deleteCoupon(coupon);
		
	}
	@POST
	@Path("addcustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addCustomer(Customer  customer) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		CompanyFacade companyFacade = getFacade();
		System.out.println("addCustomer :" +companyFacade);
		companyFacade.addCustomer(customer);
		return "Add customer completed";
	}
	@PUT
	@Path("updatecustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(Customer  customer) throws DaoException
	{
		//adminFacade = (AdminFacade) singletonCouponSystem.login("admin", "ae10ba287cb1b45b7d605e6545848f746316d544",ClientType.ADMINISTRATOR );
		CompanyFacade companyFacade  = getFacade();
		System.out.println("updateCustomer :" +companyFacade);
		companyFacade.updateCustomer(customer);
		return "Update completed";
	}
	private CompanyFacade getFacade()
	{
		HttpSession session = request.getSession(); // Do not create new
		if (session == null){} // send error response - to login page
		return  (CompanyFacade) session.getAttribute("facade");
	 }
	
	}
