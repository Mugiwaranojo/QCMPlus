package mydevmind.com.qcmplusstudent.APIService;

import android.content.Context;

import com.parse.Parse;

import mydevmind.com.qcmplusstudent.APIService.DAO.UserDAO;
import mydevmind.com.qcmplusstudent.Model.User;

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQServiceManager {


    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";

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
