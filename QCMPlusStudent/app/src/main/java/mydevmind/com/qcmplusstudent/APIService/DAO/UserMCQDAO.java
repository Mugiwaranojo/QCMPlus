package mydevmind.com.qcmplusstudent.apiService.DAO;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.User;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class UserMCQDAO implements IDAO<UserMCQDAO> {

    private static UserMCQDAO instance;

    private UserMCQDAO(){

    }

    public static UserMCQDAO getInstance(){
        if(instance==null){
            instance= new UserMCQDAO();
        }
        return instance;
    }

    @Override
    public void save(UserMCQDAO obj, IAPIServiceResultListener<UserMCQDAO> listener) {

    }

    @Override
    public void delete(UserMCQDAO obj, IAPIServiceResultListener<UserMCQDAO> listener) {

    }

    @Override
    public void find(UserMCQDAO obj, IAPIServiceResultListener<UserMCQDAO> listener) {

    }

    public void findByUser(User user, final IAPIServiceResultListener<ArrayList<UserMCQ>> listener){
        ParseObject pUser = ParseObject.createWithoutData("User", user.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserMCQ");
        query.whereEqualTo("user", pUser);
        query.include("mcq");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ArrayList<UserMCQ> userMCQArrayList= new ArrayList<UserMCQ>();
                for(ParseObject object: parseObjects){
                    try {
                        userMCQArrayList.add(parseObjectToUserMCQRelations(object, false));
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
                listener.onApiResultListener(userMCQArrayList, e);
            }
        });
    }

    public static UserMCQ parseObjectToUserMCQ(ParseObject pObject){
        UserMCQ userMCQ= new UserMCQ(pObject.getObjectId());
        userMCQ.setUser(new User(pObject.getParseObject("user").getObjectId()));
        userMCQ.setMcq(new MCQ(pObject.getParseObject("mcq").getObjectId()));
        return userMCQ;
    }

    public static UserMCQ parseObjectToUserMCQRelations(ParseObject pObject, boolean withUser) throws ParseException {
        UserMCQ userMCQ= parseObjectToUserMCQ(pObject);
        if(withUser) {
            userMCQ.setUser(UserDAO.parseObjectToUser(pObject.fetchIfNeeded()));
        }else {
            MCQ mcq= MCQDAO.parseObjectToMCQ(pObject.getParseObject("mcq").fetchIfNeeded());
            userMCQ.setMcq(mcq);
        }
        return  userMCQ;
    }
}
