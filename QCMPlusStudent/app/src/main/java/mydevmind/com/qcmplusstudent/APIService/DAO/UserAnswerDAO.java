package mydevmind.com.qcmplusstudent.apiService.DAO;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.Option;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 31/07/2014.
 */
public class UserAnswerDAO implements IDAO<UserAnswer>{

    private static UserAnswerDAO instance;

    private UserAnswerDAO(){

    }

    public static UserAnswerDAO getInstance(){
        if(instance==null){
            instance= new UserAnswerDAO();
        }
        return instance;
    }

    @Override
    public void save(UserAnswer obj, IAPIServiceResultListener<UserAnswer> listener) {

    }

    public void save(UserMCQ userMCQ, UserAnswer userAnswer){
        ParseObject pUserMCQ = ParseObject.createWithoutData("UserMCQ", userMCQ.getObjectId());
        ParseObject pQuestion = ParseObject.createWithoutData("Question", userAnswer.getQuestion().getObjectId());
        ParseObject pOption = ParseObject.createWithoutData("Option", userAnswer.getAnswer().getObjectId());
        ParseObject pUserAnswer =  new ParseObject("UserAnswer");
        pUserAnswer.put("userMCQ", pUserMCQ);
        pUserAnswer.put("question", pQuestion);
        pUserAnswer.put("option", pOption);
        try {
            pUserAnswer.save();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(UserAnswer obj, IAPIServiceResultListener<UserAnswer> listener) {

    }

    @Override
    public void find(UserAnswer obj, IAPIServiceResultListener<UserAnswer> listener) {

    }

    public void findByUserMCQ(UserMCQ userMCQ, final IAPIServiceResultListener<ArrayList<UserAnswer>> listener) {
        ParseObject pUserMCQ = ParseObject.createWithoutData("UserMCQ", userMCQ.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserAnswer");
        query.whereEqualTo("userMCQ", pUserMCQ);
        query.include("Question");
        query.include("Option");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ArrayList<UserAnswer> userAnswers= new ArrayList<UserAnswer>();
                for (ParseObject pObj: parseObjects){
                    userAnswers.add(parseObjectToUserAnswer(pObj));
                }
                listener.onApiResultListener(userAnswers, e);
            }
        });
    }

    public ArrayList<UserAnswer> findByUserMCQ(UserMCQ userMCQ){
        ArrayList<UserAnswer> userAnswers= new ArrayList<UserAnswer>();
        ParseObject pUserMCQ = ParseObject.createWithoutData("UserMCQ", userMCQ.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("UserAnswer");
        query.whereEqualTo("userMCQ", pUserMCQ);
        query.include("Question");
        query.include("Option");
        try {
            List<ParseObject> results= query.find();
            for (ParseObject pObj: results){
                userAnswers.add(parseObjectToUserAnswer(pObj));
            }
            return userAnswers;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserAnswer parseObjectToUserAnswer(ParseObject pObj) {
        UserAnswer userAnswer= new UserAnswer();
        try {
            userAnswer.setQuestion(QuestionDAO.getInstance().parseObjectToQuestion(pObj.getParseObject("question").fetchIfNeeded()));
            userAnswer.getQuestion().setOptions(OptionDAO.getInstance().findByQuestion(userAnswer.getQuestion()));
            for(Option option: userAnswer.getQuestion().getOptions()){
                String searchId=pObj.getParseObject("option").getObjectId();
                if(option.getObjectId().equals(searchId)){
                    userAnswer.setAnswer(option);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userAnswer;
    }
}
