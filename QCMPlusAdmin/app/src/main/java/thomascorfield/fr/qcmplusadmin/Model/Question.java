package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

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

    public static ArrayList<Question> getAllQuestions (int size) {

        ArrayList<Question> list = new ArrayList<Question>();

        for (int i = 0; i < size; i++) {

            Question q = new Question();
            q.setStatement("Question Test" + (i + 1));

            ArrayList options = new ArrayList();

                for (int j = 0; j < 5; j++) {

                    Option o = new Option();
                    o.setStatement("Option Test" + (j + 1));

                    options.add(j, o);
                }

            q.setOptions(options);

            list.add(q);
        }

        return list;
    }
}
