package mydevmind.com.qcmplusstudent.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

public class UserMCQ {

    private String objectId;
    private User user;
    private MCQ mcq;
    private Timer timeSpent;
    private Integer score;
    private ArrayList<UserAnswer> userAnswers;
    private Date dateCreated;
    private Date dateUpdated;

    public UserMCQ(){

    }

    public UserMCQ(String objectId){
        this.objectId= objectId;
    }

    public enum state {};

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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

    public MCQ getMcq() {
        return mcq;
    }

    public void setMcq(MCQ mcq) {
        this.mcq = mcq;
    }
}
