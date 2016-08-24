package ir.elegam.doctor.Interface;


public interface IWebserviceByTag {
    void getResult(Object result,String Tag) throws Exception;
    void getError(String ErrorCodeTitle,String Tag)throws Exception;



}
