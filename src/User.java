public abstract class User {
    protected String Role;
    protected String Username;
    protected String Password;

    User(String User, String Pass) {
        Username = User;
        Password = Pass;
    }

    public abstract String getRole();

}

