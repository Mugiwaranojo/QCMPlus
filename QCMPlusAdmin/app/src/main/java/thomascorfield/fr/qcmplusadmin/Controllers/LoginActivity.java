package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

public class LoginActivity extends Activity implements View.OnClickListener, IAPIServiceResultListener<User> {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button connectButton;

    ProgressDialog spinner;
    private MCQServiceManager serviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        serviceManager= MCQServiceManager.getInstance(this);
        serviceManager.setUserListener(this);

        this.loginEditText = (EditText) findViewById(R.id.editTextConnectLogin);
        this.passwordEditText = (EditText) findViewById(R.id.editTextConnectPassword);
        this.connectButton = (Button) findViewById(R.id.buttonConnect);
        this.connectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String user = this.loginEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        spinner = new ProgressDialog(this);
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Connexion ...");
        spinner.setCancelable(false);
        spinner.show();

        serviceManager.connect("tcorfield", "1234");
    }

    @Override
    public void onApiResultListener(User obj, ParseException e) {
        spinner.dismiss();
        if(obj!=null){
            serviceManager.setCurrentUser(obj);
            Intent showAdminPageIntent = new Intent(this, AdminActivity.class);
            startActivity(showAdminPageIntent);
        }else{
            this.loginEditText.setText("");
            this.passwordEditText.setText("");
            Toast.makeText(this, "Identifiant ou mot de passe incorrect.", Toast.LENGTH_LONG).show();
        }
    }
}
