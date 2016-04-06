package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;

/**
 * Created by Welcome on 3/24/2016.
 */
public class BaseAsyntask extends AsyncTask<Void, Void, ArrayList<NewsFeed>> {
    private Context context;
    private String mUrl;

    private OnReturnNewsFeedList mOnReturnNewsFeedList;

    public BaseAsyntask(Context context, String mUrl, OnReturnNewsFeedList mOnReturnNewsFeedList) {
        this.context = context;
        this.mUrl = mUrl;
        this.mOnReturnNewsFeedList = mOnReturnNewsFeedList;
    }

    @Override
    protected ArrayList<NewsFeed> doInBackground(Void... params) {
        ArrayList<NewsFeed> newsFeedList = (ArrayList<NewsFeed>) NewsFeed.parse(mUrl,context);
        return newsFeedList;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsFeed> newsFeed) {
        super.onPostExecute(newsFeed);
        mOnReturnNewsFeedList.OnNewsFeedListSend(newsFeed);
    }

    public interface OnReturnNewsFeedList {
        void OnNewsFeedListSend(ArrayList<NewsFeed> arr);
    }
}
