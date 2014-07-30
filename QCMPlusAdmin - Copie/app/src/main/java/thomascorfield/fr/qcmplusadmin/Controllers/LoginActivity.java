package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import thomascorfield.fr.qcmplusadmin.Service.MCQService;
import thomascorfield.fr.qcmplusadmin.Service.OnConnectionResultListener;
import thomascorfield.fr.qcmplusadmin.R;

public class LoginActivity extends Activity implements View.OnClickListener, OnConnectionResultListener {

    private EditText loginEditText;
    private EditText passwordEditText;
    private Button connectButton;

    ProgressDialog spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginEditText = (EditText) findViewById(R.id.editTextConnectLogin);
        this.passwordEditText = (EditText) findViewById(R.id.editTextConnectPassword);
        this.connectButton = (Button) findViewById(R.id.buttonConnect);

        this.connectButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        /*String user = this.loginEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        MCQService service = MCQService.getInstance(this);

        service.connect(user, password, this);

        spinner = new ProgressDialog(this);
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle("Connexion ...");
        spinner.setCancelable(false);

        spinner.show();*/

        Intent showAdminPageIntent = new Intent(this, AdminActivity.class);

        startActivity(showAdminPageIntent);
    }

    @Override
    public void onConnectionSuccess() {

        //spinner.dismiss();

        Intent showAdminPageIntent = new Intent(this, AdminActivity.class);

        startActivity(showAdminPageIntent);
    }

    @Override
    public void onConnectionError(Exception e) {

        this.loginEditText.setText("");
        this.passwordEditText.setText("");

        Toast.makeText(this, "Error : " + e.toString(), Toast.LENGTH_LONG).show();

    }
}
