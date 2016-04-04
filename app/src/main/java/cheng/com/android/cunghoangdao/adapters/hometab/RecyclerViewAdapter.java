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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private final String TAG = getClass().getSimpleName();
    Context context;
    ArrayList<Cunghoangdao> arrayCunghoangdao;

    @Override
    public int getItemViewType(int position) {
       // return TimelineView.getTimeLineViewType(position, getItemCount());
        return 0;
    }

    public RecyclerViewAdapter(Context context, ArrayList<Cunghoangdao> arrayCunghoangdao) {
        this.context = context;
        this.arrayCunghoangdao = arrayCunghoangdao;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_activity_main_slider, null);
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_activity_main_slider, viewGroup, false);

        RecyclerViewHolder viewHolder = new RecyclerViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int i) {
        Cunghoangdao model = arrayCunghoangdao.get(i);
        recyclerViewHolder.tvName.setText(model.getName());
        Log.d(TAG, model.getImage()+"");
        if(model.getImage() != 0){
            Glide.with(context).load(model.getImage()).into(recyclerViewHolder.imgCunghoangdao);
        }

    }

    @Override
    public int getItemCount() {
        return (null != arrayCunghoangdao ? arrayCunghoangdao.size() : 0);
    }

}
