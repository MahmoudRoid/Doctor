package ir.elegam.doctor.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.AsyncTask.Async_GetVersion;
import ir.elegam.doctor.AsyncTask.Async_Login;
import ir.elegam.doctor.AsyncTask.Async_SendMessage;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.R;

public class MainActivity extends AppCompatActivity implements Async_GetVersion.GetVersion , Async_Login.GetAccess{

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerList;
    private Typeface San;
    private View snack_view;
    private SweetAlertDialog pDialog;
    private String DName, DPhone, DEmail, DNationalCode;
    private boolean isOnReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArcLoader();

        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");

        database db = new database(this);
        db.useable();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RelativeLayout) findViewById(R.id.relativeLayout2);

        checkForUpdate();
        MakeRoots();

    }

    private void MakeRoots(){
        File root = new File(Variables.ROOT);
        File Fimage = new File(Variables.ROOT,"images");
        File Fvideo = new File(Variables.ROOT,"videos");
        File Fpdf = new File(Variables.ROOT,"PDFs");
        File Fvoice = new File(Variables.ROOT,"voice");
        if(!root.exists())
        {
            root.   mkdir();
            Fimage. mkdir();
            Fvideo. mkdir();
            Fpdf.   mkdir();
            Fvoice. mkdir();
        }
    }// end MakeRoots()

    private void checkForUpdate() {
        if(Internet.isNetworkAvailable(MainActivity.this)){
            Async_GetVersion async = new Async_GetVersion();
            async.mListener = MainActivity.this;
            Log.i(Variables.Tag,"ver: "+getVersionCode());
            async.execute(URLS.GetUpdate,Variables.Token,"AppVersion",getVersionCode());
        }
    }// end checkForUpdate()

    private String getVersionCode() {
        int verCode;
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            verCode = pInfo.versionCode;
            return String.valueOf(verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1";
    }

    public void onClick(View view){
        switch (view.getId()){

            case R.id.img_drawer:
//                if (mDrawerLayout.isDrawerOpen(mDrawerList))
//                    mDrawerLayout.closeDrawer(mDrawerList);
//                else
//                    mDrawerLayout.openDrawer(mDrawerList);
                startActivity(new Intent(MainActivity.this,CustomerClubActivity.class));

                break;
            case R.id.btn_aboutdoctor:
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_services:
                Intent intent_service=new Intent(MainActivity.this,ListActivity.class);
                intent_service.putExtra("faction", Variables.getServices);
                startActivity(intent_service);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_magazine:
                Intent intent_magazine=new Intent(MainActivity.this,ListActivity.class);
                intent_magazine.putExtra("faction",Variables.getMagazine);
                startActivity(intent_magazine);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_photo_gallery:
                startActivity(new Intent(MainActivity.this,ImageCategoryActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_care:
                show_care_dialog();
                break;

            case R.id.btn_social:
                show_social_dialog();
                break;

            case R.id.btn_soalat_motadavel:
                startActivity(new Intent(MainActivity.this,QuestionActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_tamas_ba_ma:
                startActivity(new Intent(MainActivity.this,TamasBaMaActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            // item haye navigation drawer

            case R.id.relative_login:
                DialogLogin();
                break;
            case R.id.relative_social:
                show_social_dialog();
                break;
            case R.id.relative_website:
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Variables.getDoctorWebSite));
                startActivity(i);
                break;
            case R.id.relative_enteghad:
                startActivity(new Intent(MainActivity.this,MessageActivity.class));
                break;
            case R.id.relative_introduce:

                try{
                    String shareBody = "  زیبایی را به خودتان هدیه کنید."+"\n "+"http://cafebazaar.ir/app/ir.elegam/?l=fa"+"";
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, ""+ "\n\n");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری"));
                }catch (Exception e) {}

                break;
            case R.id.relative_favorite:
                startActivity(new Intent(MainActivity.this,FavoriteActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.relative_contacts:
                startActivity(new Intent(MainActivity.this,TamasBaMaDetailActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
            case R.id.relative_support:
                startActivity(new Intent(MainActivity.this,SupportActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }

    public  void show_social_dialog(){
        final Dialog d = new Dialog(this);
        d.setCancelable(true);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.linear_choose_dialog_3taee);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final LinearLayout linear_1=(LinearLayout)d.findViewById(R.id.dialog_linear_1);
        final LinearLayout linear_12=(LinearLayout)d.findViewById(R.id.dialog_linear_2);
        final LinearLayout linear_13=(LinearLayout)d.findViewById(R.id.dialog_linear_3);

        final TextView txtOne= (TextView) d.findViewById(R.id.dilog_text_1);
        final TextView txtTWo = (TextView) d.findViewById(R.id.dilog_text_2);
        final TextView txtThree = (TextView) d.findViewById(R.id.dilog_text_3);

        txtOne.setText("کانال تلگرام");
        txtTWo.setText("اینستاگرام");
        txtThree.setText("وب سایت");

        linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                openTelegramChannel();

            }
        });

        linear_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                openInstagramPage();

            }
        });

        linear_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Variables.getDoctorWebSite));
                startActivity(browserIntent);

            }
        });

        d.show();
    }

    public void show_care_dialog(){
        final Dialog d = new Dialog(this);
        d.setCancelable(true);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.linear_choose_dialog_2taee);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final LinearLayout linear_1=(LinearLayout)d.findViewById(R.id.dialog_linear_1);
        final LinearLayout linear_12=(LinearLayout)d.findViewById(R.id.dialog_linear_2);

        final TextView txtOne= (TextView) d.findViewById(R.id.dilog_text_1);
        final TextView txtTWo = (TextView) d.findViewById(R.id.dilog_text_2);

        txtOne.setText("توصیه های قبل از عمل");
        txtTWo.setText("مراقبت های پس از عمل");

        linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ghable darman
                d.dismiss();
                Intent intent_care=new Intent(MainActivity.this,ListActivity.class);
                intent_care.putExtra("faction",Variables.getCare);
                startActivity(intent_care);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        linear_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bad az darman
                d.dismiss();
                Intent intent_care=new Intent(MainActivity.this,ListActivity.class);
                intent_care.putExtra("faction",Variables.getCareAfter);
                startActivity(intent_care);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);

            }
        });

        d.show();
    }

    private void openTelegramChannel() {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);
        if (isAppInstalled) {
            Intent telegram = new Intent(android.content.Intent.ACTION_VIEW);
            telegram.setData(Uri.parse("https://telegram.me/xxx"));
            telegram.setPackage("org.telegram.messenger");
            MainActivity.this.startActivity(Intent.createChooser(telegram, ""));
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.main_relative), "اپلیکیشن تلگرام یافت نشد", Snackbar.LENGTH_LONG);

            snack_view = snackbar.getView();
            snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
            TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.WHITE);
            snackbar.show();
        }
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    private void openInstagramPage() {

        Uri uri = Uri.parse("http://instagram.com/_u/xxx");
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://instagram.com/xxx")));

        }
    }

    @Override
    public void onBackPressed() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("اخطار")
                .setContentText("مایل به خروج از برنامه هستید ؟")
                .setConfirmText("بله")
                .setCancelText("خیر")
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog dd) {
                        dd.dismiss();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        moveTaskToBack(true);
                        finish();
                        System.exit(0);
                    }
                })
                .show();
    }

    @Override
    public void onFinishedRequest(String result) {
        if (result.equals("nothing_got")) {
            try {
                Log.i(Variables.Tag,"No data error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(result.startsWith("[")){
            // moshkel dare kollan
            try {
                Log.i(Variables.Tag,"extra data error");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getInt("Status")==1){
                    // anjam shavad
                    if(jsonObject.getString("Message").startsWith("http")){
                        // show dialog
                        DialogChoose(jsonObject.getString("Message"));
                    }
                    else{
                        // nothing to show

                    }
                }
                else {
                    // error darad
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void DialogChoose(final String Message) {
        final Dialog d = new Dialog(this);
        d.setCancelable(true);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_choose);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final TextView txtHeader = (TextView) d.findViewById(R.id.txtHeader_dialog);
        final TextView txtContext = (TextView) d.findViewById(R.id.txtContext_dialog);
        final TextView txtOne = (TextView) d.findViewById(R.id.txtOne_dialog);
        final TextView txtTwo = (TextView) d.findViewById(R.id.txtTwo_dialog);
        final TextView txtThree = (TextView) d.findViewById(R.id.txtThree_dialog);

        txtHeader.setTypeface(San);
        txtContext.setTypeface(San);
        txtOne.setTypeface(San);
        txtTwo.setTypeface(San);
        txtThree.setTypeface(San);

        txtHeader.setText("آپدیت جدید");
        txtContext.setText("آیا تمایل دارید ورژن جدید برنامه را دانلود کنید؟");
        txtOne.setText("بله");
        txtTwo.setVisibility(View.INVISIBLE);
        txtThree.setText("خیر");

        txtThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        txtOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Message;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        d.show();
    }// end DialogChoose()

    private void ArcLoader(){
        pDialog= new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("لطفا صبور باشید!");
        pDialog.setCancelable(true);
    }// end ArcLoader()

    @Override
    public void onStartLogin() {
        pDialog.show();
    }

    @Override
    public void onFinishedLogin(String result) {
        pDialog.dismiss();
        try {
            if(isOnReg){
                JSONObject jsonObject = new JSONObject(result);
                int Type = jsonObject.getInt("Status");
                if (Type == 1) {
                    // Open Activity Bashgahe moshtarian
                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                }
            }else{
                JSONObject jsonObject = new JSONObject(result);
                int Type = jsonObject.getInt("Status");
                if (Type == 1) {
                    // gotten answer ok

                    String res = jsonObject.optString("res");
                    if(res.equals("ok")){
                        // start activity bashgahe moshtarian
                    }else{
                        DialogRegister();
                    }

                } else {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
                }
            }
        }// end try
        catch(JSONException e){ e.printStackTrace(); }
        catch(Exception e){ e.printStackTrace(); }
    }// end onFinishedLogin()

    private void DialogLogin(){

        final Dialog d = new Dialog(this);
        d.setCancelable(true);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_login);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        final TextView txtTitleLogin = (TextView) d.findViewById(R.id.txtTitle_dialoglogin);
        final TextInputLayout till = (TextInputLayout) d.findViewById(R.id.til1_dialoglogin);
        final EditText edtlogin = (EditText) d.findViewById(R.id.edtLogin_dialoglogin);
        final Button btnLogin = (Button) d.findViewById(R.id.btnLogin_dialoglogin);
        final Button btnShowReg = (Button) d.findViewById(R.id.btnRegShow_dialoglogin);

        till.setTypeface(San);
        txtTitleLogin.setTypeface(San);
        edtlogin.setTypeface(San);
        btnLogin.setTypeface(San);


        btnShowReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                DialogRegister();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DNationalCode = edtlogin.getText().toString();
                if(DNationalCode.length()>11||DNationalCode.length()<9){
                    Toast.makeText(MainActivity.this, "تعداد ارقام کارت غیر مجاز است.", Toast.LENGTH_SHORT).show();

                }else{
                    AskSever(false);
                    d.dismiss();
                }
            }
        });

        d.show();

    }// end DialogLogin()

    public void DialogRegister(){
        final Dialog d = new Dialog(this);
        d.setCancelable(true);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.dialog_register);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = d.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        Button btnCommit,btnCancel;
        TextInputLayout till = (TextInputLayout) d.findViewById(R.id.til1_dialogreg);
        TextInputLayout til2 = (TextInputLayout) d.findViewById(R.id.til2_dialogreg);
        TextInputLayout til3 = (TextInputLayout) d.findViewById(R.id.til3_dialogreg);
        TextInputLayout til4 = (TextInputLayout) d.findViewById(R.id.til4_dialogreg);
        final EditText edtNationalcode = (EditText) d.findViewById(R.id.edtNationaCode_dialogreg);
        final EditText edtName = (EditText) d.findViewById(R.id.edtName_dialogreg);
        final EditText edtPhone = (EditText) d.findViewById(R.id.edtPhone_dialogreg);
        final EditText edtEmail = (EditText) d.findViewById(R.id.edtEmail_dialogreg);
        btnCommit = (Button) d.findViewById(R.id.btnCommit_dialogreg);
        btnCancel = (Button) d.findViewById(R.id.btnCancel_dialogreg);

        edtName.setTypeface(San);
        edtPhone.setTypeface(San);
        edtEmail.setTypeface(San);
        edtNationalcode.setTypeface(San);
        btnCancel.setTypeface(San);
        btnCommit.setTypeface(San);
        til2.setTypeface(San);
        til3.setTypeface(San);
        til4.setTypeface(San);
        till.setTypeface(San);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
            }
        });

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                DNationalCode = edtNationalcode.getText().toString();
                DName = edtName.getText().toString();
                DPhone = edtPhone.getText().toString();
                DEmail = edtEmail.getText().toString();

                if(        DNationalCode.length()>11
                        || DNationalCode.length()<9
                        || DName.equals("")
                        || DPhone.equals("")
                        ){
                    Toast.makeText(MainActivity.this, "لطفا اطلاعات خواسته شده را وارد کنید.", Toast.LENGTH_SHORT).show();

                }else{
                    AskSever(true);
                    d.dismiss();
                }
            }
        });

        d.show();
    }// end DialogRegister()

    private void AskSever(boolean isReg){
        if (Internet.isNetworkAvailable(MainActivity.this)){
            Async_Login async = new Async_Login();
            async.mListener = MainActivity.this;
            if(isReg){
                isOnReg = true;
                async.execute(URLS.REGISTER,Variables.Token,DNationalCode,DName,DPhone,DEmail);
            }else{
                async.execute(URLS.LOGIN,Variables.Token,DNationalCode,"","","");
            }
        }else{
            Toast.makeText(MainActivity.this, getResources().getString(R.string.error_internet), Toast.LENGTH_SHORT).show();
        }
    }

}// end class

