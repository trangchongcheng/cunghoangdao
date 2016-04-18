package cheng.com.android.cunghoangdao.adapters.hometab;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;

import java.util.List;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;

public class LatestPostsAdapter extends ArrayAdapter<NewsFeed> {
    private final LayoutInflater layoutInflater;
    private final String TAG = getClass().getSimpleName();
    private OnItemClickListener mOnItemClickListener;

    public LatestPostsAdapter(Context context, List<NewsFeed> objects, OnItemClickListener mOnItemClickListener) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_timeline_fragment_hometab, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.item_timeline_ll);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_timeline_tvTitle);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.item_timeline_tvDescriptions);
            viewHolder.timeline = (TimelineView) convertView.findViewById(R.id.item_timeline_timeline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        final NewsFeed newsFeed = getItem(position);

        viewHolder.tvTitle.setText(newsFeed.getmTitle());
        viewHolder.tvDescription.setText(Html.fromHtml(newsFeed.getmDescription()).toString());
        viewHolder.timeline.setTimelineType(newsFeed.getType());
        viewHolder.timeline.setTimelineAlignment(newsFeed.getAlignment());

        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, position, newsFeed.getmContent(),
                        newsFeed.getmTitle(),newsFeed.getmImageUrl());
            }
        });

        return convertView;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String content,String title, String linkImage);
    }

    static class ViewHolderItem {
        TextView tvTitle, tvDescription;
        LinearLayout ll;
        TimelineView timeline;
    }




}
