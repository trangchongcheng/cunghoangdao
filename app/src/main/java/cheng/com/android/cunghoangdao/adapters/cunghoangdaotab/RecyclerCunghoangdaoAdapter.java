package cheng.com.android.cunghoangdao.adapters.cunghoangdaotab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;
import cheng.com.android.cunghoangdao.ultils.TimeAgoUtils;

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
    public ArrayList<NewsFeed> arrNewsFeed;
    Context context;
    private OnClickItemNewsFeed onClickItemNewsFeed;

    public RecyclerCunghoangdaoAdapter(Context context, ArrayList<NewsFeed> arrNewsFeed, OnClickItemNewsFeed onClickItemNewsFeed) {
        this.context = context;
        this.arrNewsFeed = arrNewsFeed;
        this.onClickItemNewsFeed = onClickItemNewsFeed;
    }

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
        final NewsFeed newsFeed = arrNewsFeed.get(position);
        holder.tvTitle.setText(newsFeed.getmTitle());
        holder.tvDescription.setText(Html.fromHtml(newsFeed.getmDescription()));
        holder.tvPubdate.setText(TimeAgoUtils.getDate(TimeAgoUtils.formatTime(newsFeed.getmPubdate()), true));
        if (!newsFeed.getmImageUrl().equals("")) {
            Glide.with(context).load(newsFeed.getmImageUrl())
                    .placeholder(R.drawable.ic_waiting)
                    .error(R.drawable.ic_waiting)
                    .centerCrop()
                    .into(holder.imgThumbnail);
        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemNewsFeed.onItemClickListener(v, position, newsFeed.getmContent(), newsFeed.getmTitle(), newsFeed.getmImageUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrNewsFeed ? arrNewsFeed.size() : 0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvPubdate, tvTitle, tvDescription;
        public ImageView imgThumbnail, imgFavorite;
        public LinearLayout ll;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            ll = (LinearLayout) v.findViewById(R.id.item_cunghoangdao_ll);
            tvPubdate = (TextView) v.findViewById(R.id.item_cunghoangdao_tvPubdate);
            tvTitle = (TextView) v.findViewById(R.id.item_cunghoangdao_tvTitle);
            tvDescription = (TextView) v.findViewById(R.id.item_cunghoangdao_tvDescription);
            imgThumbnail = (ImageView) v.findViewById(R.id.item_cunghoangdao_imgThumbnail);
            imgFavorite = (ImageView) v.findViewById(R.id.item_cunghoangdao_imgFavorite);
            cardView = (CardView) v.findViewById(R.id.item_cunghoangdao_card_view);

        }
    }

    public interface OnClickItemNewsFeed {
        void onItemClickListener(View v, int position, String content, String title, String link);
    }
}
