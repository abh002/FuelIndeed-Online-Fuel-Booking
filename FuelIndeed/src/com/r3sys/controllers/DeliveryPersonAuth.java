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
 * Servlet implementation class DeliveryPersonAuth
 */
public class DeliveryPersonAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliveryPersonAuth() {
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
			PreparedStatement ps = con.prepareStatement("select * from deliveryperson where name=? and email=? and password=?");
			ps.setString(1, name);
			ps.setString(2, email);
			ps.setString(3, password);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				DeliveryPersonFridge.setId(rs.getInt(1));
				DeliveryPersonFridge.setName(name);
				DeliveryPersonFridge.setEmail(email);
				DeliveryPersonFridge.setPassword(password);
				response.sendRedirect("deliverypersonhomepage.html");
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
