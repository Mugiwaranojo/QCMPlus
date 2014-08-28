package mydevmind.com.qcmplusstudent.model;

import java.io.Serializable;

public class UserAnswer implements Serializable{

    private Question question;
    private Option answer;

    public UserAnswer() {
    }

    public Option getAnswer() {
        return answer;
    }

    public void setAnswer(Option answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getScore() {
        if(question.validOption().getStatement().equals(answer.getStatement())){
            return 1;
        }else{
            return 0;
        }
    }
}
