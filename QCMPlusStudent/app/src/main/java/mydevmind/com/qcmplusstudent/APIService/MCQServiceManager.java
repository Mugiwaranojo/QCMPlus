<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.APIService;
=======
package mydevmind.com.qcmplusstudent.apiService;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655

import android.content.Context;

import com.parse.Parse;

<<<<<<< HEAD
import mydevmind.com.qcmplusstudent.APIService.DAO.UserDAO;
import mydevmind.com.qcmplusstudent.Model.User;
=======
import mydevmind.com.qcmplusstudent.apiService.dao.UserDAO;
import mydevmind.com.qcmplusstudent.model.User;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655

/**
 * Created by Joan on 29/07/2014.
 */
public class MCQServiceManager {


<<<<<<< HEAD
    private static final String APP_ID="4UNxW53O9e42UjNxLaGma5foAtZQpE22H2IwZ9y3";
    private static final String CLIENT_KEY="zqk5C0BKHuWmSaIrSfuWFVyH4MRlAd7g3iY9uUCg";
=======
    private static final String APP_ID="1XpHvksxkUpokKjgKINeQuzwCUAAkyFpwRHBZTz3";
    private static final String CLIENT_KEY="pJFS5zqqaBKosNs69n1MQxV8kVSta0y3c0NrRUJW";
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655

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
