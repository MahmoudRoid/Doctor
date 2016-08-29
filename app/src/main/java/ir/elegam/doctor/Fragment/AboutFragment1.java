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
            Content = db.DisplayExtra(3,"Sid","92");
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*Content =  "دکتر حمیدرضا مفرد\r\n\r\n\r\n<  \"http://drmofrad.elegam.ir/Archive/Image/20160828110032723-tn.jpeg\" style=\"height:220px; width:220px\" > \r\n\r\n\r\nفوق تحصص جراحی پلاستیك\r\n\r\n \r\n\r\nعضو انجمن جراحان پلاستيك و زيبايي ايران\r\n\r\n \r\n\r\nسوابق علمی و حرفه ای :\r\n\r\n \r\n•فوق تخصص جراحی پلاستیک: دانشگاه علوم پزشکی شهید بهشتی ۱۳۸۹ (دستیار ارشد به مدت یکسال)\r\n• رتبه سوم بورد فوق تخصصی جراحی پلاستیک کشور درسال ۱۳۸۹\r\n• تخصص جراحی عمومی :دانشگاه علوم پزشکی تهران ۱۳۸۵ (دستیار ارشد به مدت یکسال)\r\n• رتبه پنجم بورد تخصصی جراحی عمومی کشور درسال ۱۳۸۵\r\n• دکترای پزشکی عمومی : دانشگاه علوم پزشکی تهران۱۳۷۸\r\n \r\n\r\n \r\n\r\n> جراحي پلاستيك و زيبايي بيني(رينو پلاستي)\r\n\r\n> جراحي زيبايي و جوانسازي صورت(ليفت)\r\n\r\n> جراحي زيبايي پلك و ابرو\r\n\r\n> تزريق ژل و بوتاكس\r\n\r\n> تزريق چربي,تعبيه پروتز گونه\r\n\r\n> جراحي زيبايي سينه,شكم و تعبيه پروتز سينه\r\n\r\n> ليپوساكشن صورت,تنه و اندمها\r\n"
;
*/

        setContent(Content+"<\"\">");
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
                cimg(text.substring(c2+2,c3-1));
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
                    .placeholder(R.drawable.ic_launcher)
                    .into(img);

            lay.addView(img, lp);
        }
    }// end cimg()




}// end class

