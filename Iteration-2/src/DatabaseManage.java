import java.sql.*;

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
        String clientsTable = "CREATE TABLE IF NOT EXISTS clients (id INTEGER PRIMARY KEY, email TEXT, password TEXT, affiliation TEXT, approved INTEGER DEFAULT 0);";
        String expertsTable = "CREATE TABLE IF NOT EXISTS experts (id INTEGER PRIMARY KEY, email TXT, name TEXT, license_number TEXT UNIQUE, expertise TEXT);";
        String auctionsTable = "CREATE TABLE IF NOT EXISTS auctions (id INTEGER PRIMARY KEY, email TEXT, name TEXT, date TEXT, auction_house_id INTEGER, specialty TEXT);";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(usersTable);
            stmt.execute(clientsTable);
            stmt.execute(expertsTable);
            stmt.execute(auctionsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
