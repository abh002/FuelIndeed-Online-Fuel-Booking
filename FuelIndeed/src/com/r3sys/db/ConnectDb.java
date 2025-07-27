package com.r3sys.db;

import java.sql.*;

public class ConnectDb {
	public static Connection con = null;
	public static Connection connect(){
		try{
			if(con==null){
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fuelindeed", "root", "");
				System.out.println("~~~ WELCOME TO FuelIndeed ~~~");
			}
		}
		catch(Exception e){
			System.err.println(e);
		}
		return con;
	}
}
