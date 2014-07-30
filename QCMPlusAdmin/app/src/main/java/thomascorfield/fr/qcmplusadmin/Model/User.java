package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String identifier;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String company;
    private boolean isAdmin;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }

    public static User getDefaultUser () {

        User u = new User();
        u.setFirstname("Firstname");
        u.setLastname("Lastname");
        u.setCompany("TCS Alti");

        return u;
    }

    public static ArrayList<User> getAllUsers (int size) {

        ArrayList<User> list = new ArrayList<User>();

        for (int i = 0; i < size; i++) {

            User u = new User();
            u.setFirstname("Firstname" + (i + 1));
            u.setLastname("Lastname" + (i + 1));
            u.setLogin("Login" + (i + 1));
            u.setPassword("Password" + (i + 1));
            u.setCompany("TCS Alti");
            u.setAdmin(true);

            list.add(u);
        }

        return list;
    }
}
