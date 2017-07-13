package com.android.rdc.rdcblog.bloglist.view.defined;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by PC on 2016/7/27.
 */
public class MenuViewPager extends ViewPager {
	private boolean isCanScroll = false;

	public MenuViewPager(Context context) {
		super(context);
	}

	public MenuViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onInterceptTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return isCanScroll && super.onTouchEvent(ev);
	}

}
