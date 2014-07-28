package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mydevmind.com.qcmplusstudent.R;

/**
 * Created by Joan on 28/07/2014.
 */
public class LoginFragment extends Fragment {

    public  LoginFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        return rootView;
    }
}
