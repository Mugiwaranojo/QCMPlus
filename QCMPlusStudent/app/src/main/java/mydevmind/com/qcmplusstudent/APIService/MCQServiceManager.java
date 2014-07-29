package mydevmind.com.qcmplusstudent.apiService;

import android.content.Context;

import com.parse.Parse;

import mydevmind.com.qcmplusstudent.apiService.dao.UserDAO;
import mydevmind.com.qcmplusstudent.model.User;

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQServiceManager {


    private static final String APP_ID="1XpHvksxkUpokKjgKINeQuzwCUAAkyFpwRHBZTz3";
    private static final String CLIENT_KEY="pJFS5zqqaBKosNs69n1MQxV8kVSta0y3c0NrRUJW";

    private Context context;
    private static MCQServiceManager instance;

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
}
