package ir.elegam.doctor.Helper;

/**
 * Created by Droid on 8/14/2016.
 */
public class ImagesDetailGallery {

    public  int category_id;
    public int id;
    public String image_url;

    public ImagesDetailGallery(int category_id,int id,String image_url){
        this.category_id=category_id;
        this.id=id;
        this.image_url=image_url;
    }

    public int getCategory_id(){return  this.category_id;}
    public int getid(){return this.id;}
    public String getImage_url(){return this.image_url;}
}
