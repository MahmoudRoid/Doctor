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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamas_ba_ma_detail);

        init();
        KarajShow();
    }

    private void init() {
        address = (CustomTextView)findViewById(R.id.address);
        tell = (CustomTextView)findViewById(R.id.phone);
        tell2 = (CustomTextView)findViewById(R.id.phone);
        rooz = (CustomTextView)findViewById(R.id.rooz);
        saat = (CustomTextView)findViewById(R.id.saat);
        tell_relative=(LinearLayout)findViewById(R.id.phone_relative);
    }


    private void KarajShow() {
        address.setText("کرج، سه راه گوهردشت، نبش پل، برج ققنوس، طبقه 5، واحد 19");
        tell.setText("02634209010");
        tell2.setText("02634405553");
        rooz.setText("همه روزه");
        saat.setText("");
    }

    private void TehranShow() {
        address.setText("تهران-خيابان ستارخان-جنب پمپ بنزين بهبودي-پ176-طبقه اول-واحد2");
        tell.setText("021-66554256");
        rooz.setText("شنبه,دوشنبه");
        saat.setText("19:00الي22:00");
    }

    public void goto_map(View view){

        Intent intent = new Intent(TamasBaMaDetailActivity.this,MapsActivity.class);
        startActivity(intent);


    }


    public void click1(View view){
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:" + tell.getText()));
        startActivity(call);
    }

    public void click2(View view){
        Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:" + tell2.getText()));
        startActivity(call);
    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(TamasBaMaDetailActivity.this,MainActivity.class));
        finish();
    }
}
