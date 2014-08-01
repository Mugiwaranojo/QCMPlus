package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private String objectId;
    private String statement;
    private ArrayList<Option> options;

    public Question(){

    }

    public Question(String id){
        objectId= id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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


    public Option validOption(){
        for (Option option:options){
            if(option.isChecked()){
                return option;
            }
        }
        return null;
    }

    public static ArrayList<Question> getAllQuestions (int size) {

        ArrayList<Question> list = new ArrayList<Question>();

        for (int i = 0; i < size; i++) {

            Question q = new Question(i+"");
            q.setStatement("Question Test" + (i + 1));

            ArrayList options = new ArrayList();

                for (int j = 0; j < 5; j++) {

                    Option o = new Option(j+"");
                    o.setStatement("Option Test" + (j + 1));

                    options.add(j, o);
                }

            q.setOptions(options);

            list.add(q);
        }

        return list;
    }
}
