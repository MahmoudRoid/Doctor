package ir.elegam.doctor.Database.orm;

import com.orm.SugarRecord;

public class tbl_Videos extends SugarRecord {

    public  String categoryid;
    int id;
    String title,thumbnail,video_url;

// taghir dadim
    public tbl_Videos(){}

    public tbl_Videos(String categoryid,int id, String title, String thumbnail, String video_url){
        this.id=id;
        this.categoryid=categoryid;
        this.title=title;
        this.thumbnail=thumbnail;
        this.video_url=video_url;
    }

    public String getCategory_id() {
        return categoryid;
    }

    public void setCategory_id(String category_id) {
        this.categoryid = categoryid;
    }

    public int getid() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }
}