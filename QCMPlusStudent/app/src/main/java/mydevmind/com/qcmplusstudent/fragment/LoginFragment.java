package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;

import mydevmind.com.qcmplusstudent.MainActivity;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.model.User;
import mydevmind.com.qcmplusstudent.R;

/**
 * LoginFragment
 * Fragment de connection de l'utilisateur
 */
public class LoginFragment extends Fragment implements IAPIServiceResultListener<User>{

    private EditText loginField, passwordField;

    private IFragmentActionListener listener;

    public void setListener(IFragmentActionListener listener) {
        this.listener = listener;
    }

    private MCQServiceManager manager;
    private ProgressDialog spinner;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        manager= MCQServiceManager.getInstance(getActivity());
        manager.setUserListener(this);

        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getString(R.string.login_spinner_title));
        spinner.setMessage(getString(R.string.login_spinner_text));
        spinner.setCancelable(false);

        sharedPreferences= getActivity().getSharedPreferences("Login MCQ", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loginField = (EditText) v.findViewById(R.id.editTextConnectLogin);
        loginField.setText(sharedPreferences.getString("login", ""));
        passwordField= (EditText) v.findViewById(R.id.editTextConnectPassword);
        passwordField.setText(sharedPreferences.getString("password", ""));
        Button loginButton = (Button) v.findViewById(R.id.buttonConnect);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.show();
                editor.putString("login", loginField.getText().toString());
                editor.putString("password", passwordField.getText().toString());
                editor.commit();
                manager.connect(loginField.getText().toString(), passwordField.getText().toString());
            }
        });
        return v;
    }

    @Override
    public void onApiResultListener(User obj, ParseException e) {
        spinner.dismiss();
        if(obj!=null) {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.login_toast_welcom) + " " + obj.getFirstname(), Toast.LENGTH_SHORT).show();
            manager.setCurrentUser(obj);
            listener.onFragmentAction(MainActivity.ACTION_CONNECT, obj);
        }else {
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.login_toast_connection_failed), Toast.LENGTH_SHORT).show();
        }
    }
}
