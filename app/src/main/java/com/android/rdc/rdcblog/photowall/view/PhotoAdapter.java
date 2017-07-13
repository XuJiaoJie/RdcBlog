package com.android.rdc.rdcblog.photowall.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.photowall.presenter.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

	private static final String TAG = "PhotoAdapter";
//	private List<PhotoData> photoDatas;


	private ImageLoader mImageLoader;

	private String urls[];
	private Bitmap mBitmap;

	private Context mContext;

	private OnItemActionListener mOnItemActionListener;

	public PhotoAdapter(Context mContext) {
		Log.d(TAG, "PhotoAdapter: ");
		//photoDatas=new ArrayList<>();
		mImageLoader = ImageLoader.getInstance();
		this.mContext = mContext;
		urls = ConstantData.imageUrls;
		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.photo_empty_photo);
		mHeights = new ArrayList<>();

	}

	private List<Integer> mHeights;

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Log.d(TAG, "onCreateViewHolder: ");
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_rv_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		viewHolder.image = (ImageView) view.findViewById(R.id.iv_photo);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, final int position) {
		Log.d(TAG, "onBindViewHolder: ");
		String url = urls[position];

	/*	BitmapDrawable bitmapDrawable = mImageLoader.getBitmapFromMemoryCache(url);
		if (bitmapDrawable != null) {
			//PhotoData photoData = new PhotoData(bitmapDrawable);
			//photoDatas.add(photoData);
			holder.image.setImageDrawable(bitmapDrawable);
			//holder.textView.setText(photoDatas.get(position).getmTitle());
		} else if (cancelTask(url, holder.image)) {
			TaskDownLoad task = new TaskDownLoad(holder.image);
			AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), mBitmap, task);
			holder.image.setImageDrawable(asyncDrawable);
			task.execute(url);
		}
*/
		//设定随机高度
	/*	if (mHeights.size() <= position) {
			mHeights.add((int) (100 + Math.random() * 350));
		}
		ViewGroup.LayoutParams lp = holder.image.getLayoutParams();
		lp.height = mHeights.get(position);
		holder.image.setLayoutParams(lp);*/
		Picasso.with(mContext)
				.load(url)
				.placeholder(R.drawable.photo_empty_photo)
				.into(holder.image);

		if(mOnItemActionListener!=null){
			holder.image.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnItemActionListener.onItemClickListener(view,position);
				}
			});
		}
	}


	@Override
	public int getItemCount() {
		Log.d(TAG, "getItemCount: ");
		return urls.length;
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ImageView image;

		public ViewHolder(View itemView) {
			super(itemView);

		}
	}

	public interface OnItemActionListener{
		public void onItemClickListener(View view, int pos);
		void onItemLongClickListener(View view, int pos);
	}

	public void setOnItemListener(OnItemActionListener onItemListener){
		this.mOnItemActionListener=onItemListener;
	}


	/*private boolean cancelTask(String url, ImageView imageView) {
		Log.d(TAG, "cancelTask: ");
		TaskDownLoad task = getDownLoadTask(imageView);
		if (task != null) {
			String urls = task.url;
			if (urls == null || !url.equals(urls)) {
				task.cancel(true);
			} else {
				return false;
			}
		}
		return true;

	}
*/

	public static class PhotoWall extends RecyclerView.ViewHolder {


		ImageView imageView;
		TextView textView;


		public PhotoWall(View itemView) {
			super(itemView);
			Log.d(TAG, "PhotoWall: ");
			imageView = (ImageView) itemView.findViewById(R.id.iv_photo);
			textView = (TextView) itemView.findViewById(R.id.tv_photo_detail);

		}
	}


	/*class TaskDownLoad extends AsyncTask<String, Void, BitmapDrawable> {

		String url;
		private WeakReference<ImageView> imageViewWeakReference;
		private ImageView attachImage;

		public TaskDownLoad(ImageView imageView) {
			imageViewWeakReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected BitmapDrawable doInBackground(String... strings) {
			Log.d(TAG, "doInBackground: ");
			url = strings[0];
			Bitmap bitmap = downLoad(url);
			BitmapDrawable bitmapDrawable = new BitmapDrawable(mContext.getResources(), bitmap);
			mImageLoader.addBitmaptoMemory(url, bitmapDrawable);
			return null;
		}


	*//*	private Bitmap downLoad(String url) {
			Log.d(TAG, "downLoad: ");
			Bitmap bitmap = null;
			OkHttpClient okHttpClient = new OkHttpClient();
			Request request = new Request.Builder().url(url).build();
			Response response = null;
			try {
				response = okHttpClient.newCall(request).execute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bitmap = BitmapFactory.decodeStream(response.body().byteStream());
			return bitmap;
		}
*//*

		@Override
		protected void onPostExecute(BitmapDrawable bitmap) {
			Log.d(TAG, "onPostExecute: ");
			super.onPostExecute(bitmap);
			ImageView imageView = getAttachImage();
			if (imageView != null && bitmap != null) {
				imageView.setImageDrawable(bitmap);
			}
		}

		public ImageView getAttachImage() {
			Log.d(TAG, "getAttachImage: ");
			ImageView imageView = imageViewWeakReference.get();
			if (imageView != null) {
				TaskDownLoad task = getDownLoadTask(imageView);
				if (this == task) {
					return imageView;
				}
			}
			return null;
		}
	}


	class AsyncDrawable extends BitmapDrawable {

		private WeakReference<TaskDownLoad> taskDownLoadWeakReference;

		public AsyncDrawable(Resources resource, Bitmap bitmap, TaskDownLoad taskDownLoad) {
			super(resource, bitmap);
			Log.d(TAG, "AsyncDrawable: ");
			taskDownLoadWeakReference = new WeakReference<TaskDownLoad>(taskDownLoad);
		}


		public TaskDownLoad getTaskDrawable() {
			return taskDownLoadWeakReference.get();
		}
	}

	private TaskDownLoad getDownLoadTask(ImageView imageView) {
		Log.d(TAG, "getDownLoadTask: ");
		if (imageView != null) {
			Drawable drawable = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable) {
				return ((AsyncDrawable) drawable).getTaskDrawable();
			}
		}
		return null;
	}*/
}
