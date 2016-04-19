package cheng.com.android.cunghoangdao.adapters.category;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.interfaces.OnItemClickRecyclerView;
import cheng.com.android.cunghoangdao.interfaces.OnLoadMoreListener;
import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 3/30/2016.
 */
public class RecyclerCategoryAdapter extends RecyclerView.Adapter {
    private final String TAG = getClass().getSimpleName();
    public final static String CONTENT = "content";
    public final static String TITLE = "title";
    public final static int CATEGORY = 0;
    public ArrayList<Category> arrCategory;
    Context context;
    public OnItemClickRecyclerView onItemClickRecyclerView;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;
    private int visibleThreshold = 2;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    public OnLoadMoreListener onLoadMoreListener;
    private int typeCategory;

    public RecyclerCategoryAdapter(Context context, ArrayList<Category> arrCategory,
                                   OnItemClickRecyclerView onItemClickRecyclerView, RecyclerView recyclerView, int typeCategory) {
        this.context = context;
        this.arrCategory = arrCategory;
        this.typeCategory=typeCategory;
        this.onItemClickRecyclerView = onItemClickRecyclerView;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return arrCategory.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_fragment_cunghoangdao, parent, false);
            // create ViewHolder
            vh = new CategoryViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_progress, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof CategoryViewHolder) {

            final Category category = (Category) arrCategory.get(position);

            ((CategoryViewHolder) holder).tvTitle.setText(category.getmTitle());

            if (!category.getmImage().equals("")) {
                Glide.with(context)
                        .load(category.getmImage())
                        .placeholder(R.drawable.ic_waiting)
                        .error(R.drawable.ic_waiting)
                        .centerCrop()
                        .into(((CategoryViewHolder) holder).imgThumbnail);
            }
            ((CategoryViewHolder) holder).ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickRecyclerView.onItemClickListener(v, position, category.getmTitle(),
                            category.getmLink(), category.getmImage(),typeCategory);
                }
            });

        } else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    public void setLoaded() {
        loading = false;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return (null != arrCategory ? arrCategory.size() : 0);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPubdate, tvTitle, tvDescription;
        public ImageView imgThumbnail, imgFavorite;
        public LinearLayout ll;
        public CardView cardView;

        public CategoryViewHolder(View v) {
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
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.item_progressBar);
        }
    }
}
