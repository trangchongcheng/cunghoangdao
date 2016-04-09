package cheng.com.android.cunghoangdao.adapters.hometab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.Cunghoangdao;

/**
 * Created by Welcome on 1/26/2016.
 */
public class RecyclerViewIconCungHoangDaoAdapter extends RecyclerView.Adapter<RecyclerViewIconCungHoangDaoHolder> {
    private final String TAG = getClass().getSimpleName();
    Context context;
    ArrayList<Cunghoangdao> arrayCunghoangdao;
    public OnItemClickIconCungHoangDao onItemClickIconCungHoangDao;

    @Override
    public int getItemViewType(int position) {
        // return TimelineView.getTimeLineViewType(position, getItemCount());
        return 0;
    }

    public RecyclerViewIconCungHoangDaoAdapter(Context context, ArrayList<Cunghoangdao> arrayCunghoangdao,
    OnItemClickIconCungHoangDao onItemClickIconCungHoangDao) {
        this.context = context;
        this.arrayCunghoangdao = arrayCunghoangdao;
        this.onItemClickIconCungHoangDao = onItemClickIconCungHoangDao;
    }

    @Override
    public RecyclerViewIconCungHoangDaoHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_fragment_hometab_icon_cunghoangdao, null);
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_fragment_hometab_icon_cunghoangdao, viewGroup, false);

        RecyclerViewIconCungHoangDaoHolder viewHolder = new RecyclerViewIconCungHoangDaoHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewIconCungHoangDaoHolder recyclerViewHolder, final int position) {
        final Cunghoangdao model = arrayCunghoangdao.get(position);
        recyclerViewHolder.tvName.setText(model.getName());
        Log.d(TAG, model.getImage() + "");
        if (model.getImage() != 0) {
            Glide.with(context).load(model.getImage()).into(recyclerViewHolder.imgCunghoangdao);
        }
        recyclerViewHolder.tvDate.setText(model.getDate());
        recyclerViewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickIconCungHoangDao.onItemClickIconCungHoangDao(v,position,model.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayCunghoangdao ? arrayCunghoangdao.size() : 0);
    }

    public interface OnItemClickIconCungHoangDao{
        void onItemClickIconCungHoangDao(View v,int position, String category);
    }

}
