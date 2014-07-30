package thomascorfield.fr.qcmplusadmin.apiService;

import android.content.Context;

import com.parse.Parse;
import com.parse.ParseException;

import java.util.ArrayList;

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
                    mcqArrayList.add(userMCQ.getMcq());
                }
                getCurrentUser().setUserMCQs(userMCQArrayList);
                listMCQListener.onApiResultListener(mcqArrayList, e);
            }
        });
    }
}
