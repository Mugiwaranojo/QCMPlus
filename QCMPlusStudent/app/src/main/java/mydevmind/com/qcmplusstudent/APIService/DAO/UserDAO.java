<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.APIService.DAO;
=======
<<<<<<< HEAD
package mydevmind.com.qcmplusstudent.APIService.DAO;
=======
package mydevmind.com.qcmplusstudent.apiService.dao;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

<<<<<<< HEAD
import mydevmind.com.qcmplusstudent.APIService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.Model.User;
=======
<<<<<<< HEAD
import mydevmind.com.qcmplusstudent.APIService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.Model.User;
=======
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.User;
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6

/**
 * Created by Joan on 29/07/2014.
 */
public class UserDAO implements IDAO<User> {

    private static UserDAO instance;

    private UserDAO(){

    }

    public static UserDAO getInstance(){
        if(instance==null){
            instance= new UserDAO();
        }
        return instance;
    }

    @Override
    public void save(User obj, IAPIServiceResultListener<User> listener) {

    }

    @Override
    public void delete(User obj, IAPIServiceResultListener<User> listener) {

    }

    @Override
    public void find(User obj, IAPIServiceResultListener<User> listener) {

    }

    public void findByUserPassword(String login, String password, final IAPIServiceResultListener<User> listener){
<<<<<<< HEAD
=======
<<<<<<< HEAD
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Player");
        query.whereEqualTo("login", login);
        query.whereEqualTo("password", password);
        query.whereEqualTo("isAdmin", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToUser(parseObjects.get(0)), e);
                }else{
<<<<<<< HEAD
=======
=======
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("login", login);
        query.whereEqualTo("password", password);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(e==null) {
                    if (parseObjects.size() == 1) {
                        listener.onApiResultListener(parseObjectToUser(parseObjects.get(0)), e);
                    } else {
                        listener.onApiResultListener(null, null);
                    }
                }else{
                    e.printStackTrace();
>>>>>>> 6b1dce2db8498084639cd0557e0db57879a85655
>>>>>>> 8a7fb4f6a24358192bc2fbeb25c5e02d91ae7ed6
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public User parseObjectToUser(ParseObject pUser){
        User user= new User();
        user.setObjectId(pUser.getObjectId());
        user.setFirstname(pUser.getString("firstname"));
        user.setLastname(pUser.getString("lastname"));
        user.setCompany(pUser.getString("company"));
        user.setAdmin(pUser.getBoolean("isAdmin"));
        user.setLogin(pUser.getString("login"));
        user.setPassword(pUser.getString("password"));
        user.setCreatedAt(pUser.getCreatedAt());
        user.setUpdatedAt(pUser.getUpdatedAt());
        return user;
    }


}
