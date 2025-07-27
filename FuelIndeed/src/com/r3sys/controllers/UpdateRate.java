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
 * Servlet implementation class UpdateFuel
 */
public class UpdateRate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateRate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String rate = request.getParameter("rate");
        String email = FuelStationFridge.getEmail();
        System.out.println(type);
        System.out.println(rate);
        System.out.println(email);
        try {
            Connection con = ConnectDb.connect();

            if (type.equals("petrol")) {
            	System.out.println("type is petrol");
                PreparedStatement ps = con.prepareStatement("update fuelstation set petrolRate=? where email=?");
                ps.setString(1, rate);
                ps.setString(2, email);
                int i = ps.executeUpdate();
                System.out.println(i);
                if (i > 0) {
                	System.out.println("query success");
                    response.sendRedirect("fuelstationhomepage.html");
                } else {
                	System.out.println("query failed");
                    response.sendRedirect("error.html");
                }
            } else if (type.equals("diesel")) {
                PreparedStatement ps = con.prepareStatement("update fuelstation set dieselRate=? where email=?");
                ps.setString(1, rate);
                ps.setString(2, email);
                int i = ps.executeUpdate();
                if (i > 0) {
                    response.sendRedirect("fuelstationhomepage.html");
                } else {
                    response.sendRedirect("error.html");
                }
            }

        } catch (SQLException s) {
            s.printStackTrace();
        }
    }
}
