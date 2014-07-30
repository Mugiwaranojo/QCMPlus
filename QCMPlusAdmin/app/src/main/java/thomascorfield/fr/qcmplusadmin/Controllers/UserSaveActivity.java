package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.R;

public class UserSaveActivity extends Activity {

    private EditText editTextLastname;
    private EditText editTextFirstname;
    private EditText editTextLogin;
    private EditText editTextPassword;
    private EditText editTextCompany;

    private RadioButton adminNoRadioBtn;
    private RadioButton adminYesRadioBtn;

    private Button userSaveBtn;

    private Intent intent;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_save);

        this.editTextLastname = (EditText) findViewById(R.id.editTextLastname);
        this.editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        this.editTextLogin = (EditText) findViewById(R.id.editTextLogin);
        this.editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        this.editTextCompany = (EditText) findViewById(R.id.editTextCompany);

        this.adminNoRadioBtn = (RadioButton) findViewById(R.id.adminNoRadioBtn);
        this.adminYesRadioBtn = (RadioButton) findViewById(R.id.adminYesRadioBtn);

        intent = getIntent();
        currentUser  = (User) intent.getSerializableExtra("User");

        if (currentUser.getLastname() != null) {

            this.editTextLastname.setText(currentUser.getLastname());
            this.editTextFirstname.setText(currentUser.getFirstname());
            this.editTextLogin.setText(currentUser.getLogin());
            this.editTextPassword.setText(currentUser.getPassword());
            this.editTextCompany.setText(currentUser.getCompany());
            this.adminNoRadioBtn.setChecked(!currentUser.isAdmin());
            this.adminYesRadioBtn.setChecked(currentUser.isAdmin());

        } else {

            this.editTextLastname.setText("");
            this.editTextFirstname.setText("");
            this.editTextLogin.setText("");
            this.editTextPassword.setText("");
            this.editTextCompany.setText("");
            this.adminNoRadioBtn.setChecked(true);
            this.adminYesRadioBtn.setChecked(false);
        }

        this.userSaveBtn = (Button) findViewById(R.id.userSaveBtn);
        this.userSaveBtn.setOnClickListener(new View.OnClickListener() {

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
