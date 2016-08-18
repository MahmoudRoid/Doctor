package ir.elegam.doctor.Activity;

import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class ShowActivity extends AppCompatActivity {

    public static int w = 0,h = 0;
    private Toolbar toolbar;
    private Typeface San;
    private ImageView ivHeader, ivFav;
    private TextView txtTitle, txtMatn, txtToolbar;
    private String Faction="",ImageUrl="",Title="",Content="",Fav="",Tag= Variables.Tag;
    private boolean isFav=false;
    private database db;
    private FloatingActionButton fab;
    private LinearLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        define();
        getWhat();

    }// end onCreate()

    private void define(){
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_show);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivHeader = (ImageView) findViewById(R.id.ivHeader_show);
        ivFav = (ImageView) findViewById(R.id.ivFav_show);
        txtTitle = (TextView) findViewById(R.id.txtTitle_show);
        //txtToolbar = (TextView) findViewById(R.id.toolbar_invisible_title);
        fab = (FloatingActionButton) findViewById(R.id.fab_show);
        lay = (LinearLayout) findViewById(R.id.layMatn_show);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        h = displaymetrics.heightPixels;
        w = displaymetrics.widthPixels;

        //txtMatn.setTypeface(San);
        txtTitle.setTypeface(San);
        //txtToolbar.setTypeface(San);

        db = new database(this);



    }// end define()

    private void getWhat(){
        Faction = getIntent().getStringExtra("faction");
        Title = getIntent().getStringExtra("title");
        Content = getIntent().getStringExtra("content");
        ImageUrl = getIntent().getStringExtra("image_url");
        Fav = getIntent().getStringExtra("fav");
        if(Fav.equals("1")){
            isFav = true;
            ivFav.setImageResource(R.drawable.favorite_black);
        }

        Glide.with(this)
                .load(ImageUrl)
                .override(200,200)
                .placeholder(R.drawable.sync_white)
                .into(ivHeader);


        txtTitle.setText(Title);

        if(Faction.equals("service") || Faction.equals("bime")){
            ivFav.setVisibility(View.INVISIBLE);
        }

    }// end getWhat()

}// end class
