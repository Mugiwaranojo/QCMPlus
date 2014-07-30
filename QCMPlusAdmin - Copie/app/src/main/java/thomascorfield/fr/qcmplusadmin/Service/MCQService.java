package thomascorfield.fr.qcmplusadmin.Service;

import android.content.Context;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.Parse;

public class MCQService {

    private String API_KEY = "Z4Jxdk8TR8piZ2SLUGtdi6VDAoRI3qVx6NVB7YlW";
    private String CLIENT_KEY = "GL0hetM0cEen5fhjvkx6LUxjkin4lKNb1llNnnDy";

    private static MCQService sInstance;

    public static MCQService getInstance(Context context) {

        if (sInstance == null) {

            sInstance = new MCQService(context.getApplicationContext());
        }

        return sInstance;
    }


    private MCQService(Context context) {

        Parse.initialize(context, API_KEY, CLIENT_KEY);
    }

    public void connect(String username, String password, final OnConnectionResultListener listener) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser parseUser, ParseException e) {

                if(e != null) {

                    listener.onConnectionError(e);

                } else {

                    listener.onConnectionSuccess();
                }
            }
        });
    }
}