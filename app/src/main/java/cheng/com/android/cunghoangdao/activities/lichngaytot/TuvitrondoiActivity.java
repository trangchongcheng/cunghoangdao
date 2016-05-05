package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
 * Created by Welcome on 5/4/2016.
 */
public class TuvitrondoiActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, LichngaytotAsyntask.OnReturnJsonObject {

    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private Spannable htmlSpan;
    private Spannable processedText;
    private Button btnKetqua;
    private TextInputLayout tp;
    private String toolbarName, params;
    private TextView tvDate;
    private ProgressBar progressBar;
    public static final String DATEPICKER_TAG = "datepicker";
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private RadioButton rdoNam, rdoNu;
    private EditText edtName;
    private WebView webview;

    @Override
    public void setContentView() {
    setContentView(R.layout.activity_tuvitrondoi);
    }

    @Override
    public void init() {
        final Calendar calendar = java.util.Calendar.getInstance();
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra(MainScreenActivity.TOOLBAR_NAME);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(toolbarName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rdoNam = (RadioButton) findViewById(R.id.rdoNam);
        edtName = (EditText) findViewById(R.id.activity_tuvitrondoi_edtName);
        edtName.requestFocus();
        rdoNu = (RadioButton) findViewById(R.id.rdoNu);
        btnKetqua = (Button) findViewById(R.id.activity_tuvitrondoi_btnKetqua);
        tvDate = (TextView) findViewById(R.id.activity_tuvitrondoi_tvDate);
        progressBar = (ProgressBar) findViewById(R.id.activity_tuvitrondoi_progressBar);
        tp = (TextInputLayout) findViewById(R.id.activity_tuvitrondoi_Name);
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));
        webview = (WebView) findViewById(R.id.activity_tuvitrondoi_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
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
                InputMethodManager inputManager = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                if (edtName.getText().length()<5) {
                    tp.setError(getString(R.string.loi_nhap_ten));
                }else {
                    tp.setError("");
                    progressBar.setVisibility(View.VISIBLE);
                    params = edtName.getText() + "&m_NgaySinh=" +tvDate.getText()+"&genderId="+ getPosition();
                    Log.d(TAG, "onClick: " + UrlGetXml.TU_VI_TRON_DOI + params);
                    new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.TU_VI_TRON_DOI + params,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, TuvitrondoiActivity.this).execute();
                }

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
        if (arrContent != null) {
            Log.d(TAG, "onReturnJsonObject: " + ViewingActivity.styleCss + arrContent.get(0).getmContent());
            webview.loadDataWithBaseURL(null, ViewingActivity.styleCss + arrContent.get(0).getmContent(), null, "utf-8", null);
            progressBar.setVisibility(View.INVISIBLE);
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

    public String getPosition() {
        if (rdoNam.isChecked()) {
            return "Nam";
        } else {
            return "Nữ";
        }
    }
}
