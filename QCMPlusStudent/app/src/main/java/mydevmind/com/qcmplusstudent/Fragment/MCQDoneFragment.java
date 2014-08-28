package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.fragment.adapter.UserMCQAdapter;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 01/08/2014.
 */
public class MCQDoneFragment extends Fragment{

    private UserMCQ userMCQ;

    private TextView textViewTitle;
    private TextView textViewScore;
    private TextView textViewDate;
    private TextView textViewTime;

    private ExpandableListView listViewQuestions;
    private UserMCQAdapter adapter;

    public UserMCQ getUserMCQ() {
        return userMCQ;
    }

    public void setUserMCQ(UserMCQ userMCQ) {
        this.userMCQ = userMCQ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_user_mcq, null);

        textViewTitle= (TextView) v.findViewById(R.id.textViewUserMCQTitle);
        textViewTitle.setText(userMCQ.getMcq().getName());
        textViewScore= (TextView) v.findViewById(R.id.textViewUserMCQScore);
        textViewScore.setText(userMCQ.getScore());
        textViewDate= (TextView) v.findViewById(R.id.textViewUserMCQDate);
        textViewDate.setText(userMCQ.getDateCreated().toLocaleString());
        textViewTime= (TextView) v.findViewById(R.id.textViewUserMCQTimeSpent);
        textViewTime.setText(userMCQ.getTimeSpent().toString());

        listViewQuestions = (ExpandableListView) v.findViewById(R.id.expandableListView);
        adapter= new UserMCQAdapter(getActivity(), userMCQ);
        listViewQuestions.setAdapter(adapter);

        return v;
    }
}
