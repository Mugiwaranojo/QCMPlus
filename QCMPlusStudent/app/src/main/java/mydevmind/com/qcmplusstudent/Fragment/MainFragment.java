package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.MainActivity;
import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.fragment.adapter.UserListUserMCQAdapter;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * MainFragment
 * Fragment page principale de l'utilisateur
 */
public class MainFragment extends Fragment implements IAPIServiceResultListener<ArrayList<UserMCQ>>{

    private LinearLayout selectAllMCQ;
    private ListView listViewMyMCQ;
    private ProgressDialog spinner;

    private IFragmentActionListener listener;
    public void setListener(IFragmentActionListener listener) {
        this.listener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, null);

        selectAllMCQ = (LinearLayout) v.findViewById(R.id.LinearLayoutAllMCQ);
        selectAllMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFragmentAction(MainActivity.ACTION_VIEW_ALLMCQ, null);
            }
        });

        listViewMyMCQ = (ListView) v.findViewById(R.id.listViewMyMCQ);

        if(savedInstanceState==null) {
            spinner = new ProgressDialog(getActivity());
            spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            spinner.setTitle(getString(R.string.loading_spinner_title));
            spinner.setMessage(getString(R.string.login_spinner_text));
            spinner.setCancelable(false);
            spinner.show();

            MCQServiceManager manager = MCQServiceManager.getInstance(getActivity());
            manager.setListUserMCQListener(this);
            manager.fetchCurrentUserMCQDone();
        }
        return v;
    }

    @Override
    public void onApiResultListener(final ArrayList<UserMCQ> userMCQArrayList, ParseException e) {
        spinner.dismiss();
        UserListUserMCQAdapter adapter = new UserListUserMCQAdapter(getActivity(), userMCQArrayList);
        listViewMyMCQ.setAdapter(adapter);
        listViewMyMCQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onFragmentAction(MainActivity.ACTION_VIEW_MCQDONE, userMCQArrayList.get(i));
            }
        });
        listViewMyMCQ.invalidate();
    }
}
