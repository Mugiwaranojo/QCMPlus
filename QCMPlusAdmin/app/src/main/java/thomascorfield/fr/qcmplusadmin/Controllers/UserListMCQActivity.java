package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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



    private ProgressDialog spinner;

    private UserListUserMCQAdapter adapter;
    private MCQServiceManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_mcq);

        Intent intentSelectUser= getIntent();
        User user= (User) intentSelectUser.getSerializableExtra("user");

        textViewUserName = (TextView) findViewById(R.id.textViewMCQTitleUserList);
        textViewUserName.setText(user.toString());
        textViewCompany = (TextView) findViewById(R.id.textViewMCQSummaryUserList);
        textViewCompany.setText(user.getCompany());
        listViewUserMCQ = (ListView) findViewById(R.id.listViewMCQUserList);

        spinner = new ProgressDialog(this);
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Chargement ...");
        spinner.setMessage("Patientez, ceci peut prendre quelques secondes");
        spinner.setCancelable(true);

        spinner.show();
        manager= MCQServiceManager.getInstance(this);
        manager.setListUserMCQListener(this);
        manager.fetchUserMCQ(user);
    }

    @Override
    public void onApiResultListener(final ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
        spinner.dismiss();
        adapter= new UserListUserMCQAdapter(this, userMCQArrayList);
        listViewUserMCQ.setAdapter(adapter);
        listViewUserMCQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(getApplicationContext(), UserMCQActivity.class);
                intent.putExtra("userMcq", userMCQArrayList.get(i));
                startActivity(intent);
            }
        });
        listViewUserMCQ.invalidate();
    }
}
