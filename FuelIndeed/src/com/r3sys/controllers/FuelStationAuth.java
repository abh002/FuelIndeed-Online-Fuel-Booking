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
import com.r3sys.db.FuelStationFridge;

/**
 * Servlet implementation class FuelStationAuth
 */
public class FuelStationAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuelStationAuth() {
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
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try{
			Connection con = ConnectDb.connect();
			PreparedStatement ps = con.prepareStatement("select * from fuelstation where (password=? and status=?) and name=?");
			ps.setString(1, password);
			ps.setString(2, "Approved");
			ps.setString(3,name);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				FuelStationFridge.setId(rs.getInt(1)); 
				FuelStationFridge.setEmail(email);
				FuelStationFridge.setPassword(password); 
				response.sendRedirect("fuelstationhomepage.html");
			}
			else{
				response.sendRedirect("status.html"); 
			}
		}
		catch(SQLException s){
			s.printStackTrace();
		}
	}

}
