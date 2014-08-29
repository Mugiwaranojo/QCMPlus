package mydevmind.com.qcmplusstudent;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import mydevmind.com.qcmplusstudent.fragment.AllMCQFragment;
import mydevmind.com.qcmplusstudent.fragment.IFragmentActionListener;
import mydevmind.com.qcmplusstudent.fragment.LoginFragment;
import mydevmind.com.qcmplusstudent.fragment.MCQDoneFragment;
import mydevmind.com.qcmplusstudent.fragment.MCQFragment;
import mydevmind.com.qcmplusstudent.fragment.MainFragment;
import mydevmind.com.qcmplusstudent.model.MCQ;
import mydevmind.com.qcmplusstudent.model.UserMCQ;


public class MainActivity extends Activity implements IFragmentActionListener{

    public static final Integer ACTION_CONNECT=1905;
    public static final Integer ACTION_VIEW_MCQDONE =1906;
    public static final Integer ACTION_VIEW_ALLMCQ=1907;
    public static final Integer ACTION_VIEW_MCQ=1908;

    private Integer currentAction;

    private LoginFragment loginFragment;
    private MainFragment mainFragment;
    private MCQDoneFragment mcqDoneFragment;
    private AllMCQFragment allMCQFragment;
    private MCQFragment mcqFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            loginFragment= new LoginFragment();
            loginFragment.setListener(this);
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
                mainFragment.setListener(this);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
        }else if(action.equals(ACTION_VIEW_ALLMCQ)){
            if(allMCQFragment==null){
                allMCQFragment= new AllMCQFragment();
                allMCQFragment.setListener(this);
            }
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, allMCQFragment)
                    .commit();
        }else if(action.equals(ACTION_VIEW_MCQDONE)){
            if(obj instanceof UserMCQ){
                UserMCQ userMCQ= (UserMCQ) obj;
                mcqDoneFragment= new MCQDoneFragment();
                mcqDoneFragment.setUserMCQ(userMCQ);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, mcqDoneFragment)
                        .commit();
            }
        }else if(action.equals(ACTION_VIEW_MCQ)){
            if(obj instanceof MCQ){
                MCQ mcq= (MCQ) obj;
                mcqFragment= new MCQFragment();
                mcqFragment.setCurrentMCQ(mcq);
                mcqFragment.setListener(this);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, mcqFragment)
                        .commit();
            }
        }
        currentAction= action;
    }

    @Override
    public void onBackPressed() {
        if(currentAction==ACTION_VIEW_MCQ){
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, allMCQFragment)
                    .commit();
            currentAction=ACTION_VIEW_ALLMCQ;
        }else{
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, mainFragment)
                    .commit();
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