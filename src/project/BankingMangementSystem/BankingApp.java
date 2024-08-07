package project.BankingMangementSystem;
import java.sql.*;
import java.util.*;

public class BankingApp {
	private static final String url="jdbc:mysql://localhost:3306/bankingsystem";
	private static final String username="root";
	private static final String password="roshan@550";
	public static void main(String[] args) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}catch(ClassNotFoundException e){
		System.out.println(e.getMessage());
	}
	try {
	    Connection con=DriverManager.getConnection(url, username, password);
	    Scanner scan=new Scanner(System.in);
	    User user=new User(con,scan);
	    Accounts accounts=new Accounts(con,scan); 
	    AccountManager accountmanager=new AccountManager(con,scan);
	    String email;
	    long account_number;
	    while(true){
	    	System.out.println("Welome to Banking System");
	    	System.out.println();
	    	System.out.println("1. Register");
	    	System.out.println("2. Login");
	    	System.out.println("3. Exit");
	    	System.out.println("Enter your choice: ");
	    	int choice=scan.nextInt();
	    	switch(choice){
	    		case 1:user.register();
	    		        break;
	    		case 2:email=user.login();
	                   if(email!=null) {  
		        	      System.out.println();
		        	      System.out.println("User logged In!");
		        	          if(!accounts.account_exist(email)) {
		        		        System.out.println();
		        		        System.out.println("1.Open a new Bank Account");
		        		        System.out.println("2.exit");
		        		           if(scan.nextInt()==1) {
		        			          account_number=accounts.open_account(email);
		        			          System.out.println("Account created sucessfully");
		        			          System.out.println("Your account number is: "+account_number);
		        			         
		        				
		        		             }
		        		         else {
		        			          break;
		        		             }
		        	           }
		           
		        	           account_number=accounts.getAccount_number(email);
		        	           int choice2=0;
		        	           while(choice2!=5) {
		        		         System.out.println();
		        		         System.out.println("1.Debit Money");
		        		         System.out.println("2.Credit Money");
		        		         System.out.println("3.Transfer Money");
		        		         System.out.println("4.Check Balance");
		        		         System.out.println("5. log out");
		        		         System.out.println("Enter your choice: ");
		        		         choice2=scan.nextInt();
		        		         switch(choice2) {
		        		            case 1:accountmanager.debit_money(account_number);
		        		                   break;
		        		            case 2:accountmanager.credit_money(account_number);
		        		                   break;
		        		            case 3:accountmanager.transfer_money(account_number);
		        		                   break;
		        		            case 4: accountmanager.getBalnace(account_number);
		        		                   break;
		        		            case 5:break;
		        		            default:System.out.println("Enter valid choice1");
		        		                    break;
		        		          }
		        		        
		        	           }
		                    }
		                    else {
		        	         System.out.println("Incorrect email or password");
		        	   
		                    }
	    		case 3: 
	    			    System.out.println("Thank you for using Banking System");
	    		        System.out.println("Exiting System");
	    		        return;
	    		default:
	    			    System.out.println("Enter a valid choice");
	    			    break;	
	    	
	    	}
	    }
	    
	}catch(Exception e) {
		e.printStackTrace();
	}
}
}
