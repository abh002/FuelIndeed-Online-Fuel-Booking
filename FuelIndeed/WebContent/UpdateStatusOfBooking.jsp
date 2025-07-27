<%@page import="com.r3sys.db.FuelStationFridge"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.r3sys.db.ConnectDb"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fuel Indeed :Fuel Station Dashboard</title>
</head>
<body>
<%
String status = request.getParameter("status");
System.out.println(status);
int id = Integer.parseInt(request.getParameter("id"));
System.out.println(id);
int did = 0;
Connection con = ConnectDb.connect();
PreparedStatement ps = null;
ResultSet rs = null;
if(status.equals("Assign")){
	ps = con.prepareStatement("select id from deliveryperson where stationId=?");
	ps.setInt(1, FuelStationFridge.getId());
	rs = ps.executeQuery();
	if(rs.next()){
		System.out.println("There is dp with station id "+FuelStationFridge.getId());
		did = rs.getInt(1);
		System.out.println(did);
	}
	
	ps = con.prepareStatement("update bookings set status=?, deliveryPersonId =? where id=?");
	ps.setString(1, status);
	ps.setInt(2,did);
	ps.setInt(3,id);
	int i = ps.executeUpdate();
	System.out.println(i);
	
	ps = con.prepareStatement("update deliveryperson set deliveryId=? where id=?");
	ps.setInt(1, id);
	ps.setInt(2, did);
	int j = ps.executeUpdate();
	System.out.println(j);
	
	if(i>0 && j>0){
		response.sendRedirect("PendingBooking.jsp");
	}
	else{
		response.sendRedirect("error.html");
	}
}
else if(status.equals("Reject")) {
	ps = con.prepareStatement("update bookings set status=? where id=?");
	ps.setString(1, status);
	ps.setInt(2, id);
	int i = ps.executeUpdate();
	System.out.println(i);
	if(i>0){
		response.sendRedirect("PendingBooking.jsp");
	}
	else{
		response.sendRedirect("error.html");
	}
}
%>
</body>
</html>