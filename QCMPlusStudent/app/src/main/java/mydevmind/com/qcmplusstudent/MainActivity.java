package mydevmind.com.qcmplusstudent;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import mydevmind.com.qcmplusstudent.fragment.IFragmentActionListener;
import mydevmind.com.qcmplusstudent.fragment.LoginFragment;
import mydevmind.com.qcmplusstudent.fragment.MainFragment;


public class MainActivity extends Activity implements IFragmentActionListener{

    public static final Integer ACTION_CONNECT=1905;

    private LoginFragment loginFragment;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loginFragment= new LoginFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.container, loginFragment)
                    .commit();
        }
    }

    @Override
    public void onFragmentAction(Integer action, Object obj) {
        if(action.equals(ACTION_CONNECT)){
            if(mainFragment==null){
                mainFragment= new MainFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, mainFragment)
                        .commit();
            }
        }
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