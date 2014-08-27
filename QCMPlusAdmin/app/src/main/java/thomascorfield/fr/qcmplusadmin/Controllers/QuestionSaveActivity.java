package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.Option;
import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

public class QuestionSaveActivity extends Activity implements IAPIServiceResultListener<Question> {

    private EditText editTextStatement;

    private EditText editTextOption1;
    private EditText editTextOption2;
    private EditText editTextOption3;
    private EditText editTextOption4;
    private EditText editTextOption5;

    private Button questionSaveBtn;

    private Intent intentFromMCQ;
    private Question currentQuestion;
    private MCQ currentMcq;

    private Option option1;
    private Option option2;
    private Option option3;
    private Option option4;
    private Option option5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_save);

        this.editTextStatement = (EditText) findViewById(R.id.editTextStatement);

        this.editTextOption1 = (EditText) findViewById(R.id.editTextOption1);
        this.editTextOption2 = (EditText) findViewById(R.id.editTextOption2);
        this.editTextOption3 = (EditText) findViewById(R.id.editTextOption3);
        this.editTextOption4 = (EditText) findViewById(R.id.editTextOption4);
        this.editTextOption5 = (EditText) findViewById(R.id.editTextOption5);

        intentFromMCQ = getIntent();
        currentQuestion  = (Question) intentFromMCQ.getSerializableExtra("Question");
        currentMcq  = (MCQ) intentFromMCQ.getSerializableExtra("MCQ");

        if (currentQuestion.getStatement() == null) {

            this.editTextStatement.setText("");

            this.editTextOption1.setText("");
            this.editTextOption2.setText("");
            this.editTextOption3.setText("");
            this.editTextOption4.setText("");
            this.editTextOption5.setText("");

            currentQuestion = new Question();

            option1 = new Option();
            option2 = new Option();
            option3 = new Option();
            option4 = new Option();
            option5 = new Option();

            ArrayList<Option> opts = new ArrayList<Option>();
            opts.add(option1);
            opts.add(option2);
            opts.add(option3);
            opts.add(option4);
            opts.add(option5);

            currentQuestion.setOptions(opts);

        } else {

            this.editTextStatement.setText(currentQuestion.getStatement());

            this.editTextOption1.setText(currentQuestion.getOptions().get(0).getStatement());
            this.editTextOption2.setText(currentQuestion.getOptions().get(1).getStatement());
            this.editTextOption3.setText(currentQuestion.getOptions().get(2).getStatement());
            this.editTextOption4.setText(currentQuestion.getOptions().get(3).getStatement());
            this.editTextOption5.setText(currentQuestion.getOptions().get(4).getStatement());
        }

        MCQServiceManager.getInstance(this).setQuestionListener(this);

        this.questionSaveBtn = (Button) findViewById(R.id.questionSaveBtn);
        this.questionSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                currentQuestion.setStatement(editTextStatement.getText().toString());

                currentQuestion.getOptions().get(0).setStatement(editTextOption1.getText().toString());
                currentQuestion.getOptions().get(1).setStatement(editTextOption2.getText().toString());
                currentQuestion.getOptions().get(2).setStatement(editTextOption3.getText().toString());
                currentQuestion.getOptions().get(3).setStatement(editTextOption4.getText().toString());
                currentQuestion.getOptions().get(4).setStatement(editTextOption5.getText().toString());

                MCQServiceManager.getInstance(getApplicationContext()).saveQuestion(currentQuestion, currentMcq);
            }
        });
    }

    @Override
    public void onApiResultListener(Question obj, ParseException e) {
        if(obj!=null){
            Toast.makeText(this, "Question enregistrée", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MCQListActivity.class));
        }
    }
}
