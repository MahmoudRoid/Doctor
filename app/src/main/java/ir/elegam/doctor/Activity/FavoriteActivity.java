package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.elegam.doctor.Adapter.ServiceAdapter;
import ir.elegam.doctor.Classes.ItemClickSupport;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.R;

public class FavoriteActivity extends AppCompatActivity {

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private database db;
    private ArrayList<MyObject> mylist = new ArrayList<>();
    private ServiceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        define();


        ItemClickSupport.addTo(rv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                MyObject p = mylist.get(position);
                Intent intent = new Intent(FavoriteActivity.this, ShowActivity.class);
                intent.putExtra("faction",p.getFaction());
                intent.putExtra("sid",p.getSid());
                intent.putExtra("image_url",p.getImage_url());
                intent.putExtra("title",p.getTitle());
                intent.putExtra("content",p.getContent());
                intent.putExtra("fav",p.getFavorite());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

    }// end define()

    private void define(){
        db = new database(this);
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("لیست علاقمندی ها");

        rv = (RecyclerView) findViewById(R.id.service_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(FavoriteActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);

        fab = (FloatingActionButton) findViewById(R.id.service_fab);
        fab.setVisibility(View.INVISIBLE);
    }// end define()

    public void init(){
        mylist.clear();

        db.open();
        int count = db.CountAll("Favorite","1");
        db.close();

        if(count>0){
            for(int i=0;i<count;i++){
                db.open();
                MyObject ob = new MyObject(
                        db.DisplayAll(i,1,"Favorite","1"),
                        db.DisplayAll(i,2,"Favorite","1"),
                        db.DisplayAll(i,3,"Favorite","1"),
                        db.DisplayAll(i,4,"Favorite","1"),
                        db.DisplayAll(i,5,"Favorite","1"),
                        "1"
                );
                db.close();
                mylist.add(ob);
            }
            mAdapter = new ServiceAdapter(FavoriteActivity.this,mylist);
            rv.setAdapter(mAdapter);
        }
        else {
            finish();
            Toast.makeText(FavoriteActivity.this, getResources().getString(R.string.error_empty_list), Toast.LENGTH_SHORT).show();
        }
    }// end init()

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

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
}// end class
