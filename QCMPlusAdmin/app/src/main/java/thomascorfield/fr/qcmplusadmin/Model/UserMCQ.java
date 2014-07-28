package thomascorfield.fr.qcmplusadmin.Model;

import java.util.Date;
import java.util.Timer;
import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.Model.UserAnswer;

public class UserMCQ {

    private String identifier;
    private Timer timeSpent;
    private Integer score;
    private Date dateCreated;
    private Date dateUpdated;
    private ArrayList<UserAnswer> userAnswers;
    private User user;

    public enum state {};

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Timer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Timer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public ArrayList<UserAnswer> getUserAnswers() {
        return userAnswers;
    }

    public void setUserAnswers(ArrayList<UserAnswer> userAnswers) {
        this.userAnswers = userAnswers;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
