<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.Model;
=======
<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.Model;
=======
package mydevmind.com.qcmplusstudent.model;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6

import java.util.ArrayList;

public class Question {

    private String identifier;
    private String statement;
    private ArrayList<Option> options;
    private UserAnswer userAnswer;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<Option> options) {
        this.options = options;
    }

    public UserAnswer getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(UserAnswer userAnswer) {
        this.userAnswer = userAnswer;
    }
}
