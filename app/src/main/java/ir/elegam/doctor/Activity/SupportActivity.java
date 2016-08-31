package ir.elegam.doctor.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.elegam.doctor.R;

public class SupportActivity extends AppCompatActivity {

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_imagedetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("ارتباط با پشتیبانی");


        View.OnClickListener cj = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_call) {
                    show_tell_dialog();
                }
                else if (v.getId() == R.id.btn_web) {
                    try {
                        Intent browserIntent;
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.egam.ir"));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }
                }
                else if (v.getId() == R.id.btn_network) {
                    show_social_dialog();

                }
                else
                    try {
                        Intent browserIntent;
                        browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.egam.ir"));
                        startActivity(browserIntent);
                    } catch (Exception e) {
                    }

            }
        };

        Button btn_com = (Button) findViewById(R.id.btn_com);
        Button btn_soft = (Button) findViewById(R.id.btn_soft);
        Button btn_web2 = (Button) findViewById(R.id.btn_web2);

        Button btn_call = (Button) findViewById(R.id.btn_call);
        Button btn_network = (Button) findViewById(R.id.btn_network);
        Button btn_web = (Button) findViewById(R.id.btn_web);

        btn_com.setOnClickListener(cj);
        btn_soft.setOnClickListener(cj);
        btn_web2.setOnClickListener(cj);

        btn_call.setOnClickListener(cj);
        btn_network.setOnClickListener(cj);
        btn_web.setOnClickListener(cj);

    }

    private void show_social_dialog() {
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

        txtOne.setText("تلگرام");
        txtTWo.setText("اینستاگرام");

        final Intent callIntent = new Intent(Intent.ACTION_DIAL);
        linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent browserIntent;
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.telegram.me/elegam"));
                startActivity(browserIntent);

            }
        });

        linear_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent browserIntent;
                browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/elegaaam/"));
                startActivity(browserIntent);

            }
        });
        d.show();
    }

    private void show_tell_dialog() {
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

        txtOne.setText("02632775620");
        txtTWo.setText("02632775612");

        final Intent callIntent = new Intent(Intent.ACTION_DIAL);
        linear_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "02632775620", null));
                startActivity(intent);

            }
        });

        linear_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "02632775612", null));
                startActivity(intent);

            }
        });
        d.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
           finish();
        }
        return true;
    }
}