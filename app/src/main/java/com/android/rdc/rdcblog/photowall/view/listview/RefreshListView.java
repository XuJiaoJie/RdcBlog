package com.android.rdc.rdcblog.photowall.view.listview;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;

/**
 * Time:2016/8/8 20:09
 * Created By:ThatNight
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {

	private final static int REFRESHING = 1;    //正在刷新
	private final static int NOTREFRESH = -1;    //返回
	private final static int DONE = 0;            //刷新完毕
	private final static int TOREFRESH = 2;    //正在下拉


	private LayoutInflater inflate;
	private LinearLayout headerView;
	private ImageView ivArrow;
	private TextView tvTitle;
	private ProgressBar pbHeader;

	/**
	 * 下拉刷新布局的高度
	 */
	private int headerContentHeight;


	private RotateAnimation animation, reverseAnimation;
	private int state;
	private boolean isRrefreshing;

	private boolean isRefreshable;
	private boolean isRecord;
	private boolean isBack;

	/**
	 * 记录滑动起始位置
	 */
	private int startY;
	private int RATIO = 3;
	private OnRefreshListener refreshListener;


	public RefreshListView(Context context) {
		super(context);
		init(context);
	}


	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}


	public void init(Context context) {
		inflate = LayoutInflater.from(context);
		headerView = (LinearLayout) inflate.inflate(R.layout.photo_lv_header, null);
		ivArrow = (ImageView) headerView.findViewById(R.id.photo_iv_arrow);
		tvTitle = (TextView) headerView.findViewById(R.id.photo_tv_refresh);
		pbHeader = (ProgressBar) headerView.findViewById(R.id.photo_pb_pb);

		measureView(headerView);
		headerContentHeight = headerView.getMeasuredHeight();

		headerView.setPadding(0, -1 * headerContentHeight, 0, 0);
		headerView.invalidate();
		addHeaderView(headerView, null, false);

		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;        //已刷新完毕
		isRrefreshing = false;
	}


	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public void onRefreshOk() {
		state = DONE;
		changeHeaderView();
	}

	public void onLvRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}


	private void measureView(View child) {
		ViewGroup.LayoutParams params = child.getLayoutParams();
		if (params == null) {
			params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
			);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, params.width);
		int lpHeight = params.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScrollStateChanged(AbsListView absListView, int i) {

	}

	@Override
	public void onScroll(AbsListView absListView, int i, int i1, int i2) {
		if (i == 0) {
			isRefreshable = true;    //判断是否可以刷新
		} else {
			isRefreshable = false;
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (isRefreshable) {
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (!isRecord) {
						isRecord = true;
						startY = (int) ev.getY();
					}
					break;
				case MotionEvent.ACTION_UP:
					if (state != REFRESHING) {
						if (state == NOTREFRESH) {
							state = DONE;
							changeHeaderView();
						}
						if (state == TOREFRESH) {
							state = REFRESHING;
							changeHeaderView();
							onLvRefresh();
						}
					}
					isRecord = false;
					isBack = false;
					break;
				case MotionEvent.ACTION_MOVE:
					int tempY = (int) ev.getY();
					if (!isRecord) {
						isRecord = true;
						startY = tempY;
					}

					if (state != REFRESHING && isRecord) {
						if (state == TOREFRESH) {
							setSelection(0);
							if (((tempY - startY) / RATIO < headerContentHeight) && (tempY - startY) > 0) {
								state = NOTREFRESH;
								changeHeaderView();
							} else if (tempY - startY <= 0) {
								state = DONE;
								changeHeaderView();
							}
						}
						if (state == NOTREFRESH) {
							setSelection(0);
							if ((tempY - startY) / RATIO >= headerContentHeight) {
								state = TOREFRESH;
								isBack = true;
								changeHeaderView();
							} else if (tempY - startY <= 0) {
								state = DONE;
								changeHeaderView();
							}
						}
						if (state == DONE) {
							if (tempY - startY > 0) {
								state = NOTREFRESH;
								changeHeaderView();
							}
						}
						if (state == NOTREFRESH) {
							headerView.setPadding(0, -1 * headerContentHeight + (tempY - startY) / RATIO, 0, 0);
						}
						if (state == TOREFRESH) {
							headerView.setPadding(0, (tempY - startY) / RATIO - headerContentHeight, 0, 0);
						}
					}

					break;

			}
		}
		return super.onTouchEvent(ev);
	}

	private void changeHeaderView() {
		switch (state) {
			case TOREFRESH:
				ivArrow.setVisibility(View.VISIBLE);
				pbHeader.setVisibility(View.GONE);
				tvTitle.setVisibility(View.VISIBLE);
				ivArrow.clearAnimation();
				ivArrow.startAnimation(animation);
				tvTitle.setText("松开刷新");
				break;
			case NOTREFRESH:
				ivArrow.setVisibility(View.VISIBLE);
				pbHeader.setVisibility(View.GONE);
				tvTitle.setVisibility(View.VISIBLE);
				ivArrow.clearAnimation();
				if (isBack) {
					isBack = false;
					ivArrow.clearAnimation();
					ivArrow.startAnimation(reverseAnimation);
					tvTitle.setText("下拉刷新");
				} else {
					tvTitle.setText("下拉刷新");
				}
				break;
			case REFRESHING:
				headerView.setPadding(0, 0, 0, 0);
				pbHeader.setVisibility(View.VISIBLE);
				ivArrow.clearAnimation();
				ivArrow.setVisibility(View.GONE);
				tvTitle.setText("正在刷新...");
				break;
			case DONE:
				headerView.setPadding(0,-1*headerContentHeight,0,0);
				pbHeader.setVisibility(View.GONE);
				ivArrow.setVisibility(View.VISIBLE);
				tvTitle.setVisibility(View.VISIBLE);
				tvTitle.setText("下拉刷新");
				ivArrow.clearAnimation();
		}
	}


}
