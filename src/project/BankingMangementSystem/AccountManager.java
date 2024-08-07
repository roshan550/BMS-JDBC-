package project.BankingMangementSystem;
import java.sql.*;
import java.util.*;
public class AccountManager {
	private Connection con;
	private Scanner scan;

 public AccountManager(Connection con,Scanner scan){
	 this.con=con;
	 this.scan=scan;
 }
 public void debit_money(long account_number)throws SQLException{
	 scan.nextLine();
	 System.out.println("Enter Amount : ");
	 double amount=scan.nextDouble();
	 scan.nextLine();
	 System.out.println("Enter security pin:");
	 String security_pin=scan.nextLine();
	 try {
		 con.setAutoCommit(false);
		 if(account_number!=0) {
			 PreparedStatement pstmnt=con.prepareStatement("select * from accounts where account_number=? and security_pin=?");
			 pstmnt.setLong(1, account_number);
			 pstmnt.setString(2, security_pin);
			 ResultSet res=pstmnt.executeQuery();
			 if(res.next()) {
				 double current_balance=res.getDouble("balance");
				 if(amount<=current_balance) {
					 
					 String credit_query="update accounts set balance=balance-? where account_number=?";
					 PreparedStatement pstmnt1=con.prepareStatement(credit_query);
					 pstmnt1.setDouble(1,amount);
					 pstmnt1.setLong(2,account_number);
					 int rowsaffect=pstmnt1.executeUpdate();
					 if(rowsaffect>0) {
						 System.out.println("Rs."+amount+"Debited Sucessfully!");
						 con.commit();
						 con.setAutoCommit(true);
					 }
					 else {
						 System.out.println("Transaction failed!");
						 con.rollback();
						 con.setAutoCommit(true);
					 }
				 }
				 else {
					 System.out.println("Insufficient Balance");
					 
				 }
			 }else {
				 System.out.println("Invalid Pin!");
			 }
		 }
	 }
	 catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 public void credit_money(long account_number)throws SQLException{
	 scan.nextLine();
	 System.out.println("Enter Amount : ");
	 double amount=scan.nextDouble();
	 scan.nextLine();
	 System.out.println("Enter security pin:");
	 String security_pin=scan.nextLine();
	 try {
		 con.setAutoCommit(false);
		 if(account_number!=0) {
			 PreparedStatement pstmnt=con.prepareStatement("select * from accounts where account_number=? and security_pin=?");
			 pstmnt.setLong(1, account_number);
			 pstmnt.setString(2, security_pin);
			 ResultSet res=pstmnt.executeQuery();
			 if(res.next()) {
				 String credit_query="update accounts set balance=balance+? where account_number=?";
				 PreparedStatement pstmnt1=con.prepareStatement(credit_query);
				 pstmnt1.setDouble(1,amount);
				 pstmnt1.setLong(2,account_number);
				 int rowsaffect=pstmnt1.executeUpdate();
				 if(rowsaffect>0) {
					 System.out.println("Rs."+amount+"credited sucessfully!");
					 con.commit();
					 con.setAutoCommit(true);
					 return;
				 }else {
					 System.out.println("Transaction Failed!");
					 con.rollback();
					 con.setAutoCommit(true); 
				 } 
	 
			 }
			 else {
				 System.out.println("Invalid Security Pin!");
			 }
		 }
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 
 public void getBalnace(long account_number) {
	 scan.nextLine();
	 System.out.println("Enter Security Pin:");
	 String security_pin=scan.nextLine();
	 try {
		 PreparedStatement pstmt=con.prepareStatement("select balance from accounts where account_number=? and security_pin=?");
		 pstmt.setLong(1, account_number);
		 pstmt.setString(2, security_pin);
		 ResultSet res=pstmt.executeQuery();
		 if(res.next()) {
			 double balance=res.getDouble("balance");
			 System.out.println("Balance:"+balance);
		 }else {
			 System.out.println("Invalid Pin!");
		 }
		 
	 }catch(SQLException e) {
		 e.printStackTrace();
	 }
 }
 public void transfer_money(long sender_account_number)throws SQLException{
		scan.nextLine();
		System.out.println("Enter reciver account number:");
		long reciver_account_number=scan.nextLong();
		System.out.println("Enter the amount:");
		double amount=scan.nextDouble();
		scan.nextLine();
		System.out.println("Enter security pin: ");
		String security_pin=scan.nextLine();
		try {
			 con.setAutoCommit(false);
			 if(sender_account_number!=0 && reciver_account_number!=0) {
				 PreparedStatement pstmnt=con.prepareStatement("selcet * from accounts where account_number=? and security_pin=?");
				 pstmnt.setLong(1, sender_account_number);
				 pstmnt.setString(2, security_pin);
				 ResultSet res=pstmnt.executeQuery();
				 if(res.next()) {
					 double current_balance=res.getDouble("balance");
					 if(amount<=current_balance) {
						 String debit_query="update accounts set balance=balance-? and account_number=?";
						 String credit_query="update accounts set balance=balance+? and account_number=?";
						 PreparedStatement cpstmnt=con.prepareStatement(credit_query);
						 PreparedStatement dpstmnt=con.prepareStatement(debit_query);
						 cpstmnt.setDouble(1, amount);
						 cpstmnt.setLong(2, reciver_account_number);
						 dpstmnt.setDouble(1, amount);
						 dpstmnt.setLong(3, sender_account_number);
						 int resaffect1=cpstmnt.executeUpdate();
						 int resaffect2=dpstmnt.executeUpdate();
						 if(resaffect1>0 && resaffect2>0) {
							 System.out.println("Transaction sucessfull!");
							 System.out.println("Rs."+amount+"transfered sucessfully");
							 con.commit();
							 con.setAutoCommit(true);
						 }
						 else {
							 System.out.println("Transaction failed!");
							 con.rollback();
							 con.setAutoCommit(true);
						 }
					 }
					 System.out.println("Insufficient balance..!");
				 }
				 else {
					 System.out.println("Invalid Security Pin!");
				 }
				 
			 }
			 else {
				 System.out.println("Invalid Account number!");
			 }
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		con.setAutoCommit(true);
 }
}

