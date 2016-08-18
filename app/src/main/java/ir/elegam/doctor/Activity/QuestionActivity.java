package ir.elegam.doctor.Activity;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.elegam.doctor.Adapter.ExpandableListAdapter;
import ir.elegam.doctor.AsyncTask.GetData;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class QuestionActivity extends AppCompatActivity implements IWebservice{

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private FloatingActionButton fab;
    private database db;
    private ExpandableListView elv;
    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        define();
        //init();

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        elv.setAdapter(listAdapter);

    }// end onCreate()

    private void define(){
        db = new database(this);

        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_question);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("سوالات متداول");

        fab = (FloatingActionButton) findViewById(R.id.fab_question);
        elv = (ExpandableListView) findViewById(R.id.elv_question);
        registerForContextMenu(elv);
    }// end define()

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_refresh:
                GetData getData = new GetData(QuestionActivity.this,QuestionActivity.this,"common_question");
                getData.execute();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void init(){
        db.open();
        db.Insert(new MyObject("1","question","Salam1","matn1","url","0"));
        db.Insert(new MyObject("2","question","Salam2","matn2","url","0"));
        db.Insert(new MyObject("3","question","Salam3","matn3","url","0"));
        db.Insert(new MyObject("4","question","Salam4","matn4","url","0"));
        db.close();
    }// end init()

    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        db.open();
        int count = db.CountAll("Faction","question");
        for(int i=0;i<count;i++){
            listDataHeader.add(db.DisplayAll(i,3,"Faction","question"));
            List<String> comingSoon = new ArrayList<>();
            comingSoon.add(db.DisplayAll(i,4,"Faction","question"));
            listDataChild.put(listDataHeader.get(0), comingSoon);
        }
        db.close();


    }

    @Override
    public void getResult(Object result) throws Exception {

    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {

    }
}// end class
