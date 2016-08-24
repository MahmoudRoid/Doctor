package ir.elegam.doctor.Helper;

/**
 * Created by Droid on 8/24/2016.
 */
public class Competition {
    public String question,result1,result2,result3,result4;

    public Competition(String question,String result1,String result2,String result3,String result4){
        this.question=question;
        this.result1=result1;
        this.result2=result2;
        this.result3=result3;
        this.result4=result4;
    }

    public String getQuestion(){return this.question;}
    public String getResult1(){return  this.result1;}
    public String getResult2(){return  this.result2;}
    public String getResult3(){return  this.result3;}
    public String getResult4(){return  this.result4;}
}
