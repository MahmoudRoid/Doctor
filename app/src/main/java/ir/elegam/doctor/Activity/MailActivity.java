package ir.elegam.doctor.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Adapter.MailAdapter;
import ir.elegam.doctor.Adapter.ServiceAdapter;
import ir.elegam.doctor.AsyncTask.Async_Extra;
import ir.elegam.doctor.AsyncTask.Async_GetMail;
import ir.elegam.doctor.AsyncTask.Async_Mail;
import ir.elegam.doctor.AsyncTask.Async_Message;
import ir.elegam.doctor.Classes.ItemClickSupport;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Helper.ObjectMessage;
import ir.elegam.doctor.R;

public class MailActivity extends AppCompatActivity implements
        Async_GetMail.GetMessage,
        Async_Mail.SendMail{

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private RecyclerView rv;
    private ArrayList<ObjectMessage> mylist = new ArrayList<>();
    private MailAdapter mAdapter;
    private SweetAlertDialog pDialog;
    private String URL= URLS.WEB_SERVICE_URL;
    private database db;
    private ImageView ivSend;
    private EditText edtSend;
    private String NationalCode="";
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);
        define();

        AskServer();

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = edtSend.getText().toString();
                if(!input.equals("")){
                    //edtSend.setText("");
                    SendMessage(input);
                }
            }
        });

        //TestInit();

    }// end onCreate()

    private void define(){
        db = new database(this);
        db.useable();
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_mail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("پرسش و پاسخ");

        rv = (RecyclerView) findViewById(R.id.mail_recycler);
        LinearLayoutManager lm = new LinearLayoutManager(MailActivity.this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(lm);

        ivSend = (ImageView) findViewById(R.id.ivSend_mail);
        edtSend = (EditText) findViewById(R.id.edtSend_mail);

        edtSend.setTypeface(San);

        prefs = getApplicationContext().getSharedPreferences("doctor", 0);
        NationalCode = prefs.getString("NC","");

        setSweetDialog();

    }// end define()

    private void setSweetDialog(){
        pDialog= new SweetAlertDialog(MailActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
            Async_GetMail async = new Async_GetMail();
            async.mListener = MailActivity.this;
            async.execute(URLS.GET_MESSAGES,Variables.Token,NationalCode);
        }else{
            Toast.makeText(MailActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }// end AskServer()

    private void SendMessage(String message){
        if(isNetworkAvailable()){
            Async_Mail async = new Async_Mail();
            async.mListener = MailActivity.this;
            async.execute(URLS.SEND_MAIL,Variables.Token,NationalCode,message);
        }else{
            Toast.makeText(MailActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }// end SendMessage()

    @Override
    public void onStartRequest() {
        pDialog.show();
    }

    @Override
    public void onFinishedRequest(String result) {
        pDialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int Type = jsonObject.getInt("Status");
            if (Type == 1) {
                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                mylist.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                    String id = jsonObject2.optString("Id");
                    String Sender = jsonObject2.getString("Sender");
                    String Receiver = jsonObject2.getString("Receiver");
                    String Content = jsonObject2.getString("Content");

                    Log.i(Variables.Tag,"Sender: "+Sender);

                    if(Sender.equals("admin")){
                        Log.i(Variables.Tag,"Admin: "+Sender);
                        mylist.add(new ObjectMessage("دکتر",Content,false));
                    }else{
                        Log.i(Variables.Tag,"Me: "+Sender);
                        mylist.add(new ObjectMessage("من",Content,true));
                    }
                }// end for
                refreshAdapter();
            } else {
                Toast.makeText(MailActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            }
        }// end try
        catch(JSONException e){ e.printStackTrace(); }
        catch(Exception e){ e.printStackTrace(); }
        refreshAdapter();
    }

    @Override
    public void onStartSendingMail() {
        pDialog.show();
    }

    @Override
    public void onMailSend(String result) {
        pDialog.dismiss();
        try {
            JSONObject jsonObject = new JSONObject(result);
            int Type = jsonObject.getInt("Status");
            if (Type == 1) {
                mylist.add(new ObjectMessage("من",edtSend.getText().toString(),true));
                refreshAdapter();
            } else {
                Toast.makeText(MailActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
            }
        }// end try
        catch(JSONException e){ e.printStackTrace(); }
        catch(Exception e){ e.printStackTrace(); }
    }// end onMailSend()

}// end class
