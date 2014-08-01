package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Controllers.adapter.UserListUserMCQAdapter;
import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.Model.UserMCQ;
import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

/**
 * Created by Joan on 31/07/2014.
 */
public class UserListMCQActivity extends Activity implements IAPIServiceResultListener<ArrayList<UserMCQ>> {

    private ListView listViewUserMCQ;
    private TextView textViewUserName;
    private TextView textViewCompany;

    private UserListUserMCQAdapter adapter;
    private MCQServiceManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_mcq);

        Intent intentSelectUser= getIntent();
        User user= (User) intentSelectUser.getSerializableExtra("user");

        listViewUserMCQ = (ListView) findViewById(R.id.listViewMCQUserList);

        manager= MCQServiceManager.getInstance(this);
        manager.setListUserMCQListener(this);
        manager.fetchUserMCQ(user);
    }

    @Override
    public void onApiResultListener(ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
        adapter= new UserListUserMCQAdapter(this, userMCQArrayList);
        listViewUserMCQ.setAdapter(adapter);
        listViewUserMCQ.invalidate();
    }
}
