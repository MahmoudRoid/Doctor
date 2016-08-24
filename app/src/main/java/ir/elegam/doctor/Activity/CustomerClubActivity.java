package ir.elegam.doctor.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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
                intent_care.putExtra("faction", Variables.getCare);
                startActivity(intent_care);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_porsesh_pasokh:
                startActivity(new Intent(CustomerClubActivity.this,MailActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;

            case R.id.btn_chat:
                openTelegramChannel();
                break;

            case R.id.btn_competition:
                startActivity(new Intent(CustomerClubActivity.this,CompetitionActivity.class));
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                break;
        }
    }

    private void openTelegramChannel() {
        final String appName = "org.telegram.messenger";
        final boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);
        if (isAppInstalled) {
            Intent telegram = new Intent(android.content.Intent.ACTION_VIEW);
            telegram.setData(Uri.parse("https://telegram.me/xxx"));
            telegram.setPackage("org.telegram.messenger");
            CustomerClubActivity.this.startActivity(Intent.createChooser(telegram, ""));
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CustomerClubActivity.this,MainActivity.class));
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }
}
