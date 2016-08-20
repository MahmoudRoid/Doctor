package ir.elegam.doctor.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Adapter.MailAdapter;
import ir.elegam.doctor.Adapter.ServiceAdapter;
import ir.elegam.doctor.AsyncTask.Async_Extra;
import ir.elegam.doctor.AsyncTask.Async_Message;
import ir.elegam.doctor.Classes.ItemClickSupport;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Helper.ObjectMessage;
import ir.elegam.doctor.R;

public class MailActivity extends AppCompatActivity implements Async_Message.GetMessage{

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private RecyclerView rv;
    private FloatingActionButton fab;
    private ArrayList<ObjectMessage> mylist = new ArrayList<>();
    private MailAdapter mAdapter;
    private SweetAlertDialog pDialog;
    private String URL= URLS.WEB_SERVICE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        define();

        //AskServer();

        TestInit();

    }// end onCreate()

    private void define(){
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_service);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("لیست علاقمندی ها");

        rv = (RecyclerView) findViewById(R.id.service_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(MailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);

        fab = (FloatingActionButton) findViewById(R.id.service_fab);
        fab.setVisibility(View.INVISIBLE);

        pDialog= new SweetAlertDialog(MailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        setSweetDialog();

    }// end define()

    private void setSweetDialog(){
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("لطفا صبور باشید!");
        pDialog.setCancelable(false);
    }// end setSweetDialog()

    private void refreshAdapter(){
        mAdapter = new MailAdapter(mylist,San,MailActivity.this);
        rv.setAdapter(mAdapter);
    }// end refreshAdapter()

    private void TestInit(){
        mylist.add(new ObjectMessage("من","سلام دکتر حالتون خوبه؟",true));
        mylist.add(new ObjectMessage("دکتر","درود, ممنون مشکل شما رفع شد؟",false));
        mylist.add(new ObjectMessage("من","بله دکتر حالم خیلی بهتره ممنون که انقدر خوبید",true));
        mylist.add(new ObjectMessage("دکتر","خواهش میکنم وقت کردی حتما آزمایش هات رو انجام بده و قرص هاتو بخور",false));
        mylist.add(new ObjectMessage("من","چشم دکتر شما هم مراقب خودت باش",true));
        mylist.add(new ObjectMessage("من","سلام دکتر حالتون خوبه؟",true));
        mylist.add(new ObjectMessage("دکتر","درود, ممنون مشکل شما رفع شد؟",false));
        mylist.add(new ObjectMessage("من","بله دکتر حالم خیلی بهتره ممنون که انقدر خوبید",true));
        mylist.add(new ObjectMessage("دکتر","خواهش میکنم وقت کردی حتما آزمایش هات رو انجام بده و قرص هاتو بخور",false));
        mylist.add(new ObjectMessage("من","چشم دکتر شما هببله خیلی هم خوبم  دکمتر کی میای بریم شمال کنار ویلا تو شمال بام خموش بگذرونیم و بگیم و بخندیم و باهم بازی کنیم و از دوتستانمون یاد کنیم و از این حرفات که این متن به اندازه کافی طولانی شود و ما بتوانیم تست بگیریمم مراقب خودت باش",true));
        refreshAdapter();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }// isNetworkAvailable()

    private void AskServer(){
        if(isNetworkAvailable()){
            Async_Message async = new Async_Message();
            async.mListener = MailActivity.this;
            async.execute(URL,"TOKEN","CODE","CLASS_ID");
        }else{
            Toast.makeText(MailActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }// end AskServer()

    @Override
    public void onStartRequest() {
        pDialog.show();
    }

    @Override
    public void onFinishedRequest(String res) {
        pDialog.dismiss();
        // get and parse data from server
        refreshAdapter();
    }
}// end class
