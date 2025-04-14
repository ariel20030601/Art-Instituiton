package core;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Auction {

    String name;
    String location;
    String start_time;
    String end_time;


    public static void viewAllAuctions() {
        String query = "SELECT * FROM auctions";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("===== All Auctions =====");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String house = rs.getString("auction_house");
                String location = rs.getString("location");
                String speciality = rs.getString("specialty");
                String startTime = rs.getString("start_time");
                String endTime = rs.getString("end_time");
                String date = rs.getString("date");

                System.out.println("Auction ID: " + id);
                System.out.println("Name: " + name);
                System.out.println("Auction House: " + house);
                System.out.println("Location: " + location);
                System.out.println("Speciality: " + speciality);
                System.out.println("Date: " + date);
                System.out.println("Time: " + startTime + " to " + endTime);
                System.out.println("-----------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Failed to retrieve auctions.");
            e.printStackTrace();
        }
    }

    public static void searchAuctionsByKeyword(String keyword) {
        String query = "SELECT * FROM auctions WHERE name LIKE ? OR location LIKE ? OR specialty LIKE ?";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            String searchTerm = "%" + keyword + "%";
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setString(3, searchTerm);

            ResultSet rs = stmt.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("Auction Name: " + rs.getString("name"));
                System.out.println("Location: " + rs.getString("location"));
                System.out.println("Specialty: " + rs.getString("specialty"));
                System.out.println("Date: " + rs.getString("date"));
                System.out.println("Time: " + rs.getString("start_time") + " - " + rs.getString("end_time"));
                System.out.println("-----------------------------------");
            }

            if (!found) {
                System.out.println("No auctions found matching that keyword.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
