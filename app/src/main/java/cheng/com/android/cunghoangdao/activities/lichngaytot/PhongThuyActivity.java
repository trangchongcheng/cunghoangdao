package cheng.com.android.cunghoangdao.activities.lichngaytot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.category.RecyclerCategoryAdapter;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeTabFragment;
import cheng.com.android.cunghoangdao.interfaces.OnLoadMoreListener;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;

/**
 * Created by Welcome on 3/31/2016.
 */
public class PhongThuyActivity extends BaseActivity implements RecyclerCategoryAdapter.OnClickItemCategory,
        LichngaytotAsyntask.OnReturnJsonObject {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView rcvCategory;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private String mLink, mCategory, mContent;
    protected Handler handler;
    public int page = 0;
    private boolean isFirt = true;

    @Override
    public void setContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_category);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    @Override
    public void init() {
        mLink = getIntent().getStringExtra(HomeTabFragment.URL_CATEGORY);
        mCategory = getIntent().getStringExtra(HomeTabFragment.TITLE_CATEGORY);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(mCategory);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvCategory = (RecyclerView) findViewById(R.id.activity_category_rcvNews);
        rcvCategory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.activity_category_progressbar);
        handler = new Handler();

        new LichngaytotAsyntask(this, UrlGetXml.PHONG_THUY+page,
                ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, this).execute();
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    @Override
    public void setEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void updateListItem(int page) {

    }

    @Override
    public void onItemClickListener(View v, int position, String title, String linkArticle, String linkImage) {
        //new JsoupParseContent(getApplicationContext(),link,this).execute();
        putIntent(title, linkArticle, linkImage);
    }

    public void putIntent(String title, String linkArticle, String linkImage) {
        Intent intent = new Intent(this, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK, linkArticle);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, mCategory);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        startActivity(intent);
    }

    private ArrayList<Category> array = new ArrayList<>();

    public ArrayList<Category> updateAdapter(ArrayList<Category> result) {
        if (!result.isEmpty()) {
            array.addAll(result);
        }
        return array;
    }

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent) {
        updateAdapter(arrContent);
        categoryAdapter = new RecyclerCategoryAdapter(this, array, this, rcvCategory);
        if (isFirt) {
            rcvCategory.setAdapter(categoryAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
        rcvCategory.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.INVISIBLE);
        categoryAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                isFirt = false;
                page = page + 1;
                array.add(null);
                rcvCategory.getAdapter().notifyItemInserted(array.size() - 1);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        array.remove(array.size()-1);
                        //rcvCategory.removeViewAt(rcvCategory.size());
                        //rcvCategory.getAdapter().notifyItemRemoved(array.size() - 1);
                        rcvCategory.getAdapter().notifyItemRemoved(array.size());
                        new LichngaytotAsyntask(getApplicationContext(), UrlGetXml.PHONG_THUY+page,
                                ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, PhongThuyActivity.this).execute();
                        if (!Category.isLast) {
                            categoryAdapter.setLoaded();
                        }
                    }
                }, 2500);

            }
        });
    }

}
