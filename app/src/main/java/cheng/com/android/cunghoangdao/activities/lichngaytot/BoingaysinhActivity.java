package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
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
 * Created by Welcome on 4/26/2016.
 */
public class BoingaysinhActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        LichngaytotAsyntask.OnReturnJsonObject {
    private TextView tvNam, tvNu;
    private final String TAG = getClass().getSimpleName();
    public static final String DATEPICKER_TAG = "datepicker";
    private Button btnKetqua;
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private int iType = 0;
    private String params, content;
    private ProgressBar progressBar;
    private FloatingActionButton flbtnShare;
    private FloatingActionMenu flbtnMenu;
    private Toolbar mToolbar;
    private WebView webview;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_boingaysinh);
    }


    @Override
    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(MainScreenActivity.TOOLBAR_NAME));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Calendar calendar = java.util.Calendar.getInstance();
        tvNam = (TextView) findViewById(R.id.activity_boingaysinh_tvName);
        tvNu = (TextView) findViewById(R.id.activity_boingaysinh_tvNu);
        btnKetqua = (Button) findViewById(R.id.activity_boingaysinh_btnKetqua);

        progressBar = (ProgressBar) findViewById(R.id.activity_boingaysinh_progressBar);
        flbtnShare = (FloatingActionButton) findViewById(R.id.activity_boingaysinh_flbtnShare);
        flbtnMenu = (FloatingActionMenu) findViewById(R.id.activity_boingaysinh_flbtn_menu);
        flbtnMenu.hideMenu(true);
        webview = (WebView) findViewById(R.id.activity_boingaysinh_webview);
       // webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));

    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    public void getCalendar() {
        datePickerDialog.setVibrate(true);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }

    @Override
    public void setEvent() {
        tvNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iType = 0;
                getCalendar();

            }
        });
        tvNu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iType = 1;
                getCalendar();
            }
        });
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                params = "fatherDateOfBirth=" + tvNam.getText() + "&motherDateOfBirth=" + tvNu.getText();
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.BOI_TINH_DUYEN + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 2, BoingaysinhActivity.this).execute();
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flbtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupFacebookShareIntent();
                flbtnMenu.hideMenu(true);
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int nam, int thang, int ngay) {
        this.year = nam;
        this.month = thang + 1;
        this.day = ngay;
        if (iType == 0) {
            if (day < 10 && month > 9) {
                tvNam.setText("0" + day + "-" + month + "-" + year);
            } else if (day <= 9 && month <= 9) {
                tvNam.setText("0" + day + "-0" + month + "-" + year);
            } else if (day > 9 && month <= 9) {
                tvNam.setText(day + "-0" + month + "-" + year);
            } else {
                tvNam.setText(day + "-" + month + "-" + year);
            }

        } else {
            if (day < 10 && month > 9) {
                tvNu.setText("0" + day + month + "-" + year);
            } else if (day <= 9 && month <= 9) {
                tvNu.setText("0" + day + "-0" + month + "-" + year);
            } else if (day > 9 && month <= 9) {
                tvNu.setText(day + "-0" + month + "-" + year);
            } else {
                tvNu.setText(day + "-" + month + "-" + year);
            }
        }

    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            webview.loadDataWithBaseURL(null,ViewingActivity.styleCss + arrContent.get(0).getmContent(),null, "utf-8", null);
            progressBar.setVisibility(View.INVISIBLE);
            flbtnMenu.showMenu(true);
            try {
                content = Html.fromHtml(arrContent.get(0).getmContent()).toString().substring(0, 150);
            } catch (StringIndexOutOfBoundsException e) {
                flbtnMenu.hideMenu(true);
                Toast.makeText(BoingaysinhActivity.this, getString(R.string.thu_lai), Toast.LENGTH_SHORT).show();
            }
        } else {
            webview.loadData("<h3>"+getResources().getString(R.string.thu_lai)+"</h3>","text/html; charset=utf-8", "UTF-8");
        }
    }

    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("play.google.com"))
                .setContentTitle(getResources().getString(R.string.mota_boingaysinh))
                .setContentDescription("Bói tình duyên theo ngày giữa " + tvNam.getText() + " và " + tvNu.getText() + " như thế nào?")
                .setImageUrl(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Ecliptic_path.jpg/220px-Ecliptic_path.jpg"))
                .build();
        shareDialog.show(linkContent);
    }
}
