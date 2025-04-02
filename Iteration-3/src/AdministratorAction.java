import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AdministratorAction {
    public static void registerObject(String Name, String Description, Boolean owned, Boolean Auctioned) {
        String query = "INSERT INTO objectsOfInterest (name, description, owned, auction) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, Name);
            stmt.setString(2, Description);
            stmt.setBoolean(3, owned);
            stmt.setBoolean(4, Auctioned);
            stmt.executeUpdate();
            System.out.println("Successful registration of object.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void approveClient(String ClientEmail) {
        String query = "UPDATE clients SET approved = 1 WHERE email = ? AND approved = 0";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ClientEmail);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client approved successfully!");
            } else {
                System.out.println("Client was not approved successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void showUnapprovedClients() {
        String query = "SELECT * FROM clients WHERE approved = 0";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Current client waiting-list ---");

            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                String Email = rs.getString("email");
                String intent = rs.getString("intent");
                System.out.println("User " + Email );
                System.out.println("Their intent for using the system: " + intent);
                System.out.println();
            }

            if (!hasResults) {
                System.out.println("No objects owned by the institution.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void registerExpert(String email, String password, String license_number, String areas) {
        String query = "INSERT INTO experts (email, password, license_number, expertise) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, license_number);
            stmt.setString(4, areas); // Convert array to comma-separated string
            System.out.println("Expert was added to the system");
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
