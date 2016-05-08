package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.category.RecyclerCategoryAdapter;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeTabFragment;
import cheng.com.android.cunghoangdao.interfaces.OnItemClickRecyclerView;
import cheng.com.android.cunghoangdao.interfaces.OnLoadMoreListener;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;

/**
 * Created by Welcome on 3/31/2016.
 */
public class DanhmucActivity extends BaseMainActivity implements OnItemClickRecyclerView,
        LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView rcvCategory;
    private RecyclerCategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private String mCategory;
    private String mContent;
    protected Handler handler;
    public int page = 0;
    private boolean isFirt = true;
    private String category, toolbarName,typeCategory;
    private LinearLayout ll;
    private Button btnConnect;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_category);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void init() {
        AdView mAdView = (AdView)findViewById(R.id.activity_category_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        assert mAdView != null;
        mAdView.loadAd(adRequest);
        Intent intent = getIntent();
        category = intent.getStringExtra(MainScreenActivity.TYPE_CATEGORY);
        toolbarName = intent.getStringExtra(MainScreenActivity.TOOLBAR_NAME);
        typeCategory = intent.getStringExtra(MainScreenActivity.TYPE_CATEGORY);
        String mLink = getIntent().getStringExtra(HomeTabFragment.URL_CATEGORY);
        mCategory = getIntent().getStringExtra(HomeTabFragment.TITLE_CATEGORY);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        assert mToolbar != null;
        mToolbar.setTitle(toolbarName);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcvCategory = (RecyclerView) findViewById(R.id.activity_category_rcvNews);
        assert rcvCategory != null;
        rcvCategory.setHasFixedSize(true);
        ll = (LinearLayout) findViewById(R.id.activity_category_ll);
        btnConnect = (Button) findViewById(R.id.activity_category_btnConnect);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.activity_category_progressbar);
        handler = new Handler();
        initCategoryURL(page);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    public void initCategoryURL(int page) {
        if (category.equals(getResources().getString(R.string.phong_thuy))) {
            new LichngaytotAsyntask(this, UrlGetXml.PHONG_THUY + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                    getResources().getString(R.string.category_phongthuy), this).execute();
        } else if (category.equals(getResources().getString(R.string.type_cung_hoang_dao))) {
            new LichngaytotAsyntask(this, UrlGetXml.LICH_CUNG_HOANG_DAO + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                    getResources().getString(R.string.category_cunghoangdao), this).execute();
        } else if (category.equals(getResources().getString(R.string.type_tu_vi))) {
            new LichngaytotAsyntask(this, UrlGetXml.LICH_TU_VI + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                    getResources().getString(R.string.category_tuvi), this).execute();
        } else if (category.equals(getResources().getString(R.string.xem_tuong))) {
            new LichngaytotAsyntask(this, UrlGetXml.XEM_TUONG + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                    getResources().getString(R.string.category_tuongso), this).execute();
        } else if (category.equals(getResources().getString(R.string.video_phong_thuy))) {
            new LichngaytotAsyntask(this, UrlGetXml.VIDEO_PHONG_THUY + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 1,
                    getResources().getString(R.string.category_video_phongthuy), this).execute();
        }
        else if (category.equals(getResources().getString(R.string.tam_linh))) {
            new LichngaytotAsyntask(this, UrlGetXml.TAM_LINH + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                    getResources().getString(R.string.menu_item_tam_linh), this).execute();
        }
        else if (category.equals(getResources().getString(R.string.type_tuoi_ti))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_TI + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_suu))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_SUU + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_dan))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_DAN + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_mao))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_MAO + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_thin))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_THIN + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_ty))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_TY + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_ngo))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_NGO + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_mui))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_MUI + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_than))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_THAN + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_dau))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_DAU + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_tuat))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_TUAT + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }else if (category.equals(getResources().getString(R.string.type_tuoi_hoi))) {
            new LichngaytotAsyntask(this, UrlGetXml.TUOI_HOI + page,
                    ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 3, 0,
                    toolbarName, this).execute();
        }
    }

    @Override
    public void setEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCategoryURL(0);
                ll.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClickListener(View v, int position, String title,
                                    String linkAricle, String linkImage, int typecategory, String categoryName) {
        putIntent(title, linkAricle, linkImage, typecategory);
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
            intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
            intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_VIDEO, "type_video");
        }
        assert intent != null;
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK, linkArticle);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(MainScreenActivity.TYPE_CATEGORY,typeCategory);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, toolbarName);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_LICH_NGAY_TOT, "type_lichngaytot");
        startActivity(intent);
    }

    private ArrayList<Category> array = new ArrayList<>();

    public ArrayList<Category> updateAdapter(ArrayList<Category> result) {
        if (result != null) {
            array.addAll(result);
        }
        return array;
    }

    private boolean check;

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        updateAdapter(arrContent);
        if (arrContent != null) {
            if (isFirt) {
                categoryAdapter = new RecyclerCategoryAdapter(this, array, this, rcvCategory, type, categoryName);
                rcvCategory.setAdapter(categoryAdapter);
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                categoryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
            progressBar.setVisibility(View.INVISIBLE);
            isFirt = false;
            page = page + 1;
            check = true;
            categoryAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    if (check) {
                        showProgressDialog();
                    }
                    check = false;
                    Log.d(TAG, "onLoadMore: " + "cheng");
                    array.add(null);
                    rcvCategory.getAdapter().notifyItemInserted(array.size() - 1);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            array.remove(array.size() - 1);
                            rcvCategory.getAdapter().notifyItemRemoved(array.size());
                            initCategoryURL(page);
                            if (!Category.isLast) {
                                categoryAdapter.setLoaded();
                            }
                        }
                    }, 2000);
                }
            });
        }else {
            ll.setVisibility(View.VISIBLE);
        }

    }

    private Dialog dialog = null;

    private void showProgressDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_loading);
        final ProgressBar pgrBar = (ProgressBar) dialog.findViewById(R.id.pgrBar);
        Rect bounds = pgrBar.getIndeterminateDrawable().getBounds();
        pgrBar.setIndeterminateDrawable(getProgressDrawable());
        pgrBar.getIndeterminateDrawable().setBounds(bounds);
        dialog.show();
    }

    private Drawable getProgressDrawable() {
        Drawable progressDrawable = null;
        progressDrawable = new FoldingCirclesDrawable.Builder(this)
                .colors(getProgressDrawableColors())
                .build();


        return progressDrawable;
    }

    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        colors[0] = ContextCompat.getColor(this, R.color.colorPrimary);
        colors[1] = ContextCompat.getColor(this, R.color.pink_pressed);
        colors[2] = ContextCompat.getColor(this, R.color.bright_blue);
        colors[3] = ContextCompat.getColor(this, R.color.navigationBarColor);
        return colors;
    }

}
