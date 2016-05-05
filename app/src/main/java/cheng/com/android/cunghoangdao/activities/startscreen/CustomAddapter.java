package cheng.com.android.cunghoangdao.activities.startscreen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.model.Cunghoangdao;

/**
 * Created by Welcome on 1/24/2016.
 */
public class CustomAddapter extends BaseAdapter{
    Context context;
    ArrayList<Cunghoangdao> cunghoangdao;
    LayoutInflater inflater;
    public CustomAddapter(Context context, ArrayList<Cunghoangdao> cunghoangdao) {
        this.context=context;
        this.cunghoangdao=cunghoangdao;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return cunghoangdao.size();
    }

    @Override
    public Object getItem(int position) {
        return cunghoangdao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final viewHolder viewHolder;
        if(convertView==null){
            viewHolder = new viewHolder();
            convertView = inflater.inflate(R.layout.item_fragment_hometab_icon_cunghoangdao,null);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.item_icon_tvName);
            viewHolder.imgCunghoangdao= (ImageView) convertView.findViewById(R.id.item_icon_imgSlider);

            convertView.setTag(viewHolder);
        }else {
            viewHolder=(viewHolder) convertView.getTag();
        }
        final Cunghoangdao chd = cunghoangdao.get(position);
        viewHolder.tvName.setText(chd.getName());
        viewHolder.imgCunghoangdao.setImageResource(chd.getImage());
        return convertView;
    }
    private class viewHolder{
        TextView tvName;
        ImageView imgCunghoangdao;
    }
}
