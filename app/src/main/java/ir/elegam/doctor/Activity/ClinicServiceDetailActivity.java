package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.elegam.doctor.Adapter.CategoryAdapter;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.R;

public class ClinicServiceDetailActivity extends AppCompatActivity {

    private RecyclerView rv;
    private CoordinatorLayout coordinatorLayout;
    private List<MyObject> myList= new ArrayList<>();
    private List<String> rvList = new ArrayList<>();
    private Toolbar toolbar;
    private Typeface San;
    private TextView txtToolbar;
    private String faction = Variables.getServices;
    private database db;
    private String ClinicService="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_service_detail);
        define();
        getWhat();

        rv.addOnItemTouchListener(new RecyclerItemClickListener(ClinicServiceDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ClinicServiceDetailActivity.this,ShowActivity.class);
                intent.putExtra("faction",faction);
                intent.putExtra("sid",myList.get(position).getSid());
                intent.putExtra("image_url",myList.get(position).getImage_url());
                intent.putExtra("title",myList.get(position).getTitle());
                intent.putExtra("content",myList.get(position).getContent());
                intent.putExtra("fav",myList.get(position).getFavorite());
                startActivity(intent);
            }
        }));


    }// end onCreate()

    private void getWhat() {
        ClinicService = getIntent().getStringExtra("service");
        txtToolbar.setText(ClinicService);

        db.open();
        int count = db.CountAllService("Faction",faction,"Category",ClinicService);
        Log.i(Variables.Tag,"count: "+count);
        db.close();

        db.open();
        for(int i=0;i<count;i++){
            myList.add(new MyObject(
                    db.DisplayAll(i,1,"Faction",faction,"Category",ClinicService),
                    faction,
                    db.DisplayAll(i,3,"Faction",faction,"Category",ClinicService),
                    db.DisplayAll(i,4,"Faction",faction,"Category",ClinicService),
                    db.DisplayAll(i,5,"Faction",faction,"Category",ClinicService),
                    db.DisplayAll(i,6,"Faction",faction,"Category",ClinicService),
                    ClinicService,
                    db.DisplayAll(i,8,"Faction",faction,"Category",ClinicService),
                    "0"
            ));
            rvList.add(db.DisplayAll(i,3,"Faction",faction,"Category",ClinicService));
        }
        db.close();
        Refresh();

    }// end getWhat()

    private void define() {
        db = new database(this);
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_clinic_detail_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_clinic_detail_service);
        txtToolbar.setTypeface(San);

        rv = (RecyclerView) findViewById(R.id.clinic_service_detail_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(ClinicServiceDetailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);

    }// end define()

    public void Refresh(){
        Log.i(Variables.Tag,"in refresh");
        rv.setAdapter(new CategoryAdapter(rvList,San,getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}// end class
