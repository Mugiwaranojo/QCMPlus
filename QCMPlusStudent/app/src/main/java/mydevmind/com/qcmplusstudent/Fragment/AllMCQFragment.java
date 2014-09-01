package mydevmind.com.qcmplusstudent.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.MainActivity;
import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.fragment.adapter.MCQAdapter;
import mydevmind.com.qcmplusstudent.model.MCQ;

/**
 * AllMCQFragment
 * Fragments de la listes de tous les MCQ non repondus par l'utilisateur
 */
public class AllMCQFragment extends Fragment implements IAPIServiceResultListener<ArrayList<MCQ>>{

    private ListView listViewMCQ;
    private ProgressDialog spinner;

    private IFragmentActionListener listener;
    public void setListener(IFragmentActionListener listener) {
        this.listener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_all_mcq, null);
        listViewMCQ = (ListView) v.findViewById(R.id.listViewAllMCQ);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        spinner = new ProgressDialog(getActivity());
        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        spinner.setTitle(getString(R.string.loading_spinner_title));
        spinner.setMessage(getString(R.string.login_spinner_text));
        spinner.setCancelable(false);
        spinner.show();

        MCQServiceManager manager = MCQServiceManager.getInstance(getActivity());
        manager.setListMCQListener(this);
        manager.fetchAllMCQ();

    }

    @Override
    public void onApiResultListener(final ArrayList<MCQ> mcqArrayList, ParseException e) {
        spinner.dismiss();
        MCQAdapter adapter = new MCQAdapter(getActivity(), mcqArrayList);
        listViewMCQ.setAdapter(adapter);
        listViewMCQ.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listener.onFragmentAction(MainActivity.ACTION_VIEW_MCQ, mcqArrayList.get(i));
            }
        });
        listViewMCQ.invalidate();
    }
}
