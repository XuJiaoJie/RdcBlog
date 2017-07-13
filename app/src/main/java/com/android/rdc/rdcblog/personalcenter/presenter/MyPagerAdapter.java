package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
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
 * Created by Administrator on 2016/7/27 0027.
 */
//ViewPager适配器
public class MyPagerAdapter extends PagerAdapter {
    private List<View> mViewList;
    private List<String> mTitleList;

    public MyPagerAdapter(List<View> mViewList, List<String> mTitleList) {
        this.mViewList = mViewList;
        this.mTitleList = mTitleList;
    }

    @Override
    public int getCount() {
        return mViewList.size();//页卡数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方推荐写法
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));//添加页卡
        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));//删除页卡
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleList.get(position);//页卡标题
    }

    /**
     * Created by Administrator on 2016/7/28 0028.
     */
    public static class TimeLineAdapter extends ArrayAdapter {
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
}