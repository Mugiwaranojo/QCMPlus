package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.R;

public class MCQListActivity extends Activity {

    private ListView listView;

    private ArrayList<MCQ> mcqs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);

        this.listView = (ListView) findViewById(R.id.listView);

        this.mcqs = MCQ.getAllMCQ(10);

        McqAdapter adapter = new McqAdapter();

        this.listView.setAdapter(adapter);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_list, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

            MCQ MCQToDisplay = (MCQ) getItem(i);

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

            View cell = inflater.inflate(R.layout.mcq_list_cell, null);

            TextView titleTextView = (TextView) cell.findViewById(R.id.titleTextView);
            TextView descriptionTextView = (TextView) cell.findViewById(R.id.descriptionTextView);

            titleTextView.setText(MCQToDisplay.getName());
            descriptionTextView.setText(MCQToDisplay.getDescription());

            return cell;
        }
    }
}
