package cheng.com.android.cunghoangdao.model.hometab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alorma.timeline.TimelineView;

import java.util.List;

import cheng.com.android.cunghoangdao.R;

public class EventsAdapter extends ArrayAdapter<Events> {
    private final LayoutInflater layoutInflater;

    public EventsAdapter(Context context, List<Events> objects) {
        super(context, 0, objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_timeline_fragment_hometab, parent, false);
            viewHolder = new ViewHolderItem();
            viewHolder.text = (TextView) convertView.findViewById(R.id.item_timeline_tvTitle);
            viewHolder.timeline = (TimelineView) convertView.findViewById(R.id.item_timeline_timeline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        Events events = getItem(position);

        viewHolder.text.setText(events.getName());
        viewHolder.timeline.setTimelineType(events.getType());
        viewHolder.timeline.setTimelineAlignment(events.getAlignment());

        return convertView;
    }

    static class ViewHolderItem {
        TextView text;
        TimelineView timeline;
    }
}
