package ir.elegam.doctor.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Adapter.ViewPagerAdapter;
import ir.elegam.doctor.AsyncTask.Async_Extra;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Fragment.AboutFragment1;
import ir.elegam.doctor.Fragment.AboutFragment2;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Helper.Object_Extra;
import ir.elegam.doctor.R;

public class AboutUsActivity extends AppCompatActivity implements Async_Extra.GetExtra{
    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SweetAlertDialog pDialog;
    private String URL= URLS.WEB_SERVICE_URL;
    private database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        define();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void define() {
        db = new database(this);
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_aboutus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("معرفی پزشک");
        pDialog= new SweetAlertDialog(AboutUsActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        setSweetDialog();

    }// end define()

    private void setSweetDialog(){
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("لطفا صبور باشید!");
        pDialog.setCancelable(false);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AboutFragment2(), "بیمارستان های مستقر");
        adapter.addFragment(new AboutFragment1(), "سوابق علمی و حرفه ای");
        viewPager.setAdapter(adapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }// isNetworkAvailable()

    private void AskServer(){
        if(isNetworkAvailable()){
            Async_Extra async = new Async_Extra();
            async.mListener = AboutUsActivity.this;
            async.execute(URLS.GetAboutUs,Variables.Token,"4");
        }else{
            Toast.makeText(AboutUsActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }// end AskServer()

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

    @Override
    public void onStartRequest() {
        pDialog.show();
    }

    @Override
    public void onFinishedRequest(String result) {
        pDialog.dismiss();
        Log.i(Variables.Tag,"res: "+result);

        if (result.equals("nothing_got")) {
            Toast.makeText(AboutUsActivity.this, "مشکل از سرور!", Toast.LENGTH_SHORT).show();
        }
        else if(!result.startsWith("{")){
            Toast.makeText(AboutUsActivity.this, "مشکل از سرور!", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                JSONObject jsonObject=new JSONObject(result);
                int Type=jsonObject.getInt("Status");
                if(Type==1){
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        String ID = jsonObject2.optString("Id");
                        String Title = jsonObject2.optString("Title");
                        String Content = jsonObject2.getString("Content");

                        Log.i(Variables.Tag,"Title: "+Title+" * "+"Content: "+Content);

                        Object_Extra ob = new Object_Extra(ID,Title,Content);

                        db.open();
                        boolean isExist = db.CheckExistanceExtra("Sid",ID);
                        db.close();
                        if(!isExist){
                            db.open();
                            db.InsertExtra(ob);
                            db.close();

                        }else{
                            db.open();
                            db.UpdateExtra(ob);
                            db.close();
                        }

                    }
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        setupViewPager(viewPager);

    }

}// end class
