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
import thomascorfield.fr.qcmplusadmin.R;

public class MCQListActivity extends Activity {

    private ListView listView;
    private Button addMcqBtn;

    private static final int ACTION_MODIFY = 333;
    private static final int ACTION_DELETE = 666;

    private ArrayList<MCQ> mcqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);

        this.listView = (ListView) findViewById(R.id.listView);

        this.mcqs = MCQ.getAllMCQ(15);

        McqAdapter adapter = new McqAdapter();

        this.listView.setAdapter(adapter);

        this.addMcqBtn = (Button) findViewById(R.id.addMcqBtn);

        final Intent addMcqPageIntent;
        addMcqPageIntent = new Intent(this, MCQSaveActivity.class);

        this.addMcqBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                startActivity(addMcqPageIntent);
            }
        });

        View.OnCreateContextMenuListener listener = new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                // Cast necessaire dans le contexte d'un ContextMenuListener
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

                MCQ mcqSelected = mcqs.get(info.position);

                menu.setHeaderTitle(mcqSelected.getName());
                menu.add(Menu.NONE, ACTION_MODIFY, 0, "Modifier");
                menu.add(Menu.NONE, ACTION_DELETE, 1, "Supprimer");
            }
        };

        this.listView.setOnCreateContextMenuListener(listener);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        MCQ musicSelected = mcqs.get(info.position);

        switch (item.getItemId()) {

            case ACTION_MODIFY:
                //onMusicSelected(musicSelected);
                Toast.makeText(this, "Modifier", Toast.LENGTH_LONG).show();
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

    private class McqAdapter extends BaseAdapter {

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

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            View cell = inflater.inflate(R.layout.mcq_list_cell, null);

            TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
            TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

            titleTextView.setText(mcqToDisplay.getName());
            descriptionTextView.setText(mcqToDisplay.getDescription());

            return cell;
        }
    }
}
