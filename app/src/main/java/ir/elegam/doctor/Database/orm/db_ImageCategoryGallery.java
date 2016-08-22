package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

/**
 * Created by Droid on 8/14/2016.
 */
public class db_ImageCategoryGallery extends SugarRecord {
    public int cate_id;
    public String title;

    public db_ImageCategoryGallery(){}

    public db_ImageCategoryGallery(int cate_id, String title){
        this.cate_id=cate_id;
        this.title=title;
    }

    public int getid(){return this.cate_id;}
    public String getTitle(){return this.title;}
}
