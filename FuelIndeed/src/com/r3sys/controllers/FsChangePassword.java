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
 * Servlet implementation class FsChangePassword
 */
public class FsChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FsChangePassword() {
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
		
		String password = FuelStationFridge.getPassword();
		String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        int id = FuelStationFridge.getId();

        if(password.equals(currentPassword)){
        	if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("error.html");
                
            }

            try  {
            	Connection con = ConnectDb.connect();
                String query = "UPDATE fuelstation SET password=? WHERE id=? AND password=?";
                try (PreparedStatement ps = con.prepareStatement(query)) {
                    ps.setString(1, newPassword);
                    ps.setInt(2, id);
                    ps.setString(3, currentPassword);
                    
                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        response.sendRedirect("fuelstationhomepage.html");
                    } else {
                    	 response.sendRedirect("error.html");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.html");
            }
            
        }
        else {
        	response.sendRedirect("error.html");
        }
	}

}
