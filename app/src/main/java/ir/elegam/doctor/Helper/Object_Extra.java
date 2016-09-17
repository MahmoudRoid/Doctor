package ir.elegam.doctor.Helper;

/**
 * Created by mohadi on 8/16/2016.
 */
public class Object_Extra {

    public String Sid;
    public String Title;
    public String Content;
    public String Eng;

    public Object_Extra(String Sid,String Title, String Content,String Eng){
        this.Sid = Sid;
        this.Title = Title;
        this.Content = Content;
        this.Eng = Eng;
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

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }
}// end class
