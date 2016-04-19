package cheng.com.android.cunghoangdao.adapters.hometab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Welcome on 3/24/2016.
 */
public class RecyclerNewsFeedHolder extends RecyclerView.ViewHolder {
    public CircleImageView imgThumbnail;
    public TextView tvTitle, tvDescriptions, tvPubdate;
    public LinearLayout item_listnewsfeed_ll;

    public RecyclerNewsFeedHolder(View itemView) {
        super(itemView);
        item_listnewsfeed_ll = (LinearLayout) itemView.findViewById(R.id.item_listnewsfeed_ll);
        this.imgThumbnail = (CircleImageView) itemView.findViewById(R.id.item_listnewsfeed_imgThumbnail);
        this.tvTitle = (TextView) itemView.findViewById(R.id.item_listnewsfeed_tvTitle);
        this.tvDescriptions= (TextView) itemView.findViewById(R.id.item_listnewsfeed_tvDescriptions);
        this.tvPubdate= (TextView) itemView.findViewById(R.id.item_listnewsfeed_tvPubdate);
    }
}
