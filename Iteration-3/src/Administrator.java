import java.util.Scanner;
public class Administrator extends User {
    public Administrator(String username, String password) {
        super(username, password);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

    public void approveClientSignup(Client client) {
        
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
                AdministratorAction.showUnapprovedClients();
                System.out.println("Enter the name of the user you would like to approve (or none if you would like to exit");
                String email = scanner.nextLine();
                if (email.equals("none")) {
                    break;
                } else {
                    AdministratorAction.approveClient(email);
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
                AdministratorAction.registerExpert(name, password, license, areas);
                break;
            }

            case 3:
                System.out.println("Enter expert's email");
                String aemail = scanner.nextLine();
                System.out.println("Enter expert available date");
                String date = scanner.nextLine();
                System.out.println("Enter expert available time");
                String time = scanner.nextLine();
                AdministratorAction.addExpertAvailability(aemail, date, time);
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
                AdministratorAction.registerObject(name, description, own, false);
                break;

            case 6:
                System.out.println("Logging out");
                return;
        }
      }
    }
}
