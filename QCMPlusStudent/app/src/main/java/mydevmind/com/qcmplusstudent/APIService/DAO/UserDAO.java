package mydevmind.com.qcmplusstudent.apiService.DAO;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;
import java.util.List;

import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.model.User;


/**
 * DAO du Model User
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
    public void save(User obj,final IAPIServiceResultListener<User> listener) {
        final ParseObject user=  new ParseObject("User");
        if(obj.getObjectId()!=null){
            user.setObjectId(obj.getObjectId());
        }
        user.put("firstname", obj.getFirstname());
        user.put("lastname", obj.getLastname());
        user.put("company", obj.getCompany());
        user.put("isAdmin", obj.isAdmin());
        user.put("login", obj.getLogin());
        user.put("password", obj.getPassword());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                listener.onApiResultListener(parseObjectToUser(user), e);
            }
        });
    }

    @Override
    public void delete(User obj,final IAPIServiceResultListener<User> listener) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
        query.whereEqualTo("objectId", obj.getObjectId());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                if(parseObjects.size()==1){
                    final ParseObject pUser= parseObjects.get(0);
                    pUser.deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            listener.onApiResultListener(parseObjectToUser(pUser), e);
                        }
                    });
                }else{
                    listener.onApiResultListener(null, e);
                }
            }
        });
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
