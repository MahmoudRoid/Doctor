package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.regex.Pattern;

import ir.elegam.doctor.Classes.TextViewEx;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class ShowActivity extends AppCompatActivity {

    public static int w = 0,h = 0;
    private Toolbar toolbar;
    private Typeface San;
    private ImageView ivHeader;
    private TextView txtToolbar;
    private String Faction="",ImageUrl="",Title="",Content="",Fav="",Sid="";
    private boolean isFav=false;
    private database db;
    private FloatingActionButton fab,fabShare;
    private LinearLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        define();
        getWhat();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFav){
                    db.open();
                    db.update("Favorite","0","Faction",Faction,"Sid",Sid);
                    db.close();
                    fab.setImageResource(R.drawable.favorite_outline_black);
                    Fav = "0";
                    isFav = false;
                }else{
                    db.open();
                    db.update("Favorite","1","Faction",Faction,"Sid",Sid);
                    db.close();
                    fab.setImageResource(R.drawable.favorite_black);
                    Fav = "1";
                    isFav = true;
                }
            }
        });

        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"دکتر");
                    intent.putExtra(Intent.EXTRA_TEXT, Content);
                    startActivity(Intent.createChooser(intent,"اشتراک گزاری از طریق:"));
                }catch (Exception e){ e.printStackTrace(); }
            }
        });


    }// end onCreate()

    private void define(){
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_show);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ivHeader = (ImageView) findViewById(R.id.ivHeader_show);
        txtToolbar = (TextView) findViewById(R.id.toolbar_invisible_title);
        fabShare = (FloatingActionButton) findViewById(R.id.fabShare_show);
        fab = (FloatingActionButton) findViewById(R.id.fab_show);
        lay = (LinearLayout) findViewById(R.id.layMatn_show);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        h = displaymetrics.heightPixels;
        w = displaymetrics.widthPixels;

        txtToolbar.setTypeface(San);
        txtToolbar.setText("توضیحات");
        db = new database(this);

    }// end define()

    private void getWhat(){
        Faction = getIntent().getStringExtra("faction");
        Title = getIntent().getStringExtra("title");
        Sid = getIntent().getStringExtra("sid");
        Content = getIntent().getStringExtra("content");

        ImageUrl = getIntent().getStringExtra("image_url");
        Fav = getIntent().getStringExtra("fav");

        db.open();
        Fav = db.DisplayOne(6,"Sid",Sid,"Faction",Faction);
        db.close();

        if(Fav.equals("1")){
            isFav = true;
            fab.setImageResource(R.drawable.favorite_black);
        }else{
            fab.setImageResource(R.drawable.favorite_outline_black);
        }

        Glide.with(this)
                .load(ImageUrl)
                .override(200,200)
                .placeholder(R.drawable.icon_launcher)
                .into(ivHeader);


        txtToolbar.setText(Title);

        if(Faction.equals("service") || Faction.equals("bime")){
            fab.setVisibility(View.INVISIBLE);
        }

        setContent(Content+"<\"\">");

    }// end getWhat()

    private void setContent(String text){
        int c1=0,c2=0,c3=0;

        for(int i=0;i<text.length();i++){

            if(text.charAt(i)=='<'){
                c2=i;
                if(!text.substring(c1,c2).equals("")){
                    ctext(text.substring(c1,c2));
                }

            }
            if(text.charAt(i)=='>'){
                c3=i;
                cimg(text.substring(c2+2,c3-1));
                c1=i+1;
            }
        }
    }// end setContent()

    private void ctext(String text){
        text = Html.fromHtml(text).toString();
        Log.i(Variables.Tag,"text: "+text);
//        TextView tv=new TextView(ShowActivity.this);
        TextViewEx tv=new TextViewEx(ShowActivity.this);
        tv.setTypeface(San);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 10);
//        tv.setPadding(10, 10, 10, 10);
        tv.setLineSpacing(20,1);

        lp.gravity= Gravity.TOP;

        tv.setText(text,true);
        lay.addView(tv,lp);

    }// end ctext()

    private void cimg(String image_url){
        Log.i(Variables.Tag,"imageurl: "+image_url);

        if(!image_url.equals("")){
            ImageView img=new ImageView(ShowActivity.this);
            double ch=w/2;
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(w,(int)ch);
            lp.gravity=Gravity.CENTER;

            Glide.with(this)
                    .load(image_url)
                    .override(200,200)
                    .placeholder(R.drawable.favorite_black)
                    .into(img);

            lay.addView(img, lp);
        }
    }// end cimg()

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
        return false;
    }

}// end class
