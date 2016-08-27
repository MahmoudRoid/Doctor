package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

/**
 * Created by Droid on 8/14/2016.
 */
public class db_ImagesDetailGallery extends SugarRecord {
    public  String categoryid;
    public int id;
    public String image_url;

    public  db_ImagesDetailGallery(){}

    public db_ImagesDetailGallery(String categoryid,int id,String image_url){
        this.categoryid=categoryid;
        this.id=id;
        this.image_url=image_url;
    }

    public String getCategory_id(){return  this.categoryid;}
    public int getid(){return this.id;}
    public String getImage_url(){return this.image_url;}
}

