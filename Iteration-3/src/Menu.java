import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    public static void showLogMenu() {
        while (true) {
            System.out.println("\n--- Art Advisory System ---");
            System.out.println("1. Login");
            System.out.println("2. Sign Up (Client)");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signupClient();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void login() {
        System.out.println("Enter the type of user: 1. Client, 2. Expert, 3. Administrator");
        int type = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter password: ");
        String password = scanner.nextLine();

        User loggedInUser = User.authenticateUser(email,password,type);
        if(loggedInUser != null) {
            System.out.println("Login Successful!");
            loggedInUser.showMenu();
        }

    }

    private static void signupClient() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.println("Enter your password: ");
        String password = scanner.nextLine();
        System.out.println("Enter affiliation: ");
        String affiliation = scanner.nextLine();
        System.out.println("Enter your intent for using the system");
        String intent = scanner.nextLine();
        ClientAction.registerClient(email, password, affiliation, intent);
        System.out.println("Registration Complete");
    }

    private static void showMainMenu(User user) {

    }
}


