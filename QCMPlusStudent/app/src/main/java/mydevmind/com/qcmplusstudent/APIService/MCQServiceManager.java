package mydevmind.com.qcmplusstudent.apiService;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.apiService.DAO.MCQDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.QuestionDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.UserAnswerDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.UserDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.UserMCQDAO;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.Question;
import mydevmind.com.qcmplusstudent.model.User;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQServiceManager {


    private static final String APP_ID="1XpHvksxkUpokKjgKINeQuzwCUAAkyFpwRHBZTz3";
    private static final String CLIENT_KEY="pJFS5zqqaBKosNs69n1MQxV8kVSta0y3c0NrRUJW";

    private Context context;
    private static MCQServiceManager instance;
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private MCQServiceManager(Context context){
        this.context= context;
        Parse.initialize(context, APP_ID, CLIENT_KEY);
    }

    public static MCQServiceManager getInstance(Context context){
        if(instance==null){
            instance= new MCQServiceManager(context);
        }
        return instance;
    }

    private IAPIServiceResultListener<User> userListener;

    public void setUserListener(IAPIServiceResultListener<User> userListener) {
        this.userListener = userListener;
    }

    public void connect(String login, String password){
        //UserDAO.getInstance().findByUserPassword(login, password, userListener);
        UserDAO.getInstance().findByUserPassword("gbrunier", "0000", userListener);
    }

    private IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener;

    public void setListUserMCQListener(IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener) {
        this.listUserMCQListener = listUserMCQListener;
    }

    public void fetchCurrentUserMCQDone(){
        UserMCQDAO.getInstance().findByUserStateDone(getCurrentUser(), new IAPIServiceResultListener<ArrayList<UserMCQ>>() {
            @Override
            public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
                for (UserMCQ userMCQ : userMCQArrayList) {
                    ArrayList<UserAnswer> userAnswers = UserAnswerDAO.getInstance().findByUserMCQ(userMCQ);
                    for (UserAnswer userAnswer : userAnswers) {
                        for (Question question : userMCQ.getMcq().getQuestions()) {
                            if (userAnswer.getQuestion().getObjectId().equals(question.getObjectId())) {
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

    private IAPIServiceResultListener<ArrayList<MCQ>> listMCQListener;

    public void setListMCQListener(IAPIServiceResultListener<ArrayList<MCQ>> listMCQListener) {
        this.listMCQListener = listMCQListener;
    }

    public void fetchAllMCQ(){
        MCQDAO.getInstance().fetchAllMCQ(new IAPIServiceResultListener<ArrayList<MCQ>>() {
            @Override
            public void onApiResultListener(final ArrayList<MCQ> allMCQ, ParseException e) {
                /*UserMCQDAO.getInstance().findByUserStateDone(getCurrentUser(), new IAPIServiceResultListener<ArrayList<UserMCQ>>() {
                    @Override
                    public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
                        ArrayList<MCQ> mcqs= new ArrayList<MCQ>();
                        for(MCQ mcq: allMCQ){
                            boolean find=false;
                            for(UserMCQ userMCQ: userMCQArrayList){
                                if(mcq.getObjectId().equals(userMCQ.getMcq().getObjectId())){
                                    find=true;
                                }
                            }
                            if(!find){
                                mcqs.add(mcq);
                            }
                        }
                        listMCQListener.onApiResultListener(mcqs, e);
                    }
                });*/
                listMCQListener.onApiResultListener(allMCQ, e);
            }
        });
    }


    private IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener;

    public void setListMCQQuestionListener(IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener) {
        this.listMCQQuestionListener = listMCQQuestionListener;
    }

    public void fetchMCQQuestions(MCQ mcq){
        QuestionDAO.getInstance().findByMCQ(mcq, listMCQQuestionListener);
    }
}
