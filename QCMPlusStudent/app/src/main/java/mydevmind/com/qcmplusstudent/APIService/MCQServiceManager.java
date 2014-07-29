package mydevmind.com.qcmplusstudent.apiService;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.apiService.DAO.MCQDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.UserDAO;
import mydevmind.com.qcmplusstudent.apiService.DAO.UserMCQDAO;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.User;
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
        UserDAO.getInstance().findByUserPassword(login, password, userListener);
    }

    private IAPIServiceResultListener<ArrayList<MCQ>> listMCQListener;

    public void setListMCQListener(IAPIServiceResultListener<ArrayList<MCQ>> listMCQListener) {
        this.listMCQListener = listMCQListener;
    }

    public void fetchCurrentUserMCQ(){
        final ArrayList<MCQ> mcqArrayList= new ArrayList<MCQ>();
        UserMCQDAO.getInstance().findByUser(getCurrentUser(), new IAPIServiceResultListener<ArrayList<UserMCQ>>() {
            @Override
            public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
                for(UserMCQ userMCQ: userMCQArrayList){
                    MCQDAO.getInstance().find(userMCQ.getMcq(), new IAPIServiceResultListener<MCQ>() {
                        @Override
                        public void onApiResultListener(MCQ obj, ParseException e) {
                            mcqArrayList.add(obj);
                        }
                    });
                }
            }
        });
    }
}
