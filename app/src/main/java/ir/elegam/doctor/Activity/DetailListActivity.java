package ir.elegam.doctor.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ir.elegam.doctor.R;

public class DetailListActivity extends AppCompatActivity {

    private String title,content,header_image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        // TODO : agar faction=="bime"  ===>  favorite_fab = invisible
        init();

    }

    private void init() {
        this.title=getIntent().getExtras().getString("title");
        this.content=getIntent().getExtras().getString("content");
        this.header_image_url=getIntent().getExtras().getString("image_url");
    }
}
