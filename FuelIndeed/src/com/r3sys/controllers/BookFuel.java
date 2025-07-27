package com.r3sys.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r3sys.db.BufferFridge;
import com.r3sys.db.ConnectDb;
import com.r3sys.db.UserFridge;

/**
 * Servlet implementation class BookFuel
 */
public class BookFuel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookFuel() {
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
		int userId = UserFridge.getId();
		int stationId = BufferFridge.getStationId();
		int deliveryPersonId = 0;
		String fuelType = request.getParameter("fuelType");
		float quantity = Float.parseFloat(request.getParameter("fuelQty"));
		float totalbill = 0;
		String status = "Pending";
		Random random = new Random();
        int bookingCode = 10000 + random.nextInt(90000);
		Connection con = ConnectDb.connect();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			if(fuelType.equals("petrol")){
				ps = con.prepareStatement("select petrolRate from fuelstation where id=?");
				ps.setInt(1, stationId);
				rs = ps.executeQuery();
				if(rs.next()){
					totalbill += rs.getFloat(1)*quantity;
				}
			}
			else if(fuelType.equals("diesel")){
				ps = con.prepareStatement("select dieselRate from fuelstation where id=?");
				ps.setInt(1, stationId);
				rs = ps.executeQuery();
				if(rs.next()){
					totalbill += rs.getFloat(1)*quantity;
				}
			}
			
			ps = con.prepareStatement("insert into bookings values(?,?,?,?,?,?,?,?,?,CURRENT_DATE)");
			ps.setInt(1, id);
			ps.setInt(2, userId);
			ps.setInt(3, stationId);
			ps.setInt(4, deliveryPersonId);
			ps.setString(5, fuelType);
			ps.setFloat(6, quantity);
			ps.setFloat(7, totalbill);
			ps.setString(8, status);
			ps.setInt(9, bookingCode);
			
			int i = ps.executeUpdate();
			if(i>0){
				response.sendRedirect("ViewFuelStation.jsp");
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
