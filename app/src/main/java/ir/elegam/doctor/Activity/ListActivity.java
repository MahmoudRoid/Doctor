package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ir.elegam.doctor.Adapter.ServiceAdapter;
import ir.elegam.doctor.AsyncTask.GetData;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class ListActivity extends AppCompatActivity implements IWebservice{

    private RecyclerView mRecyclerView;
    private ServiceAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<MyObject> myObjectArrayList;
    Toolbar toolbar;
    private Typeface San;
    private TextView  txtToolbar;
    private String faction;

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
                    GetData getdata=new GetData(ListActivity.this,ListActivity.this,faction);
                    getdata.execute();
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.service_relative), "اتصال اینترنت خود را چک نمایید", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        init();
    }

    private void define() {
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
    }

    public void getFaction(){
        this.faction=getIntent().getExtras().getString("faction");
        switch (faction){
            case "service":
                txtToolbar.setText("خدمات");
                break;
            case "news":
                txtToolbar.setText("اخبار");
                break;

            case "care":
                txtToolbar.setText("مراقبت ها");
                break;
        }
    }

    public void init(){
        //  check offline database
        ArrayList<MyObject> arrayList=new ArrayList<MyObject>();
//        List<db_BankAsatid> list= Select.from(db_BankAsatid.class).list();
//        if(list.size()>0){
//            // show offline list
//            for(int i=0;i<list.size();i++){
//                BankAsatid cs=new BankAsatid(list.get(i).getName(),list.get(i).getSemat(),list.get(i).getImage_url());
//                arrayList.add(cs);
//            }
//            showList(arrayList);
//        }
//        else {
        // dar gheire in soorat check net va dl

        if(Internet.isNetworkAvailable(ListActivity.this)){
            // call webservice
            GetData getdata=new GetData(ListActivity.this,ListActivity.this,faction);
            getdata.execute();
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.service_relative), "هیچ داده ای جهت نمایش وجود ندارد", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
//        }




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

        snackbar.show();
    }

    public void showList(ArrayList<MyObject> arrayList){
        this.myObjectArrayList=arrayList;

        mRecyclerView = (RecyclerView) findViewById(R.id.service_recycler);
        mLayoutManager = new LinearLayoutManager(ListActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ServiceAdapter(ListActivity.this,myObjectArrayList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ListActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ListActivity.this,DetailListActivity.class);
                switch (faction){
                    case "service":

                        intent.putExtra("faction",faction);
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        startActivity(intent);

                        break;
                    case "news":

                        intent.putExtra("faction",faction);
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        startActivity(intent);

                        break;

                    case "care":

                        intent.putExtra("faction",faction);
                        intent.putExtra("image_url",myObjectArrayList.get(position).getImage_url());
                        intent.putExtra("title",myObjectArrayList.get(position).getTitle());
                        intent.putExtra("content",myObjectArrayList.get(position).getContent());
                        startActivity(intent);

                        break;
                }
            }
        }));
    }


}
