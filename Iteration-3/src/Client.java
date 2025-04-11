package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Client implements User {
    private String affiliation;

    public String getAffilation() {
        return affiliation;
    }


    @Override
    public String getRole() {
        return "Client";
    }

    public void ViewInstitutionObjects(Institution Inst) {
        Inst.ViewObjects(this);
    }

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

    public static void showAvailableExperts() {
        String query = "SELECT * FROM expert_availability WHERE booked = 0;";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- Experts available for consultation: ---");
            boolean hasResults = false;
            int count = 0;
            while (rs.next()) {
                count++;
                hasResults = true;
                String name = rs.getString("expert_email");
                String date = rs.getString("available_date");
                String time = rs.getString("available_time");
                System.out.println(count + ". " + name + " - " + date + " - " + time);
            }
            if (!hasResults) {
                System.out.println("No objects owned by the institution.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void bookExpert(String email) {
        String query = "UPDATE expert_availability SET booked = 1 WHERE expert_email = ?";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Expert booked successfully!");
            } else {
                System.out.println("Expert was not booked successfully");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showMenu() {
        while(true) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Welcome, Client!");
            System.out.println("1. View Auctions");
            System.out.println("2. Request Expert Consultation");
            System.out.println("3. View objects owned by institution");
            System.out.println("4. Logout");
            System.out.println("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    System.out.println("Here are where the auctions are taking place");
                }

                case 2: {
                    showAvailableExperts();
                    System.out.println("if you would like to book an expert, enter their email");
                    String email = scanner.nextLine();
                    bookExpert(email);
                    break;

                }

                case 3: {
                    viewInstitutionOwnedObjects();
                    break;
                }

                case 4: {
                    System.out.println("Logging out..");
                    break;
                }
            }
        }
    }
}
