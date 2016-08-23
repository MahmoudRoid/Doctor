package ir.elegam.doctor.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.elegam.doctor.Adapter.ImageCategoryAdapter;
import ir.elegam.doctor.AsyncTask.GetImageVideoCategory;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Classes.RecyclerItemClickListener;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.orm.db_VideoCategoryGallery;
import ir.elegam.doctor.Helper.ImageCategoryGallery;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class VideoCategoryActivity extends AppCompatActivity implements IWebservice {

    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private RecyclerView mRecyclerView;
    private ImageCategoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<ImageCategoryGallery> videoCategoryGalleries;
    View snack_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_category);

        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_imagecategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("گالری ویدیو");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.images_category_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Internet.isNetworkAvailable(VideoCategoryActivity.this)){
                    // call webservice
                    GetImageVideoCategory getdata=new GetImageVideoCategory(VideoCategoryActivity.this,VideoCategoryActivity.this, Variables.getVideosCategory);
                    getdata.execute();
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.images_category_relative), "اتصال اینترنت خود را چک نمایید", Snackbar.LENGTH_LONG);
                    snack_view = snackbar.getView();
                    snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                    TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });

        init();

    }

    public void init(){
        //  check offline database
        ArrayList<ImageCategoryGallery> arrayList=new ArrayList<ImageCategoryGallery>();
        List<db_VideoCategoryGallery> list= Select.from(db_VideoCategoryGallery.class).list();
        if(list.size()>0){
            // show offline list
            for(int i=0;i<list.size();i++){
                ImageCategoryGallery cs=new ImageCategoryGallery(list.get(i).getCategory_neame());
                arrayList.add(cs);
            }
            showList(arrayList);
        }
        else {
            // dar gheire in soorat check net va dl

            if(Internet.isNetworkAvailable(VideoCategoryActivity.this)){
                // call webservice
                GetImageVideoCategory getdata=new GetImageVideoCategory(VideoCategoryActivity.this,VideoCategoryActivity.this, Variables.getVideosCategory);
                getdata.execute();
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.images_category_relative), "هیچ داده ای جهت نمایش وجود ندارد", Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId==android.R.id.home){
            startActivity(new Intent(VideoCategoryActivity.this,MainActivity.class));
        }
        return  true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(VideoCategoryActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public void getResult(Object result) throws Exception {
        showList((ArrayList<ImageCategoryGallery>) result);
    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.images_category_relative), "مشکلی پیش آمده است . مجددا تلاش نمایید", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public void showList(ArrayList<ImageCategoryGallery> arrayList){
        this.videoCategoryGalleries=arrayList;

        mRecyclerView = (RecyclerView) findViewById(R.id.images_category_recycler);
        mLayoutManager = new GridLayoutManager(VideoCategoryActivity.this,2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ImageCategoryAdapter(videoCategoryGalleries);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(VideoCategoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // TODO : check kardane id ( k doros bashe )
                Intent intent = new Intent(VideoCategoryActivity.this,VideoGallaryActivity.class);
                intent.putExtra("id",videoCategoryGalleries.get(position).getCategory_name());
                startActivity(intent);
            }
        }));
    }

}
