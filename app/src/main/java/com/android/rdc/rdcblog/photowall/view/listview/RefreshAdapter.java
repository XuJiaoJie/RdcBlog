package com.android.rdc.rdcblog.photowall.view.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.photowall.model.PhotoData;
import com.android.rdc.rdcblog.photowall.presenter.InterClick;
import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Time:2016/8/8 21:45
 * Created By:ThatNight
 */
public class RefreshAdapter extends BaseAdapter implements View.OnClickListener{

	private static final String TAG = "RefreshAdapter";

	private String[] urls;
	private Context mContext;
	private LayoutInflater mInflater;
	private List<PhotoData> photoDatas;
	private RefreshListView refreshListView;

	public Map<Integer,Boolean> isLove;

	private LruCache<String, BitmapDrawable> lruCache;

	private InterClick mListener=new InterClick() {
		@Override
		public void commentClick(View v) {
			Log.d(TAG, "commentClick: ");

		}

		@Override
		public void loveClick(View v) {
			Log.d(TAG, "loveClick: ");
			if(!isLove.get(v.getTag())){
				isLove.put((Integer) v.getTag(),true);
				v.setBackgroundResource(R.drawable.iv_photo_love_yes);

			}else {
				isLove.put((Integer)v.getTag(),false);
				v.setBackgroundResource(R.drawable.iv_photo_love);
			}
		}
	};


	public RefreshAdapter(Context mContext, RefreshListView refreshListView, List<PhotoData> photoDatas) {
		isLove=new HashMap<Integer, Boolean>();
		for(int i=0;i<photoDatas.size();i++){
			isLove.put(i,false);
		}
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
		this.photoDatas = photoDatas;
		this.refreshListView = refreshListView;
		int maxCache = (int) Runtime.getRuntime().maxMemory();
		int caCheSize = maxCache / 4;
		lruCache = new LruCache<String, BitmapDrawable>(caCheSize) {
			@Override
			protected int sizeOf(String key, BitmapDrawable value) {
				return super.sizeOf(key, value);
			}
		};


	}

	@Override
	public int getCount() {
		return photoDatas.size();
	}


	@Override
	public Object getItem(int i) {
		return photoDatas.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			view = mInflater.inflate(R.layout.photo_rv_item, null);
			viewHolder.photo = (ImageView) view.findViewById(R.id.iv_photo);
			viewHolder.detail = (TextView) view.findViewById(R.id.tv_photo_detail);
			viewHolder.time=(TextView)view.findViewById(R.id.tv_photo_time);
			viewHolder.icon=(ImageView)view.findViewById(R.id.iv_photo_icon);
			viewHolder.tvLove=(TextView)view.findViewById(R.id.tv_photo_love);
			viewHolder.tvComment=(TextView)view.findViewById(R.id.tv_photo_comment);
			viewHolder.ibLove=(ImageButton)view.findViewById(R.id.ib_photo_love);
			viewHolder.ibComment=(ImageButton)view.findViewById(R.id.ib_photo_comment);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		PhotoData photoData = photoDatas.get(i);
		/*if (photoData.getmBitmap() != null) {
			viewHolder.imageView.setImageBitmap(photoData.getmBitmap());
			Log.i(TAG, "getView: ");
		}*/
		if(photoData.getmImage() != null&&!"".equals(photoData.getmImage())) {
			/*viewHolder.imageView.setTag(photoData.getmImage());
			if (lruCache.get(photoData.getmImage()) != null) {
				viewHolder.imageView.setImageDrawable(lruCache.get(photoData.getmImage()));
			} else {
				ImageTask imageTask = new ImageTask();
				imageTask.execute(photoData.getmImage());
			}*/
			Picasso.with(mContext).load(photoData.getmImage()).into(viewHolder.photo);
		}

		if (photoData.getmDetail() != null) {
			viewHolder.detail.setText(photoData.getmDetail());
		}

		if(photoData.getmTime()!=null){
			viewHolder.time.setText(photoData.getmTime());
		}

		if(photoData.getmIcon()!=null){
			Picasso.with(mContext).load(photoData.getmIcon()).into(viewHolder.icon);
		}

		viewHolder.ibLove.setOnClickListener(this);
		viewHolder.ibComment.setOnClickListener(this);
		viewHolder.ibLove.setTag(i);
		viewHolder.ibComment.setTag(i);

		if (isLove.get(i)) {
			viewHolder.ibLove.setBackgroundResource(R.drawable.iv_photo_love_yes);
		} else {
			viewHolder.ibLove.setBackgroundResource(R.drawable.iv_photo_love);
		}
		return view;

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.ib_photo_love:
				mListener.loveClick(view);
				break;
			case R.id.ib_photo_comment:
				mListener.commentClick(view);
				break;
		}
	}





	class ViewHolder {
		TextView detail;
		TextView time;
		ImageView photo;
		TextView comment;
		ImageView icon;

		private TextView tvLove,tvComment;
		private ImageButton ibLove,ibComment;

	}



	class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {
		private String mUrl;

		@Override
		protected BitmapDrawable doInBackground(String... strings) {
			mUrl = strings[0];
			Bitmap bitmap = downloadImage(mUrl);
			BitmapDrawable db = new BitmapDrawable(mContext.getResources(), bitmap);
			if (lruCache.get(mUrl) == null) {
				lruCache.put(mUrl, db);
			}
			return db;
		}

		@Override
		protected void onPostExecute(BitmapDrawable bitmapDrawable) {
			ImageView imageView = (ImageView) refreshListView.findViewById(R.id.iv_photo);
			if (imageView != null && bitmapDrawable != null) {
				imageView.setImageDrawable(bitmapDrawable);
			}
		}

		private Bitmap downloadImage(String mUrl) {
			HttpURLConnection conn = null;
			URL url = null;
			Bitmap bitmap = null;
			try {
				url = new URL(mUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setReadTimeout(5000);
				conn.setConnectTimeout(8000);
				BitmapFactory.Options options = new BitmapFactory.Options();
				//options.inSampleSize=2;
				bitmap = BitmapFactory.decodeStream(conn.getInputStream(), null, options);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					conn.disconnect();
				}
			}
			return bitmap;

		}

		private Bitmap imageCrop(Bitmap bitmap) {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int wh = w > h ? h : w;
			int retX = w > h ? (w - h) / 2 : 0;
			int retY = w > h ? 0 : (h - w);
			return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
		}
	}



}
