package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import thomascorfield.fr.qcmplusadmin.R;


public class AdminActivity extends Activity {

    private Button usersPageBtn;
    private Button mcqPageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        this.usersPageBtn = (Button) findViewById(R.id.usersPageBtn);
        this.mcqPageBtn = (Button) findViewById(R.id.mcqPageBtn);

        final Intent showMcqPageIntent;
        showMcqPageIntent = new Intent(this, MCQActivity.class);

        this.mcqPageBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(showMcqPageIntent);
            }
        });

        final Intent showUsersPageIntent;
        showUsersPageIntent = new Intent(this, UserActivity.class);

        this.usersPageBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(showUsersPageIntent);
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
