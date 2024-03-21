package com.techcrack;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

public class DataBaseConnection {
	private String userName=null;
	private String passWord=null;
	private Connection con=null;
	private Statement st=null;
	private String URL="jdbc:mysql://localhost:3306/music";
	public DataBaseConnection(String userName,String passWord) throws ClassNotFoundException,SQLException{
		this.userName=userName;
		this.passWord=passWord;
		startTheConnection();
	}
	private void startTheConnection() throws ClassNotFoundException,SQLException{
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection(URL,userName,passWord);
			st=con.createStatement();
			System.out.println("Data Base Has Been Successfully Connected!!");
	}
	public ResultSet dataFromDataBase(String s) throws SQLException {
		return st.executeQuery(s);
	}
	public boolean createTable(String s) throws SQLException {
		st.executeUpdate(s);
		System.out.println("Table has Been created Successfully..");
		return true;
	}
	public boolean updateTable(String s,HttpServletRequest hsr) throws SQLException {
		//st.executeUpdate(s);
		PreparedStatement ps=con.prepareStatement(s);
		ps.setString(1,hsr.getParameter("user-name"));
		ps.setString(2,hsr.getParameter("passWord"));
		ps.setString(3, hsr.getParameter("name"));
		ps.setString(4,hsr.getParameter("date"));
		ps.setInt(5, Integer.parseInt(hsr.getParameter("age")));
		ps.setString(6, hsr.getParameter("gmail"));
		System.out.println(ps.executeUpdate()+" rows Affected");
		System.out.println("Update Hass Succesfull...");
		return true;
	}
	public static void main(String[] args) throws SQLException {
		try {
			DataBaseConnection dsc=new DataBaseConnection("root","KavinDharani@3");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
	}
}

