package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;
import cheng.com.android.cunghoangdao.ultils.CustomFont;

/**
 * Created by Welcome on 4/18/2016.
 */
public class TienichActivity extends BaseMainActivity implements LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private List<String> list = new ArrayList<String>();
    private Spinner spnNu, spnNam;
    private Button btnKetqua;
    private String toolbarName, params;
    private TextView tvContent;
    private ProgressBar progressBar;
    private FloatingActionButton flbtnShare;
    private FloatingActionMenu flbtnMenu;
    private String title, content,link;
    private DataHandlerSaveContent db;
    @Override
    public void setContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_tracnghiemty);
    }

    @Override
    public void init() {
        db = new DataHandlerSaveContent(this);
        Intent intent = getIntent();
        toolbarName = intent.getStringExtra(MainScreenActivity.TOOLBAR_NAME);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(toolbarName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < getResources().getStringArray(R.array.cunghoangdaoname).length; i++) {
            list.add(getResources().getStringArray(R.array.cunghoangdaoname)[i]);
        }
        spnNu = (Spinner) findViewById(R.id.activity_tnty_spnNu);
        spnNam = (Spinner) findViewById(R.id.activity_tnty_spnNam);
        btnKetqua = (Button) findViewById(R.id.activity_tnty_btnKetQua);
        tvContent = (TextView) findViewById(R.id.activity_tnty_tvContent);
        progressBar = (ProgressBar) findViewById(R.id.activity_tnty_progressBar);
        flbtnShare = (FloatingActionButton) findViewById(R.id.activity_tnty_flbtnShare);
        flbtnMenu = (FloatingActionMenu) findViewById(R.id.activity_tnty_flbtn_menu);
        CustomFont.custfont(this,tvContent);
        flbtnMenu.hideMenu(true);

    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.row_spinners_dropdown);
        spnNu.setAdapter(adapter);
        spnNam.setAdapter(adapter);

    }

    @Override
    public void setEvent() {
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                params = (spnNu.getSelectedItemPosition() + 1) + "&CungHoangDaoNam=" + (spnNam.getSelectedItemPosition() + 1);
                new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.BOI_CUNG_HOANG_DAO + params,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 0, TienichActivity.this).execute();
            }
        });

        flbtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupFacebookShareIntent();
                flbtnMenu.hideMenu(true);
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
    public void onReturnJsonObject(ArrayList<Category> arrContent) {
        if(arrContent!=null){
            tvContent.setText(Html.fromHtml(arrContent.get(0).getmContent()));
            progressBar.setVisibility(View.INVISIBLE);
            flbtnMenu.showMenu(true);
            content = tvContent.getText().toString().substring(0,150);
        }
    }
    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("play.google.com"))
                .setContentTitle(getResources().getString(R.string.boi_ty_chd))
                .setContentDescription("Khám phám xem tình yêu giữa "+spnNam.getSelectedItem()+" và "+ spnNu.getSelectedItem() +" như thế nào")
                .setImageUrl(Uri.parse("https://upload.wikimedia.org/wikipedia/commons/thumb/d/de/Ecliptic_path.jpg/220px-Ecliptic_path.jpg"))
                .build();
        shareDialog.show(linkContent);
    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }
}
