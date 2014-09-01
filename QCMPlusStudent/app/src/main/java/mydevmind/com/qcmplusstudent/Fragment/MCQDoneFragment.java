package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.fragment.adapter.UserMCQAdapter;
import mydevmind.com.qcmplusstudent.model.Question;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * MCQDoneFragment
 * Fragment de consulation d'un MCQ répondu par l'utilisateur
 */
public class MCQDoneFragment extends Fragment{

    private UserMCQ userMCQ;

    private ExpandableListView listViewQuestions;
    private UserMCQAdapter adapter;
    private ProgressDialog spinner;

    public void setUserMCQ(UserMCQ userMCQ) {
        this.userMCQ = userMCQ;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_user_mcq, null);

        TextView textViewTitle = (TextView) v.findViewById(R.id.textViewUserMCQTitle);
        textViewTitle.setText(userMCQ.getMcq().getName());
        TextView textViewScore = (TextView) v.findViewById(R.id.textViewUserMCQScore);
        textViewScore.setText(userMCQ.getScore());
        TextView textViewDate = (TextView) v.findViewById(R.id.textViewUserMCQDate);
        textViewDate.setText(userMCQ.getDateCreated().toLocaleString());
        TextView textViewTime = (TextView) v.findViewById(R.id.textViewUserMCQTimeSpent);
        Date date = new Date((long)(userMCQ.getTimeSpent()*1000));
        String formattedDate = new SimpleDateFormat("mm:ss").format(date);
        textViewTime.setText(formattedDate);

        ImageView imageViewScore= (ImageView) v.findViewById(R.id.imageViewIconScore);
        imageViewScore.setColorFilter(Color.parseColor("#4BC0C6"));
        ImageView imageViewDate= (ImageView) v.findViewById(R.id.imageViewIconDate);
        imageViewDate.setColorFilter(Color.parseColor("#4BC0C6"));
        ImageView imageViewTime= (ImageView) v.findViewById(R.id.imageViewIconTimeSpent);
        imageViewTime.setColorFilter(Color.parseColor("#4BC0C6"));

        listViewQuestions = (ExpandableListView) v.findViewById(R.id.expandableListView);
        if(userMCQ.getUserAnswers()!=null){
            loadMCQQuestions();
            adapter = new UserMCQAdapter(getActivity(), userMCQ);
            listViewQuestions.setAdapter(adapter);
            listViewQuestions.invalidate();
        }else {
            loadUserAnswers();
        }
        return v;
    }

    private void loadMCQQuestions(){
        if(userMCQ.getUserAnswers()!=null&& userMCQ.getMcq().getQuestions()==null){
            userMCQ.getMcq().setQuestions(new ArrayList<Question>());
            for (UserAnswer userAnswer: userMCQ.getUserAnswers()){
                userMCQ.getMcq().getQuestions().add(userAnswer.getQuestion());
            }
        }
    }

    private void loadUserAnswers(){
        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getString(R.string.loading_spinner_title));
        spinner.setMessage(getString(R.string.login_spinner_text));
        spinner.setCancelable(false);
        spinner.show();
        MCQServiceManager.getInstance(getActivity()).setlistUserAnswerListener(new IAPIServiceResultListener<ArrayList<UserAnswer>>() {
            @Override
            public void onApiResultListener(ArrayList<UserAnswer> answers, ParseException e) {
                userMCQ.setUserAnswers(answers);
                loadMCQQuestions();
                adapter = new UserMCQAdapter(getActivity(), userMCQ);
                listViewQuestions.setAdapter(adapter);
                listViewQuestions.invalidate();
                spinner.dismiss();
            }
        });
        MCQServiceManager.getInstance(getActivity()).fetchUserAnswers(userMCQ);
    }
}
