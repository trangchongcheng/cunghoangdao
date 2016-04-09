package cheng.com.android.cunghoangdao.fragments.cunghoangdaotab;

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
public class CungHoangDaoTabFragment extends BaseFragment implements
        BaseAsyntask.OnReturnNewsFeedList, RecyclerCunghoangdaoAdapter.OnClickItemNewsFeed
         {

    private final String TAG = getClass().getSimpleName();
    private RecyclerView rcvListNews;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCunghoangdaoAdapter recyclerCunghoangdaoAdapter;
    public final static String CONTENT = "content";
    private boolean isPrepared;
    private boolean mUserSeen = false;
    private boolean mViewCreated = false;
    private Button btnConnect;
    private LinearLayout ll;

    @Override
    public void init() {
        isPrepared = true;
        Log.d(TAG, "init: +");
        rcvListNews = (RecyclerView) getView().findViewById(R.id.fragment_cunghoangdao_rcvNews);
        rcvListNews.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rcvListNews.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_cunghoangdao_progressbar);
        btnConnect = (Button) getView().findViewById(R.id.fragment_cunghoangdao_btnConnect);
        ll = (LinearLayout) getView().findViewById(R.id.fragment_cunghoangdao_ll);
        new BaseAsyntask(getContext(), UrlGetXml.CUNG_HOANG_DAO, this).execute();
    }

    @Override
    public void setEvent() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BaseAsyntask(getContext(), UrlGetXml.CUNG_HOANG_DAO, CungHoangDaoTabFragment.this).execute();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewCreated = false;
        mUserSeen = false;
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
        if(arr!=null){
            ll.setVisibility(View.INVISIBLE);
            recyclerCunghoangdaoAdapter = new RecyclerCunghoangdaoAdapter(getContext(), arr, this);
            rcvListNews.setAdapter(recyclerCunghoangdaoAdapter);
            progressBar.setVisibility(View.INVISIBLE);
        }else {
            ll.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onItemClickListener(View v, int position, String content, String title, String linkImage) {
        putIntent(content, title, linkImage);
    }

    public void putIntent(String content, String title, String linkImage) {
        Intent intent = new Intent(context, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, "Cung Hoàng Đạo");
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        startActivity(intent);
    }


}
