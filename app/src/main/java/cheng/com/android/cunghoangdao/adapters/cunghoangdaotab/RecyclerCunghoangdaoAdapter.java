package cheng.com.android.cunghoangdao.adapters.cunghoangdaotab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cheng.com.android.cunghoangdao.R;
//import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;

/**
 * Created by Welcome on 3/30/2016.
 */
public class RecyclerCunghoangdaoAdapter extends RecyclerView.Adapter<RecyclerCunghoangdaoAdapter.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    public final static String CONTENT = "content";
    public final static String TITLE = "title";
    public final static String LINK = "link";
    public final static String LINK_IMAGE = "link_image";
    public final static String TYPE_OFFLINE = "type_offline";
    public final static String TYPE_NOTIFY = "type_notify";
    public final static String TYPE_BOI = "type_boi";
    public final static String TYPE_LICH_NGAY_TOT = "type_lichngaytot";
    public final static String CATEGORY = "category";
    public final static String TYPE_VIDEO = "typeVideo";
    Context context;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_fragment_cunghoangdao, parent, false);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
    }

    @Override
    public int getItemCount() {
        return 0;
     //   return (null != arrNewsFeed ? arrNewsFeed.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

}
