package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

/**
 * Created by Droid on 8/14/2016.
 */
public class db_ImageCategoryGallery extends SugarRecord {
    public String categoryneame;
    public int imagecategoryid;

    public db_ImageCategoryGallery(){}

    public db_ImageCategoryGallery(int imagecategoryid,String category_neame){
        this.categoryneame=category_neame;
        this.imagecategoryid=imagecategoryid;
    }

    public int getImagecategoryid(){return this.imagecategoryid;}
    public String getCategory_neame(){return this.categoryneame;}
}
