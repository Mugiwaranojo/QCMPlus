package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private String objectId;
    private String statement;
    private ArrayList<Option> options;

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
}
