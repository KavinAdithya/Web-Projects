package com.techcrack;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LogInUser extends HttpServlet {
	public LogInUser() {
		super();
	}
	@Override
	public void doPost(HttpServletRequest rq,HttpServletResponse rs) {
		PrintWriter pw=null;
		try {
			pw=rs.getWriter();
			String userName=rq.getParameter("userName");
			String passWord=rq.getParameter("passWord");
			//pw.println(userName+" "+passWord);
			DataBaseConnection dbc=new DataBaseConnection("root","KavinDharani@3");
			ResultSet rs1=dbc.dataFromDataBase("SELECT userName,passWord FROM websitetable");
			System.out.println(userName);
			while(rs1.next()) {
				System.out.println(rs1.getString(1));
				if(rs1.getString(1).equalsIgnoreCase(userName)) {
					if(rs1.getString(2).equals(passWord)) {
						pw.println("Login Success Fully");
					}
					else {
						pw.println("Wrong PassWord!!!");
					}
					System.out.println(rs1.getString(1));
					return;
				}
			}
			pw.println("Invalid User Id..");
		}
		catch(Exception e) {
			pw.println("Something Unsual Check the Details Again!! "+e);
		}
	}
	public void doGet(HttpServletRequest hsr,HttpServletResponse hs){
		Cookie []cookies=hsr.getCookies();
		System.out.println(cookies.length);
		String userName=null;
		try {
		PrintWriter pw=hs.getWriter();
		String passWord=null;
		for(Cookie cook:cookies) {
			if(cook.getName().equals("userName")) 
				userName=cook.getValue();
			else if(cook.getName().equals("passWord"))
				passWord=cook.getValue();
			System.out.println(cook.getValue());
		}
		DataBaseConnection dbc=new DataBaseConnection("root","KavinDharani@3");
		ResultSet rs1=dbc.dataFromDataBase("SELECT userName,passWord FROM websitetable");
		System.out.println(passWord);
		while(rs1.next()) {
			//System.out.println(rs1.getString(1));
			if(rs1.getString(1).equalsIgnoreCase(userName)) {
				if(rs1.getString(2).equals(passWord)) {
					pw.println("Login Success Fully");
				}
				else {
					pw.println("Wrong PassWord!!!");
				}
				//System.out.println(rs1.getString(1));
				return;
			}
			
		}
		pw.println("Log In failed Due To Some Errror But Account Has Been Created ?? Try a again After A While");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
