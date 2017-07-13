package com.android.rdc.rdcblog.photowall.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Time:2016/7/31 20:21
 * Created By:ThatNight
 */
public class ItemSpace extends RecyclerView.ItemDecoration{
	private int mSpace;

	public ItemSpace(int space){
		this.mSpace=space;
	}

	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		outRect.left=mSpace-15;
		outRect.right=mSpace-15;
		outRect.bottom=mSpace;
		if(parent.getChildAdapterPosition(view)==0){
			outRect.top=mSpace;
		}
		//super.getItemOffsets(outRect, view, parent, state);
	}
}
