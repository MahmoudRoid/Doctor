package ir.elegam.doctor.Helper;

/**
 * Created by Droid on 8/17/2016.
 */
public class MyObject {
    public int sid;
    public String faction,title,content,image_url,favorite;

    public MyObject(int sid,String faction,String title,String content,String image_url,String favorite){
        this.sid=sid;
        this.faction=faction;
        this.title=title;
        this.content=content;
        this.image_url=image_url;
        this.favorite=favorite;
    }

    public int getSid(){return  this.sid;}
    public  String getFaction(){return  this.faction;}
    public String getTitle(){return  this.title;}
    public  String getContent(){return  this.content;}
    public String getImage_url(){return  this.image_url;}
    public String getFavorite(){return  this.favorite;}

}
