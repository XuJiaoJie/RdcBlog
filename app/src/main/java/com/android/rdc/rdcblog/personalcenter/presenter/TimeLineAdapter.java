package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.HistoryBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/28 0028.
 */
public class TimeLineAdapter extends ArrayAdapter {
    int resourceId;
    List<HistoryBean> dataList;

    public TimeLineAdapter(Context context, int timeLineResource, List dataList){
        super(context, timeLineResource, dataList);
        this.dataList = dataList;
        resourceId = timeLineResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.pc_history_timeline_item, null);
            viewHolder = new ViewHolder();
            viewHolder.day = (TextView) view.findViewById(R.id.pc_show_month);
            viewHolder.month = (TextView) view.findViewById(R.id.pc_show_day);
//            viewHolder.lineIv = (ImageView) view.findViewById(R.id.pc_iv_line);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        String monthGetted = dataList.get(position).getDay();
        String dayGetted = dataList.get(position).getMonth();
        viewHolder.day.setText(monthGetted);
        viewHolder.month.setText(dayGetted);

        if(position == 0){
            viewHolder.day.setVisibility(View.VISIBLE);
            viewHolder.month.setVisibility(View.VISIBLE);
//            viewHolder.lineIv.setVisibility(View.VISIBLE);
        }else {
            if(monthGetted.equals(dataList.get(position-1).getDay()) && dayGetted.equals(dataList.get(position-1).getMonth())){
                viewHolder.day.setVisibility(View.INVISIBLE);
                viewHolder.month.setVisibility(View.INVISIBLE);
//                viewHolder.lineIv.setVisibility(View.VISIBLE);
            }else {
                viewHolder.day.setVisibility(View.VISIBLE);
                viewHolder.month.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }
    class ViewHolder {
        private TextView day;
        private TextView month;
        private ImageView lineIv;
    }
}
