package mydevmind.com.qcmplusstudent.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MCQ implements Serializable {

    private String objectId;
    private String name;
    private String description;
    private ArrayList<Question> questions;
    private Date createdAt;
    private Date updatedAt;


    public MCQ(){

    }

    public MCQ(String objectId){
        this.objectId= objectId;
    }

    public String getObjectId() {
        return objectId;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return name;
    }

}
