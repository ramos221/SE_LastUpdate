package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class UserModel {

	
	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	//public String UserID1;
	//public String UserID2;
	/*
	public String getUserID1(){
		return UserID1;
	}
	public void setUserID1(String UserID1){
		this.UserID1 = UserID1;
	}
	public String getUserID2(){
		return UserID2;
	}
	public void setUserID2(String UserID2){
		this.UserID2 = UserID2;
	}
	*/
	public String getPass(){
		return pass;
	}
	
	
	public void setPass(String pass){
		this.pass = pass;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
			// System.out.println(sql);

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	
	
	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try{
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean Follow(int UserID1,int UserID2)
	{
		try{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into follower (`UserID1`,`UserID2`) VALUES  (?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1,UserID1);
		stmt.setInt(2,UserID2);
		stmt.executeUpdate();
		
		return true ;
		
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static boolean unfollow(int UserID1,int UserID2)
	{
		try{
		Connection conn = DBConnection.getActiveConnection();
		
		String sql = "delete from TABLE follower `id1`=? and`id2`=?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1,UserID1);
		
		stmt.setInt(2,UserID2);
		return true ;
		
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	public static ArrayList<String> ListAllFollowers(int UserID2)
	{
	try {
		ArrayList<Integer> IDS=new ArrayList<Integer>();
		ArrayList<String> Following=new ArrayList<String>();
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select `UserID1` from followers where `UserID2` = ?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, UserID2);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) 
		{
			IDS.add(rs.getInt("id1"));
		}
		for(int i=0;i<IDS.size();i++)
		{
		String sql1 = "Select name from users where `id` = ?";
		PreparedStatement stmt1;
		stmt1 = conn.prepareStatement(sql1);
		stmt1.setInt(1, IDS.get(i));
		ResultSet rs1 = stmt1.executeQuery();
		if(rs1.next()) 
		{
			Following.add(rs1.getString("name"));
		}
		}
		return Following;
	}
	
	catch (SQLException e) {
		e.printStackTrace();
	}
	return null;
}
}


