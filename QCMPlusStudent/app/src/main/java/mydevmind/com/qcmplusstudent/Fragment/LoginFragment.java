package mydevmind.com.qcmplusstudent.Fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mydevmind.com.qcmplusstudent.APIService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.R;

/**
 * Created by Joan on 29/07/2014.
 */
public class LoginFragment extends Fragment {

    private EditText loginField, passwordField;
    private Button loginButton;

    private MCQServiceManager apiManager;

    private ProgressDialog spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.f)
    }
}
