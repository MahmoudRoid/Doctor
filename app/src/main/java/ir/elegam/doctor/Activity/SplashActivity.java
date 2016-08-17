package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.elegam.doctor.R;

public class SplashActivity extends AppCompatActivity {

    public static Runnable runnable = null;
    public Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        };

        handler.postDelayed(runnable, 2000);

    }
}
