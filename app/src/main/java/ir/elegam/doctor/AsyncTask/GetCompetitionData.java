package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Interface.IWebserviceByTag;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GetCompetitionData extends AsyncTask<Void,Void,String> {

    List<String> myStringList;
    public Context context;
    private IWebserviceByTag delegate = null;
    public String Tag;
    SweetAlertDialog pDialog ;


    public GetCompetitionData(Context context, IWebserviceByTag delegate,String Tag){
        this.context=context;
        this.delegate=delegate;
        this.Tag=Tag;
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
                        .url(URLS.GetImagesById)
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
                delegate.getError("NoData",this.Tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(!result.startsWith("{")){
            // moshkel dare kollan
            try {
                delegate.getError("problem",this.Tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
                myStringList = new ArrayList<String>();
            try {

                JSONObject jsonObject =new JSONObject(result);
                if(jsonObject.getInt("Status")==1){
                    String question=jsonObject.getString("Question");
                    String result1=jsonObject.getString("Result1");
                    String result2=jsonObject.getString("Result2");
                    String result3=jsonObject.getString("Result3");
                    String result4=jsonObject.getString("Result4");

                    myStringList.add(question);
                    myStringList.add(result1);
                    myStringList.add(result2);
                    myStringList.add(result3);
                    myStringList.add(result4);

                    if(myStringList.size()>0){
                        delegate.getResult(myStringList,this.Tag);
                    }
                    else {
                        delegate.getError("Error",this.Tag);
                    }
                }
                else {
                    // error
                    delegate.getError("Error",this.Tag);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
