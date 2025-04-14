package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManage {
    private static final String URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");  // Ensure SQLite driver is loaded
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void initialize() {
        String usersTable = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, email TEXT, password TEXT, role TEXT);";
        String clientsTable = "CREATE TABLE IF NOT EXISTS clients (id INTEGER PRIMARY KEY, email TEXT, password TEXT, affiliation TEXT, intent TEXT, approved INTEGER DEFAULT 0);";
        String expertsTable = "CREATE TABLE IF NOT EXISTS experts (id INTEGER PRIMARY KEY, email TXT, password TEXT, license_number TEXT UNIQUE, expertise TEXT);";
        String auctionHouse = "CREATE TABLE IF NOT EXISTS auctionHouse(id INTEGER PRIMARY KEY, name TEXT, location TEXT);";
        String ObjectsOfInterest = "CREATE TABLE IF NOT EXISTS objectsOfInterest (id INTEGER PRIMARY KEY, name TEXT, description TEXT, owned BOOLEAN, auction boolean);";
        String auctions = "CREATE TABLE IF NOT EXISTS auctions (" +
                "    id INTEGER PRIMARY KEY," +
                "    name TEXT," +
                "    auction_house TEXT," +
                "    location TEXT," +
                "    date TEXT, " +
                "    start_time TEXT, " +
                "    end_time TEXT, " +
                "    specialty TEXT" +
                ");";



        String serviceRequestsTable = "CREATE TABLE IF NOT EXISTS service_requests ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "client_email TEXT NOT NULL, "
                + "expert_email TEXT NOT NULL, "
                + "requested_date TEXT NOT NULL, "
                + "requested_time TEXT NOT NULL, "
                + "status TEXT DEFAULT 'Pending', "
                + "FOREIGN KEY ( client_email) REFERENCES clients (email), "
                + "FOREIGN KEY (expert_email) REFERENCES experts (email)"
                + ");";


        String expertAvailabilityTable = "CREATE TABLE IF NOT EXISTS expert_availability ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "expert_email TEXT NOT NULL, "
                + "available_date TEXT NOT NULL, "  // Format: YYYY-MM-DD
                + "available_time TEXT NOT NULL, "  // Format: HH:MM
                + "booked INTEGER DEFAULT 0, "  // 0 = Available, 1 = Booked
                + "FOREIGN KEY (expert_email) REFERENCES experts (email)"
                + ");";




        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(clientsTable);
            stmt.execute(expertsTable);
            stmt.execute(auctionHouse);
            stmt.execute(ObjectsOfInterest);
            stmt.execute(auctions);
            stmt.execute(serviceRequestsTable);
            stmt.execute(expertAvailabilityTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
