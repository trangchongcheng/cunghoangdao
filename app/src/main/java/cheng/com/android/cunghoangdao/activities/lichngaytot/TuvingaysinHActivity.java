package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

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

/**
 * Created by Welcome on 4/28/2016.
 */
public class TuvingaysinhActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        LichngaytotAsyntask.OnReturnJsonObject{
    private Button btnKetqua;
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private Spannable htmlSpan;
    private Spannable processedText;
    private DatePickerDialog datePickerDialog;
    public static final String DATEPICKER_TAG = "datepicker";
    private TextView tvDate;
    private ProgressBar progressBar;
    private String params;
    private ScrollView scr;
    private int year, month, day;
    private WebView webview;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tuvingaysinh);
    }

    @Override
    public void init() {
        final Calendar calendar = java.util.Calendar.getInstance();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(MainScreenActivity.TOOLBAR_NAME));
        btnKetqua = (Button) findViewById(R.id.activity_tuvingaysinh_btnKetqua);
        tvDate = (TextView) findViewById(R.id.activity_tuvingaysinh_tvDate);
        progressBar = (ProgressBar) findViewById(R.id.activity_tuvingaysinh_progressBar);
        webview = (WebView) findViewById(R.id.activity_tuvingaysinh_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        scr = (ScrollView) findViewById(R.id.srv);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                params =tvDate.getText() + "&ChuyenMucChung=33";
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.TU_VI_THANG + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, TuvingaysinhActivity.this).execute();
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
            tvDate.setText("0" + day +"-"+ month + "-" + year);
        } else if (day <= 9 && month <= 9) {
            tvDate.setText("0" + day + "-0" + month + "-" + year);
        } else if (day > 9 && month <=9) {
            tvDate.setText(day + "-0" + month + "-" + year);
        }else {
            tvDate.setText(day  +"-"+ month + "-" + year);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            Log.d(TAG, "onReturnJsonObject:1");
            webview.loadDataWithBaseURL(null,ViewingActivity.styleCss +arrContent.get(0).getmContent(),null, "utf-8", null);
            progressBar.setVisibility(View.GONE);
        } else {
            Log.d(TAG, "onReturnJsonObject: 2");
            webview.loadData("<h3>"+getResources().getString(R.string.thu_lai)+"</h3>","text/html; charset=utf-8", "UTF-8");
        }
    }

}
