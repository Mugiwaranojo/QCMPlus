package thomascorfield.fr.qcmplusadmin.apiService.DAO;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.Model.User;

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
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("login", login);
        query.whereEqualTo("password", password);
        query.whereEqualTo("isAdmin", false);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    listener.onApiResultListener(parseObjectToUser(parseObjects.get(0)), e);
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
    }

    public static User parseObjectToUser(ParseObject pUser){
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
