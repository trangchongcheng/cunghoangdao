package cheng.com.android.cunghoangdao.adapters.hometab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;
import cheng.com.android.cunghoangdao.ultils.TimeAgoUtils;

/**
 * Created by Welcome on 3/24/2016.
 */
public class RecyclerNewsFeedAdapter extends RecyclerView.Adapter<RecyclerNewsFeedHolder> {
    private final String TAG = getClass().getSimpleName();
    private Context context;

    private ArrayList<NewsFeed> newsFeedArrayList;
    private OnClickItemNewsFeed onClickItemNewsFeed;

    public RecyclerNewsFeedAdapter(Context context, ArrayList<NewsFeed> newsFeedArrayList, OnClickItemNewsFeed onClickItemNewsFeed) {
        this.context = context;
        this.newsFeedArrayList = newsFeedArrayList;
        this.onClickItemNewsFeed = onClickItemNewsFeed;
    }

    @Override
    public RecyclerNewsFeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_listnewsfeed_fragment_hometab, parent, false);

        RecyclerNewsFeedHolder viewHolder = new RecyclerNewsFeedHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerNewsFeedHolder holder, final int position) {
        final NewsFeed newsFeed = newsFeedArrayList.get(position);

        holder.tvTitle.setText(newsFeed.getmTitle());
        holder.tvDescriptions.setText(Html.fromHtml(newsFeed.getmDescription()).toString());
        //Log.d(TAG, formatTime(newsFeed.getmPubdate()));
        holder.tvPubdate.setText(TimeAgoUtils.getDate(TimeAgoUtils.formatTime(newsFeed.getmPubdate()), true));
        if (!newsFeed.getmImageUrl().equals("")) {
            Glide.with(context).load(newsFeed.getmImageUrl()).into(holder.imgThumbnail);
        }
        holder.item_listnewsfeed_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemNewsFeed.onClickItemNewsFeed(v, position, newsFeed.getmContent(),
                        newsFeed.getmTitle(),newsFeed.getmImageUrl());
            }
        });

    }

    public interface OnClickItemNewsFeed {
        void onClickItemNewsFeed(View view, int position, String content, String title,String linkImage);
    }

    @Override
    public int getItemCount() {
        return (null != newsFeedArrayList ? newsFeedArrayList.size() : 0);
    }




}
