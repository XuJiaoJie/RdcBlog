package com.android.rdc.rdcblog.photowall.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.view.defined.SlidingMenu;
import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.photowall.model.PhotoData;
import com.android.rdc.rdcblog.photowall.presenter.IGetPhotoPresenter;
import com.android.rdc.rdcblog.photowall.presenter.InterClick;
import com.android.rdc.rdcblog.photowall.view.listview.RefreshAdapter;
import com.android.rdc.rdcblog.photowall.view.listview.RefreshListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PhotoFragment extends Fragment implements View.OnClickListener {

	/**
	 * 点赞
	 */
	private boolean isLove=false;

	private static final String TAG = "PhotoFragment";
	private ImageButton ibMenu, ibAdd;
	private SlidingMenu slidingMenu;
	private TextView tvIsNoPhoto;

	private List<PhotoData> photoDatas;
	private IGetPhotoPresenter getPhotoPresenter;

	private RecyclerView rvPhoto;
//	private List<PhotoData> list;

	//private PScrollView pScrollView;

	private InterClick mInterClick;


	private RefreshListView refreshListView;
	private RefreshAdapter adapter;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 1:
					refreshListView.onRefreshOk();
					break;
				case ConstantData.DOWNLOAD_SUCCESS:
					photoDatas = (List<PhotoData>) msg.obj;
					adapter = new RefreshAdapter(getActivity(), refreshListView, photoDatas);
					refreshListView.setAdapter(adapter);
					break;
				case ConstantData.FAILED_CONNECT:
					Toast.makeText(getActivity(), "网络未连接， 请检查网络设置", Toast.LENGTH_SHORT).show();
					//tvIsNoPhoto.setVisibility(View.VISIBLE);
					break;

			}
		}
	};


	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_photo, null);

		/*rvPhoto=(RecyclerView)view.findViewById(R.id.rv_photo);
		rvPhoto.setHasFixedSize(true);
		rvPhoto.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
		rvPhoto.addItemDecoration(new ItemSpace(30));
		PhotoAdapter photoAdapter=new PhotoAdapter(getActivity());
		rvPhoto.setAdapter(photoAdapter);
		photoAdapter.setOnItemListener(new PhotoAdapter.OnItemActionListener() {
			@Override
			public void onItemClickListener(View view, int pos) {
				Intent intent=new Intent(getActivity(),SeePhotoActivity.class);
				intent.putExtra("photo_at",pos);
				startActivity(intent);
			}

			@Override
			public void onItemLongClickListener(View view, int pos) {

			}
		});*/

		slidingMenu = (SlidingMenu) getActivity().findViewById(R.id.sliding_menu_contol);
		ibMenu = (ImageButton) view.findViewById(R.id.photo_toggle);
		ibAdd = (ImageButton) view.findViewById(R.id.ib_photo_add);
		//tvIsNoPhoto=(TextView)view.findViewById(R.id.tv_isNoPhoto);

		ibAdd.setOnClickListener(this);
		ibMenu.setOnClickListener(this);

		/*下载图片*/
		/*getPhotoPresenter=new GetPhotoPresenterImp();
		getPhotoPresenter.downLoad(ConstantData.URL_PHOTO_DOWNLOAD,handler);*/

		photoDatas = new ArrayList<PhotoData>();

		for (int i = 0; i < 10; i++) {
			PhotoData photoData = new PhotoData();
			photoData.setmImage(ConstantData.imageUrls[i]);
			photoDatas.add(photoData);
		}

		//isNoPhoto();



		refreshListView = (RefreshListView) view.findViewById(R.id.photo_rvl);
		adapter = new RefreshAdapter(getActivity(), refreshListView, photoDatas);

		refreshListView.setAdapter(adapter);
		refreshListView.setOnRefreshListener(new RefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							getPhotoPresenter.downLoad(ConstantData.URL_PHOTO_DOWNLOAD, handler);
						} catch (Exception e) {
							e.printStackTrace();

						}
						handler.sendEmptyMessage(1);
					}
				}).start();
			}
		});

		refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(getActivity(), SeePhotoActivity.class);
				intent.putExtra("photo_at", i);
				intent.putExtra("photoData", (Serializable) photoDatas);
				startActivity(intent);
			}
		});

		return view;
	}

	private boolean isNoPhoto() {
		if (photoDatas == null) {
			tvIsNoPhoto.setVisibility(View.VISIBLE);
			return true;
		} else {
			tvIsNoPhoto.setVisibility(View.GONE);
			return false;
		}
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.photo_toggle:
				slidingMenu.toggle();
				break;
			case R.id.ib_photo_add:
				Intent intent = new Intent(getActivity(), AddPhotoActivity.class);
				startActivityForResult(intent, 1);
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			PhotoData photoData = (PhotoData) data.getParcelableExtra("PhotoData");
			if (photoData != null) {
				Collections.reverse(photoDatas);
				photoDatas.add(photoData);
				Collections.reverse(photoDatas);
				adapter = new RefreshAdapter(getActivity(), refreshListView, photoDatas);
				refreshListView.setAdapter(adapter);
			}
			getPhotoPresenter.downLoad(ConstantData.URL_PHOTO_DOWNLOAD, handler);
		}
	}


}
