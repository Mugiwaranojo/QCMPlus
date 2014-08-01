package thomascorfield.fr.qcmplusadmin.Controllers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.Question;
import thomascorfield.fr.qcmplusadmin.R;

public class QuestionAdapter extends BaseAdapter {

    private ArrayList<Question> questions;
    private Context context;

    public QuestionAdapter(Context context, ArrayList<Question> questions) {
        this.questions = questions;
        this.context = context;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int i) {
        return questions.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Question questionToDisplay = (Question) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cell = inflater.inflate(R.layout.layout_list_cell, null);

        TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

        titleTextView.setText(questionToDisplay.getStatement());
        descriptionTextView.setText(questionToDisplay.getStatement());

        return cell;
    }
}
