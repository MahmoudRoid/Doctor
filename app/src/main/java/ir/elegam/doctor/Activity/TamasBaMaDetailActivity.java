package ir.elegam.doctor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import ir.elegam.doctor.Classes.CustomTextView;
import ir.elegam.doctor.R;


public class TamasBaMaDetailActivity extends Activity {
    public CustomTextView address,tell,tell2,rooz,saat;
    public LinearLayout tell_relative;
    private String What="";
    private String lat , lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamas_ba_ma_detail);

        init();
        GetWhat();
    }

    private void GetWhat() {
        What = getIntent().getStringExtra("khademi");

        if(What.equals("first")){
            TehranShow();
            lng = "35.797934";
            lat = "51.4129668";
        }else if(What.equals("second")){
            KarajShow();
            lng = "35.7263365";
            lat = "50.9873754";
        }else if(What.equals("third")){
            IstanbolShow();
            lng = "41.0760475";
            lat = "29.0275024";
        }else if(What.equals("forth")){
            BakuShow();
            lng = "40.3654711";
            lat = "49.8684053";
        }
    }// end GetWhat()

    private void init() {
        address = (CustomTextView)findViewById(R.id.address);
        tell = (CustomTextView)findViewById(R.id.phone);
        tell2 = (CustomTextView)findViewById(R.id.phone2);
        rooz = (CustomTextView)findViewById(R.id.rooz);
        saat = (CustomTextView)findViewById(R.id.saat);
        tell_relative=(LinearLayout)findViewById(R.id.phone_relative);
    }


    private void KarajShow() {
        address.setText("فردیس, فلکه دوم, کوچه شهید داستانپور, ساختمان پردیس طبقه سوم");
        tell.setText("02634209010");
        tell2.setText("02634405553");
        rooz.setText("همه روزه");
        saat.setText("9 الی 13 و 16 الی 20");
    }

    private void TehranShow() {
        address.setText("زعفرانیه, خیابان مقدس اردبیلی, روبروی مجتمع تجاری پالادیوم, پ 81 واحد 22");
        tell.setText("02634209010");
        tell2.setText("02634405553");
        rooz.setText("همه روزه");
        saat.setText("9 الی 13 و 16 الی 20");
    }

    private void IstanbolShow() {
        address.setText("استانبول ترکیه");
        tell.setText("");
        tell2.setText("");
        rooz.setText("");
        saat.setText("");
    }

    private void BakuShow() {
        address.setText("آذربایجان باکو");
        tell.setText("");
        tell2.setText("");
        rooz.setText("");
        saat.setText("");
    }

    public void goto_map(View view){
        Intent intent = new Intent(TamasBaMaDetailActivity.this,MapsActivity.class);
        intent.putExtra("lat",lng);
        intent.putExtra("lng",lat);
        startActivity(intent);
    }// end goto_map()


    public void click1(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tell.getText().toString(), null));
        startActivity(intent);
    }

    public void click2(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", tell2.getText().toString(), null));
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
