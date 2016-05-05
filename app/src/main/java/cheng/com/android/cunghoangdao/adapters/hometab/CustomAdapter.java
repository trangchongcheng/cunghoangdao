package cheng.com.android.cunghoangdao.adapters.hometab;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.interfaces.OnItemClickRecyclerView;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.model.Header;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = getClass().getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    //headers
    Header header;
    public OnItemClickRecyclerView onItemClickRecyclerView;
    private Context context;
    public ArrayList<Category> arrCategory;
    private int typeCategory;
    private String categoryName;

    public CustomAdapter(Context context, Header header, ArrayList<Category> arrCategory,
                         int typeCategory, String categoryName, OnItemClickRecyclerView onItemClickRecyclerView) {
        this.header = header;
        this.context = context;
        this.arrCategory = arrCategory;
        this.typeCategory = typeCategory;
        this.categoryName = categoryName;
        this.onItemClickRecyclerView = onItemClickRecyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_fragment_hometab, parent, false);
            return new HeaderFooterViewHolder(v);
        } else if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_cunghoangdao, parent, false);
            return new CategoryViewHolder(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderFooterViewHolder) {
            // HeaderFooterViewHolder VHheader = (HeaderFooterViewHolder)holder;
            ((HeaderFooterViewHolder) holder).tvDescription.setText(header.getDescription() + "");
            ((HeaderFooterViewHolder) holder).imgThumbnail.setImageResource(header.getThumbnail());
            ((HeaderFooterViewHolder) holder).llCunghoangdao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickRecyclerView.onDaybydayitemClickLintener(v, position,header.getCategory(), header.getTitle(),
                            header.getContent());
                }
            });
        } else if (holder instanceof CategoryViewHolder) {
            final Category category = arrCategory.get(position - 1);
            ((CategoryViewHolder) holder).tvTitle.setText(category.getmTitle());
            ((CategoryViewHolder) holder).tvDescription.setText(category.getmDescription());
            ((CategoryViewHolder) holder).tvCategory.setText(category.getCategory());
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
                            category.getmLink(), category.getmImage(), typeCategory, category.getCategory());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        //make sure the adapter knows to look for all our items, headers, and footers
        return arrCategory.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private Category getItem(int position) {
        return arrCategory.get(position);
    }


    //our header/footer RecyclerView.ViewHolder is just a FrameLayout
    public static class HeaderFooterViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgThumbnail;
        public TextView tvTitle, tvDescription;
        public LinearLayout llCunghoangdao;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            llCunghoangdao = (LinearLayout) itemView.findViewById(R.id.fragment_hometab_llCunghoangdao);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.fragment_hometab_imgThumbnail);
            tvTitle = (TextView) itemView.findViewById(R.id.fragment_hometab_tvTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.fragment_hometab_tvDecription);
        }
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView tvPubdate, tvTitle, tvDescription, tvCategory;
        public ImageView imgThumbnail;
        public LinearLayout ll;
        public CardView cardView;

        public CategoryViewHolder(View v) {
            super(v);
            ll = (LinearLayout) v.findViewById(R.id.item_cunghoangdao_ll);
            tvPubdate = (TextView) v.findViewById(R.id.item_cunghoangdao_tvPubdate);
            tvTitle = (TextView) v.findViewById(R.id.item_cunghoangdao_tvTitle);
            tvDescription = (TextView) v.findViewById(R.id.item_cunghoangdao_tvDescription);
            imgThumbnail = (ImageView) v.findViewById(R.id.item_cunghoangdao_imgThumbnail);
            cardView = (CardView) v.findViewById(R.id.item_cunghoangdao_card_view);
            tvCategory = (TextView) v.findViewById(R.id.item_cunghoangdao_tvCategorye);
        }
    }
}