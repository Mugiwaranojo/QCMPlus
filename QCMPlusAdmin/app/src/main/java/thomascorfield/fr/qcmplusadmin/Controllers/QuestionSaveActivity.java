package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

public class QuestionSaveActivity extends Activity implements IAPIServiceResultListener<Question>, View.OnClickListener {

    private EditText editTextStatement;

    private ArrayList<EditText> editArray;
    private ArrayList<CheckBox> checkArray;

    private Button questionSaveBtn;

    private Intent intentFromMCQ;
    private Question currentQuestion;
    private MCQ currentMcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_save);

        this.editTextStatement = (EditText) findViewById(R.id.editTextStatement);

        editArray = new ArrayList<EditText>();
        editArray.add((EditText) findViewById(R.id.editTextOption1));
        editArray.add((EditText) findViewById(R.id.editTextOption2));
        editArray.add((EditText) findViewById(R.id.editTextOption3));
        editArray.add((EditText) findViewById(R.id.editTextOption4));
        editArray.add((EditText) findViewById(R.id.editTextOption5));

        checkArray = new ArrayList<CheckBox>();
        checkArray.add((CheckBox) findViewById(R.id.checkBoxOption1));
        checkArray.add((CheckBox) findViewById(R.id.checkBoxOption2));
        checkArray.add((CheckBox) findViewById(R.id.checkBoxOption3));
        checkArray.add((CheckBox) findViewById(R.id.checkBoxOption4));
        checkArray.add((CheckBox) findViewById(R.id.checkBoxOption5));

        for (int i = 0; i < 5; i++) { checkArray.get(i).setOnClickListener(this); }

        intentFromMCQ = getIntent();
        currentQuestion  = (Question) intentFromMCQ.getSerializableExtra("Question");
        currentMcq  = (MCQ) intentFromMCQ.getSerializableExtra("MCQ");

        if (currentQuestion.getStatement() == null) {

            this.editTextStatement.setText("");

            ArrayList<Option> opts = new ArrayList<Option>();

            for (int i = 0; i < 5; i++) {

                editArray.get(i).setText("");
                checkArray.get(i).setChecked(false);

                opts.add(new Option());
            }

            currentQuestion = new Question();
            currentQuestion.setOptions(opts);

        } else {

            this.editTextStatement.setText(currentQuestion.getStatement());

            int len = currentQuestion.getOptions().size();

            for (int i = 0; i < len; i++) {

                editArray.get(i).setText(currentQuestion.getOptions().get(i).getStatement());
                checkArray.get(i).setChecked(currentQuestion.getOptions().get(i).isChecked());
            }
        }

        MCQServiceManager.getInstance(this).setQuestionListener(this);

        this.questionSaveBtn = (Button) findViewById(R.id.questionSaveBtn);
        this.questionSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                currentQuestion.setStatement(editTextStatement.getText().toString());

                for (int i = 0; i < 5; i++) {

                    currentQuestion.getOptions().get(i).setChecked(checkArray.get(i).isChecked());
                    currentQuestion.getOptions().get(i).setStatement(editArray.get(i).getText().toString());
                }

                MCQServiceManager.getInstance(getApplicationContext()).saveQuestion(currentQuestion, currentMcq);
            }
        });
    }

    @Override
    public void onClick(View view) {

        if(view instanceof CheckBox){

            CheckBox selected = (CheckBox) view;

            for (int i = 0; i < 5; i++) {

                if(selected.equals(checkArray.get(i))){

                    checkArray.get(i).setChecked(true);

                } else {

                    checkArray.get(i).setChecked(false);
                }
            }
        }
    }

    @Override
    public void onApiResultListener(Question obj, ParseException e) {
        if(obj!=null){
            Toast.makeText(this, "Question enregistrée", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MCQListActivity.class));
        }
    }
}
