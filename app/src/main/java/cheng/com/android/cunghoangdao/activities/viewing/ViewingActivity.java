package cheng.com.android.cunghoangdao.activities.viewing;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.ChitietActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.ClipPhongThuyActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.adapters.category.RecyclerCategoryAdapter;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.interfaces.OnItemClickRecyclerView;
import cheng.com.android.cunghoangdao.interfaces.OnReturnContent;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.CovertBitmapToByte;
import cheng.com.android.cunghoangdao.services.JsoupParseLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

/**
 * Created by Welcome on 3/28/2016.
 */
public class ViewingActivity extends BaseMainActivity implements OnReturnContent,
        CovertBitmapToByte.OnReturnBimapFromByte, LichngaytotAsyntask.OnReturnJsonObject, OnItemClickRecyclerView {
    private Toolbar mToolbar;
    private int mutedColor = R.attr.colorPrimary;
    private final String TAG = getClass().getSimpleName();
    String content, title, linkArticle,typeCategory, linkImage, category, contentShare,
            typeOffline, typeNotify, typeBoi, typeLichngaytot, typeVideo;
    private Intent intent;
    private TextView tvMore;
    private ProgressBar progressBar;
    private FloatingActionButton flbtnSave, flbtnShare,flbtnZalo;
    private DataHandlerSaveContent db;
    private String contentTemp;
    private LinearLayout ll;
    private Button btnConnect;
    private NestedScrollView nestscv;
    private FloatingActionMenu flbtnMenu;
    private WebView webview;
    private RecyclerView rvMore;
    private RecyclerCategoryAdapter categoryAdapter;
    public static final String styleCss = "<meta charset=\"UTF-8\"><style>img{max-width: 100%; width:auto; height: auto;}" +
            "               a{color:#374046; text-decoration:none}" +
            "               h3{font-size: 25px;color:#374046;}" +
            "               .title_tvi h2{font-size: 1.0em;color:#374046;}" +
            "               u, ins {text-decoration: none;} " +

            "               p{color:#374046;font-family:Helvetica, Arial," +
            "               sans-serif;font-size: 25px;font-weight: 500;line-height: 1.251429em;}" +

            "               div{color:#374046;font-family:Helvetica, Arial," +
            "               sans-serif;font-size: 25px;font-weight: 500;line-height: 1.251429em;}" +

            "               .title2_left div{color:#374046;font-family:Helvetica, Arial," +
            "               sans-serif;font-size: 0.9em;font-weight: 500;line-height: 1.251429em;}" +
            "               .licht-tam-tat-col1 p{color:#374046;font-size: 25px;font-weight: 500;line-height: 1.251429em;}" +
            "               </style>";

    @Override
    public void setContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_viewing);
    }

    @Override
    public void init() {
        AdView mAdView = (AdView)findViewById(R.id.activity_viewing_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        assert mAdView != null;
        mAdView.loadAd(adRequest);
        intent = getIntent();
        typeOffline = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_OFFLINE);
        typeNotify = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_NOTIFY);
        typeBoi = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_BOI);
        typeLichngaytot = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_LICH_NGAY_TOT);
        typeVideo = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_VIDEO);
        typeCategory =intent.getStringExtra(MainScreenActivity.TYPE_CATEGORY);
        linkArticle = intent.getStringExtra(RecyclerCunghoangdaoAdapter.LINK);
        title = "<h1>" + intent.getStringExtra(RecyclerCunghoangdaoAdapter.TITLE) + "</h1>";
        linkImage = intent.getStringExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE);
        category = intent.getStringExtra(RecyclerCunghoangdaoAdapter.CATEGORY);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(category);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvMore = (TextView) findViewById(R.id.activity_viewing_tvMore);
        rvMore = (RecyclerView) findViewById(R.id.fragment_viewing_rvMore);
        rvMore.setHasFixedSize(true);
        //Set  has to Scroll on RecyclerView
        rvMore.setNestedScrollingEnabled(false);
        // mRecyclerViewSlider = (RecyclerView) getView().findViewById(R.id.cunghoangdao_fragment_recyclerSlide);
        // mRecyclerViewSlider.setHasFixedSize(true);
        //  mRecyclerViewSlider.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvMore.setLayoutManager(new LinearLayoutManager(this));
        progressBar = (ProgressBar) findViewById(R.id.activity_viewing_progress);
        flbtnSave = (FloatingActionButton) findViewById(R.id.activity_viewing_flbtnSave);
        flbtnShare = (FloatingActionButton) findViewById(R.id.activity_viewing_flbtnShare);
        flbtnZalo = (FloatingActionButton) findViewById(R.id.activity_viewing_flbtnZalo);
        progressBar.setVisibility(View.VISIBLE);
        ll = (LinearLayout) findViewById(R.id.activity_viewing_ll);
        btnConnect = (Button) findViewById(R.id.activity_viewing_btnConnect);
        nestscv = (NestedScrollView) findViewById(R.id.activity_viewing_nestscv);
        flbtnMenu = (FloatingActionMenu) findViewById(R.id.activity_viewing_flbtn_menu);
        webview = (WebView) findViewById(R.id.activity_viewing_webview);
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
        assert flbtnMenu != null;
        flbtnMenu.hideMenu(true);
        db = new DataHandlerSaveContent(this);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        setContent();
    }

    public void setContent() {
        if (linkArticle != null) {
            new JsoupParseLichNgayTot(this, "http://lichngaytot.com" + linkArticle, this, null).execute();
        } else {
            content = intent.getStringExtra(RecyclerCunghoangdaoAdapter.CONTENT);
            contentShare = Html.fromHtml(content).toString().substring(0, 150);
            Log.d(TAG, "setContent: " + content);
            setContent(ChitietActivity.styleCss + content);
        }
    }

    @SuppressLint("NewApi")


    @Override
    public void setEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setItent();
                finish();
            }
        });
        flbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CovertBitmapToByte(getApplicationContext(), ViewingActivity.this).execute(linkImage);
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
        flbtnZalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                flbtnMenu.hideMenu(true);
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContent();
                if (ConnectionUltils.isConnected(getApplicationContext())) {
                    ll.setVisibility(View.GONE);

                }
            }
        });
        nestscv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (typeOffline == null) {
                    if (scrollY > oldScrollY) {
                        flbtnMenu.hideMenu(true);
                    } else if (scrollY < oldScrollY) {
                        flbtnMenu.showMenu(true);
                    }
                }
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }


    @Override
    public void onReturnContent(String content) {
        if(content!=null) {
            contentShare = Html.fromHtml(content).toString().substring(0, 150);
            setContent(styleCss + content);
        }else {
            ll.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onReturnContent(String content, String videoUrl) {

    }

    public void setContent(final String content) {
        Log.d(TAG, "setContent: " + typeOffline);
        if (ConnectionUltils.isConnected(this)) {
            if (typeOffline != null) {
                Log.d(TAG, "setContent: 1");
                webview.loadData(title + content, "text/html; charset=UTF-8", null);
                progressBar.setVisibility(View.GONE);
            } else {
                webview.loadData(title + content, "text/html; charset=UTF-8", null);
                progressBar.setVisibility(View.INVISIBLE);
                contentTemp = content;
                if(category.equals(getString(R.string.menu_item_phong_thuy))){
                    new LichngaytotAsyntask(this, UrlGetXml.PHONG_THUY + 0,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                            getResources().getString(R.string.category_phongthuy), this).execute();
                }else if(category.equals(getString(R.string.menu_item_cunghoangdao))){
                    new LichngaytotAsyntask(this, UrlGetXml.LICH_CUNG_HOANG_DAO + 0,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                            getResources().getString(R.string.category_cunghoangdao), this).execute();
                } else if(category.equals(getString(R.string.menu_item_tu_vi))){
                    new LichngaytotAsyntask(this, UrlGetXml.LICH_TU_VI + 0,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                            getResources().getString(R.string.category_tuvi), this).execute();
                } else if(category.equals(getString(R.string.menu_item_xem_tuong))){
                    new LichngaytotAsyntask(this, UrlGetXml.XEM_TUONG + 0,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                            getResources().getString(R.string.category_tuongso), this).execute();
                }
                else if(category.equals(getString(R.string.menu_item_tam_linh))){
                    new LichngaytotAsyntask(this, UrlGetXml.TAM_LINH + 0,
                            ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                            getResources().getString(R.string.menu_item_tam_linh), this).execute();
                }

            }

        } else {
            if (typeOffline != null) {
                flbtnMenu.hideMenu(true);
                webview.loadData(title + content, "text/html; charset=UTF-8", null);
                ll.setVisibility(View.GONE);
                progressBar.setVisibility(View.INVISIBLE);
                tvMore.setVisibility(View.GONE);

            } else {
                ll.setVisibility(View.VISIBLE);
            }
        }

    }


    @Override
    public void onReturnBimapFromByte(byte[] image) {
        db.addArticle(new Article(title, category, image, contentTemp));
    }

    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(getString(R.string.link_app)))
                .setContentTitle(title.replace("<h1>","").replace("</h1>",""))
                .setContentDescription(contentShare.substring(0,150))
                .setImageUrl(Uri.parse("http://mxhviet.com/wp-content/uploads/2016/05/ic_laucher.png"))
                .build();
        shareDialog.show(linkContent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        setItent();
        super.onBackPressed();
    }

    public void setItent() {
        if (typeNotify != null) {
            Intent intent = new Intent(ViewingActivity.this, MainScreenActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if(arrContent.size()>0){
            for (int i = 0;i<arrContent.size();i++){
                if(arrContent.get(i).getmTitle().substring(0,5).equals("Tử vi")){
                    Log.d(TAG, "onReturnJsonObject: "+arrContent.get(i).getmTitle());
                    arrContent.remove(i);
                }
            }
            categoryAdapter = new RecyclerCategoryAdapter(this, arrContent, this, rvMore, type, categoryName);
            rvMore.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
            tvMore.setVisibility(View.VISIBLE);
            rvMore.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void onItemClickListener(View v, int position, String title, String linkAricle, String linkImage, int category, String categoryName) {
        putIntent(title, linkAricle, linkImage, category);
    }

    @Override
    public void onDaybydayitemClickLintener(View v, int position, String category, String title, String content) {

    }
    public void putIntent(String title, String linkArticle, String linkImage, int type) {
        Intent intent = null;
        if (type == 0) {
            intent = new Intent(this, ViewingActivity.class);
        } else if (type == 1) {
            intent = new Intent(this, ClipPhongThuyActivity.class);
            intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_VIDEO, "type_video");
        }
        assert intent != null;
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK, linkArticle);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(MainScreenActivity.TYPE_CATEGORY,typeCategory);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, category);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_LICH_NGAY_TOT, "type_lichngaytot");
        startActivity(intent);
    }
    void share() {
        try
        {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            Intent share = new Intent(android.content.Intent.ACTION_SEND);
            share.setType("text/plain");
            List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
            if (!resInfo.isEmpty()){
                for (ResolveInfo info : resInfo) {
                    Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
                    targetedShare.setType("text/plain"); // put here your mime type
                    if (info.activityInfo.packageName.toLowerCase().contains("com.zing.zalo") || info.activityInfo.name.toLowerCase().contains("com.zing.zalo")) {
                        targetedShare.putExtra(Intent.EXTRA_SUBJECT,title.replace("<h1>","").replace("</h1>",""));
                        targetedShare.putExtra(Intent.EXTRA_TEXT,contentShare.substring(0,150)
                                +"\n"+getString(R.string.taitaiday)+"\n"+getString(R.string.link_app));
                        targetedShare.setPackage(info.activityInfo.packageName);
                        targetedShareIntents.add(targetedShare);
                    }
                }
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        }
        catch(Exception e){
            Log.v("VM","Exception while sending image on" + "com.zing.zalo" + " "+  e.getMessage());
        }
    }

}
