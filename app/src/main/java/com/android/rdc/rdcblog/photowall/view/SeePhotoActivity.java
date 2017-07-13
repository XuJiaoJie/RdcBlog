package com.android.rdc.rdcblog.photowall.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.photowall.model.PhotoData;
import com.android.rdc.rdcblog.photowall.presenter.PhotoPagerAdapter;

import java.util.List;

public class SeePhotoActivity extends AppCompatActivity implements View.OnClickListener {

	private ViewPager vpPhoto;
	private PhotoPagerAdapter photoPagerAdapter;
	private ImageButton ibBack;
	private List<PhotoData> photoDatas;
	private RelativeLayout rlTitle;
	private TextView tvTime, tvDate;

	/**
	 * 判断是否全屏
	 */
	private boolean isFull=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_see_photo);
		init();
	}

	private void init() {
		ibBack = (ImageButton) findViewById(R.id.ib_see_photo_back);
		rlTitle = (RelativeLayout) findViewById(R.id.rl_see_photo_title);
		tvTime = (TextView) findViewById(R.id.tv_see_photo_title_time);
		tvDate = (TextView) findViewById(R.id.tv_see_photo_title_date);

		ibBack.setOnClickListener(this);
		int inDex = getIntent().getExtras().getInt("photo_at");
		vpPhoto = (ViewPager) findViewById(R.id.vp_see);

		photoDatas = (List<PhotoData>) getIntent().getSerializableExtra("photoData");

		photoPagerAdapter = new PhotoPagerAdapter(this, photoDatas);
		vpPhoto.setAdapter(photoPagerAdapter);
		vpPhoto.setCurrentItem(inDex - 1);

		PhotoData photoData = photoDatas.get(0);
		if(null!=photoData.getmTime()){
			String[] timeFormat = photoData.getmTime().split(" ");
			tvTime.setText(timeFormat[0]);
			tvDate.setText(timeFormat[1]);
		}


		vpPhoto.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				PhotoData photoData = photoDatas.get(position);
				if(null!=photoData.getmTime()){
					String[] timeFormat = photoData.getmTime().split(" ");
					tvTime.setText(timeFormat[0]);
					tvDate.setText(timeFormat[1]);
				}

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});


		/*vpPhoto.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				int startX= (int) motionEvent.getX();
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					if(!isFull){
						rlTitle.setVisibility(View.GONE);
					}else{
						rlTitle.setVisibility(View.VISIBLE);
					}
					if(Math.abs(motionEvent.getX()-startX)<1){
						isFull=!isFull;
					}
					Log.d("viewPager", "onTouch: ");
				}
				return false;
			}
		});*/


	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.ib_see_photo_back:
				finish();
				break;

		}
	}
}
