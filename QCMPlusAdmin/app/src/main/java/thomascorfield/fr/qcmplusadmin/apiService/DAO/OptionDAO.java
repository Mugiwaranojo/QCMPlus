package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import android.util.Log;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

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
    public void save(Option obj, final IAPIServiceResultListener<Option> listener) {
        final ParseObject option =  new ParseObject("Option");
        if(obj.getObjectId()!=null){
            option.setObjectId(obj.getObjectId());
        }
        option.put("statement", obj.getStatement());
        option.put("checked", obj.isChecked());
        option.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToOption(option), e);
            }
        });
    }

    public void save(Option option, Question question){
        ParseObject questionParse = ParseObject.createWithoutData("Question", question.getObjectId());
        ParseObject optionParse = new ParseObject("Option");
        optionParse.put("statement", option.getStatement());
        optionParse.put("checked", option.isChecked());
        optionParse.put("question", questionParse);
        try {
            optionParse.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Option obj, final IAPIServiceResultListener<Option> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Option");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject pOption= parseObjects.get(0);
                    pOption.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToOption(pOption), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    @Override
    public void find(Option obj, final IAPIServiceResultListener<Option> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Option");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToOption(parseObjects.get(0)), e);
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public ArrayList<Option> findByQuestion(Question question){
        ArrayList<Option> options= new ArrayList<Option>();
        ParseObject pQuestion = ParseObject.createWithoutData("Question", question.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Option");
        query.whereEqualTo("question", pQuestion);
        try {
            List<ParseObject> results= query.find();
            for (ParseObject pObj: results){
                options.add(parseObjectToOption(pObj));
            }
            return options;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Option parseObjectToOption(ParseObject pObj) {
        Option option=new Option(pObj.getObjectId());
        option.setStatement(pObj.getString("statement"));
        option.setChecked(pObj.getBoolean("checked"));
        return option;
    }
}
