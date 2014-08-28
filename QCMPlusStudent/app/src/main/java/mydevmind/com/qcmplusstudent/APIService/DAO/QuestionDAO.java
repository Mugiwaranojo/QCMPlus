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
import mydevmind.com.qcmplusstudent.model.Option;
import mydevmind.com.qcmplusstudent.model.Question;


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
    public void save(Question obj, final IAPIServiceResultListener<Question> listener) {
        final ParseObject question =  new ParseObject("Question");
        if(obj.getObjectId()!=null){
            question.setObjectId(obj.getObjectId());
        }
        question.put("statement", obj.getStatement());
        question.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToQuestion(question), e);
            }
        });
    }

    @Override
    public void delete(Question obj, final IAPIServiceResultListener<Question> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject pQuestion= parseObjects.get(0);
                    pQuestion.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToQuestion(pQuestion), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    @Override
    public void find(Question obj, final IAPIServiceResultListener<Question> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToQuestion(parseObjects.get(0)), e);
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public ArrayList<Question> findByMCQ(MCQ mcq){
        ArrayList<Question> questionArrayList= new ArrayList<Question>();
        ParseObject pMCQ = ParseObject.createWithoutData("MCQ", mcq.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        query.whereEqualTo("mcq", pMCQ);
        try {
            List<ParseObject> results= query.find();
            for (ParseObject pObj: results){
                Question question= parseObjectToQuestion(pObj);
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

    public void findByMCQ(MCQ mcq, final IAPIServiceResultListener<ArrayList<Question>> listener){
        ParseObject pMCQ = ParseObject.createWithoutData("MCQ", mcq.getObjectId());
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Question");
        query.whereEqualTo("mcq", pMCQ);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                ArrayList<Question> questionArrayList= new ArrayList<Question>();
                for(ParseObject obj: parseObjects){
                    Question question= parseObjectToQuestion(obj);
                    ArrayList<Option> options= OptionDAO.getInstance().findByQuestion(question);
                    question.setOptions(options);
                    questionArrayList.add(question);
                }
                listener.onApiResultListener(questionArrayList, e);
            }
        });
    }

    public Question parseObjectToQuestion(ParseObject parseObject){
        Question question= new Question(parseObject.getObjectId());
        question.setStatement(parseObject.getString("statement"));
        return question;
    }
}