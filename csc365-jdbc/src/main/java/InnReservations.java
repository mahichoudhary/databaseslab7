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
    }

    private void interpUserInput(Scanner sc) {
        //decide if we want to take it as a string or as a number
        String feature = "";

        while (!feature.equals("0") || !feature.equals("Quit")) {
            feature = sc.nextLine();
            if (feature.equals("1") || feature.equals("Rooms and Rates")){
                //roomsAndRates();
                System.out.println("success");
            }
            else if (feature.equals("2") || feature.equals("Reservations")) {
                reservations();
            }
            else if (feature.equals("3") || feature.equals("Reservation Change")) {
                //rchange();
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
    }


    private void reservCancel() {
        String deletestmt = "";
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
	 						   JDBC_USER,
							   JDBC_PASSWORD)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter your reservation code");
            String rcode = sc.nextLine();
            System.out.println("Please type 'confirm' to confirm the cancellation of your reservation");
            String input = sc.nextLine();
            if(input.equals("confirm"))
                deletestmt = "DELETE FROM lab7_reservations where code = ?";
            
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(deletestmt)){
                pstmt.setString(1, rcode);
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
                stmt.execute("CREATE TABLE lab7_reservations (CODE varchar(15) PRIMARY KEY, Room varchar(15), CheckIn DATE, Checkout DATE, Rate DECIMAL(5, 2), LastName varchar(100), FirstName varchar(100), Adults INTEGER, Kids INTEGER)");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10105', 'HBB', '2010-10-23', '2010-10-25', '100', 'SELBIG', 'CONRAD', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10183', 'IBD', '2010-09-19', '2010-09-20', '150', 'GABLER', 'DOLLIE', '2', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10449', 'RND', '2010-09-30', '2010-10-01', '150', 'KLESS', 'NELSON', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10489', 'AOB', '2010-02-02', '2010-02-05', '218.75', 'CARISTO', 'MARKITA', '2', '1')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10500', 'HBB', '2010-08-11', '2010-08-12', '90', 'YESSIOS', 'ANNIS', '1', '0')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10574', 'FNA', '2010-11-26', '2010-12-03', '287.5', 'SWEAZY', 'ROY', '2', '1')");
                stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10984', 'AOB', '2010-12-28', '2011-01-01', '201.25', 'ZULLO', 'WILLY', '2', '1')");
	    }
	}
    }

}





