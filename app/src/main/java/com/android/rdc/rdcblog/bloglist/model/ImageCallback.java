package com.android.rdc.rdcblog.bloglist.model;

import android.graphics.drawable.Drawable;

/**
 * Created by PC on 2016/8/5.
 */

//利用接口回调，更新图片UI
public interface ImageCallback {
    public void imageLoaded(Drawable obj, int id);
}
