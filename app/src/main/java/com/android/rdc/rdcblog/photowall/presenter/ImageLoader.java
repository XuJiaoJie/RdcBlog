package com.android.rdc.rdcblog.photowall.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.util.LruCache;

/**
 * Time:2016/7/29 21:33
 * Created By:ThatNight
 */
public class ImageLoader {


	private static final String TAG = "PScrollView";
	/**
	 * 图片缓存
	 */
	private static LruCache<String, BitmapDrawable> mMemoryCache;

	/**
	 * ImageLoader
	 */
	private static ImageLoader mImageLoader;

	public ImageLoader() {
		Log.d(TAG, "ImageLoader: ");
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 4;
		mMemoryCache = new LruCache<String, BitmapDrawable>(cacheSize) {
			@Override
			protected int sizeOf(String key, BitmapDrawable bitmap) {
				return bitmap.getBitmap().getByteCount();
			}
		};
	}


	public static ImageLoader getInstance() {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader();
		}
		return mImageLoader;
	}



	public BitmapDrawable getBitmapFromMemoryCache(String key) {
		Log.d(TAG, "getBitmapFromMemoryCache: ");
		return mMemoryCache.get(key);
	}

	public Bitmap decodeBitmapResource(String path, int reqWidth) {
		Log.d(TAG, "decodeBitmapResource: ");
		final BitmapFactory.Options options=new BitmapFactory.Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(path,options);
		options.inSampleSize=calculateSize(options,reqWidth);
		options.inJustDecodeBounds=false;
		return BitmapFactory.decodeFile(path,options);


	}


	public static int calculateSize(BitmapFactory.Options options,int reqWidth){
		Log.d(TAG, "calculateSize: ");
		final int width=options.outWidth;
		int size=1;
		if(width>reqWidth){
			final int widRatio=Math.round((float)width/(float)reqWidth);
			size=widRatio;

		}
		return size;
	}

	/**
	 * 将图片存储到LruCache
	 */
	public void addBitmaptoMemory(String key, BitmapDrawable bitmap) {
		Log.d(TAG, "addBitmaptoMemory: ");
		if(getBitmapFromMemoryCache(key)==null){
			mMemoryCache.put(key,bitmap);
		}
	}
}
