package ir.elegam.doctor.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.elegam.doctor.Adapter.ExpandableListAdapter;
import ir.elegam.doctor.AsyncTask.GetData;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class QuestionActivity extends AppCompatActivity implements IWebservice {

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private FloatingActionButton fab;
    private database db;
    private ExpandableListView elv;
    private List<String> listDataHeader = new ArrayList<>();
    private HashMap<String, List<String>> listDataChild;
    private ExpandableListAdapter listAdapter;
    private View snack_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        define();
        init();

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
                AskServer();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void init(){

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        db.open();
        int count = db.CountAll("Faction","getFaq");
        db.close();

        Log.i(Variables.Tag,"count: "+count);
        db.close();

        if(count>0){
            db.open();
            for(int i=0;i<count;i++){
                listDataHeader.add(db.DisplayAll(i,3,"Faction","getFaq"));
                List<String> comingSoon = new ArrayList<>();
                comingSoon.add(db.DisplayAll(i,4,"Faction","getFaq"));
                listDataChild.put(listDataHeader.get(i), comingSoon);
            }
            db.close();
            Refresh();
        }else{
            if(Internet.isNetworkAvailable(QuestionActivity.this)){
                // call webservice
                AskServer();
            }
            else {
                Toast.makeText(QuestionActivity.this, "هیچ داده ای جهت نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
            }
        }

    }// end init()

    private void AskServer() {
        GetData getData = new GetData(QuestionActivity.this,QuestionActivity.this, Variables.getFaq);
        getData.execute();
    }

    private void Refresh() {
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        elv.setAdapter(listAdapter);
    }


    @Override
    public void getResult(Object result) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.question_coordinator), "با موفقیت آپدیت شد", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
        init();
    }// end getResult()

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.question_coordinator), "مشکلی پیش آمده است . مجددا تلاش نمایید", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }// end getError()

}// end class
