package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import thomascorfield.fr.qcmplusadmin.R;


public class MCQActivity extends Activity {

    private Button addMcqBtn;
    private Button mcqListBtn;
    private Button questionsPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);

        this.addMcqBtn = (Button) findViewById(R.id.addMcqBtn);
        this.mcqListBtn = (Button) findViewById(R.id.mcqListBtn);
        this.questionsPageBtn = (Button) findViewById(R.id.questionsPageBtn);

        final Intent showMcqListIntent;
        showMcqListIntent = new Intent(this, MCQListActivity.class);

        this.mcqListBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(showMcqListIntent);
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
