package cheng.com.android.cunghoangdao.fragments.cunghoangdaotab;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
        BaseAsyntask.OnReturnNewsFeedList, RecyclerCunghoangdaoAdapter.OnClickItemNewsFeed{

    private final String TAG = getClass().getSimpleName();
    private RecyclerView rcvListNews;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCunghoangdaoAdapter recyclerCunghoangdaoAdapter;
    public final static String CONTENT = "content";
    private boolean isPrepared;
    private boolean mUserSeen = false;
    private boolean mViewCreated = false;
    @Override
    public void init() {
        isPrepared = true;

        Log.d(TAG, "init: +");
        rcvListNews = (RecyclerView) getView().findViewById(R.id.fragment_cunghoangdao_rcvNews);
        rcvListNews.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rcvListNews.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_cunghoangdao_progressbar);

        new BaseAsyntask(getContext(), UrlGetXml.CUNG_HOANG_DAO,this).execute();
    }

    @Override
    public void setEvent() {

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "setUserVisibleHint: ");
        if (!mUserSeen && isVisibleToUser) {
            mUserSeen = true;
            onUserFirstSight();
            tryViewCreatedFirstSight();
        }
        onUserVisibleChanged(isVisibleToUser);
    }
    private void tryViewCreatedFirstSight() {
        if (mUserSeen && mViewCreated) {
            onViewCreatedFirstSight(getView());
        }
    }

    /**
     * Called when the new created view is visible to user for the first time.
     */
    protected void onViewCreatedFirstSight(View view) {
        Log.d(TAG, "onViewCreatedFirstSight: ");
    }

    /**
     * Called when the fragment's UI is visible to user for the first time.
     *
     * <p>However, the view may not be created currently if in {@link android.support.v4.view.ViewPager}.
     */
    protected void onUserFirstSight() {
        Log.d(TAG, "onUserFirstSight: ");
    }

    /**
     * Called when the visible state to user has been changed.
     */
    protected void onUserVisibleChanged(boolean visible) {
        Log.d(TAG, "onUserVisibleChanged: ");
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
        recyclerCunghoangdaoAdapter = new RecyclerCunghoangdaoAdapter(getContext(),arr,this);
        rcvListNews.setAdapter(recyclerCunghoangdaoAdapter);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClickListener(View v, int position, String content,String title) {
        putIntent(content,title);
    }
    public void putIntent(String content,String title) {
            Intent intent = new Intent(context, ViewingActivity.class);
            intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
            intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, "Cung Hoàng Đạo");
            startActivity(intent);
    }
}
