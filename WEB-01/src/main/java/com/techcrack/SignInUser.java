package com.techcrack;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.IOException;
import java.io.PrintWriter;

public class SignInUser extends HttpServlet{
	public void service(HttpServletRequest hs,HttpServletResponse re) {
		mainBranch(hs,re);
		System.out.println("In service");
	}
	public void mainBranch(HttpServletRequest hs,HttpServletResponse re) {
		PrintWriter pw=null;
		try {
			pw=re.getWriter();
			DataBaseConnection dbc=new DataBaseConnection("root","KavinDharani@3");
			String user=hs.getParameter("user-name");
			if(knowYourUserIsPresent(user,dbc,pw)) {
				sendDataToDataBase(hs,re,dbc,pw);
				return;
			}
			else {
				pw.println("UserName Must Be Unique Try Again!!!");
				return ;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void sendDataToDataBase(HttpServletRequest hs,HttpServletResponse re,DataBaseConnection dbc,PrintWriter pw) throws SQLException, IOException {
		if(gmailChecker(hs.getParameter("gmail"))){
			if(hs.getParameter("passWord").equals(hs.getParameter("repass"))) {
				if(knowPassWordIsStrong(hs.getParameter("passWord"))) {
//					String query="INSERT INTO websiteTable VALUES("+hs.getParameter("user-name")+","+
//								hs.getParameter("passWord")+","+
//								hs.getParameter("name")+","+
//								hs.getParameter("date")+","+
//								Integer.parseInt(hs.getParameter("age"))+","+
//								hs.getParameter("gmail")+")";
//					System.out.println(query);
//					dbc.updateTable(query);
					String query="INSERT INTO websiteTable VALUES(?,?,?,?,?,?)";
					dbc.updateTable(query,hs);
					pw.println("Account Has Been Created SuccessFully!!!");
					Cookie cookie=new Cookie("userName",hs.getParameter("user-name"));
					re.addCookie(cookie);
					Cookie cookie1=new Cookie("passWord",hs.getParameter("passWord"));
					re.addCookie(cookie1);
					//System.out.println(cookie1.getValue());
					re.sendRedirect("Login");
				}
				else {
					pw.println("PassWord is Not Enough Secured ??");
					pw.println("Try Again After A While!!!");
				}
			}
			else {
				pw.println("PassWord Does Not Match!!!");
				pw.println("Try a Again After a While!!!");
			}
		}
		else
			pw.println("Invalid Email Id??? \nTry To sign In Again After A while!!!");
	}

	private boolean knowPassWordIsStrong(String parameter) {
		int lower=0;
		int upper=0;
		int character=0;
		int number=0;
		if(parameter.length()<8)
			return false;
		for(int k=0;k<parameter.length();k++) {
			char ch=parameter.charAt(k);
			if(ch>='A'&&ch<='Z')
				upper++;
			else if(ch>='a'&&ch<='z')
				lower++;
			else if(ch>='0'&&ch<='9')
				number++;
			else 
				character++;
			if(lower!=0&&upper!=0&&number!=0&&character!=0) {
				return true;
			}
		}
		return false;
	}
	public boolean knowYourUserIsPresent(String user,DataBaseConnection dbc,PrintWriter pw) throws SQLException {
		ResultSet rs=dbc.dataFromDataBase("SELECT username FROM websiteTable");
		while(rs.next()) {
			if(rs.getString(1).equalsIgnoreCase(user))
					return false;
		}
		return true;
	}
	public boolean gmailChecker(String gmail) {
		if(gmail.substring(gmail.length()-10,gmail.length()).equalsIgnoreCase("@gmail.com")) {
			return true;
		}
		return false;
	}

}
