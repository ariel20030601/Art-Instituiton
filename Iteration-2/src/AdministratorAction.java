import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
