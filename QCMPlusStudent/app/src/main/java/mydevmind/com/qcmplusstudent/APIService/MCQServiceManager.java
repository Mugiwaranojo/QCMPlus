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
 * MCQServiceManager
 * Service de recherche et sauvegarde des données en base
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

    public void setUserListener(IAPIServiceResultListener<User> userListener) {
        this.userListener = userListener;
    }

    public void connect(String login, String password){
        UserDAO.getInstance().findByUserPassword(login, password, userListener);
        //UserDAO.getInstance().findByUserPassword("gbrunier", "0000", userListener);
    }

    private IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener;

    public void setListUserMCQListener(IAPIServiceResultListener<ArrayList<UserMCQ>> listUserMCQListener) {
        this.listUserMCQListener = listUserMCQListener;
    }

    public void fetchCurrentUserMCQDone(){
        UserMCQDAO.getInstance().findByUserStateDone(getCurrentUser(), new IAPIServiceResultListener<ArrayList<UserMCQ>>() {
            @Override
            public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
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
                ArrayList<MCQ> allMCQWithoutUserMCQ= new ArrayList<MCQ>();
                for(MCQ mcq : allMCQ){
                    if(!mcqIsAlreadyAnswer(mcq)){
                        allMCQWithoutUserMCQ.add(mcq);
                    }
                }
                listMCQListener.onApiResultListener(allMCQWithoutUserMCQ, e);
            }
        });
    }

    private boolean mcqIsAlreadyAnswer(MCQ mcq){
        for(UserMCQ userMCQ: getCurrentUser().getUserMCQs()){
            if(userMCQ.getMcq().getObjectId().equals(mcq.getObjectId())){
                return true;
            }
        }
        return false;
    }


    private IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener;

    public void setListMCQQuestionListener(IAPIServiceResultListener<ArrayList<Question>> listMCQQuestionListener) {
        this.listMCQQuestionListener = listMCQQuestionListener;
    }

    public void fetchMCQQuestions(MCQ mcq){
        QuestionDAO.getInstance().findByMCQ(mcq, listMCQQuestionListener);
    }

    private IAPIServiceResultListener<UserMCQ> userMCQListener;

    public void setUserMCQListener(IAPIServiceResultListener<UserMCQ> userMCQListener) {
        this.userMCQListener = userMCQListener;
    }

    public void saveUserMCQ(UserMCQ userMCQ){
        UserMCQDAO.getInstance().save(userMCQ, userMCQListener);
    }

    private IAPIServiceResultListener<ArrayList<UserAnswer>> listUserAnswerListener;

    public void setlistUserAnswerListener(IAPIServiceResultListener<ArrayList<UserAnswer>> listUserAnswerListener) {
        this.listUserAnswerListener = listUserAnswerListener;
    }

    public void fetchUserAnswers(UserMCQ userMCQ){
        UserAnswerDAO.getInstance().findByUserMCQ(userMCQ, listUserAnswerListener);
    }
}
