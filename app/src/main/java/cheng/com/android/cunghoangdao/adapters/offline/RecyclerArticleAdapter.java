package cheng.com.android.cunghoangdao.adapters.offline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.Article;

/**
 * Created by Welcome on 4/8/2016.
 */
public class RecyclerArticleAdapter extends RecyclerView.Adapter<RecyclerArticleHolder> {
    private final String TAG = getClass().getSimpleName();
    Context context;
    ArrayList<Article> arrayArticle;
    public OnClickItemListener onClickItemListener;
    public OnLongClickItemListener onLongClickItemListener;

    public RecyclerArticleAdapter(Context context, ArrayList<Article> arrayArticle,
                                  OnClickItemListener onClickItemListener, OnLongClickItemListener onLongClickItemListener) {
        this.context = context;
        this.arrayArticle = arrayArticle;
        this.onClickItemListener = onClickItemListener;
        this.onLongClickItemListener = onLongClickItemListener;
    }

    @Override
    public RecyclerArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_fragment_cunghoangdao, parent, false);

        RecyclerArticleHolder viewHolder = new RecyclerArticleHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerArticleHolder holder, final int position) {
        final Article model = arrayArticle.get(position);
        if(model.getmTitle()!=null){
            holder.tvTitle.setText(model.getmTitle().replace("<h1>","").replace("</h1>",""));
        }
        Log.d(TAG, "onBindViewHolder: "+model.getmCategory());
        if(model.getmCategory()!=null){
            holder.tvCategory.setText(model.getmCategory());
        }
        if (model.getmImage().length > 0) {
            holder.imgThumbnail.setImageBitmap(getPhoto(model.getmImage()));
        }
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onSendContentFinish(model.getmTitle(),model.getmContent(),model.getmCategory());
            }
        });
        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onLongClickItemListener.onSendPositionFinish(position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayArticle ? arrayArticle.size() : 0);
    }

    public interface OnClickItemListener {
        void onSendContentFinish(String title,String content,String category);
    }

    public interface OnLongClickItemListener {
        void onSendPositionFinish(int position);
    }

    public static Bitmap getPhoto(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
