package ir.elegam.doctor.Helper;

public class ObjectMessage {

    public String Author;
    public String Message;
    public boolean isMe;

    public ObjectMessage(String Author,String Message,boolean isMe){
        this.Author = Author;
        this.Message = Message;
        this.isMe = isMe;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
