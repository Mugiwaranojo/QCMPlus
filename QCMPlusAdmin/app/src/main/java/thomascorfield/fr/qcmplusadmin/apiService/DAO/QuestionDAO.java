package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;

/**
 * Created by Joan on 31/07/2014.
 */
public class QuestionDAO implements IDAO<Question> {

    private static QuestionDAO instance;

    private QuestionDAO(){

    }

    public static QuestionDAO getInstance(){
        if(instance==null){
            instance= new QuestionDAO();
        }
        return instance;
    }

    @Override
    public void save(Question obj, IAPIServiceResultListener<Question> listener) {

    }

    @Override
    public void delete(Question obj, IAPIServiceResultListener<Question> listener) {

    }

    @Override
    public void find(Question obj, IAPIServiceResultListener<Question> listener) {

    }

    public ArrayList<Question> findByMQC(MCQ mcq){
        ArrayList<Question> questionArrayList= new ArrayList<Question>();
        ParseObject pMCQ = ParseObject.createWithoutData("MCQ", mcq.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        query.whereEqualTo("mcq", pMCQ);
        try {
            List<ParseObject> results= query.find();
            for (ParseObject pObj: results){
                Question question= parseOjectToQuestion(pObj);
                ArrayList<Option> options= OptionDAO.getInstance().findByQuestion(question);
                question.setOptions(options);
                questionArrayList.add(question);
            }
            return questionArrayList;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Question parseOjectToQuestion(ParseObject parseObject){
        Question question= new Question(parseObject.getObjectId());
        question.setStatement(parseObject.getString("statement"));
        return question;
    }
}
