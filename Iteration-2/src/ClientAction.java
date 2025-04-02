import java.sql.*;

public class ClientAction {
    public static void registerClient(String email, String password, String affiliation) {
        String query = "INSERT INTO clients (email, password, affiliation, approved) VALUES (?, ?, ?,0)";
        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, affiliation);
            stmt.executeUpdate();
            System.out.println("Signup request sent. Waiting for approval.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User authenticateUser(String email, String password, int role) {

        if(role == 3) {
            if (email.equals("BossMan@gmail.com") && password.equals("TheBoss")) {
                return new Administrator(email,password);
            }
        }

        String table = (role == 1) ? "clients" : (role == 2) ? "experts" : "admins" ;
        String query = "SELECT * FROM " + table + " WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // If user exists
                int id = rs.getInt("id");
                String userEmail = rs.getString("email");

                if (role == 1) {
                    String Password = rs.getString("password");
                    String affiliation = rs.getString("affiliation");
                    boolean approved = rs.getInt("approved") == 1;
                    return new Client(userEmail, Password, affiliation);
                }
                else if (role == 2) {
                    //String expertise = rs.getString("expertise");
                    //return new Expert(id, userEmail, expertise);
                }
                else {
                    //return new Admin(id, userEmail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }

    public static void viewInstitutionOwnedObjects() {
        String query = "SELECT name, description FROM objectsOfInterest WHERE owned = 1";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Objects Owned by the Institution ---");

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String name = rs.getString("name");
                String description = rs.getString("description");
                System.out.println("🔹 " + name + " - " + description);
            }

            if (!hasResults) {
                System.out.println("No objects owned by the institution.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
