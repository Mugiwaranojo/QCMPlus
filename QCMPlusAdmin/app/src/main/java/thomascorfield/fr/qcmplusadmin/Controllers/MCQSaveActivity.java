package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.Controllers.adapter.QuestionAdapter;
import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

public class MCQSaveActivity extends Activity implements IAPIServiceResultListener<MCQ>  {

    private EditText editTextTitle;
    private EditText editTextDescription;

    private ListView listView;
    private Button addQuestionBtn;
    private Button mcqSaveBtn;

    private static final int ACTION_MODIFY = 333;
    private static final int ACTION_DELETE = 666;

    private ArrayList<Question> questions;

    private Intent addQuestionPageIntent;

    private Intent intentFromMCQList;
    private MCQ currentMcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_save);

        this.editTextTitle = (EditText) findViewById(R.id.editTextName);
        this.editTextDescription = (EditText) findViewById(R.id.editTextDescription);

        intentFromMCQList = getIntent();
        currentMcq  = (MCQ) intentFromMCQList.getSerializableExtra("MCQ");

        if (currentMcq.getName() == null) {

            this.editTextTitle.setText("");
            this.editTextDescription.setText("");

        } else {

            this.editTextTitle.setText(currentMcq.getName());
            this.editTextDescription.setText(currentMcq.getDescription());
        }

        MCQServiceManager.getInstance(this).setMCQListener(this);
        this.mcqSaveBtn = (Button) findViewById(R.id.mcqSaveBtn);
        this.mcqSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                currentMcq.setName(editTextTitle.getText().toString());
                currentMcq.setDescription(editTextDescription.getText().toString());
                MCQServiceManager.getInstance(getApplicationContext()).saveMCQ(currentMcq);
            }
        });

        this.questions = Question.getAllQuestions(10);

        this.listView = (ListView) findViewById(R.id.listView);
        QuestionAdapter adapter = new QuestionAdapter(this, questions);
        this.listView.setAdapter(adapter);

        this.addQuestionPageIntent = new Intent(this, QuestionSaveActivity.class);

        this.addQuestionBtn = (Button) findViewById(R.id.addQuestionBtn);
        this.addQuestionBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                addQuestionPageIntent.putExtra("Question", new Question());
                addQuestionPageIntent.putExtra("MCQ", currentMcq);
                startActivity(addQuestionPageIntent);
            }
        });

        View.OnCreateContextMenuListener listener = new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

                Question questionSelected = questions.get(info.position);

                menu.setHeaderTitle(questionSelected.getStatement());
                menu.add(Menu.NONE, ACTION_MODIFY, 0, "Modifier");
                menu.add(Menu.NONE, ACTION_DELETE, 1, "Supprimer");
            }
        };

        this.listView.setOnCreateContextMenuListener(listener);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        Question questionSelected = questions.get(info.position);

        switch (item.getItemId()) {

            case ACTION_MODIFY:

                addQuestionPageIntent.putExtra("Question", questionSelected);
                addQuestionPageIntent.putExtra("MCQ", currentMcq);
                startActivity(addQuestionPageIntent);

                break;

            case ACTION_DELETE:

                Toast.makeText(this, "Supprimer", Toast.LENGTH_LONG).show();
                break;

            default:
                break;

        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onApiResultListener(MCQ obj, ParseException e) {

        if(obj!= null){
            Toast.makeText(this, "Questionnaire enregistré.", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MCQListActivity.class));
        }
    }
}

