package ir.elegam.doctor.Helper;

/**
 * Created by mohadi on 8/16/2016.
 */
public class Object_Extra {

    public String Sid;
    public String Title;
    public String Content;

    public Object_Extra(String Sid,String Title, String Content){
        this.Sid = Sid;
        this.Title = Title;
        this.Content = Content;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}// end class
