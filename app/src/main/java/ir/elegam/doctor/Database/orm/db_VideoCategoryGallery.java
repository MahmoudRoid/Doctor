package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

/**
 * Created by Droid on 8/18/2016.
 */
public class db_VideoCategoryGallery extends SugarRecord {
    public String category_neame;
    public int videocategoryid;


    public db_VideoCategoryGallery(){}

    public db_VideoCategoryGallery(int videocategoryid,String category_neame){
        this.category_neame=category_neame;
        this.videocategoryid=videocategoryid;
    }

    public int getVideocategoryid(){return  this.videocategoryid;}
    public String getCategory_neame(){return this.category_neame;}
}

