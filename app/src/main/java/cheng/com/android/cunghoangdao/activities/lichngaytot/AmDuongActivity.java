package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Calendar;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;
import cheng.com.android.cunghoangdao.ultils.CustomFont;

/**
 * Created by Welcome on 4/29/2016.
 */
public class AmDuongActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        LichngaytotAsyntask.OnReturnJsonObject {
    private TextView tvNam, tvNu;
    private Spannable htmlSpan, processedText;
    private final String TAG = getClass().getSimpleName();
    public static final String DATEPICKER_TAG = "datepicker";
    private Button btnKetqua;
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private int iType = 0;
    private String params, content;
    private TextView tvContent, tvDate;
    private ProgressBar progressBar;
    private Toolbar mToolbar;
    private RadioButton rdoDuonglich, rdoAmlich;
    private WebView webview;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_amduong);
    }

    @Override
    public void init() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(MainScreenActivity.TOOLBAR_NAME));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Calendar calendar = java.util.Calendar.getInstance();
        tvDate = (TextView) findViewById(R.id.activity_amduong_tvDate);
        btnKetqua = (Button) findViewById(R.id.activity_amduong_btnKetqua);
        rdoDuonglich = (RadioButton) findViewById(R.id.rdoDuonglich);
        rdoAmlich = (RadioButton) findViewById(R.id.rdoAmlich);
        webview = (WebView) findViewById(R.id.activity_amduong_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        progressBar = (ProgressBar) findViewById(R.id.activity_amduong_progressBar);
        CustomFont.custfont(getApplicationContext(), tvContent, "fonts/Roboto-Regular.ttf");
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    @Override
    public void setEvent() {
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCalendar();
            }
        });
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                params = tvDate.getText() + "&vlAD=" + getPosition();
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.AM_DUONG + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, AmDuongActivity.this).execute();

            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int nam, int thang, int ngay) {
        this.year = nam;
        this.month = thang + 1;
        this.day = ngay;
        if (day < 10 && month > 9) {
            tvDate.setText("0" + day + "-" + month + "-" + year);
        } else if (day <= 9 && month <= 9) {
            tvDate.setText("0" + day + "-0" + month + "-" + year);
        } else if (day > 9 && month <= 9) {
            tvDate.setText(day + "-0" + month + "-" + year);
        } else {
            tvDate.setText(day + "-" + month + "-" + year);
        }
    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        String  lichamduong = null;
        Document document = null;
        String day, month, year, today;
        if (arrContent != null) {
            document = Jsoup.parse(arrContent.get(0).getmContent());
            day = document.select("div[class=\"lich-conten-center\"]").text();
            month = document.select("div[class=\"td_lich_center\"]").text();
            year = document.select("div[class=\"td_lich_left\"]").text();
            today = document.select("div[class=\"td_lich_right\"]").text();
            if (rdoAmlich.isChecked()) {
                lichamduong = "<h2>Ngày sinh Âm lịch của bạn là vào "
                        + today + " ngày " + day + " " + month + " Năm " + year + "</h2>";
            } else {
                lichamduong = "<h2>Ngày sinh Dương lịch của bạn là vào "
                        + today + " ngày " + day + " " + month + " Năm " + year + "</h2>";
            }
            document.select("div[class=\"td_lich\"]").remove();
            document.select("div[class=\"lich-conten\"]").remove();
            webview.loadDataWithBaseURL(null,ViewingActivity.styleCss + lichamduong+document.toString(),null, "utf-8", null);
            progressBar.setVisibility(View.GONE);
        } else {
            webview.loadData("<h3>"+getResources().getString(R.string.thu_lai)+"</h3>","text/html; charset=utf-8", "UTF-8");
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }

    public void getCalendar() {
        datePickerDialog.setVibrate(true);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }

    public int getPosition() {
        if (rdoDuonglich.isChecked()) {
            return 1;
        } else {
            return 0;
        }
    }
}
