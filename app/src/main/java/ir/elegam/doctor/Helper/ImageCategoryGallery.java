package ir.elegam.doctor.Helper;

/**
 * Created by Droid on 8/14/2016.
 */
public class ImageCategoryGallery {
    public String category_neame;
    public int imagecategoryid;

    public ImageCategoryGallery( int imagecategoryid,String category_neame){
        this.category_neame=category_neame;
        this.imagecategoryid=imagecategoryid;
    }

    public int getImagecategoryid(){return this.imagecategoryid;}
    public String getCategory_name(){return this.category_neame;}
}
