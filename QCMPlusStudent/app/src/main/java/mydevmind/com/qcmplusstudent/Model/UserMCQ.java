<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.Model;
=======
package mydevmind.com.qcmplusstudent.model;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

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
