package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Adapter.CategoryAdapter;
import ir.elegam.doctor.AsyncTask.Async_Category;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.R;

public class ClinicServicesActivity extends AppCompatActivity implements Async_Category.GetServices {

    private RecyclerView rv;
    private FloatingActionButton fab;
    private CoordinatorLayout coordinatorLayout;
    private List<MyObject> myList= new ArrayList<>();
    private List<String> rvList = new ArrayList<>();
    private Toolbar toolbar;
    private Typeface San;
    private TextView txtToolbar;
    private String faction = Variables.getServices;
    private database db;
    boolean isDetails = false;
    private SweetAlertDialog pDialog;
    private HashMap<String,String> CatList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_services);
        define();
        init();
        // TestInit();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Internet.isNetworkAvailable(getApplicationContext())){
                    AskCategoryServer();
                }
                else {
                    Snackbar_show("اتصال اینترنت خود را چک نمایید");
                }
            }
        });

        rv.addOnItemTouchListener(new RecyclerItemClickListener(ClinicServicesActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(),ClinicServiceDetailActivity.class);
                intent.putExtra("service",rvList.get(position));
                startActivity(intent);
            }
        }));

    }// end onCreate()

    private void TestInit() {
        for (int i=0;i<3;i++){
            rvList.add("new");
        }
        Refresh();
    }

    private void define() {
        db = new database(this);
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_clinic_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.clinic_service_fab);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinate_clinic_service);
        txtToolbar.setTypeface(San);

        rv = (RecyclerView) findViewById(R.id.clinic_service_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(ClinicServicesActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);

        txtToolbar.setText("خدمات کلینیک");

        setSweetDialog();

    }// end define()

    private void setSweetDialog(){
        pDialog= new SweetAlertDialog(ClinicServicesActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("لطفا صبور باشید!");
        pDialog.setCancelable(true);
    }

    public void init(){
        //  check offline database
        ArrayList<MyObject> arrayList=new ArrayList<>();
        db.open();
        int count = db.CountByGroub("Faction",faction,"Category");
        Log.i(Variables.Tag,"count: "+count);
        db.close();

        if(count>0){
            db.open();
            String temp;
            for(int i=0;i<count;i++){
                temp = db.SelectAllByGroup(i,7,"Faction",faction,"Category");
                Log.i(Variables.Tag,"temp: "+temp);
                rvList.add(temp);
            }
            db.close();
            Refresh();
        }else{
            if(Internet.isNetworkAvailable(ClinicServicesActivity.this)){
                // call webservice
                AskCategoryServer();
            }
            else {
                Snackbar_show("هیچ داده ای جهت نمایش وجود ندارد");
            }
        }

    }// end init()

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

    @Override
    public void onStartAskServer() {

    }

    @Override
    public void onServiceNamesGotten(String result) {
        pDialog.dismiss();
        Log.i(Variables.Tag,"res: "+result);

        if (result.equals("nothing_got")) {
            try {
                Snackbar_show("داده ای وجود ندارد!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!result.startsWith("{")){
            // moshkel dare kollan
            try {
                Snackbar_show("مشکل از سرور");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            try {

                JSONObject jsonObject=new JSONObject(result);
                int Type=jsonObject.getInt("Status");
                if(Type==1){
                    if(isDetails){
                        myList.clear();
                        rvList.clear();
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String id = jsonObject2.optString("Id");
                            String title = jsonObject2.getString("Title");
                            String content = jsonObject2.getString("Content");
                            String CategoryId = jsonObject2.getString("CategoryId");
                            if(CategoryId.equals("null")){
                                CategoryId = "عمومی";
                            }

                            String Category = CategoryChecker(CategoryId);
                            Log.i(Variables.Tag,"Category: "+Category);

                            String image_url = jsonObject2.getString("Url");


                            MyObject myObject=new MyObject(id,faction,title,content,image_url,"0",Category,"1");
                            myList.add(myObject);

                            db.open();
                            boolean isExist = db.CheckExistanceNews("Faction",faction,"Sid",id);
                            db.close();

                            if(!isExist){
                                db.open();
                                db.Insert(myObject);
                                db.close();

                            }else{
                                Log.i(Variables.Tag,"in update id: "+id);
                                db.open();
                                db.Update(myObject);
                                db.close();
                            }


                        }// end for

                        // Check if list is empty or not
                        if(myList.size()>0){
                            init();
                        } else{
                            Snackbar_show("داده ای جهت نمایش وجود ندارد.");
                        }
                    }else{
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        CatList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                            String id = jsonObject2.optString("Id");
                            String Category = jsonObject2.getString("Name");

                            CatList.put(id,Category);
                            Log.i(Variables.Tag,"id: "+id+" cat: "+Category);

                        }// end for

                        // Check if list is empty or not
                        if(CatList.isEmpty()){
                            Snackbar_show("هیچ داده ای جهت نمایش وجود ندارد.");
                        }else{
                            pDialog.show();
                            AskAllServer();
                        }
                    }
                }// end if Type==1 (data gotten without problem

                else {
                    Snackbar_show("مشکل در دریافت اطلاعات!");
                }

            }// end try
            catch (JSONException e) {e.printStackTrace(); }
            catch (Exception e) { e.printStackTrace(); }

        }// end parse data

    }

    private String CategoryChecker(String Id) {
        String temp="";
        for (Map.Entry<String,String> entry : CatList.entrySet()) {
            temp = entry.getKey();
            if(temp.equals(Id)) {
                return entry.getValue();
            }
        }
        return "";
    }// end CategoryChecker()

    private void Snackbar_show(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("×", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.show();
        snackbar.setActionTextColor(getResources().getColor(R.color.FABcolor));
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(getResources().getColor(R.color.V_Blue));
        textView.setTypeface(San);
        sbView.setBackgroundColor(ContextCompat.getColor(ClinicServicesActivity.this, R.color.white));
        snackbar.show();
    }// end Snackbar_show()

    private void AskCategoryServer(){
        if(Internet.isNetworkAvailable(ClinicServicesActivity.this)){
            Async_Category async = new Async_Category();
            async.mListener = ClinicServicesActivity.this;
            async.execute(URLS.GetCategoryByType,Variables.Token,3);
        }else{
            Snackbar_show("اتصال اینترنت خود را چک نمایید");
        }
    }// end AskServer()

    private void AskAllServer(){
       /* GetData getdata = new GetData(getApplicationContext(),ClinicServicesActivity.this,faction);
        getdata.execute();*/
        isDetails = true;
        Async_Category async = new Async_Category();
        async.mListener = ClinicServicesActivity.this;
        async.execute(URLS.GetItemsbyType,Variables.Token,3);

    }

}// end class
