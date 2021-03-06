package cheng.com.android.cunghoangdao.adapters.hometab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 1/26/2016.
 */
public class RecyclerViewIconCungHoangDaoHolder extends RecyclerView.ViewHolder {
    public TextView tvName;
    public ImageView imgCunghoangdao;
    public LinearLayout ll;

    public RecyclerViewIconCungHoangDaoHolder(View view) {
        super(view);
        this.tvName = (TextView) view
                .findViewById(R.id.item_icon_tvName);
        this.imgCunghoangdao = (ImageView) view
                .findViewById(R.id.item_icon_imgSlider);
        this.ll = (LinearLayout) view.findViewById(R.id.item_icon_ll);

    }
}
