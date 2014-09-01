package mydevmind.com.qcmplusstudent.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class UserMCQ implements Serializable{


    public enum State {DONE, INPROGRESS};

    private String objectId;
    private User user;
    private MCQ mcq;
    private String state;
    private String score;
    private Integer timeSpent;
    private ArrayList<UserAnswer> userAnswers;
    private Date dateCreated;
    private Date dateUpdated;

    public UserMCQ(){

    }

    public UserMCQ(String objectId){
        this.objectId= objectId;
    }


    public String getObjectId() {
        return objectId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Integer timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
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
