package ir.elegam.doctor.Activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.AsyncTask.Async_SendMessage;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.R;

public class MessageActivity extends AppCompatActivity implements Async_SendMessage.SendMessage{

    private Toolbar toolbar;
    private Typeface San;
    private TextView txtToolbar;
    private SweetAlertDialog pDialog;
    private EditText edtNameFamily, edtPhone, edtEmail, edtTitle, edtMessage;
    private TextInputLayout til1,til2, til3, til4, til5;
    private Button btnSend;
    private CoordinatorLayout coordinatorLayout;
    private String namefamily, email, phone1, title, content;
    private String URL= URLS.WEB_SERVICE_URL,TOKEN="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        define();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptMessage();
                hideSoftKeyboard(MessageActivity.this, view);
            }
        });

    }// end onCreate()

    private void define(){
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_message);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout_form);
        til1 = (TextInputLayout) findViewById(R.id.til1_form);
        til2 = (TextInputLayout) findViewById(R.id.til2_form);
        til3 = (TextInputLayout) findViewById(R.id.til3_form);
        til4 = (TextInputLayout) findViewById(R.id.til4_form);
        til5 = (TextInputLayout) findViewById(R.id.til5_form);
        btnSend = (Button) findViewById(R.id.btnConfirm_from);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        edtMessage = (EditText) findViewById(R.id.edtMessage_form);
        edtTitle = (EditText) findViewById(R.id.edtTitle_form);
        edtEmail = (EditText) findViewById(R.id.edtEmail_form);
        edtPhone = (EditText) findViewById(R.id.edtPhone_form);
        edtNameFamily = (EditText) findViewById(R.id.edtName_form);

        txtToolbar.setTypeface(San);
        btnSend.setTypeface(San);
        til1.setTypeface(San);
        til2.setTypeface(San);
        til3.setTypeface(San);
        til4.setTypeface(San);
        til5.setTypeface(San);
        edtMessage.setTypeface(San);
        edtNameFamily.setTypeface(San);
        edtTitle.setTypeface(San);
        edtEmail.setTypeface(San);
        edtPhone.setTypeface(San);

        ArcLoader();

    }// end define()

    private void ArcLoader(){
        pDialog= new SweetAlertDialog(MessageActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("لطفا صبور باشید!");
        pDialog.setCancelable(false);
    }// end ArcLoader()

    private void Snackbar_show(String message){
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("×", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
        snackbar.show();
        snackbar.setActionTextColor(getResources().getColor(R.color.Myred));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(San);
        sbView.setBackgroundColor(ContextCompat.getColor(MessageActivity.this, R.color.FABcolor));
        snackbar.show();
    }// end Snackbar_show()

    public static void hideSoftKeyboard(Activity activity, View view){
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }// end hideSoftKeyboard()

    private boolean isEmailValid(String email){
        return email.contains("@");
    }// end isEmailValid()

    private boolean isInputValid(String data){
        if(data.length() > 5 && data.length() < 50){
            return true;
        }else{
            return false;
        }
    }// end isInputValid()

    private void attemptMessage() {
        edtNameFamily.setError(null);
        edtPhone.setError(null);
        edtEmail.setError(null);
        edtTitle.setError(null);
        edtMessage.setError(null);

        namefamily = edtNameFamily.getText().toString();
        phone1 = edtPhone.getText().toString();
        title = edtTitle.getText().toString();
        content = edtMessage.getText().toString();
        email = edtEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid Name and Family.
        if (TextUtils.isEmpty(namefamily)) {
            edtNameFamily.setError(getString(R.string.error_empty));
            focusView = edtNameFamily;
            cancel = true;
        } else if (!isInputValid(namefamily)) {
            edtNameFamily.setError(getString(R.string.error_length));
            focusView = edtNameFamily;
            cancel = true;
        }

        // Check for a valid mobile phone.
        if (TextUtils.isEmpty(phone1)) {
            edtPhone.setError(getString(R.string.error_empty));
            focusView = edtPhone;
            cancel = true;
        } else if (!isInputValid(phone1)) {
            edtPhone.setError(getString(R.string.error_invalid));
            focusView = edtPhone;
            cancel = true;
        }

        // Check for a valid email.
        if (TextUtils.isEmpty(email)) {

        } else if (!isEmailValid(email)) {
            edtEmail.setError(getString(R.string.error_invalid));
            focusView = edtEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            SendTOServer();

        }
    }// end attemptLogin()

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }// isNetworkAvailable()

    private void SendTOServer(){
        if (isNetworkAvailable()){
            Async_SendMessage asyncSendMessage = new Async_SendMessage();
            asyncSendMessage.mListener = MessageActivity.this;
            asyncSendMessage.execute(URLS.InsertContactUs,Variables.Token,namefamily,email,phone1,title,content);
        }else{
            Snackbar_show(getResources().getString(R.string.error_internet));
        }
    }// end SendTOServer()






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_send:
                attemptMessage();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStratRequest() {
        pDialog.show();
    }

    @Override
    public void onFinishedRequest(String result) {
        pDialog.dismiss();
        if (result.equals("nothing_got")) {
            try {
                Log.i(Variables.Tag,"No data error");
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar_show( "خطا در ارسال اطلاعات");
            }
        }
        else if(result.startsWith("[")){
            // moshkel dare kollan
            try {
                Log.i(Variables.Tag,"extra data error");
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar_show( "خطا در ارسال اطلاعات");
            }
        }
        else {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getInt("Status")==1){
                    // ok
                    Toast.makeText(MessageActivity.this, "پیام شما با موفقیت ثبت شد!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    // error
                    Toast.makeText(MessageActivity.this, "متاسفانه پیام شما ارسال نشد", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}// end class
