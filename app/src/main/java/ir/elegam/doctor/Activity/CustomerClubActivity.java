package ir.elegam.doctor.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.R;

public class CustomerClubActivity extends AppCompatActivity {
    View snack_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_club);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_care, R.id.btn_porsesh_pasokh, R.id.btn_chat, R.id.btn_competition})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_care:
                Intent intent_care=new Intent(CustomerClubActivity.this,ListActivity.class);
                intent_care.putExtra("faction",Variables.getCareAfter);
                startActivity(intent_care);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                //show_care_dialog();
                break;

            case R.id.btn_porsesh_pasokh:
                startActivity(new Intent(CustomerClubActivity.this,MailActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_chat:
                openTelegramChannel();
                break;

            case R.id.btn_competition:
                Toast.makeText(CustomerClubActivity.this, "این امکان در ورژن بعدی موجود است.", Toast.LENGTH_SHORT).show();
                /*startActivity(new Intent(CustomerClubActivity.this,CompetitionActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);*/
                break;
        }
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
                Intent intent_care=new Intent(CustomerClubActivity.this,ListActivity.class);
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
                Intent intent_care=new Intent(CustomerClubActivity.this,ListActivity.class);
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
            telegram.setData(Uri.parse("https://telegram.me/Drmazloomi"));
            telegram.setPackage("org.telegram.messenger");
            CustomerClubActivity.this.startActivity(Intent.createChooser(telegram, ""));
        }
        else {
            Snackbar snackbar = Snackbar
                    .make(findViewById(R.id.club_relative), "اپلیکیشن تلگرام یافت نشد", Snackbar.LENGTH_LONG);

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CustomerClubActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
