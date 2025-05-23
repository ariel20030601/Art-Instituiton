public class Expert extends User {
    private String license_number;
    private String[] areasOfExpertise;

    public Expert(String username, String password, String license_number, String[] areasOfExpertise) {
        super(username, password);
        this.license_number = license_number;
        this.areasOfExpertise = areasOfExpertise;
    }

    @Override
    public void showMenu() {
        System.out.println("Welcome, Expert!");
        System.out.println("1. View Available Consultations");
        System.out.println("2. Update Availability");
        System.out.println("3. Logout");



    }

    @Override
    public String getRole() {
        return "Expert";
    }

    public void ViewInstitutionObjects(Institution Inst) {
        Inst.ViewObjects(this);
    }
}
