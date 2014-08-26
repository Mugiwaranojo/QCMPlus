package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.Model.UserAnswer;
import thomascorfield.fr.qcmplusadmin.Model.UserMCQ;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;

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

    @Override
    public void delete(UserAnswer obj, IAPIServiceResultListener<UserAnswer> listener) {

    }

    @Override
    public void find(UserAnswer obj, IAPIServiceResultListener<UserAnswer> listener) {

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

    public UserAnswer parseObjectToUserAnswer(ParseObject pObj) throws ParseException {
        UserAnswer userAnswer= new UserAnswer();
        userAnswer.setQuestion(QuestionDAO.getInstance().parseObjectToQuestion(pObj.getParseObject("question").fetchIfNeeded()));
        userAnswer.setAnswer(OptionDAO.getInstance().parseObjectToOption(pObj.getParseObject("option").fetchIfNeeded()));
        return userAnswer;
    }
}
