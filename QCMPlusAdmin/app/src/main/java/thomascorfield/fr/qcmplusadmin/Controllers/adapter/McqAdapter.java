package thomascorfield.fr.qcmplusadmin.Controllers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.R;

public class McqAdapter extends BaseAdapter {

    private ArrayList<MCQ> mcqs;
    private Context context;

    public McqAdapter(Context context, ArrayList<MCQ> mcqs) {
        this.mcqs = mcqs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mcqs.size();
    }

    @Override
    public Object getItem(int i) {
        return mcqs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        MCQ mcqToDisplay = (MCQ) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cell = inflater.inflate(R.layout.layout_list_cell, null);

        TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

        titleTextView.setText(mcqToDisplay.getName());
        descriptionTextView.setText(mcqToDisplay.getDescription());

        return cell;
    }
}
