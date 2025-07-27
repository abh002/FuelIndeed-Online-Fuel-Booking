package com.r3sys.db;

public class UserFridge {
	public static String name;
	public static String email;
	public static String password;
	public static int id;
	public static int getId() {
		return id;
	}
	public static void setId(int id) {
		UserFridge.id = id;
	}
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		UserFridge.name = name;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		UserFridge.email = email;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		UserFridge.password = password;
	}
}
	