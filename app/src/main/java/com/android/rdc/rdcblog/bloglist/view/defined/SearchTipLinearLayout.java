package com.android.rdc.rdcblog.bloglist.view.defined;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.presenter.OnClickHistory;
import com.android.rdc.rdcblog.bloglist.presenter.OnClickSuggest;

import java.util.List;

/**
 * Created by PC on 2016/7/30.
 */
public class SearchTipLinearLayout extends LinearLayout{
    private Context context;


    public SearchTipLinearLayout(Context context) {
        super(context);
        this.context = context;
        setOrientation(VERTICAL);  //设置方向
    }

    public SearchTipLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOrientation(VERTICAL);  //设置方向
    }

    public SearchTipLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOrientation(VERTICAL);  //设置方向
    }
    /**
     * 点击系统推荐外部接口调用
     */
    public  void initView(String items[] , final OnClickSuggest onClickSuggest){
        int temp = 0;   //行数
        int length = 0;//一行加载item 的宽度
        LinearLayout layout = null;
        LayoutParams layoutLp = null;
        boolean isNewLine = true;//是否换行
        boolean hasData = false;  //是否有数据，防止出现空指针

        int screenWidth = getScreenWidth();//屏幕的宽度
        int size = items.length;
            for(int i = 0;i<size;i++) {          //遍历items
                hasData = true ;
                if(temp<3){
                if (isNewLine) {//是否开启新的一行
                    layout = new LinearLayout(context);
                    layout.setOrientation(HORIZONTAL);
                    layoutLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutLp.topMargin = 10;
                }
                View view = LayoutInflater.from(context).inflate(R.layout.bloglist_search_tv_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bloglist_search_tv);
                itemView.setText(items[i]);

                //给每个item设置点击事件
                final int j = i;
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != onClickSuggest){
                            onClickSuggest.onClickSuggestItem(j);
                        }
                    }
                });

                //设置item的参数
                LayoutParams itemLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemLp.leftMargin = 10;

                //得到当前行的长度
                length += 10 + getViewWidth(itemView);
                if (length > screenWidth) {         //当前行的长度大于屏幕宽度则换行
                    length = 0;
                    addView(layout, layoutLp);
                    isNewLine = true;
                    i--;
                    temp++;
                }else {                //否则添加到当前行
                    isNewLine = false;
                    layout.addView(view,itemLp);
                }
            }
        }
        if (temp!=3 && hasData ){
            addView(layout,layoutLp);
        }
    }

    /**
     *点击历史搜索记录外部接口调用
     */
    public void initView(List<String> items ,final OnClickHistory onClickHistory){
        int temp = 0;    //行数
        int length = 0;//一行加载item 的宽度
        LinearLayout layout = null;
        LayoutParams layoutLp = null;
        boolean isNewLine = true;//是否换行
        boolean hasData = false;  //是否有数据，防止出现空指针

        int screenWidth = getScreenWidth();//屏幕的宽度
        int size = items.size();
        for(int i = 0;i<size;i++) {          //遍历items
            hasData = true ;
            if(temp<3){
                if (isNewLine) {//是否开启新的一行
                    layout = new LinearLayout(context);
                    layout.setOrientation(HORIZONTAL);
                    layoutLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutLp.topMargin = 10;
                }
                View view = LayoutInflater.from(context).inflate(R.layout.bloglist_search_tv_item, null);
                TextView itemView = (TextView) view.findViewById(R.id.bloglist_search_tv);
                itemView.setText(items.get(i));

                //给每个item设置点击事件
                final int j = i;
                itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != onClickHistory){
                            onClickHistory.onClickHistoryItem(j);
                        }
                    }
                });

                //设置item的参数
                LayoutParams itemLp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                itemLp.leftMargin = 10;

                //得到当前行的长度
                length += 10 + getViewWidth(itemView);
                if (length > screenWidth) {         //当前行的长度大于屏幕宽度则换行
                    length = 0;
                    addView(layout, layoutLp);
                    isNewLine = true;
                    i--;
                    temp++;
                }else {                //否则添加到当前行
                    isNewLine = false;
                    layout.addView(view,itemLp);
                }
            }
        }
        if (temp!=3 && hasData ){
            addView(layout,layoutLp);
        }
    }

    /**
     * 得到手机屏幕的宽度
     */
    private int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 得到view控件的宽度
     */
    private int getViewWidth(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w,h);
        return view.getMeasuredWidth();
    }

}
