package thomascorfield.fr.qcmplusadmin.apiService;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.Model.UserAnswer;
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

    public void setUserListener(IAPIServiceResultListener<User> userListener) {
        this.userListener = userListener;
    }

    public void setMCQListener(IAPIServiceResultListener<MCQ> mcqListener) {
        this.mcqListener = mcqListener;
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
}
