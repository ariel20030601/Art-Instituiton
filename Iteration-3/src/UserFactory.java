package core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserFactory {
    public static User authenticateUser(String email, String password, int role) {

        if(role == 3) {
            if (email.equals("BossMan@gmail.com") && password.equals("TheBoss")) {
                return new Administrator();
            }
        }

        String table = (role == 1) ? "clients" : (role == 2) ? "experts" : "admins" ;
        String query = "SELECT * FROM " + table + " WHERE email = ? AND password = ?";

        try (Connection conn = DatabaseManage.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) { // If user exists
                int id = rs.getInt("id");
                String userEmail = rs.getString("email");

                if (role == 1 || role == 2) {
                    String Password = rs.getString("password");
                    if(role == 2) {
                        String license_number = rs.getString("license_number");
                        String areas = rs.getString("expertise");
                        return new Expert(email, password, license_number, areas);
                    } else {
                        String affiliation = rs.getString("affiliation");
                        String intent = rs.getString("intent");
                        int approved = rs.getInt("approved");
                        if (approved == 0) {
                            System.out.println("User has not been approved");
                        } else if (approved == 1) {
                            return new Client(userEmail, Password, affiliation, intent, approved);
                        }
                    }
                }
                else {
                    //return new Admin(id, userEmail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Login failed
    }
}

