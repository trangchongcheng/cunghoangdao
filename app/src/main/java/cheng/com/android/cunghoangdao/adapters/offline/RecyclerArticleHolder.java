package cheng.com.android.cunghoangdao.adapters.offline;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 4/8/2016.
 */
public class RecyclerArticleHolder extends RecyclerView.ViewHolder {
    public TextView tvPubdate, tvTitle, tvDescription,tvCategory;
    public ImageView imgThumbnail, imgFavorite;
    public LinearLayout ll;
    public CardView cardView;

    public RecyclerArticleHolder(View v) {
        super(v);
        ll = (LinearLayout) v.findViewById(R.id.item_cunghoangdao_ll);
        tvCategory = (TextView) v.findViewById(R.id.item_cunghoangdao_tvCategorye);
        tvPubdate = (TextView) v.findViewById(R.id.item_cunghoangdao_tvPubdate);
        tvTitle = (TextView) v.findViewById(R.id.item_cunghoangdao_tvTitle);
        tvDescription = (TextView) v.findViewById(R.id.item_cunghoangdao_tvDescription);
        imgThumbnail = (ImageView) v.findViewById(R.id.item_cunghoangdao_imgThumbnail);
        cardView = (CardView) v.findViewById(R.id.item_cunghoangdao_card_view);

    }
}
