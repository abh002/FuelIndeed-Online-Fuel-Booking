
<%@page import="com.r3sys.db.BufferFridge"%>
<%@page import="com.r3sys.db.FuelStationFridge"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.r3sys.db.ConnectDb"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fuel Indeed : Pending Bookings</title>
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
            display: none;
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
        .sidebar ul {
            list-style-type: none;
            padding-left: 0;
        }
        .sidebar ul li {
            margin: 0;
        }
        .sidebar ul ul {
            padding-left: 20px;
        }
        .sidebar ul ul li {
            margin: 0;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #dee2e6;
        }
        th, td {
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #343a40;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
    <div class="sidebar">
        <div class="sidebar-header">
            Fuel Indeed
            <span class="sidebar-toggler" onclick="toggleSidebar()">
                <i class="fas fa-bars"></i>
            </span>
        </div>
        <ul>
            <li><a href="updatefuelqty.html"><i class="fas fa-tachometer-alt"></i><span>Update Fuel Availability</span></a></li>
            <li><a href="updatefuelrate.html"><i class="fas fa-dollar-sign"></i><span>Update Fuel Rate</span></a></li>
            <li>
                
                <ul>
                    <li><a href="adddeliveryperson.html"><i class="fas fa-user-plus"></i><span>Add Delivery Person</span></a></li>
                    <li><a href="ViewDeliveryPerson.jsp"><i class="fas fa-users"></i><span>View Delivery Person</span></a></li>
                </ul>
            </li>
            <li>
               
                <ul>
                    <li><a href="PendingBooking.jsp"><i class="fas fa-hourglass-start"></i><span>Pending Bookings</span></a></li>
                    <li><a href="AssignedBookings.jsp"><i class="fas fa-tasks"></i><span>Assigned Bookings</span></a></li>
                    <li><a href="DeliveredBookings.jsp"><i class="fas fa-check-circle"></i><span>Delivered Bookings</span></a></li>
                    <li><a href="RejectedBookings.jsp"><i class="fas fa-times-circle"></i><span>Rejected Bookings</span></a></li>
                </ul>
            </li>
            <li><a href="fschangepassword.html"><i class="fas fa-key"></i><span>Change Password</span></a></li>
        </ul>
    </div>
    <div class="main-content">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <a class="navbar-brand" href="#">Fuel Indeed</a>
            <button class="logout-btn ml-auto" onclick="location.href='fuelstationlogin.html'">
                <i class="fas fa-sign-out-alt"></i> Log Out
            </button>
        </nav>
        <div class="container mt-4">
            <h2 class="text-center mb-4">Delivered Bookings</h2>
            <table>
                <tr>
		<th>Id</th>
		<th>UserId</th>
		<th>StationId</th>		
		<th>Delivery Person Id</th>
		<th>Fuel Type</th>
		<th>Quantity</th>
		<th>Total Bill</th>
		<th>Status</th>
	</tr>
	
		<%
		try{
			Connection con = ConnectDb.connect();
			PreparedStatement ps = con.prepareStatement("select * from Bookings where (status=? and stationId=?)");
			ps.setString(1,"Deliver");
			ps.setInt(2, FuelStationFridge.getId());
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				%>
				<tr>
				<td><%=rs.getInt(1) %></td>
				<td><%=rs.getInt(2) %></td>
				<td><%=rs.getInt(3) %></td>
				<td><%=rs.getInt(4) %></td>			
				<td><%=rs.getString(5) %></td>
				<td><%=rs.getFloat(6) %></td>
				<td><%=rs.getFloat(7) %></td>
				<td><%=rs.getString(8) %></td>
				<%
			}
		}
		catch(SQLException s){
			s.printStackTrace();
		}

		%>
                
            </table>
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
