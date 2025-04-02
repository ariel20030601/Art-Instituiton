import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

public class ExpertAction {
    public static void viewAuctionsForExpert(String email) {
        String query = "SELECT a.name, a.auction_house, a.location, a.date, a.start_time, a.end_time, a.specialty " +
                "FROM auctions a JOIN experts e ON a.specialty LIKE '%' || e.areas_of_expertise || '%' " +
                "WHERE e.email = ? AND a.viewing_only = 0";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Auctions Related to Your Expertise ---");
            boolean hasResults = false;
            while (rs.next()) {
                hasResults = true;
                System.out.println(" Auction: " + rs.getString("name"));
                System.out.println(" House: " + rs.getString("auction_house"));
                System.out.println(" Location: " + rs.getString("location"));
                System.out.println(" Date: " + rs.getString("date"));
                System.out.println(" Time: " + rs.getString("start_time") + " - " + rs.getString("end_time"));
                System.out.println(" Specialty: " + rs.getString("specialty"));
                System.out.println("----------------------------");
            }

            if (!hasResults) {
                System.out.println("No auctions available for your expertise.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
