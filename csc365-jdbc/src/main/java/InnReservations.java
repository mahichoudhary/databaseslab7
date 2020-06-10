import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import java.util.Map;
import java.util.Scanner;
import java.util.LinkedHashMap;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class InnReservations {
    
    private final String JDBC_URL = "jdbc:h2:~/csc365_lab7";
    private final String JDBC_USER = "";
    private final String JDBC_PASSWORD = "";

    public static void main(String[] args) {
        try {
            InnReservations ir = new InnReservations();
                ir.initDb();
                ir.displayOptions();
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    private static void displayOptions(){
        System.out.println("Main Menu Features:");
        System.out.println("[1] Rooms and Rates");
        System.out.println("[2] Reservations");
        System.out.println("[3] Reservation Change");
        System.out.println("[4] Reservation Cancellation");
        System.out.println("[5] Revenue Summary");
    }

    // private static void interpUserInput(Scanner sc) {
    //     Scanner sc = new Scanner(System.in); 
    //     displayOptions();
    //     //decide if we want to take it as a string or as a number
    //     String feature = sc.nextLine(); 
    //     if (feature == "1" || "Rooms and Rates"){
    //         roomsAndRates();
    //     }
    //     if(feature == "2" || "Reservations"){
    //         reservations();
    //     }
    //     if(feature == "3" || "Reservation Change"){
    //         rchange();
    //     }
    //     if(feature == "4" || "Reservation Cancellation"){
    //         rcancel();
    //     }
    //     if(feature == "5" || "Revenue Summary"){
    //         revenue();
    //     }
    //     else{
    //         //some kind of error handeling
    //     }

    // }

 //    private void demo2() throws SQLException {

	// // Step 1: Establish connection to RDBMS
	// try (Connection conn = DriverManager.getConnection(JDBC_URL,
	// 						   JDBC_USER,
	// 						   JDBC_PASSWORD)) {
	//     // Step 2: Construct SQL statement
	//     String sql = "SELECT * FROM hp_goods";

	//     // Step 3: (omitted in this example) Start transaction

	//     // Step 4: Send SQL statement to DBMS
	//     try (Statement stmt = conn.createStatement();
	// 	 ResultSet rs = stmt.executeQuery(sql)) {

	// 	// Step 5: Receive results
	// 	while (rs.next()) {
	// 	    String flavor = rs.getString("Flavor");
	// 	    String food = rs.getString("Food");
	// 	    float price = rs.getFloat("Price");
	// 	    System.out.format("%s %s ($%.2f) %n", flavor, food, price);
	// 	}
	//     }

	//     // Step 6: (omitted in this example) Commit or rollback transaction
	// }
	// // Step 7: Close connection (handled by try-with-resources syntax)
 //    }
    private void reservations(){
         try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)){
            Scanner sc = new Scanner(System.in);
            //prompt for firstname
            System.out.println("Please enter your firstname");
            String first = scanner.nextLine();
            System.out.println("Please enter your lastname");
            String last = scanner.nextLine();
            System.out.println("Please enter your room code"); 
            String code = scanner.nextLine(); 
            System.out.println("Please enter your begin date of stay"); 
            String begin = scanner.nextLine(); 
            System.out.println("Please enter your end date of stay"); 
            String end = scanner.nextLine();
            System.out.println("Please enter the number of children"); 
            int numchild = scanner.nextInt();
            System.out.println("Please enter the number of adults"); 
            int numadult = scanner.nextInt();

            //check availability
            //create sql statement
            //print confirmation
    }


    private void reservChange(){
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)){
            Scanner sc = new Scanner(System.in);
            //prompt for firstname
            System.out.println("Please enter your firstname");
            String first = scanner.nextLine();
            System.out.println("Please enter your lastname");
            String last = scanner.nextLine();
            System.out.println("Please enter your new begin date(for example 2010-10-07) or type 'no change' to indicate no change to this field"); 
            String begin = scanner.nextLine(); 
            if(!begin.equals("no change")){
                //parse 
            }
            System.out.println("Please enter your new end date(for example 2010-10-16) or type 'no change' to indicate no change to this field"); 
            String end = scanner.nextLine(); 
            if(!begin.equals("no change")){
                //parse 
            }
            System.out.println("Please enter the number of children"); 
            String numchild = scanner.nextLine();
            System.out.println("Please enter the number of adults"); 
            String numadult = scanner.nextLine();

            //call create statement to make sql statement
            //rs.get for all variables
            //check availability

                               }

    private void reservCancel(){
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)){
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter your reservation code");
            int rcode = scanner.nextInt();
            System.out.println("Please type 'confirm' to confirm the cancellation of your reservation");
            String input = new Scanner(System.in);
            if(input.equals("confirm"))
                String deletestmt = "DELETE FROM lab7_reservations where code = ?";
            
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.PreparedStatement(deletestmt)){
                pstmt.setString(1, rcode);
                int rowCount = pstmt.executeUpdate();

                System.out.format("Removed %d reservation with code %s", rowCount, rcode);
                conn.commit();            
            
            catch(SQLException e){
                conn.rollback();
            }

            catch(SQLException e){
                System.out.print("connection not made w database");
            }

            }
            


    }


    }
    private void initDb() throws SQLException {
	try (Connection conn = DriverManager.getConnection(JDBC_URL,
							   JDBC_USER,
							   JDBC_PASSWORD)) {
	    try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS reservations");
                stmt.execute("CREATE TABLE reservations (﻿CODE varchar(15) PRIMARY KEY, Room varchar(15), CheckIn DATE, Checkout DATE, Rate DECIMAL(5, 2), LastName varchar(100), FirstName varchar(100), Adults INTEGER, Kids INTEGER)");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10105', 'HBB', '2010-10-23', '2010-10-25', '100', 'SELBIG', 'CONRAD', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10183', 'IBD', '2010-09-19', '2010-09-20', '150', 'GABLER', 'DOLLIE', '2', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10449', 'RND', '2010-09-30', '2010-10-01', '150', 'KLESS', 'NELSON', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10489', 'AOB', '2010-02-02', '2010-02-05', '218.75', 'CARISTO', 'MARKITA', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10500', 'HBB', '2010-08-11', '2010-08-12', '90', 'YESSIOS', 'ANNIS', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10574', 'FNA', '2010-11-26', '2010-12-03', '287.5', 'SWEAZY', 'ROY', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10984', 'AOB', '2010-12-28', '2011-01-01', '201.25', 'ZULLO', 'WILLY', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10990', 'CAS', '2010-09-21', '2010-09-27', '175', 'TRACHSEL', 'DAMIEN', '1', '3')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11631', 'FNA', '2010-04-10', '2010-04-12', '312.5', 'ESPINO', 'MARCELINA', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11645', 'IBD', '2010-05-13', '2010-05-19', '135', 'SWAIT', 'DAN', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11703', 'IBD', '2010-09-10', '2010-09-11', '172.5', 'HAVIS', 'SHERILYN', '2', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11718', 'CAS', '2010-03-18', '2010-03-19', '157.5', 'GLIWSKI', 'DAN', '2', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11857', 'IBD', '2010-10-27', '2010-10-29', '187.5', 'HARDINA', 'LORITA', '4', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('11996', 'IBS', '2010-09-14', '2010-09-16', '187.5', 'BURBANK', 'ROBERT', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12085', 'IBD', '2010-09-04', '2010-09-08', '135', 'GLASGLOW', 'EMMANUEL', '2', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12138', 'IBS', '2010-02-28', '2010-03-05', '150', 'SHARIAT', 'JARRED', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12142', 'RTE', '2010-08-13', '2010-08-23', '175', 'JUNOR', 'LENNY', '3', '1')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12258', 'AOB', '2010-04-23', '2010-04-27', '175', 'KANNEL', 'RODGER', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12631', 'CAS', '2010-04-24', '2010-04-26', '175', 'ONEEL', 'PASQUALE', '1', '0')");
                stmt.execute("INSERT INTO reservations (﻿CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('12686', 'HBB', '2010-07-22', '2010-07-25', '85', 'GROWNEY', 'MELVIN', '2', '0')");
	    }
	}
    }

}





