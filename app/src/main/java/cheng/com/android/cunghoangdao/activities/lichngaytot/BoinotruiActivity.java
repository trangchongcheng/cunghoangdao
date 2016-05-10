package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.startapp.android.publish.StartAppAd;

import java.util.ArrayList;
import java.util.List;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;
import cheng.com.android.cunghoangdao.ultils.CustomFont;

/**
 * Created by Welcome on 4/27/2016.
 */
public class BoinotruiActivity extends BaseMainActivity implements LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private SubsamplingScaleImageView imgNotrui;
    private List<String> list = new ArrayList<String>();
    private Toolbar mToolbar;
    private Spinner spnType;
    private Button btnKetqua;
    private EditText edtVitri;
    private TextInputLayout tp;
    private TextView tvContent;
    private ProgressBar progressBar;
    private String params;
    private ScrollView scr;
    private StartAppAd startAppAd;
    public int isClick = 0;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_boinotrui);
    }

    @Override
    public void init() {
        startAppAd = new StartAppAd(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(MainScreenActivity.TOOLBAR_NAME));
        btnKetqua = (Button) findViewById(R.id.activity_boinotrui_btnKetQua);
        edtVitri = (EditText) findViewById(R.id.activity_boinotrui_edtVitri);
        edtVitri.addTextChangedListener(new MyTextWatcher(edtVitri));
        edtVitri.requestFocus();
        tp = (TextInputLayout) findViewById(R.id.activity_boinotrui_tp);
        tvContent = (TextView) findViewById(R.id.activity_boinotrui_tvContent);
        progressBar = (ProgressBar) findViewById(R.id.activity_boinotrui_progressBar);
        scr = (ScrollView) findViewById(R.id.srv);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgNotrui = (SubsamplingScaleImageView) findViewById(R.id.activity_boinotrui_imgNotrui);
        spnType = (Spinner) findViewById(R.id.activity_boinotrui_spnType);
        for (int i = 0; i < getResources().getStringArray(R.array.type_boinotrui).length; i++) {
            list.add(getResources().getStringArray(R.array.type_boinotrui)[i]);
        }
        CustomFont.custfont(getApplicationContext(), tvContent, "fonts/Roboto-Regular.ttf");

    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.row_spinners_dropdown);
        spnType.setAdapter(adapter);
    }

    @Override
    public void setEvent() {
        spnType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_mat_nam));
                        break;
                    case 1:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_mat_nu));
                        break;
                    case 2:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_co_the_nam));
                        break;
                    case 3:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_co_the_nu));
                        break;
                    case 4:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_tay));
                        break;
                    case 5:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_chan));
                        break;
                    default:
                        imgNotrui.setImage(ImageSource.resource(R.drawable.img_not_rui_tren_mat_nam));
                        break;
                }
                edtVitri.setText("");
                tvContent.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
                scr.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //replace this line to scroll up or down
                        scr.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                }, 500);
            }
        });
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void submitForm() {
        if (validate()) {
            tvContent.setText("");
            progressBar.setVisibility(View.VISIBLE);
            params = ((spnType.getSelectedItemPosition() + 1) + "&ViTriNotRuoi=" + edtVitri.getText().toString().trim());
            new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.BOI_NOT_RUI + params,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, BoinotruiActivity.this).execute();
        } else {
            tp.setError("Vị trí không tìm thấy vui lòng chọn lại!");
        }
    }

    private boolean validate() {
        int position = spnType.getSelectedItemPosition();
        if (position == 0) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_mat_nam));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 78 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_mat_nam));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        if (position == 1) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_mat_nu));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 66 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_mat_nu));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        if (position == 2) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_co_the_nam));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 20 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_co_the_nam));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        if (position == 3) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_co_the_nu));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 31 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_co_the_nu));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        if (position == 4) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_ban_tay));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 28 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_ban_tay));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        if (position == 5) {
            if (edtVitri.getText().toString().trim().isEmpty()) {
                setError(getString(R.string.loi_nhap_not_rui_tren_ban_chan));
                return false;
            } else if (Integer.parseInt(edtVitri.getText().toString()) > 11 || Integer.parseInt(edtVitri.getText().toString()) <= 0) {
                setError(getString(R.string.loi_nhap_not_rui_tren_ban_chan));
                return false;
            } else {
                tp.setErrorEnabled(false);
            }
        }
        return true;
    }

    public void setError(String error) {
        tp.setError(error);
    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            isClick++;
            if (isClick == 3 || isClick == 7 || isClick == 12) {
                startAppAd.showAd();
                startAppAd.loadAd();
            }
            tvContent.setText(Html.fromHtml(arrContent.get(0).getmContent()));
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            tvContent.setText(getString(R.string.thu_lai));
        }

    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.activity_boinotrui_edtVitri:
                    validate();
                    break;
            }
        }
    }
}
