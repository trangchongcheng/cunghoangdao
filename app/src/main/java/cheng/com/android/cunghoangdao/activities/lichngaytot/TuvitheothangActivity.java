package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.banner.Banner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
 * Created by Welcome on 4/28/2016.
 */
public class TuvitheothangActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private List<String> list = new ArrayList<String>();
    private Spinner spnThang;
    private Spannable htmlSpan;
    private Spannable processedText;
    private Button btnKetqua;
    private String toolbarName, params;
    private TextView tvContent, tvDate;
    private ProgressBar progressBar;
    public static final String DATEPICKER_TAG = "datepicker";
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private WebView webview;
    private Banner banner;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tuvitheothang);
    }

    @Override
    public void init() {
        banner = (com.startapp.android.publish.banner.Banner) findViewById(R.id.activity_tuvitheothang_startAppBanner);
        assert banner != null;
        banner.hideBanner();
        StartAppSDK.init(this,getString(R.string.banner_startapp), false);
        final Calendar calendar = java.util.Calendar.getInstance();
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra(MainScreenActivity.TOOLBAR_NAME);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        assert mToolbar != null;
        mToolbar.setTitle(toolbarName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < getResources().getStringArray(R.array.tienichboitheothang).length; i++) {
            list.add(getResources().getStringArray(R.array.tienichboitheothang)[i]);
        }
        spnThang = (Spinner) findViewById(R.id.activity_tuvitheothang_spnThang);
        btnKetqua = (Button) findViewById(R.id.activity_tuvitheothang_btnKetqua);
        webview = (WebView) findViewById(R.id.activity_tuvitheothang_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        tvDate = (TextView) findViewById(R.id.activity_tuvitheothang_tvDate);
        progressBar = (ProgressBar) findViewById(R.id.activity_tuvitheothang_progressBar);
        CustomFont.custfont(getApplicationContext(), tvContent, "fonts/Roboto-Regular.ttf");
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.row_spinners_dropdown);
        spnThang.setAdapter(adapter);
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
                params = tvDate.getText() + "&monthView="+getMonth(spnThang.getSelectedItemPosition());;
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.TU_VI_THEO_THANG + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, TuvitheothangActivity.this).execute();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getCalendar() {
        datePickerDialog.setVibrate(true);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int nam, int thang, int ngay) {
        this.year = nam;
        this.month = thang + 1;
        this.day = ngay;
        if (day < 10 && month > 9) {
            tvDate.setText("0" + day + "-"+ month + "-" + year);
        } else if (day <= 9 && month <= 9) {
            tvDate.setText("0" + day + "-0" + month + "-" + year);
        } else if (day > 9 && month <= 9) {
            tvDate.setText(day + "-0" + month + "-" + year);
        } else {
            tvDate.setText(day  +"-"+ month + "-" + year);
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            webview.loadDataWithBaseURL(null,ViewingActivity.styleCss +arrContent.get(0).getmContent(),null, "utf-8", null);
            progressBar.setVisibility(View.GONE);
            banner.showBanner();
        } else {
            webview.loadData("<h3>"+getResources().getString(R.string.thu_lai)+"</h3>","text/html; charset=utf-8", "UTF-8");
        }
    }

    private String getMonth(int i) {
        String month = "01";
        switch (i) {
            case 0:
                month = "01";
                break;
            case 1:
                month = "02";
                break;
            case 2:
                month = "03";
                break;
            case 3:
                month = "04";
                break;
            case 4:
                month = "05";
                break;
            case 5:
                month = "06";
                break;
            case 6:
                month = "07";
                break;
            case 7:
                month = "08";
                break;
            case 8:
                month = "09";
                break;
            case 10:
                month = "10";
                break;
            case 11:
                month = "11";
                break;
            case 12:
                month = "12";
                break;
        }
        return month;
    }

}
