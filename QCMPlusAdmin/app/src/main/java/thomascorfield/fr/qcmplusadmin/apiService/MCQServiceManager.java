package thomascorfield.fr.qcmplusadmin.apiService;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.Model.UserAnswer;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.OptionDAO;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.QuestionDAO;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.UserAnswerDAO;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.MCQDAO;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.UserDAO;
import thomascorfield.fr.qcmplusadmin.apiService.DAO.UserMCQDAO;
import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.Model.UserMCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQServiceManager {


    private static final String APP_ID="1XpHvksxkUpokKjgKINeQuzwCUAAkyFpwRHBZTz3";
    private static final String CLIENT_KEY="pJFS5zqqaBKosNs69n1MQxV8kVSta0y3c0NrRUJW";

    private static MCQServiceManager instance;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private MCQServiceManager(Context context){
        Parse.initialize(context, APP_ID, CLIENT_KEY);
    }

    public static MCQServiceManager getInstance(Context context){
        if(instance==null){
            instance= new MCQServiceManager(context);
        }
        return instance;
    }

    private IAPIServiceResultListener<User> userListener;
    private IAPIServiceResultListener<MCQ> mcqListener;
    private IAPIServiceResultListener<Question> questionListener;
    private IAPIServiceResultListener<Option> optionListener;

    public void setUserListener(IAPIServiceResultListener<User> userListener) {
        this.userListener = userListener;
    }

    public void setMCQListener(IAPIServiceResultListener<MCQ> mcqListener) {
        this.mcqListener = mcqListener;
    }

    public void setQuestionListener(IAPIServiceResultListener<Question> questionListener) {
        this.questionListener = questionListener;
    }

    public void setOptionListener(IAPIServiceResultListener<Option> optionListener) {
        this.optionListener = optionListener;
    }

    public void connect(String login, String password){
        UserDAO.getInstance().findByUserPassword(login, password, userListener);
    }

    private IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener;

    public void setListUserMCQListener(IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener) {
        this.listUserMCQListener = listUserMCQListener;
    }

    public void fetchUserMCQ(User user){
        UserMCQDAO.getInstance().findByUser(user, new IAPIServiceResultListener<ArrayList<UserMCQ>>() {
            @Override
            public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
                for(UserMCQ userMCQ: userMCQArrayList){
                    ArrayList<UserAnswer> userAnswers= UserAnswerDAO.getInstance().findByUserMCQ(userMCQ);
                    for(UserAnswer userAnswer: userAnswers){
                        for(Question question: userMCQ.getMcq().getQuestions()){
                            if(userAnswer.getQuestion().getObjectId().equals(question.getObjectId())){
                                userAnswer.getQuestion().setOptions(question.getOptions());
                            }
                        }
                    }
                    userMCQ.setUserAnswers(userAnswers);
                }
                getCurrentUser().setUserMCQs(userMCQArrayList);
                listUserMCQListener.onApiResultListener(userMCQArrayList, e);
            }
        });
    }

    private IAPIServiceResultListener<ArrayList<MCQ>> mcqListListener;

    public void setMCQListListener(IAPIServiceResultListener<ArrayList<MCQ>> mcqListListener) {
        this.mcqListListener = mcqListListener;
    }

    private IAPIServiceResultListener<ArrayList<User>> userListListener;

    public void setUserListListener(IAPIServiceResultListener<ArrayList<User>> userListListener) {
        this.userListListener = userListListener;
    }

    private IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener;

    public void setListMCQQuestionListener(IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener) {
        this.listMCQQuestionListener = listMCQQuestionListener;
    }

    public void fetchAllUser(){
        UserDAO.getInstance().fetchAllUser(userListListener);
    }

    public void saveUser(User user) {
        UserDAO.getInstance().save(user, userListener);
    }

    public void deleteUser(User user){
        UserDAO.getInstance().delete(user, userListener);
    }

    public void fetchAllMCQ(){
        MCQDAO.getInstance().fetchAllMCQ(mcqListListener);
    }

    public void saveMCQ(MCQ mcq) { MCQDAO.getInstance().save(mcq, mcqListener); }

    public void deleteMCQ(MCQ mcq){
        MCQDAO.getInstance().delete(mcq, mcqListener);
    }

    public void fetchMCQQuestions(MCQ mcq){
        QuestionDAO.getInstance().findByMCQ(mcq, listMCQQuestionListener);
    }

    public void saveQuestion(final Question question, final MCQ mcq) {
        QuestionDAO.getInstance().save(question, mcq, new IAPIServiceResultListener<Question>() {
            @Override
            public void onApiResultListener(Question obj, ParseException e) {
                if(obj!=null){
                   for (int i = 0; i < 5; i++) {
                       Option opt = question.getOptions().get(i);
                       if (opt.getStatement() != "") {
                           OptionDAO.getInstance().save(opt, obj);
                       }
                   }
                }
                questionListener.onApiResultListener(question, e);
            }
        });
    }

    public void deleteQuestion(final Question question){
        QuestionDAO.getInstance().delete(question, new IAPIServiceResultListener<Question>() {
            @Override
            public void onApiResultListener(Question obj, ParseException e) {
                if(obj!=null){
                    int len = question.getOptions().size();
                    for (int i = 0; i < len; i++) {
                        Option opt = question.getOptions().get(i);
                        OptionDAO.getInstance().delete(opt, new IAPIServiceResultListener<Option>() {
                            @Override
                            public void onApiResultListener(Option obj, ParseException e) {

                            }
                        });
                    }
                }
                questionListener.onApiResultListener(question, e);
            }
    }); }
}
