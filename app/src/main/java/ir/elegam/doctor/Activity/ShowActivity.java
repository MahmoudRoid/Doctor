package ir.elegam.doctor.Activity;

import android.graphics.Typeface;
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

    public static int W=0,H=0;
    private Toolbar toolbar;
    private Typeface San;
    private ImageView ivHeader, ivFav;
    private TextView txtTitle, txtMatn, txtToolbar;
    private String Faction="",ImageUrl="",Title="",Content="",Fav="",Tag= Variables.Tag;
    private boolean isFav=false;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        define();
        getWhat();
        MakeContext();

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
        txtToolbar = (TextView) findViewById(R.id.toolbar_invisible_title);

        txtMatn.setTypeface(San);
        txtTitle.setTypeface(San);
        txtToolbar.setTypeface(San);

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
                .into(ivHeader);

        txtTitle.setText(Title);

        if(Faction.equals("service") || Faction.equals("bime")){
            ivFav.setVisibility(View.INVISIBLE);
        }

    }// end getWhat()

    private void MakeContext(){
        //LinearLayOut Setup
        LinearLayout linearLayout= new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT
                )
        );

        //ImageView Setup
        ImageView imageView = new ImageView(this);

        //setting image resource
        imageView.setImageResource(R.drawable.favblack);

        //setting image position
        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                )
        );

        //adding view to layout
        linearLayout.addView(imageView);
        //make visible to program
        setContentView(linearLayout);


    }// end MakeContext()

}// end class
