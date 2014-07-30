package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class MCQ implements Serializable {

    private String identifier;
    private String name;
    private String description;
    private ArrayList<Question> questions;
    private ArrayList<UserMCQ> usermcqs;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public ArrayList<UserMCQ> getUsermcqs() {
        return usermcqs;
    }

    public void setUsermcqs(ArrayList<UserMCQ> usermcqs) {
        this.usermcqs = usermcqs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static MCQ getDefaultMCQ () {

        MCQ m = new MCQ();
        m.setName("QCM par défaut");
        m.setDescription("Un chouette QCM");

        return m;
    }

    public static ArrayList<MCQ> getAllMCQ (int size) {

        ArrayList<MCQ> list = new ArrayList<MCQ>();

        for (int i = 0; i < size; i++) {

            MCQ m = new MCQ();
            m.setName("QCM" + (i + 1));
            m.setDescription("Un chouette QCM");

            list.add(m);
        }

        return list;
    }
}
