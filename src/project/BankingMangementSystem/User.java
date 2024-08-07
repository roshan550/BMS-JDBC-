package project.BankingMangementSystem;
import java.sql.*;
import java.util.*;
public class User {
private Connection con;
private Scanner scan;
public User(Connection con,Scanner scan) {
	this.con=con;
	this.scan=scan;
	
}
public void register() {
	scan.nextLine();
	System.out.println("Full Name: ");
	String full_name=scan.nextLine();
	System.out.println("Email: ");
	String email=scan.next();
	System.out.println("Password: ");
	String password=scan.next();
	if(user_exists(email)) {
		System.out.println("user Already Exists for this Email Address");
		return;
	}
	String rigester_query="Insert into User(full_name,email,password) values (?,?,?)";
	try {
		PreparedStatement pstmnt=con.prepareStatement(rigester_query);
		pstmnt.setString(1, full_name);
		pstmnt.setString(2,email);
		pstmnt.setString(3, password);
		int affectedrows=pstmnt.executeUpdate();
		if(affectedrows>0) {
			System.out.println("Rigester sucessful");
		}
		else {
			System.out.println("Rigestration failed");
		}
		
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	
}
public String login() {
	scan.nextLine();
	System.out.println("Email: ");
	String email=scan.next();
	System.out.println("Password: ");
	String password=scan.next();
	String login_query="SELECT * FROM user where email = ? AND password=?";
	
	try {
		PreparedStatement pstmnt=con.prepareStatement(login_query);
		pstmnt.setString(1,email);
		pstmnt.setString(2, password);
		ResultSet res=pstmnt.executeQuery();
		if(res.next()) {
			return email;
		}else {
			return null;
		}
	}catch(SQLException e) {
		e.printStackTrace();
		
	}
	return null;
}

public boolean user_exists(String email) {
	String query="select * from where email=?";
	try {
		PreparedStatement pstmnt=con.prepareStatement(query);
		pstmnt.setString(1, email);
		ResultSet res=pstmnt.executeQuery();
		if(res.next()) {
			return true;
		}
		else {
			return false;
		}
	}catch(SQLException e) {
		e.printStackTrace();
	}
	return false;
	
}


}
