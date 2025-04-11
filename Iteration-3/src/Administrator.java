package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Administrator implements User {

    @Override
    public String getRole() {
        return "Administrator";
    }

    public void approveClientSignup(Client client) {
        
    }


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

    public static void addExpertAvailability(String expertEmail, String availableDate, String availableTime) {
        String checkExpertQuery = "SELECT 1 FROM experts WHERE email = ?";
        String insertAvailabilityQuery = "INSERT INTO expert_availability (expert_email, available_date, available_time, booked) VALUES (?, ?, ?, 0)";

        try (Connection conn = DatabaseManage.connect()) {
            // Check if expert exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkExpertQuery)) {
                checkStmt.setString(1, expertEmail);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {  // If no result, expert does not exist
                    System.out.println(" Error: Expert with email " + expertEmail + " does not exist.");
                    return;
                }
            }

            // Insert availability
            try (PreparedStatement insertStmt = conn.prepareStatement(insertAvailabilityQuery)) {
                insertStmt.setString(1, expertEmail);
                insertStmt.setString(2, availableDate);  // Format: YYYY-MM-DD
                insertStmt.setString(3, availableTime);  // Format: HH:MM

                int rowsInserted = insertStmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println(" Availability added for " + expertEmail + " on " + availableDate + " at " + availableTime);
                } else {
                    System.out.println(" Failed to add availability.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createAuctionHouse(String ahName, String ahLocation) {
        AuctionHouse a = new AuctionHouse(ahName, ahLocation);
        a.insertAuctionHouse();
    }

    public static void insertAuction(String name, String auctionHouse, String location,
                                     String date, String startTime, String endTime, String specialty) {
        String query = "INSERT INTO auctions(name, auction_house, location, date, start_time, end_time, specialty) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, auctionHouse);
            stmt.setString(3, location);
            stmt.setString(4, date);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, specialty);
            stmt.executeUpdate();
            System.out.println("Auction added.");

        } catch (SQLException e) {
            System.out.println("Error inserting auction: " + e.getMessage());
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
                System.out.println("User " + Email);
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



    @Override
    public void showMenu() {
    while(true) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome, Admin!");
        System.out.println("1. Approve Clients");
        System.out.println("2. Add Experts");
        System.out.println("3. Add expert availability");
        System.out.println("4. Manage Auctions");
        System.out.println("5. Add ObjectsOfInterest to Instituiton");
        System.out.println("6. Logout");


        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                showUnapprovedClients();
                System.out.println("Enter the name of the user you would like to approve (or none if you would like to exit");
                String email = scanner.nextLine();
                if (email.equals("none")) {
                    break;
                } else {
                    approveClient(email);
                    break;
                }
            case 2: {
                System.out.println("Enter expert email");
                String name = scanner.nextLine();
                System.out.println("Enter expert password");
                String password = scanner.nextLine();
                System.out.println("Enter expert license number");
                String license = scanner.nextLine();
                System.out.println("Enter areas of expertise of said expert");
                String areas = scanner.nextLine();
                registerExpert(name, password, license, areas);
                break;
            }

            case 3:
                System.out.println("Enter expert's email");
                String aemail = scanner.nextLine();
                System.out.println("Enter expert available date");
                String datee = scanner.nextLine();
                System.out.println("Enter expert available time");
                String time = scanner.nextLine();
                Administrator.addExpertAvailability(aemail, datee, time);
                break;

            case 4:
                System.out.println("Enter 1 to add AuctionHouses and enter 2 to add Auctions");
                String option = scanner.nextLine();

                switch(option) {
                    case "1":
                        System.out.print("Enter Auction House Name: ");
                        String ahName = scanner.nextLine();
                        System.out.print("Enter Location: ");
                        String ahLocation = scanner.nextLine();
                        createAuctionHouse(ahName, ahLocation);
                        break;

                    case "2":
                        System.out.print("Enter Auction Name: ");
                        String auctionName = scanner.nextLine();
                        System.out.print("Enter Auction House: ");
                        String auctionHouse = scanner.nextLine();
                        System.out.print("Enter Location: ");
                        String location = scanner.nextLine();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();
                        System.out.print("Enter Start Time (HH:MM): ");
                        String startTime = scanner.nextLine();
                        System.out.print("Enter End Time (HH:MM): ");
                        String endTime = scanner.nextLine();
                        System.out.print("Enter Specialty: ");
                        String specialty = scanner.nextLine();
                        insertAuction(auctionName, auctionHouse, location, date, startTime, endTime, specialty);
                        break;
                }
                break;

            case 5:
                System.out.println("Enter object name");
                String name = scanner.nextLine();
                System.out.println("Enter object description");
                String description = scanner.nextLine();
                System.out.println("Is the object owned by the institution?");
                String owned = scanner.nextLine();
                boolean own = false;
                if (owned.equals("Yes")) {
                    own = true;
                }
                Administrator.registerObject(name, description, own, false);
                break;

            case 6:
                System.out.println("Logging out");
                return;
        }
      }
    }
}
