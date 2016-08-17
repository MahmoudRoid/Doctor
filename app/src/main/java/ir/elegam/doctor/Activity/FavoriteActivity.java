package ir.elegam.doctor.Activity;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class FavoriteActivity extends AppCompatActivity {

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        define();

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

}// end class
