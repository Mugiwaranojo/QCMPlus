package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.R;

public class MCQSaveActivity extends Activity {

    private EditText editTextTitle;
    private EditText editTextDescription;

    private Button mcqSaveBtn;

    private Intent intent;
    private MCQ currentMcq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_save);

        this.editTextTitle = (EditText) findViewById(R.id.editTextName);
        this.editTextDescription = (EditText) findViewById(R.id.editTextDescription);

        intent = getIntent();
        currentMcq  = (MCQ) intent.getSerializableExtra("MCQ");

        if (currentMcq.getName() != null) {

            this.editTextTitle.setText(currentMcq.getName());
            this.editTextDescription.setText(currentMcq.getDescription());

        } else {

            this.editTextTitle.setText("");
            this.editTextDescription.setText("");
        }

        this.mcqSaveBtn = (Button) findViewById(R.id.mcqSaveBtn);
        this.mcqSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

