package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ir.elegam.doctor.Adapter.ServiceAdapter;
import ir.elegam.doctor.AsyncTask.GetData;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class ListActivity extends AppCompatActivity implements IWebservice{

    private RecyclerView mRecyclerView;
    private ServiceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<MyObject> myObjectArrayList= new ArrayList<>();
    private Toolbar toolbar;
    private Typeface San;
    private TextView  txtToolbar;
    private String faction;
    private database db;
    View snack_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        define();
        getFaction();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.service_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Internet.isNetworkAvailable(ListActivity.this)){
                    // call webservice
                    GetData getdata = new GetData(ListActivity.this,ListActivity.this,faction);
                    getdata.execute();
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.service_relative), "اتصال اینترنت خود را چک نمایید", Snackbar.LENGTH_LONG);
                    snack_view = snackbar.getView();
                    snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                    TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });

        init();
    }

    private void define() {
        db = new database(this);
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);

        mRecyclerView = (RecyclerView) findViewById(R.id.service_recycler);
        mLayoutManager = new LinearLayoutManager(ListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void getFaction(){
        this.faction=getIntent().getExtras().getString("faction");
        Log.i(Variables.Tag,"faction: "+faction);
        switch (faction){
            case "getServices":
                txtToolbar.setText("خدمات");
                break;
            case "getMagazine":
                txtToolbar.setText("مجله");
                break;

            case "getCare":
                txtToolbar.setText("مراقبت ها");
                break;
            case "getInsurance":
                txtToolbar.setText("بیمه");
                break;
        }
    }

    public void init(){

        //  check offline database
        ArrayList<MyObject> arrayList=new ArrayList<>();
        db.open();
        int count = db.CountAll("Faction",faction);
        Log.i(Variables.Tag,"count: "+count);
        db.close();

        if(count>0){
            db.open();
            for(int i=0;i<count;i++){
                MyObject ob = new MyObject(
                        db.DisplayAll(i,1,"Faction",faction),
                        db.DisplayAll(i,2,"Faction",faction),
                        db.DisplayAll(i,3,"Faction",faction),
                        db.DisplayAll(i,4,"Faction",faction),
                        db.DisplayAll(i,5,"Faction",faction),
                        db.DisplayAll(i,6,"Faction",faction),
                        "-",
                        "-"
                );
                Log.i(Variables.Tag,"ob.Title: "+ob.getContent());
                arrayList.add(ob);
            }
            db.close();
            showList(arrayList);
        }else{
            if(Internet.isNetworkAvailable(ListActivity.this)){
                // call webservice
                GetData getdata=new GetData(ListActivity.this,ListActivity.this,faction);
                getdata.execute();
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.service_relative), "هیچ داده ای جهت نمایش وجود ندارد", Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==android.R.id.home){
            startActivity(new Intent(ListActivity.this,MainActivity.class));
        }
        return  true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ListActivity.this,MainActivity.class));
        finish();
    }



    @Override
    public void getResult(Object result) throws Exception {
        showList((ArrayList<MyObject>) result);
    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.service_relative), "مشکلی پیش آمده است . مجددا تلاش نمایید", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showList(ArrayList<MyObject> arrayList){
        this.myObjectArrayList=arrayList;


        mAdapter = new ServiceAdapter(ListActivity.this,myObjectArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ListActivity.this,ShowActivity.class);
                switch (faction){
                    case "getServices":

                        intent.putExtra("faction",faction);
                        intent.putExtra("sid",myObjectArrayList.get(position).getSid());
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        intent.putExtra("fav",myObjectArrayList.get(position).getFavorite());
                        startActivity(intent);

                        break;
                    case "getMagazine":

                        intent.putExtra("faction",faction);
                        intent.putExtra("sid",myObjectArrayList.get(position).getSid());
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        intent.putExtra("fav",myObjectArrayList.get(position).getFavorite());
                        startActivity(intent);

                        break;

                    case "getCare":

                        intent.putExtra("faction",faction);
                        intent.putExtra("sid",myObjectArrayList.get(position).getSid());
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        intent.putExtra("fav",myObjectArrayList.get(position).getFavorite());
                        startActivity(intent);

                        break;

                    case "getInsurance":
                        // mitavanad har applicationi in ghesmta ra dashte bashad ya nadashte bashad
                        intent.putExtra("faction",faction);
                        intent.putExtra("sid",myObjectArrayList.get(position).getSid());
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        intent.putExtra("fav",myObjectArrayList.get(position).getFavorite());
                        startActivity(intent);

                        break;
                }
            }
        }));
    }


    /*@Override
    protected void onResume() {
        super.onResume();
        myObjectArrayList.clear();
        init();
    }*/
}
