package mydevmind.com.qcmplusstudent.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import mydevmind.com.qcmplusstudent.R;
import mydevmind.com.qcmplusstudent.model.UserMCQ;

/**
 * UserListUserMCQAdapter
 * Adapter pour la ListView des MCQ repondus par l'utilisateur
 */
public class UserListUserMCQAdapter extends BaseAdapter {

    private ArrayList<UserMCQ> userMCQArrayList;
    private Context context;

    public UserListUserMCQAdapter(Context context, ArrayList<UserMCQ> userMCQArrayList) {
        this.userMCQArrayList = userMCQArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userMCQArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return userMCQArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= inflater.inflate(R.layout.layout_list_cell_score, null);

        TextView textTitle= (TextView) v.findViewById(R.id.titleListScoreTextView);
        textTitle.setText(userMCQArrayList.get(i).getMcq().getName());
        TextView textDescription= (TextView) v.findViewById(R.id.descriptionListScoreTextView);
        textDescription.setText(userMCQArrayList.get(i).getMcq().getDescription());
        TextView textScore= (TextView) v.findViewById(R.id.scoreListScoreTextView);
        textScore.setText(userMCQArrayList.get(i).getScore());
        return v;
    }
}
