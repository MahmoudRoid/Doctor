package ir.elegam.doctor.Helper;

/**
 * Created by Droid on 8/14/2016.
 */
public class ImageCategoryGallery {
    public int id;
    public String title;

    public ImageCategoryGallery(int id, String title){
        this.id=id;
        this.title=title;
    }

    public int getid(){return this.id;}
    public String getTitle(){return this.title;}
}
