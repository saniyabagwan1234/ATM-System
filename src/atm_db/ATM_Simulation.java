package atm_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class ATMSimulation
{
	int choice,pin;
	String name;
	long contact;
	long balance=0;
	String url,user,password,query;
	Scanner sc=new Scanner(System.in);
	 int amount;
	
	void CreateAccount() throws ClassNotFoundException, SQLException {
		Scanner sc=new Scanner(System.in);
		System.out.println("*****************=Welcome to Create Account***********************");
		System.out.println("Enter EName: ");
		name=sc.next();
		System.out.println("Enter Pin: ");
		pin=sc.nextInt();
		System.out.println("Enter Balance: ");
		balance=sc.nextLong();
		System.out.println("Enter Contact: ");
		contact=sc.nextLong();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		url="jdbc:mysql://localhost:3306/atmminiproject";
		user="root";
		password="root";
		query="insert into AccountHolder(name,pin,balance,contact)values(?,?,?,?)";
		
		Connection con = DriverManager.getConnection(url,user,password);
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setString(1, name);
		ps.setInt(2, pin);
		ps.setLong(3, balance);
		ps.setLong(4, contact);
		
		int i=ps.executeUpdate();
		
		if(i>0) {
			System.out.println("Account Created Successfully...");
		}else {
			System.out.println("Account Creation Failed...");
		}
	}
	
	void Withdraw() throws ClassNotFoundException, SQLException {
		
		url="jdbc:mysql://localhost:3306/atmminiproject";
		user="root";
		password="root";
		query="Select balance from accountHolder where id=? ";
		
		System.out.println("Enter id : ");
		int id=sc.nextInt();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,user,password);
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, id);
		
		ResultSet rs=ps.executeQuery();
		
		while(rs.next()) {
			System.out.println("Balance Check: ");
			balance=rs.getLong("balance");
			System.out.println(balance);
		}		
	
		System.out.println("Enter the Amount to Withdraw: ");
		
		long amount=sc.nextLong();
		
		if(amount>0 && amount<=balance) {
			balance-=amount;
			//String UpdateQuery;
			String UpdateQuery = "Update  accountholder set balance=? where id=?";
			
			PreparedStatement pr=con.prepareStatement(UpdateQuery);
			pr.setLong(1, balance);
			pr.setInt(2, id);
			int rowUpdate=pr.executeUpdate();
			if(rowUpdate>0) {
				System.out.println("Withdraw Succesfull..."+balance);
			}else {
			System.out.println("Balance failed");
			}		
		}
		else {
			System.out.println("Invalid Withdrawl amount or insufficient balance.");
	}
	}

	void Deposit() throws ClassNotFoundException, SQLException {
		
		url="jdbc:mysql://localhost:3306/atmminiproject";
		user="root";
		password="root";
		query="select * from accountholder where id=? ";
		

		System.out.println("Enter Your id: ");
		int id=sc.nextInt();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,user,password);
		
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1,id);
		
	ResultSet rs=ps.executeQuery();
	while(rs.next()) {
		System.out.println("Balance Check");
		balance=rs.getLong("balance");
		System.out.println(balance);
		
	}
	System.out.println("Enter The Deposit Amount: ");
	long Amount=sc.nextLong();
	balance+=Amount;
	
	String update="update accountholder set balance=? where id=?";
	PreparedStatement pr=con.prepareStatement(update);
	pr.setLong(1, balance);
	pr.setInt(2, id);
	
	int rowUpdate=pr.executeUpdate();
	if(rowUpdate>0) {
		System.out.println("Update Successfully.."+balance);
	}else {
		System.out.println("Update Failed..");
	}
	
	}
	
	void ChangePin() throws ClassNotFoundException, SQLException {
		
		url="jdbc:mysql://localhost:3306/atmminiproject";
		user="root";
		password="root";
		query="select * from accountHolder where id = ? ";
		System.out.println("Enter Your Id: ");
		int id=sc.nextInt();
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con=DriverManager.getConnection(url,user,password);
		PreparedStatement ps=con.prepareStatement(query);
		ps.setInt(1, id);
		
		ResultSet rs=ps.executeQuery();
		while(rs.next()) {
			System.out.println("pin");
		}
		String Query1="Update accountHolder set pin=? where id=?";
		System.out.println("Enter new Pin: ");
		pin=sc.nextInt();
		
		PreparedStatement pr=con.prepareStatement(Query1);
		pr.setInt(1, pin);
		pr.setInt(2, id);
		int updatePin=pr.executeUpdate();
		System.out.println(updatePin+"pin changed..");
}
	
	void checkbalance() throws SQLException, ClassNotFoundException {
		//String url,user,pass;
		//Class.forName("com.mysql.cj.jdbc.Driver");
		url="jdbc:mysql://localhost:3306/atmminiproject";
		user="root";
		password="root";
		query="select * from accountholder where id=? ";
		
		System.out.println("Enter Your id: ");
		int id =sc.nextInt();
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn=DriverManager.getConnection(url,user,password);
		PreparedStatement ps=conn.prepareStatement(query);
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			System.out.println("Balance check");
			System.out.println(rs.getLong("balance"));
		}
	}
		void AccountDelete() throws ClassNotFoundException, SQLException {
			
			url="jdbc:mysql://localhost:3306/atmminiproject";
			user="root";
			password="root";
			query="delete from accountHolder where id=?";
			System.out.println("Enter Your ID : ");
			int id=sc.nextInt();
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection con=DriverManager.getConnection(url,user,password);
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1, id);
			int i=ps.executeUpdate();
			System.out.println(i+"row is delete");
		}
	}
	
public class ATM_Simulation {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
       int choice;
       ATMSimulation atm=new ATMSimulation();
	   
	do {
    	   System.out.println("************************Automated Teller Machine***********************\n"
    	   		+ "Choose 1 for Create Account\n"
    	   		+ "Choose 2 for Withdraw Amount\n"
    	   		+ "Choose 3 for Deposite\n"
    	   		+ "Choose 4 for Check Balance\n"
    	   		+ "Choose 5 for Change Pin\n"
    	   		+ "Choose 6 for account Delete\n"
    	   		+ "Choose 7 for Exit\n"
    	   		+ "Choose the operation you want to perform : ");
    	   
    	   Scanner sc=new Scanner(System.in);
    	   choice=sc.nextInt();
    	   switch(choice) {
    	   case 1:
    		  atm.CreateAccount(); break;
    	   case 2:
    		   atm.Withdraw();break;
    	   case 3:
    		   atm.Deposit();break;
    	   case 4:
    		   atm.checkbalance();break;
    	   case 5:
    		   atm.ChangePin();break;
    	   case 6:
    		   atm.AccountDelete();break;   
    	   case 7:
    		   System.out.println("Exit");
    		   break; 
    		   default:
    			   System.out.println("invalid input.");
    			   break;}
       }while(choice<8);}}
