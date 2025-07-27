package com.r3sys.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.r3sys.db.ConnectDb;
import com.r3sys.db.DeliveryPersonFridge;

/**
 * Servlet implementation class DeliverBooking
 */
public class DeliverBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliverBooking() {
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
		doGet(request, response);
		
		int deliveryCode = Integer.parseInt(request.getParameter("deliveryCode"));
		int id = DeliveryPersonFridge.getId();
		
		try{
			Connection con = ConnectDb.connect();
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			ps = con.prepareStatement("update bookings set status=? where bookingCode=?");
			ps.setString(1, "Deliver");
			ps.setInt(2, deliveryCode);
			int  i = ps.executeUpdate();
			
			if(i>0){
				try{
					ps = con.prepareStatement("update deliveryperson set deliveryId=0 where id=?");
					ps.setInt(1, id);
					int j = ps.executeUpdate();
					
					if(j>0){
						response.sendRedirect("deliverypersonhomepage.html");
					}
				}
				catch(SQLException s){
					s.printStackTrace();
				}
				
			}
			else{
				response.sendRedirect("failed.html");
			}
			
		}
		catch(SQLException s){
			s.printStackTrace();
		}
	}

}
