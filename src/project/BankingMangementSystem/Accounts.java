package project.BankingMangementSystem;
import java.util.*;
import java.sql.*;
public class Accounts {
 private Connection  con;
 private Scanner scan;
 public Accounts(Connection con,Scanner scan) {
	 this.con=con;
	 this.scan=scan;
 }
 public long open_account(String email) {
	 if(!account_exist(email)) {
		 String open_account_query="insert into accounts(account_number,full_name,email,balance,security_pin)values(?,?,?,?,?)";
		 scan.nextLine();
		 System.out.println("Enter Full name:");
		 String full_name=scan.nextLine();
		 System.out.println("Enter Initial amount: ");
		 double balance=scan.nextInt();
		 scan.nextLine();
		 System.out.println("Enter Security pin: ");
		 String security_pin=scan.nextLine();
		 try {
			 long account_number=generateAccont_number();
			 PreparedStatement pstmnt=con.prepareStatement(open_account_query);
			 pstmnt.setLong(1, account_number);
			 pstmnt.setString(2, full_name);
			 pstmnt.setString(3, email);
			 pstmnt.setDouble(4, balance);
			 pstmnt.setString(5, security_pin);
			 int affectedRows=pstmnt.executeUpdate();
			 if(affectedRows>0) {
				 return account_number;
			 }
			 else {
				 throw new RuntimeException("Account creation failed");
			 }
			 
		 }catch(SQLException e) {
			 e.printStackTrace();
		 }
	 }
	 throw new RuntimeException("Account already exists");
 } 
 public long getAccount_number(String email) {
	 String query="select account_number from Accounts where email=?";
	 try {
		 PreparedStatement pstmnt=con.prepareStatement(query);
		 pstmnt.setString(1, email);
		 ResultSet res=pstmnt.executeQuery();
		 if(res.next()) {
			 return res.getLong("account_number");
		 }
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
	 throw new RuntimeException("Account Number Doesn't Exist");
 }
 private long generateAccont_number()
 {
	try {
		Statement stmnt= con.createStatement();
		ResultSet resultSet=stmnt.executeQuery("Select account_number from Accounts order by account_number DESC LIMIT 1");
		if(resultSet.next()) {
			long last_account_number=resultSet.getLong("account_number");
			return last_account_number+1;
		}
		else {
			return 10000100;
		}
	}
	catch(SQLException e) {
		e.printStackTrace();
	}
	return 10000100;
 }
 public boolean account_exist(String email) {
	 String query="select account_number from accounts where email=?";
	 try {
		 PreparedStatement pstmnt=con.prepareStatement(query);
		 pstmnt.setString(1, email);
		 ResultSet res=pstmnt.executeQuery();
		 if(res.next()) {
			 return true;
		 }else {
			 return false;
		 }
		 
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
	 return false;
 }
}

