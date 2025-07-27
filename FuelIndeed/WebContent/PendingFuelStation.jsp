

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.r3sys.db.ConnectDb"%>
<%@page import="java.sql.SQLException"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fuel Indeed :Admin Dashboard</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f8f9fa;
        }
        .sidebar {
            height: 100vh;
            width: 250px;
            position: fixed;
            top: 0;
            left: 0;
            background-color: #343a40;
            padding-top: 20px;
            transition: all 0.3s;
            overflow-x: hidden;
        }
        .sidebar.collapsed {
            width: 80px;
        }
        .sidebar a {
            padding: 15px 10px;
            text-align: center;
            display: block;
            color: white;
            text-decoration: none;
            font-size: 18px;
            transition: padding 0.3s;
        }
        .sidebar a i {
            display: block;
            font-size: 24px;
            margin-bottom: 10px;
        }
        .sidebar a span {
            display: block;
            font-size: 18px;
            transition: opacity 0.3s;
        }
        .sidebar.collapsed a span {
            opacity: 0;
        }
        .sidebar a:hover {
            background-color: #495057;
        }
        .sidebar .sidebar-header {
            padding: 20px;
            text-align: center;
            color: white;
            font-size: 24px;
            position: relative;
        }
        .sidebar-toggler {
            font-size: 24px;
            color: white;
            cursor: pointer;
            position: absolute;
            right: 20px;
            top: 20px;
        }
        .main-content {
            margin-left: 250px;
            padding: 20px;
            transition: margin-left 0.3s;
        }
        .main-content.expanded {
            margin-left: 80px;
        }
        .navbar {
            padding: 10px 20px;
            background-color: #343a40;
            color: white;
        }
        .navbar .navbar-brand {
            color: white;
        }
        .navbar .logout-btn {
            color: white;
            border: none;
            background: none;
        }
        .table {
            width: 100%; /* Ensure the table takes up full width */
        }
        .table-responsive {
            overflow-x: auto;
        }
        .table th, .table td {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            Admin Panel
            <span class="sidebar-toggler" onclick="toggleSidebar()">
                <i class="fas fa-bars"></i>
            </span>
        </div>
        <a href="">
            <i class="fas fa-tachometer-alt"></i>
            <span>Dashboard</span>
        </a>
        <a href="PendingFuelStation.jsp">
            <i class="fas fa-hourglass-start"></i>
            <span>Pending Fuel Stations</span>
        </a>
        <a href="ApprovedFuelStation.jsp">
            <i class="fas fa-check-circle"></i>
            <span>Approved Fuel Stations</span>
        </a>
        <a href="DisapprovedFuelStation.jsp">
            <i class="fas fa-times-circle"></i>
            <span>Disapproved Fuel Stations</span>
        </a>
        <a href="ViewUsers.jsp">
            <i class="fas fa-users"></i>
            <span>Users</span>
        </a>
    </div>
    <div class="main-content">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <a class="navbar-brand" href="">Fuel Indeed</a>
            <button class="logout-btn ml-auto" onclick="location.href='adminlogin.html'">
                <i class="fas fa-sign-out-alt"></i> Log Out
            </button>
        </nav>
        <div class="container mt-4">
            <h1>Pending Fuel Stations</h1>
            <div class="table-responsive">
                <table class="table table-bordered table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th>Id</th>
                            <th>Name</th>
                            <th>Address</th>        
                            <th>Area</th>
                            <th>City</th>
                            <th>Pincode</th>
                            <th>Contact</th>
                            <th>Email</th>
                            <th>Petrol</th>
                            <th>Petrol Rate</th>
                            <th>Diesel</th>
                            <th>Diesel Rate</th>
                            <th>Open Time</th>
                            <th>Close Time</th>
                            <th>Status</th>
                            <th>Change Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                        try {
                            Connection con = ConnectDb.connect();
                            PreparedStatement ps = con.prepareStatement("SELECT * FROM fuelstation WHERE status=?");
                            ps.setString(1, "Pending");
                            ResultSet rs = ps.executeQuery();
                            
                            while (rs.next()) {
                        %>
                            <tr>
                                <td><%= rs.getInt("id") %></td>
                                <td><%= rs.getString("name") %></td>
                                <td><%= rs.getString("address") %></td>
                                <td><%= rs.getString("area") %></td>
                                <td><%= rs.getString("city") %></td>
                                <td><%= rs.getInt("pincode") %></td>
                                <td><%= rs.getString("contact") %></td>
                                <td><%= rs.getString("email") %></td>
                                <td><%= rs.getFloat("petrol") %></td>
                                <td><%= rs.getFloat("petrol_rate") %></td>
                                <td><%= rs.getFloat("diesel") %></td>
                                <td><%= rs.getFloat("diesel_rate") %></td>
                                <td><%= rs.getTime("open_time") %></td>
                                <td><%= rs.getTime("close_time") %></td>
                                <td><%= rs.getString("status") %></td>
                                <td>
                                    <a class="btn btn-success btn-sm" href="ApproveStatus.jsp?id=<%= rs.getInt("id") %>">Approve</a>
                                    <a class="btn btn-danger btn-sm" href="DisapproveStatus.jsp?id=<%= rs.getInt("id") %>">Disapprove</a>
                                </td>
                            </tr>
                        <%
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
        function toggleSidebar() {
            const sidebar = document.querySelector('.sidebar');
            const mainContent = document.querySelector('.main-content');
            sidebar.classList.toggle('collapsed');
            mainContent.classList.toggle('expanded');
        }
        
        function logout() {
            // Add your logout logic here
            alert("Logging out...");
        }
    </script>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
