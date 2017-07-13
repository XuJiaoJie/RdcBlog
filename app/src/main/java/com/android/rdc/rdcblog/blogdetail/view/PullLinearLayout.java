package com.android.rdc.rdcblog.blogdetail.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;

/**
 * Created by zjz on 2016/8/5.
 */
public class PullLinearLayout extends LinearLayout {

    private Context mContext;
    private Scroller scroller;

    private View headerView;  //header 布局
    private ImageView refreshIndicatorView;  //箭头图案
    private TextView downTextView;//刷新提示文字
    private ProgressBar bar;//圆形进度条

    private int lastX;
    private int lastY;
    private int refreshTargetTop;//headerView 的高度

    private IRefreshListener iRefreshListener;  //更新事件的接口

    private boolean isDragging = false;// 拉动标记
    private boolean isRefreshEnabled = true; // 是否可刷新标记
    private boolean isRefreshing = false;// 在刷新中标记

    /*  跟更新时间有关的
     Calendar LastRefreshTime;
    private TextView timeTextView;
    private LinearLayout reFreshTimeLayout;//显示上次刷新时间的layout
    private String downTextString;
    private String releaseTextString;
    private Long refreshTime = null;*/

    public PullLinearLayout(Context context) {
        super(context);
        mContext = context;
    }

    public PullLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {
        // TODO Auto-generated method stub
        //     LastRefreshTime = Calendar.getInstance();

        //滑动对象
        scroller = new Scroller(mContext);
        //刷新视图顶端的的view
        headerView = LayoutInflater.from(mContext).inflate(R.layout.blogdetail_comment_pull_header, null);
        //指示器view(箭头)
        refreshIndicatorView = (ImageView) headerView.findViewById(R.id.iv_pullheader);
        //刷新bar
        bar = (ProgressBar) headerView.findViewById(R.id.pb_pullheader);
        //下拉显示text
        downTextView = (TextView) headerView.findViewById(R.id.tv_pullheader);
        //下拉显示时间
        //   timeTextView = (TextView) headerView.findViewById(R.id.refresh_time);
        //   reFreshTimeLayout=(LinearLayout)headerView.findViewById(R.id.refresh_time_layout);


        //让headerView.getMeasuredHeight()正常显示高度
        measureView(headerView);
        refreshTargetTop = -headerView.getMeasuredHeight();
        Log.d("haha", headerView.getMeasuredHeight() + "aaaa");
        Log.d("haha", headerView.getHeight() + "aaaa");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, -refreshTargetTop);

        //让headerView往上挪，隐藏headerView
        lp.topMargin = refreshTargetTop;
        //添加headerView这个View，lp为布局的属性
        addView(headerView, lp);
    }


    /**
     * 测量header的高度
     */
    private void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeigth = p.height;
        if (tempHeigth > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeigth, MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }

    /**
     * 该方法一般和ontouchEvent 一起用
     * (non-Javadoc)
     *
     * @see android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        Log.d("haha", "onInterceptTouchEvent");
        // TODO Auto-generated method stub
        int action = e.getAction();
        int y = (int) e.getRawY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d("haha", "onInterceptTouchEvent    down");
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                Log.d("haha", "onInterceptTouchEvent     move");
                //y移动坐标
                int m = y - lastY;

                //记录下此刻y坐标
                this.lastY = y;
                if (m > 6 && canScroll()) {
                    return true;//返回true，在这个ViewGroup截获手势，执行该ViewGroup的onTouchEvent（）
                }
                if (m < -6 && isRefreshing) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("haha", "onInterceptTouchEvent   up");
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("haha", "onTouchEvent");
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("haha", "onTouchEvent   down");
                //记录下y坐标
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("haha", "onTouchEvent   move");
                //y移动坐标
                int m = y - lastY;
                if (((m < 6) && (m > -1)) || (!isDragging)) {
                    //设置时间  setLastRefreshTimeText();
                    doMovement(m);
                } else if (m < -6) {
                    doMovement(m);
                }
                //记录下此刻y坐标
                this.lastY = y;
                break;
            case MotionEvent.ACTION_UP:
                Log.d("haha", "onTouchEvent   up");
                fling();
                break;
        }
        return true;
    }


    /**
     * up事件处理
     */
    private void fling() {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lp = (LayoutParams) headerView.getLayoutParams();

        if (lp.topMargin > 0) {//拉到了触发可刷新事件
            refresh();
        } else {
            returnInitState();//回到初始位置
        }
    }


    /**
     * 回到初始位置
     */
    private void returnInitState() {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headerView.getLayoutParams();
        int i = lp.topMargin;
        // i 为开始的y坐标 ， refreshTargetTop 为最后的位置
        Log.d("haha", "refreshTargetTop=" + refreshTargetTop);
        scroller.startScroll(0, i, 0, refreshTargetTop);
        invalidate();
    }

    //更新
    private void refresh() {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headerView.getLayoutParams();
        int i = lp.topMargin;
//        reFreshTimeLayout.setVisibility(View.GONE);
        refreshIndicatorView.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        //  timeTextView.setVisibility(View.GONE);
        downTextView.setText("正在刷新");
        scroller.startScroll(0, i, 0, 0 - i); //滑到刚刚好可以看见完整的headerView

        invalidate();//重绘，因为上面想用代码使View移动，不重绘则无法移动View
        if (iRefreshListener != null) {
            iRefreshListener.onRefresh(this);
            isRefreshing = true;
        }
    }

    /**
     *
     */
    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if (scroller.computeScrollOffset()) {
            int i = this.scroller.getCurrY();
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headerView.getLayoutParams();
            int k = Math.max(i, refreshTargetTop);
            lp.topMargin = k;
            this.headerView.setLayoutParams(lp);
            this.headerView.invalidate();
            invalidate();
        }
    }

    /**
     * 下拉move事件处理
     *
     * @param moveY
     */
    private void doMovement(int moveY) {
        // TODO Auto-generated method stub
        LinearLayout.LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
        if (moveY > 0) {
            //获取view的上边距
            float f1 = lp.topMargin;
            float f2 = moveY * 0.4F;
            int i = (int) (f1 + f2);
            //修改上边距
            lp.topMargin = i;
            //修改后刷新
            headerView.setLayoutParams(lp);
            headerView.invalidate();
            invalidate();
        } else {
            float f1 = lp.topMargin;
            int i = (int) (f1 + moveY * 0.9F);
            Log.i("aa", String.valueOf(i));
            if (i >= refreshTargetTop) {
                lp.topMargin = i;
                //修改后刷新
                headerView.setLayoutParams(lp);
                headerView.invalidate();
                invalidate();
            } else {

            }
        }

    /*         timeTextView.setVisibility(View.VISIBLE);
       if(refreshTime!= null){
       setRefreshTime(refreshTime);
        }*/
        downTextView.setVisibility(View.VISIBLE);
        refreshIndicatorView.setVisibility(View.VISIBLE);
        bar.setVisibility(View.GONE);
        if (lp.topMargin > 0) {
            downTextView.setText("释放可刷新");
            refreshIndicatorView.setImageResource(R.drawable.blogdetail_iv_up);
        } else {
            downTextView.setText("下拉可刷新");
            refreshIndicatorView.setImageResource(R.drawable.blogdetail_iv_down);
        }

    }

    public void setRefreshEnabled(boolean b) {
        this.isRefreshEnabled = b;
    }


    /**
     * 被Activity调用
     * 结束刷新事件
     */
    public void finishRefresh() {

        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) this.headerView.getLayoutParams();
        int i = lp.topMargin;
        bar.setVisibility(View.GONE);
        refreshIndicatorView.setVisibility(View.VISIBLE);
        //   timeTextView.setVisibility(View.VISIBLE);
        scroller.startScroll(0, i, 0, refreshTargetTop);
        invalidate();
        isRefreshing = false;
        //    LastRefreshTime = Calendar.getInstance();
    }


    private boolean canScroll() {
        // TODO Auto-generated method stub
        View childView;
        if (getChildCount() > 1) {
            childView = this.getChildAt(1);
            if (childView instanceof ListView) {
                int top = ((ListView) childView).getChildAt(0).getTop();
                int pad = ((ListView) childView).getListPaddingTop();
                if ((Math.abs(top - pad)) < 3 &&
                        ((ListView) childView).getFirstVisiblePosition() == 0) {
                    return true;
                } else {
                    return false;
                }
            } else if (childView instanceof ScrollView) {
                if (((ScrollView) childView).getScrollY() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    /**
     * 给Activity用的，这样才可以把Activity实现的方法传进来
     * 让这里的IRefreshListener 的实例被Ativity的IRefreshListener实例赋值
     */
    public void setIRefreshListener(IRefreshListener listener) {
        this.iRefreshListener = listener;
    }

    /**
     * 刷新监听接口
     *
     * @author Nono
     */
    public interface IRefreshListener {
        void onRefresh(PullLinearLayout view);
    }

    /**
     * 设置上次刷新时间
     * @param
     */
/*    private void setLastRefreshTimeText() {
        // TODO Auto-generated method stub
        reFreshTimeLayout.setVisibility(View.VISIBLE);
        Calendar NowTime=Calendar.getInstance();
        long l=NowTime.getTimeInMillis()-LastRefreshTime.getTimeInMillis();
        int days=new Long(l/(1000*60*60*24)).intValue();
        int hour=new Long(l/(1000*60*60)).intValue();
        int min=new Long(l/(1000*60)).intValue();
        if(days!=0)
        {
            timeTextView.setText(days+"天");
        }
        else  if(hour!=0)
        {
            timeTextView.setText(hour+"小时");
        }
        else if(min!=0)
        {
            timeTextView.setText(min+"分钟");
        }


    timeTextView.setText(time);
      }*/


}
