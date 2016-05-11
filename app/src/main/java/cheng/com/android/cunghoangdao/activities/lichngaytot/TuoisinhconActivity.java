package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

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

/**
 * Created by Welcome on 4/29/2016.
 */
public class TuoisinhconActivity extends BaseMainActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener, LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private Spannable htmlSpan;
    private Spannable processedText;
    private Button btnKetqua;
    private TextInputLayout tp;
    private String toolbarName, params;
    private TextView tvDateBo, tvDateMe;
    private ProgressBar progressBar;
    private Spinner spnDate;
    public static final String DATEPICKER_TAG = "datepicker";
    private DatePickerDialog datePickerDialog;
    private int year, month, day;
    private RadioButton rdoNam, rdoNu;
    private EditText edtYear;
    private List<Integer> list = new ArrayList<Integer>();
    private int iType;
    private WebView webview;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_tuoisinhcon);
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
        btnKetqua = (Button) findViewById(R.id.activity_tuoisinhcon_btnKetqua);
        spnDate = (Spinner) findViewById(R.id.activity_tuoisinhcon_spnDate);
        webview = (WebView) findViewById(R.id.activity_tuoisinhcon_webview);
        webview.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        tvDateBo = (TextView) findViewById(R.id.activity_tuoisinhcon_tvDateBo);
        tvDateMe = (TextView) findViewById(R.id.activity_tuoisinhcon_tvDateMe);
        progressBar = (ProgressBar) findViewById(R.id.activity_tuoisinhcon_progressBar);
        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH), calendar.get(java.util.Calendar.DAY_OF_MONTH));

        for (int i = 2014; i <= 2036; i++) {
            list.add(i);
        }
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.row_spinners_dropdown);
        spnDate.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        tvDateBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iType = 0;
                getCalendar();
            }
        });
        tvDateMe.setOnClickListener(new View.OnClickListener() {
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
                params = tvDateBo.getText() + "&motherDateOfBirth=" + tvDateMe.getText() + "&yearView=" + spnDate.getSelectedItem();
                Log.d(TAG, "onClick: " + UrlGetXml.TUOI_SINH_CON + params);
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.TUOI_SINH_CON + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, TuoisinhconActivity.this).execute();
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
        if (iType == 0) {
            if (day < 10 && month > 9) {
                tvDateBo.setText("0" + day +"-"+ month + "-" + year);
            } else if (day <= 9 && month <= 9) {
                tvDateBo.setText("0" + day + "-0" + month + "-" + year);
            } else if (day > 9 && month <=9) {
                tvDateBo.setText(day + "-0" + month + "-" + year);
            }else {
                tvDateBo.setText(day  +"-"+ month + "-" + year);
            }

        } else {
            if (day < 10 && month > 9) {
                tvDateMe.setText("0" + day + month + "-" + year);
            } else if (day <= 9 && month <= 9) {
                tvDateMe.setText("0" + day + "-0" + month + "-" + year);
            } else if (day > 9 && month <=9) {
                tvDateMe.setText(day + "-0" + month + "-" + year);
            }else {
                tvDateMe.setText(day  +"-"+ month + "-" + year);
            }
        }
    }
    public void getCalendar() {
        datePickerDialog.setVibrate(true);
        datePickerDialog.setYearRange(1985, 2028);
        datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
    }
    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            webview.loadDataWithBaseURL(null,ViewingActivity.styleCss +arrContent.get(0).getmContent(),null, "utf-8", null);
            progressBar.setVisibility(View.GONE);
        } else {
            webview.loadData("<h3>"+getResources().getString(R.string.thu_lai)+"</h3>","text/html; charset=utf-8", "UTF-8");
        }
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i1) {

    }
}
