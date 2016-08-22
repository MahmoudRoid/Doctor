package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.Helper.MyObject;
import ir.elegam.doctor.Interface.IWebservice;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Droid on 8/17/2016.
 */
public class GetData extends AsyncTask<Void,Void,String> {

    public ArrayList<MyObject> myObjectArrayList;
    public Context context;
    private IWebservice delegate = null;
    public String faction;
    SweetAlertDialog pDialog ;
    private database db;
    public String Url;

    public GetData(Context context, IWebservice delegate,String faction) {
        this.context = context;
        this.delegate = delegate;
        this.faction=faction;
        Log.i(Variables.Tag,"faction in Async: "+faction);
        pDialog= new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        db = new database(context);

        getUrl(faction);
    }

    public void getUrl(String faction){

        switch (faction){
            case "getServices":
                this.Url=URLS.GetServices;
                break;
            case "getMagazine":
                this.Url=URLS.GetMagazine;
                break;
            case "getInsurance":
                this.Url=URLS.GetInsurance;
                break;
            case "getCare":
                this.Url=URLS.GetCare;
                break;
            case "getFaq":
                this.Url=URLS.GetFaq;
                break;
        }

    }

    @Override
    protected void onPreExecute() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("در حال دریافت اطلاعات");
        pDialog.setCancelable(false);
        pDialog.show();
    }


    @Override
    protected String doInBackground(Void... params) {

        Response response = null;
        String strResponse = "nothing_got";

        for(int i=0;i<=9;i++){
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("Token", Variables.Token)
                        .build();
                Request request = new Request.Builder()
                        .url(this.Url)
                        .post(body)
                        .build();

                response = client.newCall(request).execute();
                strResponse= response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(response!=null) break;
        }
        return strResponse;
    }

    @Override
    protected void onPostExecute(String result) {
        pDialog.dismiss();

        Log.i(Variables.Tag,"res: "+result);

        if (result.equals("nothing_got")) {
            try {
                delegate.getError("NoData");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!result.startsWith("{")){
            // moshkel dare kollan
            try {
                delegate.getError("problem");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            try {

                JSONObject jsonObject=new JSONObject(result);
                int Type=jsonObject.getInt("Status");
                if(Type==1){
                    myObjectArrayList = new ArrayList<>();
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        String id = jsonObject2.optString("Id");
                        String title = jsonObject2.getString("Title");
                        String content = jsonObject2.getString("Content");
                        String image_url;
                        if(!this.faction.equals("getInsurance")){
                            image_url = jsonObject2.getString("Url");
                            // image_url = URLS.
                        }
                        else image_url ="";

                        MyObject myObject=new MyObject(id,faction,title,content,image_url,"0");
                        myObjectArrayList.add(myObject);

                        db.open();
                        Log.i(Variables.Tag,"id: "+id +" and faction: "+faction);
                        boolean isExist = db.CheckExistanceNews("Faction",faction,"Sid",id);
                        db.close();
                        if(!isExist){
                            db.open();
                            db.Insert(myObject);
                            db.close();

                        }else{
                            Log.i(Variables.Tag,"in update id: "+id);
                            /*db.open();
                            db.Update(myObject);
                            db.close();*/
                        }

                    }

                    if(myObjectArrayList.size()>0){
                        delegate.getResult(myObjectArrayList);
                    }
                    else { delegate.getError("problem");}
                }

                else {
                    // error argarde
                    delegate.getError("problem");
                }

            }
            catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
