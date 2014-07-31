package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;

/**
 * Created by Joan on 31/07/2014.
 */
public class OptionDAO implements IDAO<Option> {

    private static OptionDAO instance;

    private OptionDAO(){

    }

    public static OptionDAO getInstance(){
        if(instance==null){
            instance= new OptionDAO();
        }
        return instance;
    }

    @Override
    public void save(Option obj, IAPIServiceResultListener<Option> listener) {

    }

    @Override
    public void delete(Option obj, IAPIServiceResultListener<Option> listener) {

    }

    @Override
    public void find(Option obj, IAPIServiceResultListener<Option> listener) {

    }

    public ArrayList<Option> findByQuestion(Question question){
        ArrayList<Option> options= new ArrayList<Option>();
        ParseObject pQuestion = ParseObject.createWithoutData("Question", question.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Option");
        query.whereEqualTo("question", pQuestion);
        try {
            List<ParseObject> results= query.find();
            for (ParseObject pObj: results){
                options.add(parseOjectToOption(pObj));
            }
            return options;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Option parseOjectToOption(ParseObject pObj) {
        Option option=new Option(pObj.getObjectId());
        option.setStatement(pObj.getString("statement"));
        option.setChecked(pObj.getBoolean("checked"));
        return option;
    }
}
