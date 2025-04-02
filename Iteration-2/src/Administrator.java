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
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome, Admin!");
        System.out.println("1. Approve Clients");
        System.out.println("2. Manage Auctions");
        System.out.println("3. Add ObjectsOfInterest to Instituiton");
        System.out.println("4. Logout");


        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 3:
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
                AdministratorAction.registerObject(name,description,own,false);
        }


    }
}
