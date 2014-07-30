package mydevmind.com.qcmplusstudent.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.parse.ParseException;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.apiService.IAPIServiceResultListener;
import mydevmind.com.qcmplusstudent.apiService.MCQServiceManager;
import mydevmind.com.qcmplusstudent.model.MCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MainFragment extends Fragment implements IAPIServiceResultListener<ArrayList<MCQ>>{

    private LinearLayout selectAllMCQ;
    private ListView listViewMyMCQ;
    private MyMCQAdapter adapter;
    private MCQServiceManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, null);

        manager = MCQServiceManager.getInstance(getActivity());
        manager.setListMCQListener(this);
        selectAllMCQ = (LinearLayout) v.findViewById(R.id.LinearLayoutAllMCQ);
        selectAllMCQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        listViewMyMCQ = (ListView) v.findViewById(R.id.listViewMyMCQ);
        manager.fetchCurrentUserMCQ();

        manager.getCurrentUser().getUserMCQs();
        return v;

    }


    @Override
    public void onApiResultListener(ArrayList<MCQ> mcqArrayList, ParseException e) {
        adapter= new MyMCQAdapter(getActivity(), mcqArrayList);
        listViewMyMCQ.setAdapter(adapter);
        listViewMyMCQ.invalidate();
    }
}
