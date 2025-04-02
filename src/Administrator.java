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
}
