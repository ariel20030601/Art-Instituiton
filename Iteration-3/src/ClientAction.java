import java.sql.*;

public class ClientAction {
    public static void registerClient(String email, String password, String affiliation, String intent) {
        String query = "INSERT INTO clients (email, password, affiliation, intent, approved) VALUES (?, ?, ?, ?, 0)";
        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, affiliation);
            stmt.setString(4, intent);
            stmt.executeUpdate();
            System.out.println("Signup request sent. Waiting for approval.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
