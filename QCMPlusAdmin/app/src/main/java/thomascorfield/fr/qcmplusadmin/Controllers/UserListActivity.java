package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.Model.User;
import thomascorfield.fr.qcmplusadmin.R;

public class UserListActivity extends Activity {

    private ListView listView;
    private Button addUserBtn;

    private static final int ACTION_MODIFY = 333;
    private static final int ACTION_DELETE = 666;

    private ArrayList<User> users;

    private Intent addUserPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        this.users = User.getAllUsers(15);

        this.listView = (ListView) findViewById(R.id.listView);
        UserAdapter adapter = new UserAdapter();
        this.listView.setAdapter(adapter);

        this.addUserPageIntent = new Intent(this, UserSaveActivity.class);

        this.addUserBtn = (Button) findViewById(R.id.addUserBtn);
        this.addUserBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                addUserPageIntent.putExtra("User", new User());
                startActivity(addUserPageIntent);
            }
        });

        View.OnCreateContextMenuListener listener = new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                // Cast necessaire dans le contexte d'un ContextMenuListener
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

                User userSelected = users.get(info.position);

                menu.setHeaderTitle(userSelected.toString());
                menu.add(Menu.NONE, ACTION_MODIFY, 0, "Modifier");
                menu.add(Menu.NONE, ACTION_DELETE, 1, "Supprimer");
            }
        };

        this.listView.setOnCreateContextMenuListener(listener);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        User userSelected = users.get(info.position);

        switch (item.getItemId()) {

            case ACTION_MODIFY:

                addUserPageIntent.putExtra("User", userSelected);
                startActivity(addUserPageIntent);
                //Toast.makeText(this, "Modifier", Toast.LENGTH_LONG).show();
                break;

            case ACTION_DELETE:
                //this.listView.setAdapter(new MusicAdapter(getActivity()));
               //this.listView.invalidateViews();
                Toast.makeText(this, "Supprimer", Toast.LENGTH_LONG).show();
                break;

            default:
                break;

        }

        return super.onContextItemSelected(item);
    }

    private class UserAdapter extends BaseAdapter {

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

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            View cell = inflater.inflate(R.layout.layout_list_cell, null);

            TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
            TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

            titleTextView.setText(userToDisplay.toString());
            descriptionTextView.setText(userToDisplay.getCompany());

            return cell;
        }
    }
}
