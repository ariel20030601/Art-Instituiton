import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExpertAction {
    public static void registerExpert(String email, String affiliation) {
        String query = "INSERT INTO experts (email, affiliation, approved) VALUES (?, ?, 0)";
        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setString(2, affiliation);
            stmt.executeUpdate();
            System.out.println("Signup request sent. Waiting for approval.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
