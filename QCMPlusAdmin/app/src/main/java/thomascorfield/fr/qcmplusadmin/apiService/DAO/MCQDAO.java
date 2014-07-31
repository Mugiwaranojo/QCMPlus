package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.Model.MCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQDAO implements IDAO<MCQ> {


    private static MCQDAO instance;

    private MCQDAO(){

    }

    public static MCQDAO getInstance(){
        if(instance==null){
            instance= new MCQDAO();
        }
        return instance;
    }


    @Override
    public void save(MCQ obj, final IAPIServiceResultListener<MCQ> listener) {
        final ParseObject mcq =  new ParseObject("MCQ");
        if(obj.getObjectId()!=null){
            mcq.setObjectId(obj.getObjectId());
        }
        mcq.put("name", obj.getName());
        mcq.put("lastname", obj.getDescription());
        mcq.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToMCQ(mcq), e);
            }
        });
    }

    @Override
    public void delete(MCQ obj, IAPIServiceResultListener<MCQ> listener) {

    }

    @Override
    public void find(MCQ obj, final IAPIServiceResultListener<MCQ> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MCQ");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
               if(parseObjects.size()==1){
                   listener.onApiResultListener(parseObjectToMCQ(parseObjects.get(0)), e);
               }else{
                   listener.onApiResultListener(null, e);
               }
            }
        });
    }

    public MCQ find(String objectId){
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("MCQ");
            query.whereEqualTo("objectId", objectId);
            List<ParseObject> results= query.find();
            if(results.size()==1){
                return parseObjectToMCQ(results.get(0));
            }else{
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fetchAllMCQ(final IAPIServiceResultListener<ArrayList<MCQ>> listener){
        final ArrayList<MCQ> mcqArrayList= new ArrayList<MCQ>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MCQ");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                for (ParseObject pMCQ: parseObjects){
                    mcqArrayList.add(parseObjectToMCQ(pMCQ));
                }
                listener.onApiResultListener(mcqArrayList, e);
            }
        });
    }

    public static MCQ parseObjectToMCQ(ParseObject pMCQ){
        MCQ mcq= new MCQ(pMCQ.getObjectId());
        mcq.setName(pMCQ.getString("name"));
        mcq.setDescription(pMCQ.getString("summary"));
        mcq.setCreatedAt(pMCQ.getCreatedAt());
        mcq.setUpdatedAt(pMCQ.getUpdatedAt());
        return mcq;
    }
}
