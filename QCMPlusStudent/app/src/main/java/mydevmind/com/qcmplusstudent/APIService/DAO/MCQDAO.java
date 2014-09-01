package mydevmind.com.qcmplusstudent.apiService.DAO;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.MCQ;


/**
 * DAO du Model MCQ
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
        mcq.put("summary", obj.getDescription());
        mcq.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToMCQ(mcq), e);
            }
        });
    }

    @Override
    public void delete(MCQ obj, final IAPIServiceResultListener<MCQ> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MCQ");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject pMcq= parseObjects.get(0);
                    pMcq.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToMCQ(pMcq), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
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
