package mydevmind.com.qcmplusstudent.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.model.MCQ;

/**
 * Created by Joan on 29/07/2014.
 */
public class MyMCQAdapter extends BaseAdapter{

    private ArrayList<MCQ> mcqArrayList;
    private Context context;

    public MyMCQAdapter(Context context, ArrayList<MCQ> mcqArrayList){
        this.context= context;
        this.mcqArrayList= mcqArrayList;
    }

    @Override
    public int getCount() {
        return mcqArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mcqArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_mymcq_item, null);

        MCQ mcq= (MCQ) getItem(i);
        TextView fieldMCQName= (TextView) rowView.findViewById(R.id.textViewMyMCQItem);
        fieldMCQName.setText(mcq.getName());
        return rowView;
    }
}
