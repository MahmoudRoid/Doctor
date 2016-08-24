package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Interface.IWebserviceByTag;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Droid on 8/24/2016.
 */
public class PostCompetitionData extends AsyncTask<Void,Void,String> {

    public Context context;
    private IWebserviceByTag delegate = null;
    public String item_id,Tag;
    SweetAlertDialog pDialog ;

    public PostCompetitionData(Context context, IWebserviceByTag delegate,String item_id,String Tag){
        this.context=context;
        this.delegate=delegate;
        this.item_id=item_id;
        this.Tag=Tag;
    }



    @Override
    protected void onPreExecute() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("در حال ارسال اطلاعات");
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
                        .add("Result",this.item_id)
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
            try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getInt("Status")==1){

                String javab = jsonObject.getString("Message");
                    delegate.getResult(javab,this.Tag);
                }
                else {
                    // error
                    delegate.getResult("Error",this.Tag);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
