package mydevmind.com.qcmplusstudent.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class User implements Serializable {

    private String objectId;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String company;
    private boolean isAdmin;
    private ArrayList<UserMCQ> userMCQs;
    private Date createdAt;
    private Date updatedAt;

    public User(){

    }

    public User(String objectId){
        this.objectId= objectId;
    }
    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String identifier) {
        this.objectId = identifier;
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

    public ArrayList<UserMCQ> getUserMCQs() {
        return userMCQs;
    }

    public void setUserMCQs(ArrayList<UserMCQ> userMCQs) {
        this.userMCQs = userMCQs;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
