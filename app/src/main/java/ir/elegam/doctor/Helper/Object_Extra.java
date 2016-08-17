package ir.elegam.doctor.Helper;

/**
 * Created by mohadi on 8/16/2016.
 */
public class Object_Extra {

    public String Title;
    public String Content;

    public Object_Extra(String Title, String Content){
        this.Title = Title;
        this.Content = Content;
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
