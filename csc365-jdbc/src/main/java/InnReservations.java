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
            while (true) {
                displayOptions();
                feature = sc.nextLine();
                if (feature.equals("1") || feature.equals("Rooms and Rates")){
                    roomsAndRates();
                    System.out.println("success");
                }
                else if (feature.equals("2") || feature.equals("Reservations")) {
                    //reservations();
                }
                else if (feature.equals("3") || feature.equals("Reservation Change")) {
                    rchange();
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

    private void roomsAndRates() throws SQLException
    {
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
                                   JDBC_USER,
                                   JDBC_PASSWORD)) {

            LocalDate today = java.time.LocalDate.now();

            String sql = "SELECT RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor, Checkout, next_begin FROM lab7_rooms LEFT OUTER JOIN (SELECT Room, CheckIn, Checkout FROM lab7_reservations WHERE CheckIn < ? AND Checkout > ?) AS B ON B.Room = RoomCode LEFT OUTER JOIN (SELECT Room, Min(CheckIn) AS next_begin FROM lab7_reservations WHERE ? < CheckIn GROUP BY Room) AS C ON C.Room = RoomCode";

            // Step 3: (omitted in this example) Start transaction
            conn.setAutoCommit(false);


            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setDate(1, java.sql.Date.valueOf(today));
                stmt.setDate(2, java.sql.Date.valueOf(today));
                stmt.setDate(3, java.sql.Date.valueOf(today));

                try (ResultSet rs = stmt.executeQuery()) {

                    // Step 5: Receive results
                    while (rs.next()) {
                        String roomCode = rs.getString("RoomCode");
                        String roomName = rs.getString("RoomName");
                        int beds = rs.getInt("Beds");
                        String bedType = rs.getString("bedType");
                        int maxOcc = rs.getInt("maxOcc");
                        float basePrice = rs.getFloat("basePrice");
                        String decor = rs.getString("decor");
                        java.sql.Date cOut = rs.getDate("Checkout");
                        java.sql.Date nextBegin = rs.getDate("next_begin");
                        
                        if (cOut == null)
                        {
                            if (nextBegin == null)
                            {
                                System.out.format("%s %s %d %s %d ($%.2f) %s Today None %n", roomCode, roomName, beds, bedType, maxOcc, basePrice, decor); 
                            } else {
                                System.out.format("%s %s %d %s %d ($%.2f) %s Today %tF %n", roomCode, roomName, beds, bedType, maxOcc, basePrice, decor, nextBegin.toLocalDate()); 
                            }
                                              
                        }
                        else {
                            
                            if (nextBegin == null)
                            {
                                System.out.format("%s %s %d %s %d ($%.2f) %s %tF None %n", roomCode, roomName, beds, bedType, maxOcc, basePrice, decor, cOut.toLocalDate());
                            } else {
                                System.out.format("%s %s %d %s %d ($%.2f) %s %tF %tF %n", roomCode, roomName, beds, bedType, maxOcc, basePrice, decor, cOut.toLocalDate(), nextBegin.toLocalDate());
                            }
                            
                        }
                    }
                }
                conn.commit();
            } catch (SQLException e) {
                System.out.println(e);
                conn.rollback();
            }
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
            String input = "";
            String first = "";
            String last = "";
            int numchild = 0;
            int numadult = 0;

            System.out.println("Please enter reservation code");
            int rcode = Integer.parseInt(sc.nextLine());
            
            while (true) {
                displayChangeOptions();            
                input = sc.nextLine();
                if (input.equals("0") || input.equals("Quit")) {
                    break;
                }
                if (input.equals("1")) {
                    System.out.println("Enter edit for firstname");
                    first = sc.nextLine();    

                    updateSql = "UPDATE lab7_reservations SET FirstName = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, first);
                    pstmt.setInt(2, rcode);
                    
                    // Step 5: Handle results
                    System.out.format("Updated first name to %s for reservation %d%n%n", first, rcode);

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (input.equals("2")) {
                    System.out.println("Enter edit for lastname");
                    last = sc.nextLine(); 
                    updateSql = "UPDATE lab7_reservations SET LastName = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setString(1, last);
                    pstmt.setInt(2, rcode);
                    
                    // Step 5: Handle results
                    System.out.format("Updated last name to %s for reservation %d%n%n", last, rcode);

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else if (input.equals("3")) {
                    System.out.println("Enter edit for reservation begin date (YYYY-MM-DD)"); 
                    LocalDate begin = LocalDate.parse(sc.nextLine());
                    updateSql = "UPDATE lab7_reservations SET CheckIn = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);

                    String querySQL = "SELECT * FROM lab7_reservations WHERE Room = (SELECT Room FROM lab7_reservations WHERE CODE = ? ) AND Checkout > ? AND CheckIn < (SELECT Checkout FROM lab7_reservations WHERE CODE = ? ) AND CODE != ? ";                    

                    try (PreparedStatement pstmtq = conn.prepareStatement(querySQL)) {
                        pstmtq.setInt(1, rcode);
                        pstmtq.setDate(2, java.sql.Date.valueOf(begin));
                        pstmtq.setInt(3, rcode);
                        pstmtq.setInt(4, rcode);
                        try (ResultSet rs = pstmtq.executeQuery()) {
                            if (rs.next()) //if next is not false
                            {
                                // not empty so conflict
                                System.out.println("Unable to edit: Begin Date conflicts with another reservation");
                                System.out.println();
                            }
                            else {
                                // if empty, no conflicts
                                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                                
                                // Step 4: Send SQL statement to DBMS
                                pstmt.setDate(1, java.sql.Date.valueOf(begin));
                                pstmt.setInt(2, rcode);
                                
                                // Step 5: Handle results
                                System.out.format("Updated begin date to %tF for reservation %d%n%n", begin, rcode);

                                // Step 6: Commit or rollback transaction
                                conn.commit();
                                } catch (SQLException e) {
                                    conn.rollback();
                                }
                            }
                        }
                    }
                }
                else if (input.equals("4")) {
                    System.out.println("Enter edit for reservation end date (YYYY-MM-DD)"); 
                    LocalDate end = LocalDate.parse(sc.nextLine()); 
                    updateSql = "UPDATE lab7_reservations SET Checkout = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);

                    String querySQL = "SELECT * FROM lab7_reservations WHERE Room = (SELECT Room FROM lab7_reservations WHERE CODE = ? ) AND Checkout > (SELECT CheckIn FROM lab7_reservations WHERE CODE = ? ) AND CheckIn < ? AND CODE != ? ";

                    try (PreparedStatement pstmtq = conn.prepareStatement(querySQL)) {
                        pstmtq.setInt(1, rcode);
                        pstmtq.setInt(2, rcode);
                        pstmtq.setDate(3, java.sql.Date.valueOf(end));
                        pstmtq.setInt(4, rcode);
                        try (ResultSet rs = pstmtq.executeQuery()) {
                            if (rs.next()) //if next is not false
                            {
                                // not empty so conflict
                                System.out.println("Unable to edit: End Date conflicts with another reservation");
                                System.out.println();
                            }
                            else {
                                try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                                
                                // Step 4: Send SQL statement to DBMS
                                pstmt.setDate(1, java.sql.Date.valueOf(end));
                                pstmt.setInt(2, rcode);
                                
                                // Step 5: Handle results
                                System.out.format("Updated end date to %tF for reservation %d%n%n", end, rcode);

                                // Step 6: Commit or rollback transaction
                                conn.commit();
                                } catch (SQLException e) {
                                    conn.rollback();
                                }
                            }
                        }
                    }
                }
                else if (input.equals("5")) {
                    System.out.println("Enter edit for the number of children"); 
                    numchild = Integer.parseInt(sc.nextLine());
                    updateSql = "UPDATE lab7_reservations SET Kids = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setInt(1, numchild);
                    pstmt.setInt(2, rcode);
                    
                    // Step 5: Handle results
                    System.out.format("Updated number of children to %d for reservation %d%n%n", numchild, rcode);

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        System.out.println("error");
                        conn.rollback();
                    }
                }
                else if (input.equals("6")) {
                    System.out.println("Enter edit the number of adults"); 
                    numadult = Integer.parseInt(sc.nextLine());
                    updateSql = "UPDATE lab7_reservations SET Adults = ? WHERE CODE = ?";
                    
                    // Step 3: Start transaction
                    conn.setAutoCommit(false);
                    
                    try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                    
                    // Step 4: Send SQL statement to DBMS
                    pstmt.setInt(1, numadult);
                    pstmt.setInt(2, rcode);
                    
                    // Step 5: Handle results
                    System.out.format("Updated number of adults to %d for reservation %d%n%n", numadult, rcode);

                    // Step 6: Commit or rollback transaction
                    conn.commit();
                    } catch (SQLException e) {
                        conn.rollback();
                    }
                }
                else {
                    System.out.println("Please enter one of the number options [0-6] to edit.");
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
        System.out.println("[5] Edit number of Children");
        System.out.println("[6] Edit number of Adults");
        System.out.println();
    }

    private void reservCancel() throws SQLException {
        String deletestmt = "";
        try (Connection conn = DriverManager.getConnection(JDBC_URL,
                               JDBC_USER,
                               JDBC_PASSWORD)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please enter your reservation code");

            int rcode = Integer.parseInt(sc.nextLine());
            System.out.println("Please type 'confirm' to confirm the cancellation of your reservation or anything else to abort");
            String input = sc.nextLine();
            if(input.equals("confirm"))
                deletestmt = "DELETE FROM lab7_reservations where code = ?";
            
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement(deletestmt)){
                pstmt.setInt(1, rcode);
                int rowCount = pstmt.executeUpdate();

                System.out.format("Removed %d reservation with code %s%n%n", rowCount, rcode);
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
            stmt.execute("CREATE TABLE lab7_reservations (CODE int(10) PRIMARY KEY, Room varchar(15), CheckIn DATE, Checkout DATE, Rate float, LastName varchar(100), FirstName varchar(100), Adults INTEGER, Kids INTEGER)");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10105', 'HBB', '2010-10-23', '2010-10-25', '100', 'SELBIG', 'CONRAD', '1', '0')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10183', 'IBD', '2010-09-19', '2010-09-20', '150', 'GABLER', 'DOLLIE', '2', '0')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10449', 'RND', '2010-09-30', '2010-10-01', '150', 'KLESS', 'NELSON', '1', '0')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10489', 'AOB', '2010-02-02', '2010-02-05', '218.75', 'CARISTO', 'MARKITA', '2', '1')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10500', 'HBB', '2010-08-11', '2010-08-12', '90', 'YESSIOS', 'ANNIS', '1', '0')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10574', 'FNA', '2010-11-26', '2010-12-03', '287.5', 'SWEAZY', 'ROY', '2', '1')");
            stmt.execute("INSERT INTO lab7_reservations (CODE, Room, CheckIn, Checkout, Rate, LastName, FirstName, Adults, Kids) VALUES ('10984', 'AOB', '2010-12-28', '2011-01-01', '201.25', 'ZULLO', 'WILLY', '2', '1')");

            stmt.execute("DROP TABLE IF EXISTS lab7_rooms");
            stmt.execute("CREATE TABLE lab7_rooms (RoomCode char(5) PRIMARY KEY, RoomName varchar(30), Beds int(11), bedType varchar(8), maxOcc int(11), basePrice float, decor varchar(20))");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('AOB', 'Abscond or bolster', '2', 'Queen', '4', '175', 'traditional')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('CAS', 'Convoke and sanguine', '2', 'King', '4', '175', 'traditional')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('FNA', 'Frugal not apropos', '2', 'King', '4', '250', 'traditional')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('HBB', 'Harbinger but bequest', '1', 'Queen', '2', '100', 'modern')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('IBD', 'Immutable before decorum', '2', 'Queen', '4', '150', 'rustic')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('IBS', 'Interim but salutary', '1', 'King', '2', '150', 'traditional')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('MWC', 'Mendicant with cryptic', '2', 'Double', '4', '125', 'modern')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('RND', 'Recluse and defiance', '1', 'King', '2', '150', 'modern')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('RTE', 'Riddle to exculpate', '2', 'Queen', '4', '175', 'rustic')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('SAY', 'Stay all year (added May 19th)', '1', 'Queen', '3', '100', 'modern')");
            stmt.execute("INSERT INTO lab7_rooms (RoomCode, RoomName, Beds, bedType, maxOcc, basePrice, decor) VALUES ('TAA', 'Thrift and accolade', '1', 'Double', '2', '75', 'modern')");            
        }
    }
    }
}