package mydevmind.com.qcmplusstudent.apiService.DAO;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

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
    public void save(MCQ obj, IAPIServiceResultListener<MCQ> listener) {

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

    public static MCQ parseObjectToMCQ(ParseObject pMCQ){
        MCQ mcq= new MCQ(pMCQ.getObjectId());
        mcq.setName(pMCQ.getString("name"));
        mcq.setDescription(pMCQ.getString("summary"));
        mcq.setCreatedAt(pMCQ.getCreatedAt());
        mcq.setUpdatedAt(pMCQ.getUpdatedAt());
        return mcq;
    }
}
