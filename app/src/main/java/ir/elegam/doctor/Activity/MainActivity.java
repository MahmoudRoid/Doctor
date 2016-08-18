package ir.elegam.doctor.Activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database db = new database(this);
        db.useable();

        TextView txt = (TextView) findViewById(R.id.txtMain);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                intent.putExtra("faction","services");
                intent.putExtra("image_url","");
                intent.putExtra("title","onvan");
                intent.putExtra("content","matn");
                intent.putExtra("fav","0");
                startActivity(intent);
            }
        });

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

        final LinearLayout linear_1=(LinearLayout)findViewById(R.id.dialog_linear_1);
        final LinearLayout linear_12=(LinearLayout)findViewById(R.id.dialog_linear_2);
        final LinearLayout linear_13=(LinearLayout)findViewById(R.id.dialog_linear_3);

        final TextView txtOne= (TextView) d.findViewById(R.id.dilog_text_1);
        final TextView txtTWo = (TextView) d.findViewById(R.id.dilog_text_2);
        final TextView txtThree = (TextView) d.findViewById(R.id.dilog_text_3);

        txtOne.setText("کانال تلگرام");
        txtTWo.setText("اینستاگرام");
        txtThree.setText("وب سایت");

        final Intent callIntent = new Intent(Intent.ACTION_DIAL);
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
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.ir"));
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
