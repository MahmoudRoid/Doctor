package ir.elegam.doctor.Activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.elegam.doctor.AsyncTask.GetCompetitionData;
import ir.elegam.doctor.AsyncTask.PostCompetitionData;
import ir.elegam.doctor.Classes.CustomTextView;
import ir.elegam.doctor.Classes.Internet;
import ir.elegam.doctor.Interface.IWebserviceByTag;
import ir.elegam.doctor.R;

public class CompetitionActivity extends AppCompatActivity implements IWebserviceByTag {
    @BindView(R.id.question_tv)
    CustomTextView questionTv;
    @BindView(R.id.result1)
    AppCompatRadioButton result1;
    @BindView(R.id.result2)
    AppCompatRadioButton result2;
    @BindView(R.id.result3)
    AppCompatRadioButton result3;
    @BindView(R.id.result4)
    AppCompatRadioButton result4;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.send_competition_btn)
    AppCompatButton sendCompetitionBtn;
    @BindView(R.id.competition_cardview)
    CardView competitionCardview;
    private Typeface San;
    private Toolbar toolbar;
    private TextView txtToolbar;
    private List<String> stringList;
    private View snack_view;
    public static final String Tag_GetData = "Tag_GetData";
    public static final String Tag_PostData = "Tag_PostData";
    public int result_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);
        ButterKnife.bind(this);

        define();
        init();

    }

    private void init() {
        if (Internet.isNetworkAvailable(CompetitionActivity.this)) {
            GetCompetitionData getdata = new GetCompetitionData(CompetitionActivity.this, CompetitionActivity.this, Tag_GetData);
            getdata.execute();
        }
    }

    private void define() {
        San = Typeface.createFromAsset(getAssets(), "fonts/SansLight.ttf");
        toolbar = (Toolbar) findViewById(R.id.toolbar_competition);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtToolbar = (TextView) findViewById(R.id.txtToolbar_appbar);
        txtToolbar.setTypeface(San);
        txtToolbar.setText("مسابقه");
    }

    @OnClick(R.id.send_competition_btn)
    public void onClick() {

        int id = radiogroup.getCheckedRadioButtonId();
        switch (id) {
            case R.id.result1:
                result_id=1;
                break;
            case R.id.result2:
                result_id=2;
                break;
            case R.id.result3:
                result_id=3;
                break;
            case R.id.result4:
                result_id=4;
                break;
        }

        if(Internet.isNetworkAvailable(CompetitionActivity.this)){
            PostCompetitionData postdata=new PostCompetitionData(CompetitionActivity.this,CompetitionActivity.this,String.valueOf(result_id),Tag_PostData);
            postdata.execute();
        }


    }


    @Override
    public void getResult(Object result, String Tag) throws Exception {
        switch (Tag) {
            case Tag_GetData:
              competitionCardview.setVisibility(View.VISIBLE);

                stringList = (List<String>) result;
                questionTv.setText(stringList.get(0));
                result1.setText(stringList.get(1));
                result2.setText(stringList.get(2));
                result3.setText(stringList.get(3));
                result4.setText(stringList.get(4));
                break;
            case Tag_PostData:

                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.competition_relative),(String)result, Snackbar.LENGTH_LONG);
                snack_view = snackbar.getView();
                snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
                TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
                tv.setTextColor(Color.WHITE);
                snackbar.show();
                break;

        }
    }

    @Override
    public void getError(String ErrorCodeTitle, String Tag) throws Exception {
        Snackbar snackbar = Snackbar
                .make(findViewById(R.id.competition_relative), "مشکلی پیش آمده است . مجددا تلاش نمایید", Snackbar.LENGTH_LONG);
        snack_view = snackbar.getView();
        snack_view.setBackgroundColor(getResources().getColor(R.color.pri500));
        TextView tv = (TextView) snack_view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
        snackbar.show();
    }
}
