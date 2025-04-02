public class Client extends User {
    private String affiliation;

    public Client(String username, String password, String affiliation) {
        super(username, password);
        this.affiliation = affiliation;
    }

    @Override
    public String getRole() {
        return "Client";
    }

    public void ViewInstitutionObjects(Institution Inst) {
        Inst.ViewObjects(this);
    }
}
