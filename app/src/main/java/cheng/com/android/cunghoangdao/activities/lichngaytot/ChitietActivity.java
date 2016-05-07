package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.interfaces.OnReturnArticleCunghoangdaoByDay;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;
import cheng.com.android.cunghoangdao.services.CovertBitmapToByte;
import cheng.com.android.cunghoangdao.services.JsoupParseCungHoangDaoByDay;

/**
 * Created by Welcome on 5/5/2016.
 */
public class ChitietActivity extends BaseMainActivity implements OnReturnArticleCunghoangdaoByDay ,CovertBitmapToByte.OnReturnBimapFromByte{
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private List<String> list = new ArrayList<String>();
    private Spinner spnCunghoangdao;
    private Button btnKetqua;
    private String toolbarName, params;
    private ProgressBar progressBar;
    private FloatingActionButton flbtnShare,flbtnSave;
    private FloatingActionMenu flbtnMenu;
    private String title, content, link;
    private DataHandlerSaveContent db;
    private WebView webview;
    private String linkImage;
    private String contentTemp;
    public static final String styleCss = "<meta charset=\"UTF-8\"><style>img{max-width: 100%; width:auto; height: auto;}" +
            "               a{color:#374046; text-decoration:none}" +
            "               h3,.lien_ket{font-size: 25px;color:#374046;font-weight: bold}" +
            "               .lienket_down,.title_tvi h2{font-size: 1.0em;color:#374046;font-weight: 700}" +
            "               u, ins {text-decoration: none;} " +
            "               p,div,.title2_left div,.licht-tam-tat-col1 p{color:#374046;font-family:Helvetica, Arial," +
            "               sans-serif;font-size: 25px;font-weight: 500;line-height: 1.251429em;}" +
            "               </style>";

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chitiet);
    }

    @Override
    public void init() {
        db = new DataHandlerSaveContent(this);
        linkImage = getIntent().getStringExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getIntent().getStringExtra(MainScreenActivity.TOOLBAR_NAME));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        for (int i = 0; i < getResources().getStringArray(R.array.cunghoangdaoname).length; i++) {
            list.add(getResources().getStringArray(R.array.cunghoangdaoname)[i]);
        }
        webview = (WebView) findViewById(R.id.activity_chitiet_webview);
        webview.setWebViewClient(new WebViewClient());
        webview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
        spnCunghoangdao = (Spinner) findViewById(R.id.activity_chitiet_spncunghoangdao);
        btnKetqua = (Button) findViewById(R.id.activity_chitiet_btnKetQua);
        progressBar = (ProgressBar) findViewById(R.id.activity_chitiet_progressBar);
        flbtnShare = (FloatingActionButton) findViewById(R.id.activity_chitiet_flbtnShare);
        flbtnSave = (FloatingActionButton) findViewById(R.id.activity_chitiet_flbtnSave);
        flbtnMenu = (FloatingActionMenu) findViewById(R.id.activity_chitiet_flbtn_menu);
        assert flbtnMenu != null;
        flbtnMenu.hideMenu(true);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item_spinner, list);
        adapter.setDropDownViewResource(R.layout.row_spinners_dropdown);
        spnCunghoangdao.setAdapter(adapter);

    }

    private int returnPosition(){
        return spnCunghoangdao.getSelectedItemPosition();
    }

    @Override
    public void setEvent() {
        btnKetqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                switch (returnPosition()){
                    case 0:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_BACH_DUONG).execute();
                        break;
                    case 1:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_KIM_NGUU).execute();
                        break;
                    case 2:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_SONG_TU).execute();
                        break;
                    case 3:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_CU_GIAI).execute();
                        break;
                    case 4:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_SU_TU).execute();
                        break;
                    case 5:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_XU_NU).execute();
                        break;
                    case 6:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_THIEN_BINH).execute();
                        break;
                    case 7:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_BO_CAP).execute();
                        break;
                    case 8:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_NHAN_MA).execute();
                        break;
                    case 9:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_MA_KET).execute();
                        break;
                    case 10:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_BAO_BINH).execute();
                        break;
                    case 11:
                        new JsoupParseCungHoangDaoByDay(getApplicationContext(),
                                ChitietActivity.this, UrlGetXml.CUNG_SONG_NGU).execute();
                        break;
                    default:
                        Toast.makeText(ChitietActivity.this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        flbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CovertBitmapToByte(getApplicationContext(), ChitietActivity.this).execute(linkImage);
                flbtnMenu.hideMenu(true);
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

    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(getString(R.string.link_app)))
                .setContentTitle(getResources().getString(R.string.tu_vi_tron_doi)+spnCunghoangdao.getSelectedItem())
                .setContentDescription("Xem tử vi 12 cung hoàng đạo trọn đời theo tình duyên, gia đình, sự nghiệm...chính xác nhất")
                .setImageUrl(Uri.parse(getString(R.string.link_image)))
                .build();
        shareDialog.show(linkContent);
    }

    @Override
    public void onReturnArticleCunghoangdaoByDay(Article article) {
        if (article != null) {
            webview.loadDataWithBaseURL(null, styleCss + article.getmContent().replace("small","large"), null, "utf-8", null);
            progressBar.setVisibility(View.GONE);
            contentTemp = article.getmContent().replace("small","large");
            flbtnMenu.showMenu(true);
            try {
                content = Html.fromHtml(article.getmContent()).toString().substring(0, 150);
            } catch (StringIndexOutOfBoundsException e) {
                flbtnMenu.hideMenu(true);
                Toast.makeText(this, getString(R.string.thu_lai), Toast.LENGTH_SHORT).show();
            }
        } else {
            webview.loadData("<h3>" + getResources().getString(R.string.thu_lai) + "</h3>", "text/html; charset=utf-8", "UTF-8");
        }
    }

    @Override
    public void onReturnBimapFromByte(byte[] image) {
        db.addArticle(new Article("Tủ vi Cung hoàng đạo trọn đời của "+spnCunghoangdao.getSelectedItem(), getString(R.string.chi_tiet_ve_ban), image, contentTemp));
    }
}
