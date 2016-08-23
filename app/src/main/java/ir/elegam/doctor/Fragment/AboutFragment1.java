package ir.elegam.doctor.Fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import ir.elegam.doctor.Classes.Variables;
import ir.elegam.doctor.Database.database;
import ir.elegam.doctor.R;

public class AboutFragment1 extends Fragment {

    private String Content="";
    private ViewGroup layout;
    private Typeface San;
    public static int w = 0,h = 0;
    private database db;
    private LinearLayout lay;

    public AboutFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new database(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        San = Typeface.createFromAsset(getActivity().getAssets(), "fonts/SansLight.ttf");
        layout = (ViewGroup) inflater.inflate(R.layout.fragment_about1, container, false);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lay = (LinearLayout) layout.findViewById(R.id.layMatn_about1);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        h = displaymetrics.heightPixels;
        w = displaymetrics.widthPixels;

        try {
            db.open();
            Content = db.DisplayExtra("درباره ما");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*Content = "بودند، برای خود برد قائل میشدند. در آن مسابقه هم ناداوری وجود داشت و در چند صحنه باید به او کارت قرمز میدادند.\n" +
                "\n" +
                "* احساس میشد در بازی رده بندی انگیزه لازم را نداشتی؟\n" +
                "\n" +
                "انگیزه داشتم که صد در صد مدال بگیرم، اما آن ناداوری مقابل حریف آمریکایی روحیه من را به شدت کاهش داد. متاسفانه در آن مسابقه برگشتم، اما خیلی دیر شده بود.\n" +
                "\n" +
                "<  \"http://media.farsnews.com/media/Uploaded/Files/Images/1395/06/01/13950601000846_PhotoL.jpg\" >\n" +
                "\n" +
                "\n" +
                "\n" +
                "* قبل از مسابقات آیا فکر ناداوری ها را کرده بودید؟\n" +
                "\n" +
                "در یک سال گذشته که ما در جامهای جهانی حاضر شدیم، همیشه قبل از مسابقه 2 تا سه ضربه را برای ناداوری ها کنار\n";
*/
        setContent(Content+"<\"   \">");
    }

    private void setContent(String text){
        int c1=0,c2=0,c3=0;

        for(int i=0;i<text.length();i++){

            if(text.charAt(i)=='<'){
                c2=i;
                if(!text.substring(c1,c2).equals("")){
                    ctext(text.substring(c1,c2));
                }

            }
            if(text.charAt(i)=='>'){
                c3=i;
                cimg(text.substring(c2+4,c3-2));
                c1=i+1;
            }
        }
    }// end setContent()

    private void ctext(String text){
        Log.i(Variables.Tag,"text: "+text);
        TextView tv=new TextView(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 10, 0, 10);
        tv.setPadding(10, 10, 10, 10);
        lp.gravity= Gravity.RIGHT & Gravity.TOP;
        tv.setGravity(Gravity.RIGHT & Gravity.TOP);
        //lp.gravity= Gravity.TOP;
        tv.setText(text);
        lay.addView(tv,lp);

    }// end ctext()

    private void cimg(String image_url){
        Log.i(Variables.Tag,"imageurl: "+image_url);

        if(!image_url.equals("")){
            ImageView img=new ImageView(getActivity());
            double ch=w/2;
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(w,(int)ch);
            lp.gravity=Gravity.CENTER;

            Glide.with(this)
                    .load(image_url)
                    .override(200,200)
                    .placeholder(R.drawable.favorite_black)
                    .into(img);

            lay.addView(img, lp);
        }
    }// end cimg()




}// end class

