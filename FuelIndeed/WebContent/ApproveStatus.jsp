<%@page import="java.sql.PreparedStatement"%>
<%@page import="com.r3sys.db.ConnectDb"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Fuel Indeeed :Admin Dashboard</title>
</head>
<body>
<%
try{
	Connection con = ConnectDb.connect();
	int id = Integer.parseInt(request.getParameter("id"));
	PreparedStatement ps = con.prepareStatement("update fuelstation set status=? where id=?");
	ps.setString(1,"Approved");
	ps.setInt(2,id);
	int i = ps.executeUpdate();
	if(i>0){
		response.sendRedirect("PendingFuelStation.jsp");
	}
	else{
		response.sendRedirect("error.html");
	}
}
catch(SQLException s){
	s.printStackTrace();
}
%>
</body>
</html>