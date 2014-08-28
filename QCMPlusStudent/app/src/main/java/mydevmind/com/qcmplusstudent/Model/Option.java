package mydevmind.com.qcmplusstudent.model;

import java.io.Serializable;

public class Option implements Serializable{

    private String objectId;
    private String statement;
    private boolean checked;

    public Option(String objectId) {

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
