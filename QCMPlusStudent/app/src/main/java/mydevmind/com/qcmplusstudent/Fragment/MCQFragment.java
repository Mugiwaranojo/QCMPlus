package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ScrollView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.MainActivity;
import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.Question;
import mydevmind.com.qcmplusstudent.model.UserAnswer;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * MCQFragment
 * Fragment perméttant à l'utilisateur de répondre à un questionnaire
 */
public class MCQFragment extends Fragment implements IAPIServiceResultListener<ArrayList<Question>>, View.OnClickListener {

    private MCQ currentMCQ;

    public void setCurrentMCQ(MCQ currentMCQ) {
        this.currentMCQ = currentMCQ;
    }

    private UserMCQ userMCQ;

    private Integer currentQuestionID;
    private MCQServiceManager manager;
    private ProgressDialog spinner;
    private ScrollView scrollView;

    public void setListener(IFragmentActionListener listener) {
        this.listener = listener;
    }

    private IFragmentActionListener listener;

    private TextView textViewQuestionStatement;
    private CheckBox[] checkboxOptions;
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
        checkboxOptions = new CheckBox[5];
        checkboxOptions[0]= (CheckBox) v.findViewById(R.id.checkBoxOption1);
        checkboxOptions[1]= (CheckBox) v.findViewById(R.id.checkBoxOption2);
        checkboxOptions[2]= (CheckBox) v.findViewById(R.id.checkBoxOption3);
        checkboxOptions[3]= (CheckBox) v.findViewById(R.id.checkBoxOption4);
        checkboxOptions[4]= (CheckBox) v.findViewById(R.id.checkBoxOption5);
        for(int i=0; i<5;i++){
            checkboxOptions[i].setOnClickListener(this);
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

        scrollView= (ScrollView) v.findViewById(R.id.scrollViewMCQ);
        scrollView.setVisibility(View.INVISIBLE);
        return v;
    }

    private void saveUserAnswer() {
        int reponseFind=-1;
        for(int i=0; i<5; i++){
            if(checkboxOptions[i].isChecked()){
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
                for(CheckBox checkBox: checkboxOptions){
                    if(checkBox.getText().toString().equals(userAnswer.getAnswer().getStatement())){
                        checkBox.setChecked(true);
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
                checkboxOptions[i].setText(question.getOptions().get(i).getStatement());
            }else{
                checkboxOptions[i].setVisibility(View.INVISIBLE);
            }
        }
        if(currentQuestionID==0){
            buttonPrevious.setVisibility(View.INVISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        }else if(currentQuestionID==currentMCQ.getQuestions().size()-1){
            buttonPrevious.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.INVISIBLE);
        }else{
            buttonPrevious.setVisibility(View.VISIBLE);
            buttonNext.setVisibility(View.VISIBLE);
        }

        int nbr= currentQuestionID+1;
        textViewNbrQuestion.setText(nbr+"/"+currentMCQ.getQuestions().size());
        uncheckedAllOptions();
        loadUserAnswer();
    }

    @Override
    public void onApiResultListener(ArrayList<Question> obj, ParseException e) {
        spinner.dismiss();
        scrollView.setVisibility(View.VISIBLE);
        currentMCQ.setQuestions(obj);
        userMCQ.getMcq().setQuestions(obj);
        loadQuestion();
    }

    @Override
    public void onClick(View view) {
        if(view instanceof CheckBox){
            CheckBox selected= (CheckBox) view;
            for(CheckBox checkBox: checkboxOptions){
                if(selected.equals(checkBox)){
                    checkBox.setChecked(true);
                }else{
                    checkBox.setChecked(false);
                }
            }
            saveUserAnswer();
            if(userMCQ.getUserAnswers().size()==currentMCQ.getQuestions().size()) {
                buttonSaveMCQ.setVisibility(View.VISIBLE);
            }
        }
    }

    private void uncheckedAllOptions(){
        for(CheckBox checkBox: checkboxOptions){
            checkBox.setChecked(false);
        }
    }
}
