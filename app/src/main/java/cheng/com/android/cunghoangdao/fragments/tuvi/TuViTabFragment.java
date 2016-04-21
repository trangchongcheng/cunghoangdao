package cheng.com.android.cunghoangdao.fragments.tuvi;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;
import cheng.com.android.cunghoangdao.services.BaseAsyntask;

/**
 * Created by Welcome on 3/30/2016.
 */
public class TuViTabFragment extends BaseFragment implements
        BaseAsyntask.OnReturnNewsFeedList, RecyclerCunghoangdaoAdapter.OnClickItemNewsFeed {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView rcvListNews;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCunghoangdaoAdapter recyclerCunghoangdaoAdapter;
    private Button btnConnect;
    private LinearLayout ll;

    @Override
    public void init() {
        Log.d(TAG, "init: ");
        rcvListNews = (RecyclerView) getView().findViewById(R.id.fragment_cunghoangdao_rcvNews);
        rcvListNews.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rcvListNews.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_cunghoangdao_progressbar);
        btnConnect = (Button) getView().findViewById(R.id.fragment_cunghoangdao_btnConnect);
        ll = (LinearLayout) getView().findViewById(R.id.fragment_cunghoangdao_ll);
        new BaseAsyntask(getContext(), UrlGetXml.TU_VI, this).execute();
    }

    @Override
    public void setEvent() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BaseAsyntask(getContext(), UrlGetXml.CUNG_HOANG_DAO, TuViTabFragment.this).execute();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void setValue() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cunghoangdaotab;
    }

    @Override
    public void OnNewsFeedListSend(ArrayList<NewsFeed> arr) {
        if (arr != null) {
            ll.setVisibility(View.INVISIBLE);
            recyclerCunghoangdaoAdapter = new RecyclerCunghoangdaoAdapter(getContext(), arr, this);
            rcvListNews.setAdapter(recyclerCunghoangdaoAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClickListener(View v, int position, String content, String title, String linkImage) {
        putIntent(content, title, linkImage);
    }

    public void putIntent(String content, String title, String linkImage) {
        Intent intent = new Intent(context, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, getResources().getString(R.string.tu_vi));
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        startActivity(intent);
    }
}
