package cheng.com.android.cunghoangdao.activities.offline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.adapters.offline.RecyclerArticleAdapter;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;

/**
 * Created by Welcome on 4/8/2016.
 */
public class OfflineActivity extends BaseActivity implements RecyclerArticleAdapter.OnClickItemListener,
        RecyclerArticleAdapter.OnLongClickItemListener{
    private final String TAG = getClass().getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView rcvArticle;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerArticleAdapter recyclerArticleAdapter;
    private ProgressBar progressBar;
    private ArrayList<Article> arrArticle = new ArrayList<>();
    private DataHandlerSaveContent db;
    private TextView tvMess;
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
        tvMess = (TextView) findViewById(R.id.activity_category_tvMess);
        db = new DataHandlerSaveContent(this);
        arrArticle = db.getAllArticle();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Bài viết yêu thích");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rcvArticle = (RecyclerView) findViewById(R.id.activity_category_rcvNews);
        progressBar = (ProgressBar) findViewById(R.id.activity_category_progressbar);
        rcvArticle.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        rcvArticle.setLayoutManager(mLayoutManager);



    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        Log.d(TAG, "setValue: "+arrArticle.size());
        if(arrArticle.size()<1){
            tvMess.setVisibility(View.VISIBLE);
        }
        recyclerArticleAdapter = new RecyclerArticleAdapter(this, arrArticle,this,this);
        rcvArticle.setAdapter(recyclerArticleAdapter);
        progressBar.setVisibility(View.INVISIBLE);
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

    @Override
    public void onSendContentFinish(String title,String content,String category) {
        Intent intent = new Intent(OfflineActivity.this, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT,content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY,category);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE,title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_OFFLINE,"type_offline");
        startActivity(intent);
    }

    @Override
    public void onSendPositionFinish(int position) {
        db.deleteArticle(arrArticle.get(position).getId());
        arrArticle.remove(position);
        recyclerArticleAdapter.notifyDataSetChanged();
    }
}
