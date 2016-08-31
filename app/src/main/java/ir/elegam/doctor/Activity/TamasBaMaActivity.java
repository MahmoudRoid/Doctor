package ir.elegam.doctor.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ir.elegam.doctor.R;

public class TamasBaMaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamas_ba_ma);

    }

    public void onitemclicked(View v) {
        Intent intent = new Intent(TamasBaMaActivity.this, TamasBaMaDetailActivity.class);

        switch (v.getId()) {
            case R.id.first:
                intent.putExtra("khademi", "first");
                startActivity(intent);
                break;

            case R.id.second:
                intent.putExtra("khademi", "second");
                startActivity(intent);
                break;

            /*case R.id.third:
                intent.putExtra("khademi", "third");
                startActivity(intent);
                break;

            case R.id.forth:
                intent.putExtra("khademi", "forth");
                startActivity(intent);
                break;*/
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TamasBaMaActivity.this,MainActivity.class));
    }
}
