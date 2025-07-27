package com.r3sys.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r3sys.db.ConnectDb;
import com.r3sys.db.FuelStationFridge;

/**
 * Servlet implementation class AddDeliveryPerson
 */
public class AddDeliveryPerson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddDeliveryPerson() {
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
	int deliveryId;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		int stationId = FuelStationFridge.getId();
		String name = request.getParameter("name");
		String contact = request.getParameter("contact");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		int pincode = Integer.parseInt(request.getParameter("pincode"));
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try{
			Connection con = ConnectDb.connect();
			PreparedStatement ps = con.prepareStatement("insert into deliveryperson values(?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, id);
			ps.setInt(2, stationId);
			ps.setString(3, name);
			ps.setString(4, contact);
			ps.setString(5, email);
			ps.setString(6, password);
			ps.setString(7, address);
			ps.setString(8, city);
			ps.setInt(9, pincode);
			ps.setInt(10, deliveryId);
			
			int i = ps.executeUpdate();
			if(i>0){
				response.sendRedirect("fuelstationhomepage.html");
			}
			else{
				response.sendRedirect("error.html");
			}
		}
		catch(SQLException s){
			s.printStackTrace();
		}
	}

}
