package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import thomascorfield.fr.qcmplusadmin.Controllers.adapter.UserMCQAdapter;
import thomascorfield.fr.qcmplusadmin.Model.UserMCQ;
import thomascorfield.fr.qcmplusadmin.R;

/**
 * Created by Joan on 01/08/2014.
 */
public class UserMCQActivity extends Activity{

    private TextView textViewTitle;
    private TextView textViewScore;
    private TextView textViewDate;
    private TextView textViewTime;

    private ExpandableListView listViewQuestions;
    private UserMCQAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mcq);

        Intent intent= getIntent();
        UserMCQ userMCQ= (UserMCQ) intent.getSerializableExtra("userMcq");

        textViewTitle= (TextView) findViewById(R.id.textViewUserMCQTitle);
        textViewTitle.setText(userMCQ.getMcq().getName());
        textViewScore= (TextView) findViewById(R.id.textViewUserMCQScore);
        textViewScore.setText(userMCQ.getScore());
        textViewDate= (TextView) findViewById(R.id.textViewUserMCQDate);
        textViewDate.setText(userMCQ.getDateCreated().toLocaleString());
        textViewTime= (TextView) findViewById(R.id.textViewUserMCQTimeSpent);
        textViewTime.setText(userMCQ.getTimeSpent().toString());

        listViewQuestions = (ExpandableListView) findViewById(R.id.expandableListView);
        adapter= new UserMCQAdapter(this, userMCQ);
        listViewQuestions.setAdapter(adapter);
    }
}
