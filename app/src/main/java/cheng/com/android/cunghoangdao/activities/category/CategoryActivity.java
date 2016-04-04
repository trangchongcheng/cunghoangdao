package cheng.com.android.cunghoangdao.activities.category;

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
import cheng.com.android.cunghoangdao.adapters.category.RecyclerCategoryAdapter;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeTabFragment;
import cheng.com.android.cunghoangdao.interfaces.OnLoadMoreListener;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.services.JsoupParseAsyntask;

/**
 * Created by Welcome on 3/31/2016.
 */
public class CategoryActivity extends BaseActivity implements JsoupParseAsyntask.OnReturnCategoryList,
        RecyclerCategoryAdapter.OnClickItemCategory {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView rcvCategory;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private String mLink;
    protected Handler handler;
    public int page = 1;
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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        rcvCategory = (RecyclerView) findViewById(R.id.activity_category_rcvNews);
        rcvCategory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.activity_category_progressbar);
        mLink = getIntent().getStringExtra(HomeTabFragment.URL_CATEGORY);
        handler = new Handler();
        new JsoupParseAsyntask(this, mLink, this).execute();
    }

    @Override
    public void setValue(Bundle savedInstanceState) {

    }

    @Override
    public void setEvent() {

    }

    private ArrayList<Category> array = new ArrayList<>();

    public ArrayList<Category> updateAdapter(ArrayList<Category> result) {
        if (!result.isEmpty()) {
            array.addAll(result);
        }
        return array;
    }

    public void updateListItem(int page) {


    }

    @Override
    public void onReturnCategoryListFinish(final ArrayList<Category> arrCategory) {
        updateAdapter(arrCategory);
        categoryAdapter = new RecyclerCategoryAdapter(this, array, this, rcvCategory);
        if (isFirt) {
            rcvCategory.setAdapter(categoryAdapter);
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
                        array.remove(array.size() - 1);
                        rcvCategory.getAdapter().notifyItemRemoved(array.size() - 1);
                        new JsoupParseAsyntask(getApplicationContext(), mLink + "page/" + page,
                                CategoryActivity.this).execute();
                        if (!Category.isLast) {
                            categoryAdapter.setLoaded();
                        }
                    }
                }, 2000);
            }
        });
    }

    @Override
    public void onItemClickListener(View v, int position, String link) {

    }
}
