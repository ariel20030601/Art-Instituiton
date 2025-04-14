package core;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
public class Expert implements User {

    String email;
    String password;
    String license_number;
    String areasOfExpertise;

    public Expert(String email, String password, String license_number,String aoe) {
        this.password = password;
        this.email = email;
        this.license_number = license_number;
        this.areasOfExpertise = aoe;
    }

    @Override
    public void showMenu() {
        while(true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome, Expert!");
            System.out.println("1. View current booked sessions");
            System.out.println("2. Add/Update Availability");
            System.out.println("3. View Auctions");
            System.out.println("4. Logout");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {


                case 1: {
                    viewAvailability();
                    break;
                }

                case 2: {
                    System.out.println("If you would like to add an availability, enter 1");
                    System.out.println("If you would like to update an availability, enter 2");
                    int newchoice = scanner.nextInt();
                    scanner.nextLine();

                    if (newchoice == 1) {
                        System.out.println("Enter your prefered date:");
                        String date = scanner.nextLine();
                        System.out.println("Enter your prefered time");
                        String time = scanner.nextLine();
                        addAvailability(date,time);
                    }
                    break;
                }

                case 3: {
                    viewAuctions();
                    break;
                }

                case 4: {
                    System.out.println("Logging out...");
                    return;
                }

            }
        }
    }

    @Override
    public String getRole() {
        return "Expert";
    }

    public void ViewInstitutionObjects(Institution Inst) {
        Inst.ViewObjects(this);
    }

    private void viewAuctions() {
        Auction.viewAllAuctions();
    }

    private void addAvailability(String date, String time) {
        String query = "INSERT INTO expert_availability (expert_email, available_date, available_time, booked) " +
                "VALUES (?, ?, ?, 0)";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.email);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.executeUpdate();
            System.out.println("Availability added: " + date + " at " + time);
        } catch (SQLException e) {
            if (e.getMessage().contains("UNIQUE")) {
                System.out.println("This time slot already exists.");
            } else {
                System.out.println("Error adding availability: " + e.getMessage());
            }
        }
    }

    private void viewAvailability() {
        String query = "SELECT available_date, available_time, booked " +
                "FROM expert_availability WHERE expert_email = ? ORDER BY available_date, available_time";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, this.email);
            ResultSet rs = stmt.executeQuery();

            System.out.println("\n--- Your Availability ---");
            while (rs.next()) {
                String date = rs.getString("available_date");
                String time = rs.getString("available_time");
                boolean isBooked = rs.getInt("booked") == 1;
                System.out.printf("%s at %s [%s]\n", date, time, isBooked ? "Booked" : "Available");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing availability: " + e.getMessage());
        }
    }

}
