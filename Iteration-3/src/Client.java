import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.Scanner;

public class Client extends User {
    private String affiliation;

    public Client(String username, String password, String affiliation) {
        super(username, password);
        this.affiliation = affiliation;
    }

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
                    ClientAction.showAvailableExperts();
                    System.out.println("if you would like to book an expert, enter their email");
                    String email = scanner.nextLine();
                    ClientAction.bookExpert(email);
                    break;

                }

                case 3: {
                    ClientAction.viewInstitutionOwnedObjects();
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
