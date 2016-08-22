package ir.elegam.doctor.AsyncTask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import ir.elegam.doctor.Classes.URLS;
import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.orm.db_ImageCategoryGallery;
import ir.elegam.doctor.Database.orm.db_VideoCategoryGallery;
import ir.elegam.doctor.Helper.ImageCategoryGallery;
import ir.elegam.doctor.Interface.IWebservice;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Droid on 8/14/2016.
 */
public class GetImageVideoCategory extends AsyncTask<String,Void,String> {
    public ArrayList<ImageCategoryGallery> imageGalleryArrayList;
    public Context context;
    private IWebservice delegate = null;
    private String category;
    SweetAlertDialog pDialog ;
    public String url;

    public GetImageVideoCategory(Context context, IWebservice delegate,String category){
        this.context=context;
        this.delegate=delegate;
        this.category=category;
        getUrl(category);
        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
    }

    public void getUrl(String category){
        switch (category){
            case "getVideosCategory":
                this.url=URLS.GetVideos;
                break;
            case "getImagesCategory":
                this.url=URLS.GetImages;
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("در حال دریافت اطلاعات");
        pDialog.setCancelable(true);
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
                        .build();
                Request request = new Request.Builder()
                        .url(this.url)
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
            if(this.category.equals("getImagesCategory")){
                try {
                    List<db_ImageCategoryGallery> list = db_ImageCategoryGallery.listAll(db_ImageCategoryGallery.class);
                    if(list.size()>0){
                        db_ImageCategoryGallery.deleteAll(db_ImageCategoryGallery.class);
                    }
                }
                catch (Exception e){e.printStackTrace();}
            }
            else {
                try {
                    List<db_VideoCategoryGallery> list = db_VideoCategoryGallery.listAll(db_VideoCategoryGallery.class);
                    if(list.size()>0){
                        db_VideoCategoryGallery.deleteAll(db_VideoCategoryGallery.class);
                    }
                }
                catch (Exception e){e.printStackTrace();}
            }


            try {
                imageGalleryArrayList = new ArrayList<ImageCategoryGallery>();

                JSONObject jsonObject =new JSONObject(result);
                if(jsonObject.getInt("Status")==1){
                    // save Token into my application class
                    JSONArray jsonArray=jsonObject.getJSONArray("Data");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject obj = jsonArray.getJSONObject(i);

                        int id=obj.getInt("Id");
                        String title=obj.getString("Title");

                        ImageCategoryGallery category=new ImageCategoryGallery(id,title);
                        imageGalleryArrayList.add(category);

                        // TODO : add too database
                        if(this.category.equals("getImagesCategory")){

                            db_ImageCategoryGallery db = new db_ImageCategoryGallery(id,title);
                            db.save();
                        }
                        else {
                            db_VideoCategoryGallery db = new db_VideoCategoryGallery(id,title);
                            db.save();
                        }
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

