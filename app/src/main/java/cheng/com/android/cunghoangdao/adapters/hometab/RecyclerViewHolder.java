package cheng.com.android.cunghoangdao.adapters.hometab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 1/26/2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public ImageView imgCunghoangdao;
    public RecyclerViewHolder(View view) {
        super(view);
        // Find all views ids

        this.tvName = (TextView) view
                .findViewById(R.id.item_slider_tvName);
        this.imgCunghoangdao = (ImageView) view
                .findViewById(R.id.item_slider_imgSlider);


    }
}
