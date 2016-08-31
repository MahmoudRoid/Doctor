package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orm.query.Condition;
import com.orm.query.Select;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.elegam.doctor.Adapter.ImageDetailAdapter;
import ir.elegam.doctor.AsyncTask.GetImageDetail;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Database.orm.db_ImagesDetailGallery;
import ir.elegam.doctor.Helper.ImagesDetailGallery;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;


public class ImagesDetailActivity extends AppCompatActivity implements IWebservice {

    @BindView(R.id.images_detail_main_image)
    ImageView imagesDetailImage;
    private RecyclerView mRecyclerView;
    private ImageDetailAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ImagesDetailGallery> imagesDetailGalleryArrayList;
    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private View snack_view;

    private String categoryId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_detail);
        ButterKnife.bind(this);

        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_imagedetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("گالری عکس");

        getMyIntent();
        init();
    }

    private void getMyIntent() {
        int category_id = getIntent().getExtras().getInt("cat_id");
        this.categoryId=String.valueOf(category_id);
    }

    public void init() {

        //  check offline database
        ArrayList<ImagesDetailGallery> arrayList = new ArrayList<ImagesDetailGallery>();
        List<db_ImagesDetailGallery> list = Select.from(db_ImagesDetailGallery.class).where(Condition.prop("categoryid").eq(this.categoryId)).list();
        if (list.size() > 0) {
            // show offline list
            for (int i = 0; i < list.size(); i++) {
                ImagesDetailGallery cs = new ImagesDetailGallery(list.get(i).getCategory_id(),list.get(i).getid(), list.get(i).getImage_url());
                arrayList.add(cs);
            }
            Glide.with(this).load(arrayList.get(0).getImage_url())
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imagesDetailImage);
            showList(arrayList);
        } else {
            // dar gheire in soorat check net va dl

            if (Internet.isNetworkAvailable(ImagesDetailActivity.this)) {
                // call webservice
                GetImageDetail getdata = new GetImageDetail(ImagesDetailActivity.this, ImagesDetailActivity.this,this.categoryId);
                getdata.execute();
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.images_detail_relative), "هیچ داده ای جهت نمایش وجود ندارد", Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_refresh, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            startActivity(new Intent(ImagesDetailActivity.this, ImageCategoryActivity.class));
        }
        if (itemId == R.id.action_refresh) {
            if (Internet.isNetworkAvailable(ImagesDetailActivity.this)) {
                // call webservice
                GetImageDetail getdata = new GetImageDetail(ImagesDetailActivity.this, ImagesDetailActivity.this,this.categoryId);
                getdata.execute();
            } else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.images_detail_relative), "اتصال اینترنت خود را چک نمایید", Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ImagesDetailActivity.this, ImageCategoryActivity.class));
        finish();
    }

    @Override
    public void getResult(Object result) throws Exception {
        showList((ArrayList<ImagesDetailGallery>) result);
    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.images_detail_relative), "مشکلی پیش آمده است . مجددا تلاش نمایید", Snackbar.LENGTH_LONG);

        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public void showList(ArrayList<ImagesDetailGallery> arrayList) {
        this.imagesDetailGalleryArrayList = arrayList;
        mRecyclerView = (RecyclerView) findViewById(R.id.images_detail_recycler);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageDetailAdapter(ImagesDetailActivity.this, imagesDetailGalleryArrayList);
        mRecyclerView.setAdapter(mAdapter);

        Glide.with(this).load(arrayList.get(0).getImage_url())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .into(imagesDetailImage);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(ImagesDetailActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Glide.with(ImagesDetailActivity.this).load(imagesDetailGalleryArrayList.get(position).getImage_url())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.ic_launcher).into(imagesDetailImage);

            }
        }));
    }

}