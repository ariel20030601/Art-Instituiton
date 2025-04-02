public abstract class User {
    protected String Role;
    protected String Username;
    protected String Password;

    User(String User, String Pass) {
        Username = User;
        Password = Pass;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public abstract String getRole();

    public abstract void showMenu();

}

