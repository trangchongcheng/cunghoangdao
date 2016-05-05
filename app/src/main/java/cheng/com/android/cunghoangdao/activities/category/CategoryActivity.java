package cheng.com.android.cunghoangdao.activities.category;

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
import cheng.com.android.cunghoangdao.activities.BaseMainActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.category.RecyclerCategoryAdapter;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeTabFragment;
import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 3/31/2016.
 */
public class CategoryActivity extends BaseMainActivity {
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView rcvCategory;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCategoryAdapter categoryAdapter;
    private ProgressBar progressBar;
    private String mLink, mCategory, mContent;
    protected Handler handler;
    public int page = 1;
    private boolean isFirt = true;
    private ArrayList<Category> array;

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
        array = new ArrayList<>();
        rcvCategory = (RecyclerView) findViewById(R.id.activity_category_rcvNews);
        rcvCategory.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rcvCategory.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) findViewById(R.id.activity_category_progressbar);
        handler = new Handler();

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

    public ArrayList<Category> updateAdapter(ArrayList<Category> result) {
        if (!result.isEmpty()) {
            array.addAll(result);
        }
        return array;
    }

    public void updateListItem(int page) {

    }

//    @Override
//    public void onReturnCategoryListFinish(final ArrayList<Category> arrCategory, final int type, String categoryName) {
//        Log.d(TAG, "onReturnCategoryListFinish: " + arrCategory.size());
//        if (isFirt) {
//            updateAdapter(arrCategory);
//            categoryAdapter = new RecyclerCategoryAdapter(this, array, this, rcvCategory, type, categoryName);
//            rcvCategory.setAdapter(categoryAdapter);
//            progressBar.setVisibility(View.INVISIBLE);
//        } else {
//            for (int i = 0; i < arrCategory.size(); i++) {
//                array.add(arrCategory.get(i));
//                Log.d(TAG, "onReturnJsonObject: " + array.get(i).getmTitle());
//                rcvCategory.getAdapter().notifyItemInserted(array.size());
//            }
//        }
//        rcvCategory.getAdapter().notifyDataSetChanged();
//        progressBar.setVisibility(View.INVISIBLE);
//
//        categoryAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                isFirt = false;
//                page = page + 1;
//                array.add(null);
//                rcvCategory.getAdapter().notifyItemInserted(array.size() - 1);
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        array.remove(array.size() - 1);
//                        categoryAdapter.notifyItemRemoved(array.size());
//                        new JsoupParseCategory(getApplicationContext(), mLink + "page/" + page,
//                                CategoryActivity.this, type).execute();
//                        if (!Category.isLast) {
//                            categoryAdapter.setLoaded();
//                        }
//                    }
//                }, 2500);
//
//            }
//        });
//    }

    public void putIntent(String title, String linkArticle, String linkImage) {
        Intent intent = new Intent(this, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK, linkArticle);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, mCategory);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_BOI, "type_boi");
        startActivity(intent);
    }


}
