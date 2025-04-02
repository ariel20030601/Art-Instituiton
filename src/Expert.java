public class Expert extends User {
    private String licenseNumber;
    private String[] areasOfExpertise;

    public Expert(String username, String password, String licenseNumber, String[] areasOfExpertise) {
        super(username, password);
        this.licenseNumber = licenseNumber;
        this.areasOfExpertise = areasOfExpertise;
    }

    @Override
    public String getRole() {
        return "Expert";
    }

    public void ViewInstitutionObjects(Institution Inst) {
        Inst.ViewObjects(this);
    }
}
