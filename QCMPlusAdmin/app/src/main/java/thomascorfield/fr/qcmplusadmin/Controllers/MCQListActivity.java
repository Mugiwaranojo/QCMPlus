package thomascorfield.fr.qcmplusadmin.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;

import thomascorfield.fr.qcmplusadmin.Controllers.adapter.McqAdapter;
import thomascorfield.fr.qcmplusadmin.R;
import thomascorfield.fr.qcmplusadmin.Model.MCQ;
import thomascorfield.fr.qcmplusadmin.apiService.IAPIServiceResultListener;
import thomascorfield.fr.qcmplusadmin.apiService.MCQServiceManager;

public class MCQListActivity extends Activity implements IAPIServiceResultListener<ArrayList<MCQ>> {

    private ListView listView;
    private Button addMcqBtn;

    private static final int ACTION_MODIFY = 333;
    private static final int ACTION_DELETE = 666;

    private ArrayList<MCQ> mcqs;

    private Intent addMcqPageIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_list);

        this.listView = (ListView) findViewById(R.id.listView);

        this.addMcqPageIntent = new Intent(this, MCQSaveActivity.class);

        this.addMcqBtn = (Button) findViewById(R.id.addMcqBtn);
        this.addMcqBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                addMcqPageIntent.putExtra("MCQ", new MCQ());
                startActivity(addMcqPageIntent);
            }
        });

        View.OnCreateContextMenuListener listener = new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;

                MCQ mcqSelected = mcqs.get(info.position);

                menu.setHeaderTitle(mcqSelected.getName());
                menu.add(Menu.NONE, ACTION_MODIFY, 0, "Modifier");
                menu.add(Menu.NONE, ACTION_DELETE, 1, "Supprimer");
            }
        };

        this.listView.setOnCreateContextMenuListener(listener);

        MCQServiceManager.getInstance(this).setMCQListListener(this);
        MCQServiceManager.getInstance(this).fetchAllMCQ();
    }

    @Override
    public void onApiResultListener(ArrayList<MCQ> obj, ParseException e) {
        mcqs = obj;
        McqAdapter adapter = new McqAdapter(this, mcqs);
        this.listView.setAdapter(adapter);
        listView.invalidate();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        MCQ mcqSelected = mcqs.get(info.position);

        switch (item.getItemId()) {

            case ACTION_MODIFY:

                addMcqPageIntent.putExtra("MCQ", mcqSelected);
                startActivity(addMcqPageIntent);

                break;

            case ACTION_DELETE:

                MCQServiceManager.getInstance(this).setMCQListener(new IAPIServiceResultListener<MCQ>() {
                    @Override
                    public void onApiResultListener(MCQ obj, ParseException e) {
                        MCQServiceManager.getInstance(getApplicationContext()).fetchAllMCQ();
                        Toast.makeText(getApplicationContext(), obj.toString() + " a été supprimé", Toast.LENGTH_LONG).show();
                    }
                });

                MCQServiceManager.getInstance(this).deleteMCQ(mcqSelected);

                break;

            default:
                break;

        }

        return super.onContextItemSelected(item);
    }
}
