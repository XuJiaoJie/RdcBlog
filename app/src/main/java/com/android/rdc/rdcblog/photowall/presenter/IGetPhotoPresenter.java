package com.android.rdc.rdcblog.photowall.presenter;

import android.os.Handler;

/**
 * Time:2016/8/30 22:00
 * Created By:ThatNight
 */
public interface IGetPhotoPresenter {

	void downLoad(String url, Handler handler);
	void jsonUtil(String result, Handler handler);
}
