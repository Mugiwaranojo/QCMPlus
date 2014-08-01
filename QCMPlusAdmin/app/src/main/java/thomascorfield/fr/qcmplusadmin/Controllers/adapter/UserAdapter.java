package thomascorfield.fr.qcmplusadmin.Controllers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.R;

public class UserAdapter extends BaseAdapter {

    private ArrayList<User> users;
    private Context context;

    public UserAdapter(Context context, ArrayList<User> users) {
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        User userToDisplay = (User) getItem(i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cell = inflater.inflate(R.layout.layout_list_cell, null);

        TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

        titleTextView.setText(userToDisplay.toString());
        descriptionTextView.setText(userToDisplay.getCompany());

        return cell;
    }
}
