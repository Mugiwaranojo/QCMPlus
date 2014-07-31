package thomascorfield.fr.qcmplusadmin.Model;

import java.io.Serializable;

public class Option implements Serializable {

    private String identifier;
    private String statement;
    private boolean checked;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
