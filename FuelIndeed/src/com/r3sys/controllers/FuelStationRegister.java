package com.r3sys.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r3sys.db.ConnectDb;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Servlet implementation class FuelStationRegister
 */
public class FuelStationRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuelStationRegister() {
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
	int id;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		String name = request.getParameter("name");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String area = request.getParameter("area");
		String city = request.getParameter("city");
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		float petrol = Float.parseFloat(request.getParameter("petrol"));
		float petrolRate = Float.parseFloat(request.getParameter("petrolRate"));
		float diesel = Float.parseFloat(request.getParameter("diesel"));
		float dieselRate = Float.parseFloat(request.getParameter("dieselRate"));
		
		String openTimeString = request.getParameter("openTime");
		String closeTimeString = request.getParameter("closeTime");
		
		
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Time openTime = null;
		Time closeTime = null;
		
		try {
		    long ms = format.parse(openTimeString).getTime();
		    openTime = new Time(ms);
		    long cs = format.parse(closeTimeString).getTime();
		    closeTime = new Time(cs);
		} 
		catch (ParseException e) {
		    e.printStackTrace();
		}
		
		String status = "Pending";
		
		try{
			Connection con = ConnectDb.connect();
			PreparedStatement ps = con.prepareStatement("insert into fuelstation values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setString(2,name);
			ps.setString(3,address);
			ps.setString(4,area);
			ps.setString(5,city);
			ps.setInt(6, pincode);
			ps.setString(7,contact);
			ps.setString(8,email);
			ps.setString(9,password);
			ps.setFloat(10, petrol);
			ps.setFloat(11, petrolRate);
			ps.setFloat(12, diesel);
			ps.setFloat(13, dieselRate);
			ps.setTime(14, openTime);
			ps.setTime(15, closeTime);
			ps.setString(16, status);
			
			int i = ps.executeUpdate();
			if(i>0){
				response.sendRedirect("fuelstationlogin.html");
			}
			else{
				response.sendRedirect("fuelstationregister.html");
			}
		}
		catch(SQLException s){
			s.printStackTrace();
		}
	}

}
