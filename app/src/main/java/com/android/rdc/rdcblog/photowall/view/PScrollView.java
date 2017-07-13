/*
package com.android.rdc.rdcblog.photowall.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.config.ConfigData;
import com.android.rdc.rdcblog.photowall.presenter.ImageLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

*/
/**
 * Time:2016/7/29 19:37
 * Created By:ThatNight
 *//*

public class PScrollView extends ScrollView implements View.OnTouchListener {

	private static final String TAG = "PScrollView";

	private static final int PAGE_SIZE = 15;

	*/
/**
	 * 记录当前第几页
	 *//*

	private int page;

	*/
/**
	 * 每一列的宽度
	 *//*

	private int columnWidth;

	*/
/**
	 * 每一列的高度
	 *//*

	private int firstHeight, twoHeight, thirdHeight;

	*/
/**
	 * 上下文
	 *//*

	private Context mContext;

	*/
/**
	 * 3个LinearLayout
	 *//*

	private LinearLayout layoutFirst, layoutTwo, layoutThree;


	*/
/**
	 * ScrollView的高度
	 *//*

	private int scrollViewHeight;

	*/
/**
	 * 判断是否加载过一次
	 *//*

	private boolean loadOnce;


	*/
/**
	 * ScrollView的第一个子布局
	 *//*

	private View scrollLayout;

	*/
/**
	 * 所有照片的，随时释放
	 *//*

	private List<ImageView> imageViewList = new ArrayList<ImageView>();


	*/
/**
	 * 图片管理工具类
	 *//*

	private ImageLoader imageLoader;


	*/
/**
	 * 记录垂直方向上的滚动距离
	 *//*

	private int lastScrollY = -1;

	*/
/**
	 * 记录正在下载的图片
	 *//*

	private Set<LoadImageTask> taskCollection;


	*/
/**
	 *
	 *//*


	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			PScrollView pScrollView = (PScrollView) msg.obj;
			int scrollY = pScrollView.getScrollY();
			//位置和上次一样
			if (scrollY == lastScrollY) {
				//滑动到最底部
				if (scrollY + scrollViewHeight >= scrollLayout.getHeight() && taskCollection.isEmpty()) {
					pScrollView.loadMorePhoto();
				}
				pScrollView.checkVisibility();
			} else {
				lastScrollY = scrollY;
				Message message = new Message();
				message.obj = pScrollView;
				handler.sendMessageDelayed(message, 5);
				//5毫秒刷新
			}
		}
	};


	public PScrollView(Context context) {
		super(context);

	}

	public PScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		Log.d(TAG, "PScrollView: ");
		imageLoader=ImageLoader.getInstance();
		taskCollection=new HashSet<LoadImageTask>();
		setOnTouchListener(this);
	}



	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (changed && !loadOnce) {
			Log.d(TAG, "onLayout: ");
			scrollViewHeight = getHeight();
			scrollLayout = getChildAt(0);
			layoutFirst = (LinearLayout) findViewById(R.id.ll_photo_first);
			layoutTwo = (LinearLayout) findViewById(R.id.ll_photo_two);
			layoutThree = (LinearLayout) findViewById(R.id.ll_photo_three);
			columnWidth = layoutFirst.getWidth();
			loadOnce = true;
			loadMorePhoto();
		}
	}



	@Override
	public boolean onTouch(View view, MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			Message message = new Message();
			Log.d(TAG, "onTouch: ");
			message.obj = this;
			handler.sendMessageDelayed(message, 5);
		}
		return false;
	}

	private void checkVisibility() {
		for (int i = 0; i < imageViewList.size(); i++) {
			ImageView imageView = imageViewList.get(i);
			int borderTop = (Integer) imageView.getTag(R.string.border_top);
			int borderBottom = (Integer) imageView.getTag(R.string.border_bottom);

			if (borderBottom > getScrollY() && borderTop < getScrollY() + scrollViewHeight) {
				String imageUrl = (String) imageView.getTag(R.string.image_url);
				Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					LoadImageTask task = new LoadImageTask(imageView);
					task.execute(imageUrl);
				}
			} else {
				imageView.setImageResource(R.drawable.photo_empty_photo);
			}


		}
	}

	private void loadMorePhoto() {
		if (hasSDcard()) {
			Log.d(TAG, "loadMorePhoto: ");
			int startIndex = page * PAGE_SIZE;
			int endIndex = page * PAGE_SIZE + PAGE_SIZE;
			if (startIndex < ConstantData.imageUrls.length) {
				if (endIndex > ConstantData.imageUrls.length) {
					endIndex = ConstantData.imageUrls.length;
				}
				for (int i = startIndex; i < endIndex; i++) {
					LoadImageTask task = new LoadImageTask();
					taskCollection.add(task);
					task.execute(ConstantData.imageUrls[i]);
					Log.d(TAG, "LoadTask");
				}
				page++;
				Log.d(TAG, "loadMorePhoto() called with: " + "");
			} else {
				Toast.makeText(mContext, "没照片咯", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(mContext, "未发现SD卡", Toast.LENGTH_SHORT).show();
		}
	}


	*/
/**
	 * 找SD卡
	 *//*

	private boolean hasSDcard() {
		Log.d(TAG, "hasSDcard: ");
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}


	 class LoadImageTask extends AsyncTask<String, Void, Bitmap> {


		*/
/**
		 * 图片的URL
		 *//*

		private String mImageUrl;

		*/
/**
		 * 可重复使用的Imageview
		 *//*

		private ImageView mImageView;


		public LoadImageTask() {

		}

		public LoadImageTask(ImageView imageView) {
			mImageView = imageView;
		}


		@Override
		protected Bitmap doInBackground(String... strings) {
			Log.d(TAG, "doInBackground() called with: " + "strings = [" + strings + "]");
			mImageUrl = strings[0];
			Bitmap bitmap = imageLoader.getBitmapFromMemoryCache(mImageUrl);
			if (bitmap == null) {
				bitmap = loadImage(mImageUrl);
			}
			return bitmap;
		}


		@Override
		protected void onPostExecute(Bitmap bitmap) {
			Log.d(TAG, "onPostExecute: ");
			if (bitmap != null) {
				double ratio = bitmap.getWidth() / (columnWidth * 1.0);
				int scaleHeight = (int) (bitmap.getHeight() / ratio);
				addImage(bitmap, columnWidth	, scaleHeight);
			}
			taskCollection.remove(this);

		}

		private void addImage(Bitmap bitmap, int columns, int scaleHeight) {
			Log.d(TAG, "addImage: ");
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(columns, scaleHeight);
			if (mImageView != null) {
				mImageView.setImageBitmap(bitmap);
			} else {
				ImageView imageView = new ImageView(mContext);
				imageView.setLayoutParams(params);
				imageView.setImageBitmap(bitmap);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				imageView.setPadding(5, 5, 5, 5);
				imageView.setTag(R.string.image_url, mImageUrl);
				findColumnToAdd(imageView, scaleHeight).addView(imageView);
				Log.d(TAG, "addImage: ");
				imageViewList.add(imageView);
			}
		}

		private LinearLayout findColumnToAdd(ImageView imageView, int scaleHeight) {
			Log.d(TAG, "findColumnToAdd: ");
			if (firstHeight <= twoHeight) {
				if (firstHeight <= thirdHeight) {
					imageView.setTag(R.string.border_top, firstHeight);
					firstHeight += scaleHeight;
					imageView.setTag(R.string.border_bottom, firstHeight);
					return layoutFirst;
				}
				imageView.setTag(R.string.border_top, thirdHeight);
				thirdHeight += scaleHeight;
				imageView.setTag(R.string.border_bottom, thirdHeight);
				return layoutThree;
			} else {
				if (twoHeight <= thirdHeight) {
					imageView.setTag(R.string.border_top, twoHeight);
					twoHeight += scaleHeight;
					imageView.setTag(R.string.border_bottom, twoHeight);
					return layoutTwo;
				}
				imageView.setTag(R.string.border_top, thirdHeight);
				thirdHeight += scaleHeight;
				imageView.setTag(R.string.border_bottom, thirdHeight);
				return layoutThree;
			}
		}

		private Bitmap loadImage(String mImageUrl) {
			File imageFile = new File(getImagePath(mImageUrl));
			Log.d(TAG, "loadImage: ");
			if (!imageFile.exists()) {
				downLoadImage(mImageUrl);
			}
			if (mImageUrl != null) {
				Bitmap bitmap = imageLoader.decodeBitmapResource(imageFile.getPath(), columnWidth);
				if (bitmap != null) {
					imageLoader.addBitmaptoMemory(mImageUrl, bitmap);
					return bitmap;
				}
			}
			return null;
		}


		*/
/**
		 * 下载图片
		 *//*

		private void downLoadImage(String mImageUrl) {
			if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

			}
			Log.d(TAG, "downLoadImage: ");
			HttpURLConnection conn=null;
			FileOutputStream fos=null;
			BufferedInputStream bis=null;
			BufferedOutputStream bos=null;
			File imageFile=null;
			try{
				URL url=new URL(mImageUrl);
				conn=(HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(5*1000);
				conn.setReadTimeout(15*1000);
				conn.setDoInput(true);
				conn.setDoOutput(true);
				bis=new BufferedInputStream(conn.getInputStream());
				imageFile=new File(getImagePath(mImageUrl));
				fos=new FileOutputStream(imageFile);
				bos=new BufferedOutputStream(fos);
				byte[] b= new byte[1024];
				int length;
				while((length=bis.read(b))!=-1){
					bos.write(b,0,length);
					bos.flush();
				}
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				try {
					if(bis!=null){
						bis.close();
					}
					if(bos!=null){
						bos.close();
					}
					if (conn!=null){
						conn.disconnect();
					}

				}catch (Exception e){
					e.printStackTrace();
				}
			}
			if(imageFile!=null){
				Bitmap bitmap=imageLoader.decodeBitmapResource(imageFile.getPath(),columnWidth);
				if(bitmap!=null){
					imageLoader.addBitmaptoMemory(mImageUrl,bitmap);
				}
			}



		}

		private String getImagePath(String mImageUrl) {
			Log.d(TAG, "getImagePath: ");
			int lastSlashIndex = mImageUrl.lastIndexOf("/");
			String imageName = mImageUrl.substring(lastSlashIndex + 1);
			Log.d("path", "getImagePath: "+imageName);

			//String imageDir = Environment.getExternalStorageDirectory().getPath()+"/PhotoWall/";
			Log.d("mPath",mContext.getPackageResourcePath()+"\n"+mContext.getFilesDir().getAbsolutePath());
			String imageDir=mContext.getFilesDir().getAbsolutePath()+"/PhotoWall/";
			File file = new File(imageDir);
			if (!file.exists()) {
				file.mkdirs();
				Log.d("path","new file");
			}
			String imagePath = imageDir + imageName;
			Log.d(TAG, "getImagePath: "+imagePath+"\n"+"imageDir"+imageDir);
			return imagePath;
		}
	}

}
*/
