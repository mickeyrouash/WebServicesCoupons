package com.course.coupons.services;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.project.coupons.beans.Coupon;
import com.project.coupons.dao.DaoException;
import com.project.coupons.facade.ClientType;
import com.project.coupons.facade.CompanyFacade;
import com.project.coupons.facade.CouponType;
import com.project.coupons.facade.CustomerFacade;
import com.project.coupons.system.CouponSystem;

@Path("/customer") // change path to your liking
public class CustomerService {
	
	@Context
	private HttpServletRequest request;
	private static final String FACADE_ATTR = "facade_attr";
	private String FACADE;
	//private String USER ="admin";
	private final String  APP = "CouponSystem";
	private final String  LOGIN_FAILED = "Login Failed";
	private final String  LOGIN_SUCCEED = "Login Succeed";
	CouponSystem singletonCouponSystem = null;
	CustomerFacade customerFacade = null;
	
	
	
	// TODO: Find if JAXB knows how to marshal a list to json. 
	// if not - find another solution (e.g. return Response)

	
	@POST
	@Path("buycoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces (MediaType.TEXT_PLAIN)
	public Response buyCoupon(Coupon coupon) 
	{
		Response r=null;
		CustomerFacade customerFacade = getFacade();
		System.out.println("buycoupon :" +customerFacade);
		try {
			customerFacade.buyCoupon(coupon);
		} catch (DaoException e) {
			//r =Response.ok(e.getMessage()).header("someHeader", "someHeaderValue").status(501).build();
			r =Response.serverError().entity(e.getMessage()).status(501).build();
			e.printStackTrace();
			System.out.println(r);
			return r;
		}
		return r ;
		
	}
	
	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getAllCoupons() throws DaoException
	{
		CustomerFacade customerFacade = getFacade();
		//companyFacade = (CompanyFacade) singletonCouponSystem.login("company1003", "f2ad8a4b314be24732d1a8219174cc8043f20ef6",ClientType.COMPANY );
		System.out.println("getallcoupons :" +customerFacade);
		return customerFacade.viewAllCoupons();
		
	}
	
	@GET
	@Path("getbuyshistory")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getBuysHistoryCoupons() throws DaoException
	{
		CustomerFacade customerFacade = getFacade();
		//singletonCouponSystem = CouponSystem.getInstance();
		//customerFacade = (CustomerFacade) singletonCouponSystem.login("customer3003", "e41801fdc55a7bf9b1ce8c2d268e0441aaabcb86",ClientType.CUSTOMER );
		System.out.println("getbuyshistory: "+customerFacade);
		return customerFacade.viewBuysHistory();
		
	}
	@GET
	@Path("getcouponbytype/{couponType}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getBuysHistoryCouponsByType(@PathParam("couponType") String couponType ) throws DaoException
	{
		//companyFacade facade = (companyFacade) getFacade();
		//singletonCouponSystem = CouponSystem.getInstance();
		customerFacade = (CustomerFacade) singletonCouponSystem.login("customer3003", "e41801fdc55a7bf9b1ce8c2d268e0441aaabcb86",ClientType.CUSTOMER );
		System.out.println("getcouponbytype: " + couponType +" " +customerFacade);
		return customerFacade.viewCouponByType(CouponType.valueOf(couponType));
		
	}	
	@GET
	@Path("getcouponbyprice/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getBuysHistoryCouponsByPrice(@PathParam("price") double price ) throws DaoException
	{
		//companyFacade facade = (companyFacade) getFacade();
		singletonCouponSystem = CouponSystem.getInstance();
		customerFacade = (CustomerFacade) singletonCouponSystem.login("customer3003", "e41801fdc55a7bf9b1ce8c2d268e0441aaabcb86",ClientType.CUSTOMER );
		System.out.println("getcouponbytype: " + price +" " +customerFacade);
		return customerFacade.viewCouponByPrice(price);
		
	}
	private CustomerFacade getFacade()
	{
		HttpSession session = request.getSession(); // Do not create new
		if (session == null){} // send error response - to login page
		return  (CustomerFacade) session.getAttribute("facade");
	 }
	
	
	
	}
