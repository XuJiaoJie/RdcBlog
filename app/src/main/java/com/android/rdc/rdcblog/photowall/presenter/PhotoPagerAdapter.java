package com.android.rdc.rdcblog.photowall.presenter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.photowall.model.PhotoData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Time:2016/8/3 12:06
 * Created By:ThatNight
 */
public class PhotoPagerAdapter extends PagerAdapter {

	private String[] urls;
	private Context mContext;
	private List<PhotoData> photoDatas;
	public PhotoPagerAdapter(Context context, List<PhotoData> photoDatas) {
		mContext=context;
		this.photoDatas=photoDatas;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View view= LayoutInflater.from(mContext).inflate(R.layout.photo_page,null);
		ImageView ivPhoto=(ImageView)view.findViewById(R.id.iv_photo_page);
		TextView tvDetail=(TextView)view.findViewById(R.id.tv_see_photo_info);
		ImageView ivIcon=(ImageView)view.findViewById(R.id.iv_see_photo_icon);

		PhotoData photoData=photoDatas.get(position);
		if(photoData.getmImage()!=null&&!"".equals(photoData.getmImage())){
			Picasso.with(mContext).load(photoData.getmImage()).into(ivPhoto);
		}
		if(photoData.getmDetail()!=null){
			tvDetail.setText(photoData.getmDetail());
		}

		if(photoData.getmIcon()!=null){
			Picasso.with(mContext).load(photoData.getmIcon()).into(ivIcon);
		}

		container.addView(view);
		return view;

	}

	@Override
	public int getCount() {
		return  photoDatas.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}
}
