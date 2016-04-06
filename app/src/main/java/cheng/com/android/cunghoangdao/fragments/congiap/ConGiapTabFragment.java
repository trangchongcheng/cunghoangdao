package cheng.com.android.cunghoangdao.fragments.congiap;

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
public class ConGiapTabFragment extends BaseFragment implements
        BaseAsyntask.OnReturnNewsFeedList, RecyclerCunghoangdaoAdapter.OnClickItemNewsFeed{

    private final String TAG = getClass().getSimpleName();
    private RecyclerView rcvListNews;
    private ProgressBar progressBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerCunghoangdaoAdapter recyclerCunghoangdaoAdapter;

    @Override
    public void init() {
        Log.d(TAG, "init: ");
        rcvListNews = (RecyclerView) getView().findViewById(R.id.fragment_cunghoangdao_rcvNews);
        rcvListNews.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        rcvListNews.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_cunghoangdao_progressbar);

        new BaseAsyntask(getContext(), UrlGetXml.CON_GIAP,this).execute();
    }

    @Override
    public void setEvent() {

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
        Intent intent = new Intent(getActivity(), ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, "12 Con Giáp");
        startActivity(intent);
    }
}
