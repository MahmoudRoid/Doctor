package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Classes.URLS;
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

    public GetData(Context context, IWebservice delegate,String faction) {
        this.context = context;
        this.delegate = delegate;
        this.faction=faction;
        pDialog= new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
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
                        .add("Faction",this.faction)
                        .build();
                Request request = new Request.Builder()
                        .url(URLS.WEB_SERVICE_URL)
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

        if (result.equals("nothing_got")) {
            try {
                delegate.getError("NoData");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!result.startsWith("[")){
            // moshkel dare kollan
            try {
                delegate.getError("problem");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            // pak kardane database ha baraye rikhtane data e jadid
            // TODO : delete database
//            try {
//                List<db_BankAsatid> list = db_BankAsatid.listAll(db_BankAsatid.class);
//                if(list.size()>0){
//                    db_BankAsatid.deleteAll(db_BankAsatid.class);
//                }
//            }
//            catch (Exception e){e.printStackTrace();}


            try {

                JSONObject jsonObject=new JSONObject(result);
                int Type=jsonObject.getInt("Type");
                if(Type==1){
                    myObjectArrayList = new ArrayList<MyObject>();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                        String id = jsonObject2.optString("Id");
                        String title = jsonObject2.getString("Title");
                        String content = jsonObject2.getString("Content");
                        String image_url = jsonObject2.getString("Photo");

                        MyObject myObject=new MyObject(id,faction,title,content,image_url,"0");
                        myObjectArrayList.add(myObject);

                        // TODO : save to DB
//                        db_BankAsatid db_bankAsatid=new db_BankAsatid(name,semat,image_url);
//                        db_bankAsatid.save();

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
