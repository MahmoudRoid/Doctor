package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.orm.db_ImagesDetailGallery;
import ir.elegam.doctor.Helper.ImagesDetailGallery;
import ir.elegam.doctor.Interface.IWebservice;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Droid on 8/14/2016.
 */
public class GetImageDetail extends AsyncTask<String,Void,String> {
    public ArrayList<ImagesDetailGallery> imageGalleryArrayList;
    public Context context;
    private IWebservice delegate = null;
    public int category_id;

    SweetAlertDialog pDialog ;

    public GetImageDetail(Context context, IWebservice delegate,int category_id){
        this.context=context;
        this.delegate=delegate;
        this.category_id=category_id;
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }

    @Override
    protected void onPreExecute() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("در حال دریافت اطلاعات");
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {

        Response response = null;
        String strResponse = "nothing_got";

        for(int i=0;i<=9;i++){
            try {

                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody.Builder()
                        .add("Token", Variables.Token)
                        .add("Category_id",String.valueOf(category_id))
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
                delegate.getError("NoData");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {

            // pak kardane database ha baraye rikhtane data e jadid
            try {
                List<db_ImagesDetailGallery> list = Select.from(db_ImagesDetailGallery.class).where(Condition.prop("category_id").eq(this.category_id)).list();

                if(list.size()>0){
                    db_ImagesDetailGallery.deleteAll(db_ImagesDetailGallery.class,"category_id = ?", String.valueOf(this.category_id));
                }
            }
            catch (Exception e){e.printStackTrace();}

            try {
                imageGalleryArrayList = new ArrayList<ImagesDetailGallery>();

                JSONObject jsonObject =new JSONObject(result);
                if(jsonObject.getInt("Type")==1){
                    // save Token into my application class
                    JSONArray jsonArray=jsonObject.getJSONArray("List");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj = jsonArray.getJSONObject(i);

                        int id=obj.getInt("Id");
                        String image_url=obj.getString("Photo");

                        ImagesDetailGallery category=new ImagesDetailGallery(this.category_id,id,image_url);
                        imageGalleryArrayList.add(category);

                        // TODO : add too database
                        db_ImagesDetailGallery db = new db_ImagesDetailGallery(this.category_id,id,image_url);
                        db.save();
                    }

                    if(imageGalleryArrayList.size()>0){
                        delegate.getResult(imageGalleryArrayList);
                    }
                    else { delegate.getError("problem");}
                }
                else {
                    // set error
                    delegate.getError("error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

