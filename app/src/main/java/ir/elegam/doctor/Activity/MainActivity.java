package ir.elegam.doctor.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class MainActivity extends AppCompatActivity {

    View snack_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database db = new database(this);
        db.useable();



    }

    public void onClick(View view){
        switch (view.getId()){

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
                Intent intent_care=new Intent(MainActivity.this,ListActivity.class);
                intent_care.putExtra("faction",Variables.getCare);
                startActivity(intent_care);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_social:
                show_social_dialog();
                break;

            case R.id.btn_soalat_motadavel:
                startActivity(new Intent(MainActivity.this,QuestionActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_tamas_ba_ma:
                startActivity(new Intent(MainActivity.this,TamasBaMaDetailActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            // item haye navigation drawer

            case R.id.relative_login:
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);

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
                    .make(findViewById(R.id.relative), "اپلیکیشن تلگرام یافت نشد", Snackbar.LENGTH_LONG);

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


}
