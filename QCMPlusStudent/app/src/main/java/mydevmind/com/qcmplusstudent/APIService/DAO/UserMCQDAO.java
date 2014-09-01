package mydevmind.com.qcmplusstudent.apiService.DAO;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.User;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * DAO du Model UserMCQ
 */
public class UserMCQDAO implements IDAO<UserMCQ> {

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
    public void save(final UserMCQ obj, final IAPIServiceResultListener<UserMCQ> listener) {
        obj.setState(UserMCQ.State.DONE.toString());
        obj.setTimeSpent(30);
        ParseObject pUser = ParseObject.createWithoutData("User", obj.getUser().getObjectId());
        ParseObject pMCQ = ParseObject.createWithoutData("MCQ", obj.getMcq().getObjectId());
        final ParseObject userMCQ =  new ParseObject("UserMCQ");
        userMCQ.put("user", pUser);
        userMCQ.put("mcq", pMCQ);
        userMCQ.put("state", obj.getState());
        userMCQ.put("timeSpent", obj.getTimeSpent());

        int uScore=0;
        int nbrQ=0;
        for(UserAnswer userAnswer:obj.getUserAnswers()){
            uScore+=userAnswer.getScore();
            nbrQ++;
        }
        String score= uScore+"/"+nbrQ;
        userMCQ.put("score", score);
        userMCQ.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                UserMCQ savedUserMCQ= parseObjectToUserMCQ(userMCQ);
                savedUserMCQ.setUserAnswers(obj.getUserAnswers());
                for(UserAnswer userAnswer: obj.getUserAnswers()){
                    UserAnswerDAO.getInstance().save(savedUserMCQ, userAnswer);
                }
                listener.onApiResultListener(savedUserMCQ, e);
            }
        });
    }

    @Override
    public void delete(UserMCQ obj, IAPIServiceResultListener<UserMCQ> listener) {

    }

    @Override
    public void find(UserMCQ obj, IAPIServiceResultListener<UserMCQ> listener) {

    }

    public void findByUserStateDone(User user, final IAPIServiceResultListener<ArrayList<UserMCQ>> listener){
        ParseObject pUser = ParseObject.createWithoutData("User", user.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserMCQ");
        query.whereEqualTo("user", pUser);
        query.whereEqualTo("state", UserMCQ.State.DONE.toString());
        query.include("mcq");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ArrayList<UserMCQ> userMCQArrayList= new ArrayList<UserMCQ>();
                for(ParseObject object: parseObjects){
                    userMCQArrayList.add(parseObjectToUserMCQ(object));
                }
                listener.onApiResultListener(userMCQArrayList, e);
            }
        });
    }

    public static UserMCQ parseObjectToUserMCQ(ParseObject pObject){
        UserMCQ userMCQ= new UserMCQ(pObject.getObjectId());
        userMCQ.setUser(new User(pObject.getParseObject("user").getObjectId()));
        try {
            userMCQ.setMcq(MCQDAO.parseObjectToMCQ(pObject.getParseObject("mcq").fetchIfNeeded()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        userMCQ.setState(pObject.getString("state"));
        userMCQ.setTimeSpent((Integer) pObject.getNumber("timeSpent"));
        userMCQ.setScore(pObject.getString("score"));
        userMCQ.setDateCreated(pObject.getCreatedAt());
        userMCQ.setDateUpdated(pObject.getUpdatedAt());
        return userMCQ;
    }
}
