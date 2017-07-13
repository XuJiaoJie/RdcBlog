package com.android.rdc.rdcblog.bloglist.view.defined;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.android.rdc.rdcblog.R;

/**
 * Created by xjhaobang
 * Time: 2016/7/25.
 */
public class SlidingMenu extends HorizontalScrollView {

	private int mScreenWidth;
	private int mMenuRightPadding;
	private int mMenuWidth;
	private int mHalfMenuWidth;
	private boolean isOpen;
	private boolean once;
	private ViewGroup menu;
	private ViewGroup content;
	private boolean isFrist = true;

	public SlidingMenu(Context context) {
		this(context, null);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		mScreenWidth = dm.widthPixels;

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.SlidingMenu_rightPadding:
					mMenuRightPadding = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
							TypedValue.COMPLEX_UNIT_DIP, 50f, getResources().getDisplayMetrics()));
					Log.d("mMenuRightPadding", "Padding " + mMenuRightPadding + " ");
					break;
			}
		}
		a.recycle();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!once) {
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			menu = (ViewGroup) wrapper.getChildAt(0);
			content = (ViewGroup) wrapper.getChildAt(1);
			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;
		} else {
			closeMenuForce();
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			//将菜单隐藏
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();
				if (scrollX >= mHalfMenuWidth) {
					this.smoothScrollTo(mMenuWidth, 0);
					isOpen = false;
				} else {
					this.smoothScrollTo(0, 0);
					isOpen = true;
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		float scale = l * 1.0f / mMenuWidth;
		ObjectAnimator.ofFloat(menu, "translationX", l, mMenuWidth * scale).start();
	}

	/**
	 * 打开菜单
	 */
	public void openMenu() {
		if (isOpen)
			return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	/**
	 * 关闭菜单
	 */
	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(mMenuWidth, 0);
			isOpen = false;
		}
	}

	public void closeMenuForce() {
		this.scrollTo(mMenuWidth, 0);
		isOpen = false;
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if (!isOpen) {
			if (ev.getX() <250)
				return super.onInterceptTouchEvent(ev);
		} else if (isOpen) {
			return super.onInterceptTouchEvent(ev);
		}
		return false;
	}


	public void setIsFrist(boolean isFrist) {
		this.isFrist = isFrist;
	}


}
