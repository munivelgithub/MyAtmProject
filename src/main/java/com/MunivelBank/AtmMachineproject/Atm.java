package com.MunivelBank.AtmMachineproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class Atm {  public static void main(String [] args) {
	  
	  
	  
	  //so we need to create an try and catch method 
	  try {
		 
//	       
	  
		  // getting an input from the user
          System.out.println("Hey welcome to all-in-one ATM ");
		  Scanner sc=new Scanner(System.in);
		  System.out.println("Press 1 to Create an Account");
		  System.out.println("Press 2 to Processed transaction");
		  int Create=sc.nextInt();
		  if(Create == 1) {
			  try {
				  Class.forName("com.mysql.cj.jdbc.Driver");
			      Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Atm","root","Munivel@9787");
			      
				  int length=4; 
				  Scanner scanner=new Scanner(System.in);
				  System.out.println("Welcome to Our Bank ");
				
				  System.out.println("Enter Account Holder Name :");
				  String name=scanner.nextLine();
				
				  System.out.println("Enter the Account Number :");
				 
				  long Accountnumber=scanner.nextLong(13);
				  System.out.println("Enter the Four Digit Pin Password :");
				  scanner.nextLine();
				  String  pins=scanner.nextLine();
				  System.out.println("Enter the Confirm Pin");
				  
				  String  cpins=scanner.nextLine();
				  System.out.println();
				  
				  System.out.println("Deposit minimum amount 500 for your newly created Account");
				  int balance=sc.nextInt();
				  
				  String query="insert into Atmuser values (?,?,?,?,?)";
				  if(pins.equals(cpins) && (pins.length()== length && cpins.length()==length)){
					
					  
					  PreparedStatement ps=connection.prepareStatement(query);
					  
					  ps.setString(1, name);
					  ps.setLong(2, Accountnumber);
					  ps.setString(3,pins);
					  ps.setString(4, cpins);
					  ps.setInt(5, balance);
					  int row=ps.executeUpdate();
					  if(row >0) {
						  System.out.println("Account Created Successfully...");
					  }
					 
				  }else {
					  System.out.println("Make sure the entered Pin invalid...");
				  }
				  
			  }catch(Exception e) {
				  System.out.println("Enter the valid data's");
			  }
			  
		  }else if (Create == 2) {
			   Class.forName("com.mysql.cj.jdbc.Driver");
		       Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Atm","root","Munivel@9787");
	          Statement st=con.createStatement();
			  System.out.println("Enter your pin number");
			  int pin=sc.nextInt();
		       sc.nextLine();
			 
			  ResultSet rs=st.executeQuery("select * from Atmuser where pins="+pin);
			   int balance=0;
			   int count=0;
			   String name=null;
			   while(rs.next()) {
				   name=rs.getString(1);
				   balance=rs.getInt(5);
				   count++;
			   }
			  int amount=0;
				  int take_amount=0;
				  if(count>0) {
					  System.out.println("Hello "+name);
					  while(true) {// it will continuously loop until the user get exit
						  System.out.println("Press 1 to Check Balance");
						  System.out.println("Press 2 to Add amount");
						  System.out.println("Press 3 to Withdrawal Amount");
						  System.out.println("Press 4 to Print the Receipt");
						  System.out.println("Press 5 to Exit");
						  int ch=0;
						  System.out.println();
						  System.out.println("Enter your choice");
						   ch=sc.nextInt();
						
						switch(ch) {
							  case 1:
								  System.out.println();
								  System.out.println("Your balance is :"+balance);
								  break;
							  case 2:
								  System.out.println("Enter the amount to Add");
								  amount=sc.nextInt();
								 
								  balance =balance+amount;
								  
								   int addedbalance=st.executeUpdate("update Atmuser set balance="+balance+" where pins="+pin);
								  System.out.println("Successfully added now your current amount is :"+balance );
								  break;
							  case 3:
								  String result =NetworkSpeedChecker. Widhrawlcheck();
								  if(result.equals("Network conditions are acceptable. You can proceed with the transaction.")) {
									  System.out.println("Network conditions are acceptable. You can proceed with the transaction.");
									  System.out.println("Enter the amount to take ");
									  take_amount=sc.nextInt();
									  if(take_amount >balance) {
										  System.out.println("Your balance is insufficient");
									  }else{
										  balance =balance-take_amount;
										 // int sub =st.executeUpdate("update list set balance ="+balance+"WHERE ac_number ="+pin);
										  //int sub=st.executeUpdate("update Atmuser set balance="+balance+"where pins="+pin);
										  int sub=st.executeUpdate("update Atmuser set balance="+balance+" where pins="+pin);

										  System.out.println("Successfully withdrawd now your current balance is:"+balance);
									  }
									 
								  }else if(result.equals("Alert: Network speed is too low. Avoid taking funds.")) {
									  System.out.println();
									  System.out.println("Alert: Network speed is too low. Avoid taking funds.");
								  }
							

								  
								  break;
							  case 4:
								  System.out.println("Thank you for coming");
								  System.out.println("Your current balance is :"+balance);
								  System.out.println("Amount add :"+amount);
								  System.out.println("Amount taken :"+take_amount);
								
								  break;
							default :
								System.out.println();
								break;
							  }
							  if(ch ==5) {
								  break;
							  }
							  
						  }
						   
					  }else {
						  System.out.println("Wrong pin number");
					  }
					  
						 
			  
			  }
		
			 

		  
	  }catch(Exception e) {
		  System.out.println("Error occured ");
	  }
  }
}

