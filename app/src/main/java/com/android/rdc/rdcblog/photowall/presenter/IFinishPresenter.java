package com.android.rdc.rdcblog.photowall.presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;

import java.io.File;

/**
 * Time:2016/8/29 22:20
 * Created By:ThatNight
 */
public interface IFinishPresenter {
	Intent saveData(String s, Bitmap mBitmap, String fileName, Handler handler);

	void updateData(String s, File file, Handler handler);
	void jsonUtil(String result, Handler handler);
}
