package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.model.MCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MainFragment extends Fragment {

    private LinearLayout selectAllMCQ;
    private ListView listViewMyMCQ;

    private MCQServiceManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, null);

        manager = MCQServiceManager.getInstance(getActivity());

        selectAllMCQ = (LinearLayout) v.findViewById(R.id.LinearLayoutAllMCQ);
        selectAllMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        listViewMyMCQ = (ListView) v.findViewById(R.id.listViewMyMCQ);
        return v;
    }


}
