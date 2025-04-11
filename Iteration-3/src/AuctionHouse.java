package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AuctionHouse {
    String Location;
    String Name;

    AuctionHouse(String Name, String Loc) {
        Location = Loc;
        Name = Name;
    };

    public void ExecuteAuction(int Time, String AuctionName) {
        System.out.println("The " + AuctionName + "Auction, " + Name + "'s, in " + Location + "is programmed for " + Time);
    }


    public void insertAuctionHouse() {
        String query = "INSERT INTO auctionHouse(name, location) VALUES(?, ?)";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, this.Name);
            stmt.setString(2, this.Location);
            stmt.executeUpdate();
            System.out.println("Auction house added.");

        } catch (SQLException e) {
            System.out.println("Error inserting auction house: " + e.getMessage());
        }
    }
}
