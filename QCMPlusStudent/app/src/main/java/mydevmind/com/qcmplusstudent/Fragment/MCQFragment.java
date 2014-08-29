package mydevmind.com.qcmplusstudent.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.MainActivity;
import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.Question;
import mydevmind.com.qcmplusstudent.model.User;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * Created by Joan on 28/08/2014.
 */
public class MCQFragment extends Fragment implements IAPIServiceResultListener<ArrayList<Question>>, View.OnClickListener {

    private MCQ currentMCQ;

    public MCQ getCurrentMCQ() {
        return currentMCQ;
    }

    public void setCurrentMCQ(MCQ currentMCQ) {
        this.currentMCQ = currentMCQ;
    }

    private UserMCQ userMCQ;

    private Integer currentQuestionID;
    private MCQServiceManager manager;
    private ProgressDialog spinner;

    public void setListener(IFragmentActionListener listener) {
        this.listener = listener;
    }

    private IFragmentActionListener listener;

    private TextView textViewQuestionStatement;
    private RadioButton[] radioButtonsOptions;
    private Button buttonPrevious;
    private Button buttonNext;
    private Button buttonSaveMCQ;
    private TextView textViewNbrQuestion;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mcq, null);


        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getString(R.string.loading_spinner_title));
        spinner.setMessage(getString(R.string.login_spinner_text));
        spinner.setCancelable(false);
        spinner.show();

        manager= MCQServiceManager.getInstance(getActivity());
        manager.setListMCQQuestionListener(this);
        manager.fetchMCQQuestions(currentMCQ);


        userMCQ= new UserMCQ();
        userMCQ.setMcq(currentMCQ);
        userMCQ.setUser(manager.getCurrentUser());
        userMCQ.setState(UserMCQ.State.INPROGRESS.toString());
        userMCQ.setUserAnswers(new ArrayList<UserAnswer>());

        textViewQuestionStatement= (TextView) v.findViewById(R.id.textViewQuestionStatement);
        radioButtonsOptions= new RadioButton[5];
        radioButtonsOptions[0]= (RadioButton) v.findViewById(R.id.radioButtonOption1);
        radioButtonsOptions[1]= (RadioButton) v.findViewById(R.id.radioButtonOption2);
        radioButtonsOptions[2]= (RadioButton) v.findViewById(R.id.radioButtonOption3);
        radioButtonsOptions[3]= (RadioButton) v.findViewById(R.id.radioButtonOption4);
        radioButtonsOptions[4]= (RadioButton) v.findViewById(R.id.radioButtonOption5);
        for(int i=0; i<5;i++){
            radioButtonsOptions[i].setOnClickListener(this);
        }

        buttonNext= (Button) v.findViewById(R.id.buttonNextQuestion);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestionID++;
                loadQuestion();
            }
        });
        buttonPrevious= (Button) v.findViewById(R.id.buttonPreviousQuestion);
        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuestionID--;
                loadQuestion();
            }
        });
        textViewNbrQuestion= (TextView) v.findViewById(R.id.textViewNbrQuestion);
        currentQuestionID=0;

        buttonSaveMCQ= (Button) v.findViewById(R.id.buttonSaveUserMCQ);
        buttonSaveMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manager.setUserMCQListener(new IAPIServiceResultListener<UserMCQ>() {
                    @Override
                    public void onApiResultListener(UserMCQ obj, ParseException e) {
                        spinner.dismiss();
                        listener.onFragmentAction(MainActivity.ACTION_VIEW_MCQDONE, obj);
                    }
                });
                manager.saveUserMCQ(userMCQ);
                spinner.show();
            }
        });
        buttonSaveMCQ.setVisibility(View.INVISIBLE);
        return v;
    }

    private void saveUserAnswer() {
        int reponseFind=-1;
        for(int i=0; i<5; i++){
            if(radioButtonsOptions[i].isChecked()){
                reponseFind=i;
            }
        }
        if(reponseFind!=-1){
            boolean find=false;
            Question question= currentMCQ.getQuestions().get(currentQuestionID);
            for(UserAnswer userAnswer: userMCQ.getUserAnswers()){
                if(userAnswer.getQuestion().getObjectId().equals(question.getObjectId())){
                    userAnswer.setAnswer(question.getOptions().get(reponseFind));
                    find=true;
                }
            }
            if(!find){
                UserAnswer userAnswer= new UserAnswer();
                userAnswer.setQuestion(question);
                userAnswer.setAnswer(question.getOptions().get(reponseFind));
                userMCQ.getUserAnswers().add(userAnswer);
            }
        }
    }

    public void loadUserAnswer(){
        Question question= currentMCQ.getQuestions().get(currentQuestionID);
        for(UserAnswer userAnswer: userMCQ.getUserAnswers()){
            if(userAnswer.getQuestion().getObjectId().equals(question.getObjectId())){
                for(RadioButton radioButton: radioButtonsOptions){
                    if(radioButton.getText().toString().equals(userAnswer.getAnswer().getStatement())){
                        radioButton.setChecked(true);
                    }
                }
            }
        }
    }

    private void loadQuestion(){
        Question question= currentMCQ.getQuestions().get(currentQuestionID);
        textViewQuestionStatement.setText(question.getStatement());
        for(int i=0; i<5; i++){
            if(i<question.getOptions().size()){
                radioButtonsOptions[i].setText(question.getOptions().get(i).getStatement());
            }else{
                radioButtonsOptions[i].setVisibility(View.INVISIBLE);
            }
        }
        if(currentQuestionID==0){
            buttonPrevious.setEnabled(false);
            buttonNext.setEnabled(true);
        }else if(currentQuestionID==currentMCQ.getQuestions().size()-1){
            buttonPrevious.setEnabled(true);
            buttonNext.setEnabled(false);
            if(userMCQ.getUserAnswers().size()==currentMCQ.getQuestions().size()) {
                buttonSaveMCQ.setVisibility(View.VISIBLE);
            }
        }else{
            buttonPrevious.setEnabled(true);
            buttonNext.setEnabled(true);
            buttonSaveMCQ.setVisibility(View.INVISIBLE);
        }

        int nbr= currentQuestionID+1;
        textViewNbrQuestion.setText(nbr+"/"+currentMCQ.getQuestions().size());
        uncheckedAllOptions();
        loadUserAnswer();
    }

    @Override
    public void onApiResultListener(ArrayList<Question> obj, ParseException e) {
        spinner.dismiss();
        currentMCQ.setQuestions(obj);
        userMCQ.getMcq().setQuestions(obj);
        loadQuestion();
    }

    @Override
    public void onClick(View view) {
        if(view instanceof RadioButton){
            RadioButton selected= (RadioButton) view;
            for(RadioButton radioButton: radioButtonsOptions){
                if(selected.equals(radioButton)){
                    radioButton.setChecked(true);
                }else{
                    radioButton.setChecked(false);
                }
            }
            saveUserAnswer();
            if(userMCQ.getUserAnswers().size()==currentMCQ.getQuestions().size()) {
                buttonSaveMCQ.setVisibility(View.VISIBLE);
            }
        }
    }

    private void uncheckedAllOptions(){
        for(RadioButton radioButton: radioButtonsOptions){
            radioButton.setChecked(false);
        }
    }
}
