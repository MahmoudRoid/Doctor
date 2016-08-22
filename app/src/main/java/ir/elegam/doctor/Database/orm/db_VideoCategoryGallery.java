package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

/**
 * Created by Droid on 8/18/2016.
 */
public class db_VideoCategoryGallery extends SugarRecord {
    public int id;
    public String title;

    public db_VideoCategoryGallery(){}

    public db_VideoCategoryGallery(int id, String title){
        this.id=id;
        this.title=title;
    }

    public int getid(){return this.id;}
    public String getTitle(){return this.title;}
}
