package ir.elegam.doctor.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import ir.elegam.doctor.AsyncTask.GetData;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Interface.IWebservice;
import ir.elegam.doctor.R;

public class ServicesActivity extends AppCompatActivity implements IWebservice{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.service_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Internet.isNetworkAvailable(ServicesActivity.this)){
                    // call webservice
                    GetData getdata=new GetData(ServicesActivity.this,ServicesActivity.this,"service");
                    getdata.execute();
                }
                else {
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.service_relative), "اتصال اینترنت خود را چک نمایید", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
            init();
    }

    public void init(){
        //  check offline database
        ArrayList<MyObject> arrayList=new ArrayList<MyObject>();
//        List<db_BankAsatid> list= Select.from(db_BankAsatid.class).list();
//        if(list.size()>0){
//            // show offline list
//            for(int i=0;i<list.size();i++){
//                BankAsatid cs=new BankAsatid(list.get(i).getName(),list.get(i).getSemat(),list.get(i).getImage_url());
//                arrayList.add(cs);
//            }
//            showList(arrayList);
//        }
//        else {
            // dar gheire in soorat check net va dl

            if(Internet.isNetworkAvailable(ServicesActivity.this)){
                // call webservice
                GetData getdata=new GetData(ServicesActivity.this,ServicesActivity.this,"service");
                getdata.execute();
            }
            else {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.service_relative), "هیچ داده ای جهت نمایش وجود ندارد", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
//        }




    }



    @Override
    public void getResult(Object result) throws Exception {

    }

    @Override
    public void getError(String ErrorCodeTitle) throws Exception {

    }
}
