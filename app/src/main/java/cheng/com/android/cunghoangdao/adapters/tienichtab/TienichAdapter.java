package cheng.com.android.cunghoangdao.adapters.tienichtab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.interfaces.OnReturnPositionTienich;
import cheng.com.android.cunghoangdao.model.Tienich;

/**
 * Created by Welcome on 4/25/2016.
 */
public class TienichAdapter extends BaseAdapter  {
    private ArrayList<Tienich> arrTienich;
    private Context context;
    public LayoutInflater inflater;
    private OnReturnPositionTienich onReturnPositionTienich;
    public TienichAdapter(ArrayList<Tienich> arrTienich, Context context,OnReturnPositionTienich onReturnPositionTienich) {
        this.arrTienich = arrTienich;
        this.context = context;
        this.onReturnPositionTienich=onReturnPositionTienich;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrTienich.size();
    }

    @Override
    public Object getItem(int position) {
        return arrTienich.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_tienichtab, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.item_tienichtab_tvTitle);
            viewHolder.imgType = (ImageView) convertView.findViewById(R.id.item_tienichtab_imgType);
            viewHolder.tvDescription = (TextView) convertView.findViewById(R.id.item_tienichtab_tvDescription);
            viewHolder.ll = (LinearLayout) convertView.findViewById(R.id.item_tienichtab_ll);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Tienich tienich = arrTienich.get(position);
        viewHolder.imgType.setImageResource(    tienich.getImage());
        viewHolder.tvTitle.setText(tienich.getTitle());
        viewHolder.tvDescription.setText(tienich.getDecription());
        viewHolder.ll.setBackgroundColor(tienich.getColor());
        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReturnPositionTienich.onReturnPositionFinish(position);
            }
        });

        return convertView;
    }
    public class ViewHolder {
        private ImageView imgType;
        private LinearLayout ll;
        private TextView tvTitle, tvDescription;
    }
}
