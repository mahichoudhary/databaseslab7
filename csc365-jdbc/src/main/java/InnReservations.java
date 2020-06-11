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
            Scanner scanner = new Scanner(System.in);
            ir.displayOptions();
            ir.interpUserInput(scanner);
        } catch (SQLException e) {
            System.err.println("SQLException: " + e.getMessage());
        }
    }

    private static void displayOptions(){
        System.out.println("Main Menu Features:");
        System.out.println("[0] Quit");
        System.out.println("[1] Rooms and Rates");
        System.out.println("[2] Reservations");
        System.out.println("[3] Reservation Change");
        System.out.println("[4] Reservation Cancellation");
        System.out.println("[5] Revenue Summary");
        System.out.println();
    }

    private void interpUserInput(Scanner sc) {
        //decide if we want to take it as a string or as a number
        String feature = "";
        try {
            while (!feature.equals("0") || !feature.equals("Quit")) {
                feature = sc.nextLine();
                if (feature.equals("1") || feature.equals("Rooms and Rates")){
                    //roomsAndRates();
                    System.out.println("success");
                }
                else if (feature.equals("2") || feature.equals("Reservations")) {
                    //reservations();
                }
                else if (feature.equals("3") || feature.equals("Reservation Change")) {
                    rchange();
                    System.out.println("success");
                }
                else if (feature.equals("4") || feature.equals("Reservation Cancellation")) {
                    reservCancel();
                }
                else if (feature.equals("5") || feature.equals("Revenue Summary")) {
                    //revenue();
                    System.out.println("success");
                }
                else if (feature.equals("0") || feature.equals("Quit")) {
                    break;
                }
                else {
                    System.out.println("Please enter one of the options above.");
                }
            }
            
        } catch (SQLException e) {
	        System.err.println("SQLException: " + e.getMessage());
	    }
    }

    // private void reservations(){
    //     try (Connection conn = DriverManager.getConnection(JDBC_URL,
    //                         JDBC_USER,
    //                         JDBC_PASSWORD)){
    //     Scanner sc = new Scanner(System.in);
    //     //prompt for firstname
    //     String query = "";
    //     String first = "";
    //     String last = "";
    //     String code = "";
    //     String begin = "";
    //     String end = "";
    //     String roomname = "";
    //     String bedtype = "";
    //     int numchild = 0;
    //     int numadult = 0;
    //     boolean availability = false;
        
    //     System.out.println("Please enter your firstname");
    //     first = sc.nextLine();
    //     System.out.println("Please enter your lastname");
    //     last = sc.nextLine();
    //     System.out.println("Please enter your room code"); 
    //     code = sc.nextLine(); 
    //     System.out.println("Please enter your begin date of stay"); 
    //     begin = sc.nextLine(); 
    //     System.out.println("Please enter your end date of stay"); 
    //     end = sc.nextLine();
    //     System.out.println("Please enter the number of children"); 
    //     numchild = sc.nextInt();
    //     System.out.println("Please enter the number of adults"); 
    //     numadult = sc.nextInt();
    //                         }
    // }

    private void rchange() throws SQLException {
         try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)) {
            Scanner sc = new Scanner(System.in);

            String updateSql = "";

            String feature = "";
            String query = "";
            String first = "";
            String last = "";
            String begin = "";
            String end = "";
            int numchild = 0;
            int numadult = 0;

            System.out.println("Please enter your reservation code");
            int rcode = sc.nextInt();

            while (!feature.equals("0") || !feature.equals("Quit")) {
                displayChangeOptions();
                feature = sc.nextLine();
                if (feature.equals("1")) {
                    System.out.println("Please edit your firstname");
                    first = sc.nextLine();                    
                    updateSql = "UPDATE lab7_reservations SET FirstName = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, first);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, first);
                    System.out.println();

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (feature.equals("2")) {
                    System.out.println("Please edit your lastname");
                    last = sc.nextLine(); 
                    updateSql = "UPDATE lab7_reservations SET LastName = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, last);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, last);
                    System.out.println();

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (feature.equals("3")) {
                    System.out.println("Please edit your begin date of stay"); 
                    begin = sc.nextLine(); 
                    updateSql = "UPDATE lab7_reservations SET CheckIn = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, begin);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, begin);
                    System.out.println();

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (feature.equals("4")) {
                    System.out.println("Please edit your end date of stay"); 
                    end = sc.nextLine();
                    updateSql = "UPDATE lab7_reservations SET Checkout = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, end);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, end);
                    System.out.println();
                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (feature.equals("5")) {
                    System.out.println("Please edit the number of children"); 
                    numchild = sc.nextInt();
                    updateSql = "UPDATE lab7_reservations SET children = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setInt(1, numchild);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, numchild);
                    System.out.println();

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (feature.equals("6")) {
                    System.out.println("Please edit the number of adults"); 
                    numadult = sc.nextInt();
                    updateSql = "UPDATE lab7_reservations SET adults = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setInt(1, numadult);
                    pstmt.setInt(2, rcode);
                    //int rowCount = pstmt.executeUpdate();
                    
                    // Step 5: Handle results
                    System.out.format("Reservation %d first name to %s%n", rcode, numadult);
                    System.out.println();

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else {
                    System.out.println("Please enter one of the number options above to edit [0-6].");
                }
            }
        }
    }

    private static void displayChangeOptions(){
        System.out.println("Edit Reservation Values:");
        System.out.println("[0] Finish editing and exit");
        System.out.println("[1] Edit First Name");
        System.out.println("[2] Edit Last Name");
        System.out.println("[3] Edit Begin Date");
        System.out.println("[4] Edit End Date");
        System.out.println("[5] Edit number of Adults");
        System.out.println();
    }

    private void reservCancel() throws SQLException {
        String deletestmt = "";
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter your reservation code");

            int rcode = sc.nextInt();
            System.out.println("Please type 'confirm' to confirm the cancellation of your reservation");
            String input = sc.nextLine();
            if(input.equals("confirm"))
                deletestmt = "DELETE FROM lab7_reservations where code = ?";
            
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(deletestmt)){
                pstmt.setInt(1, rcode);
                int rowCount = pstmt.executeUpdate();

                System.out.format("Removed %d reservation with code %s", rowCount, rcode);
                conn.commit(); 
            }           
            
            catch (SQLException e){
                conn.rollback();
            }    
        } 

        catch (SQLException e){
            System.out.print("connection not made w database");
        } 
    }

    private void initDb() throws SQLException {
	try (Connection conn = DriverManager.getConnection(JDBC_URL,
							   JDBC_USER,
							   JDBC_PASSWORD)) {
	    try (Statement stmt = conn.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS lab7_reservations");
                stmt.execute("CREATE TABLE lab7_reservations (CODE int(10) PRIMARY KEY, Room varchar(15), CheckIn DATE, Checkout DATE, Rate DECIMAL(5, 2), LastName varchar(100), FirstName varchar(100), Adults INTEGER, Kids INTEGER)");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10105', 'HBB', '2010-10-23', '2010-10-25', '100', 'SELBIG', 'CONRAD', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10183', 'IBD', '2010-09-19', '2010-09-20', '150', 'GABLER', 'DOLLIE', '2', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10449', 'RND', '2010-09-30', '2010-10-01', '150', 'KLESS', 'NELSON', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10489', 'AOB', '2010-02-02', '2010-02-05', '218.75', 'CARISTO', 'MARKITA', '2', '1')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10500', 'HBB', '2010-08-11', '2010-08-12', '90', 'YESSIOS', 'ANNIS', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10574', 'FNA', '2010-11-26', '2010-12-03', '287.5', 'SWEAZY', 'ROY', '2', '1')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10984', 'AOB', '2010-12-28', '2011-01-01', '201.25', 'ZULLO', 'WILLY', '2', '1')");


                stmt.execute("DROP TABLE IF EXISTS lab7_rooms");
                

	    }
	}
    }

}