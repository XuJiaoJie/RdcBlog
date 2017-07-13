package com.android.rdc.rdcblog.bloglist.view.defined;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.presenter.OnLoadListener;
import com.android.rdc.rdcblog.bloglist.presenter.OnRefreshListener;

/**
 * Created by PC on 2016/7/28.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    private static final String TAG = "RefreshListView";
    // 区分当前操作是刷新还是加载
    public static final int REFRESH = 0;
    public static final int LOAD = 1;
    // 区分PULL和RELEASE的距离的大小
    private static final int SPACE = 40;
    // 定义header的四种状态和当前状态
    private static final int NONE = 0;
    private static final int PULL = 1;
    private static final int RELEASE = 2;
    private static final int REFRESHING = 3;
    private int state;

    private LayoutInflater inflater;
    private View header;
    private View footer;
    private TextView tip;
    private ImageView arrow;
    private ProgressBar refreshing;
    private TextView more;
    private ProgressBar loading;

    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    private int startY;
    private int firstVisibleItem;
    private int scrollState;
    private int headerContentInitialHeight;
    private int headerContentHeight;

    // 只有在listview第一个item显示的时候（listview滑到了顶部）才进行下拉刷新
    private boolean isRecorded;
    private boolean isLoading;// 判断是否正在加载
    private boolean loadEnable = true;// 开启或者关闭加载更多功能
    private boolean isLoadFull;
    private int pageSize = 10;
    private OnRefreshListener onRefreshListener;
    private OnLoadListener onLoadListener;

    public RefreshListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    // 下拉刷新监听
    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    // 加载更多监听
    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.loadEnable = true;
        this.onLoadListener = onLoadListener;
    }

    public void onRefresh() {
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
    }

    public void onLoad() {
        if (onLoadListener != null) {
            onLoadListener.onLoad();
        }
    }

    /**
     * 用于下拉刷新结束后的回调
     */
    public void onRefreshComplete() {
        state = NONE;
        refreshHeaderViewByState();
    }

    /**
     * 用于加载更多结束后的回调
     */
    public void onLoadComplete(int resultSize) {
        isLoading = false;
        loadfooterViewByState(resultSize);
    }

    //初始化组件
    private void initView(Context context){
        // 设置箭头特效
        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(100);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(100);
        reverseAnimation.setFillAfter(true);

        inflater = LayoutInflater.from(context);
        footer = inflater.inflate(R.layout.bloglist_load_footer,null);
        more = (TextView)footer.findViewById(R.id.bloglist_tview_load);
        loading = (ProgressBar)footer.findViewById(R.id.bloglist_bar_load);

        header = inflater.inflate(R.layout.bloglist_refresh_header,null);
        arrow = (ImageView)header.findViewById(R.id.bloglist_imgview_arrow);
        tip = (TextView)header.findViewById(R.id.bloglist_tview_refresh);
        refreshing = (ProgressBar)header.findViewById(R.id.bloglist_bar_refresh);

        header.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        footer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        // 为listview添加头部和尾部，并进行初始化
        headerContentInitialHeight = header.getPaddingTop();
        measureView(header);
        headerContentHeight = header.getMeasuredHeight();
        topPadding(-headerContentHeight);
        this.addHeaderView(header);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }

    /**
     * 调整header的大小。将其隐藏到顶部或拉伸
     */
    private void topPadding(int topPadding) {
        if(topPadding<80){
            header.setPadding(header.getPaddingLeft(), topPadding,
                    header.getPaddingRight(), header.getPaddingBottom());
        }else {
            header.setPadding(header.getPaddingLeft(), 80,
                    header.getPaddingRight(), header.getPaddingBottom());
        }
        header.invalidate();
    }

    /**
     * 带动画滑动的header回弹
     */
    private void topPaddingAnimation(int topPadding){
        ValueAnimator animator = ValueAnimator.ofInt(header.getPaddingTop(),topPadding);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                header.setPadding(header.getLeft(),(Integer)valueAnimator.getAnimatedValue(),
                        header.getPaddingRight(),header.getPaddingBottom());
            }
        });
        animator.setDuration(400);
        animator.start();
    }

    /**
     * 用来计算header大小的
     */
    private void measureView(View child){
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if(p == null){
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWIdthSpec = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if(lpHeight > 0){
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.EXACTLY);
        }else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWIdthSpec,childHeightSpec);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        scrollState = i;
        ifNeedLoad(absListView, scrollState);
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        firstVisibleItem = i;
    }



    /**
     * 根据listview滑动的状态判断是否需要加载更多
     */
    private void ifNeedLoad(AbsListView view , int scrollState){
        if(!loadEnable){
            return;
        }
        try {
            if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && !isLoading &&
                    view.getLastVisiblePosition()==view.getPositionForView(footer)
                    && !isLoadFull){
                onLoad();
                isLoading = true;
                loadfooterViewByState(10);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 监听触摸事件，解读手势
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(firstVisibleItem == 0){
                    isRecorded = true;
                    startY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if(state == PULL){
                    state = NONE;
                    refreshHeaderViewByState();
                }else if(state == RELEASE){
                    state = REFRESHING;
                    refreshHeaderViewByState();
                    onRefresh();
                }
                isRecorded = false;
                break;
            case MotionEvent.ACTION_MOVE:
                whenMove(ev);
                break;
        }
        return super.onTouchEvent(ev);
    }
    /**
     * 解读手势，刷新header状态
     */
    private void whenMove(MotionEvent ev){
        if(!isRecorded){
            return;
        }
        int tmpY = (int) ev.getY();
        int space = tmpY - startY;
        int topPadding = space - headerContentHeight;
        switch (state){
            case NONE:
                if(space > 0 ){
                    state = PULL;
                    refreshHeaderViewByState();
                }
                break;
            case PULL:
                topPadding(topPadding);
                if(scrollState == SCROLL_STATE_TOUCH_SCROLL && space>headerContentHeight+SPACE){
                    state = RELEASE;
                    refreshHeaderViewByState();
                }
                break;
            case RELEASE:
                topPadding(topPadding);
                if(space > 0 && space< headerContentHeight+SPACE){
                    state = PULL;
                    refreshHeaderViewByState();
                }else if(space <= 0){
                    state = NONE;
                    refreshHeaderViewByState();
                }
                break;
        }
    }
    /**
     * 根据当前状态，调整header
     */
    private void refreshHeaderViewByState(){
        switch (state){
            case NONE:
                topPaddingAnimation(-headerContentHeight);
                tip.setText("下拉可刷新");
                refreshing.setVisibility(View.GONE);
                arrow.clearAnimation();
                arrow.setVisibility(VISIBLE);
                break;
            case PULL:
                arrow.setVisibility(View.VISIBLE);
                tip.setVisibility(View.VISIBLE);
                refreshing.setVisibility(View.GONE);
                tip.setText("下拉可刷新");
                arrow.clearAnimation();
                arrow.startAnimation(reverseAnimation);
                break;
            case RELEASE:
                arrow.setVisibility(View.VISIBLE);
                tip.setVisibility(View.VISIBLE);
                refreshing.setVisibility(View.GONE);
                tip.setText("松开可刷新");
                arrow.clearAnimation();
                arrow.startAnimation(animation);
                break;
            case REFRESHING:
                topPaddingAnimation(30);
                refreshing.setVisibility(View.VISIBLE);
                arrow.clearAnimation();
                arrow.setVisibility(View.GONE);
                tip.setVisibility(View.VISIBLE);
                tip.setText("正在刷新");
                break;
        }
    }

    /**
     * 假定根据请求的条数为8，若请求到了8条。则认为还有数据。
     * 如过结果不足8条，则认为数据已经全部加载
     */
    private void loadfooterViewByState(int resultSize){
        if (isLoading == true){
            more.setText("正在加载");
            loading.setVisibility(View.VISIBLE);
            isLoading = false;
        }else if(isLoading == false && resultSize<8){
            loading.setVisibility(View.GONE);
            more.setText("已加载完毕");
            loadEnable = false;
        }else if (isLoading == false && resultSize==8){
            loading.setVisibility(View.GONE);
            more.setText("上拉加载更多");
            loadEnable = true;
        }
    }
}
