package com.android.rdc.rdcblog.bloglist.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.presenter.MenuFragmentAdapter;
import com.android.rdc.rdcblog.bloglist.view.defined.SlidingMenu;
import com.android.rdc.rdcblog.bloglist.view.fragment.BlogSortFragment;
import com.android.rdc.rdcblog.login.model.bean.UserBean;
import com.android.rdc.rdcblog.login.view.LoginActivity;
import com.android.rdc.rdcblog.personalcenter.view.PCInfoAct;
import com.android.rdc.rdcblog.personalcenter.view.PCMainFragment;
import com.android.rdc.rdcblog.photowall.view.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/7/25.
 */
public class BlogListActivity extends AppCompatActivity implements View.OnClickListener {
	private LinearLayout llPcIcon;
	private LinearLayout linear_blogList;
	private LinearLayout linear_pictureWall;
	private LinearLayout linear_person;
	private ViewPager viewPager_slidingMenu;
	private List<Fragment> menuFragmentList;
	private MenuFragmentAdapter slidingMenuAdapter;
	private SlidingMenu slidingMenu;
	private BlogSortFragment blogSortFragment;
	private PCMainFragment pcMainFragment;
	private PhotoFragment photoFragment;
	private Button btn_logOff;
	private long exitTime = 0;

	private ImageView ivIcon;
	private TextView tvUserName;

	private ImageView ivBlog, ivPhoto, ivPc;
	private TextView tvBlog, tvPhoto, tvPc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bloglist_slide_menu);
		innit();
		innitListener();
		slidingMenuAdapter = new MenuFragmentAdapter(getSupportFragmentManager(), menuFragmentList);
		viewPager_slidingMenu.setAdapter(slidingMenuAdapter);
	}

	private void innit() {
		llPcIcon=(LinearLayout)findViewById(R.id.bloglist_ll_pc);
		linear_blogList = (LinearLayout) findViewById(R.id.linear_blog);
		linear_person = (LinearLayout) findViewById(R.id.linear_person);
		linear_pictureWall = (LinearLayout) findViewById(R.id.linear_picturewall);
		viewPager_slidingMenu = (ViewPager) findViewById(R.id.sliding_menu);
		slidingMenu = (SlidingMenu) findViewById(R.id.sliding_menu_contol);
		btn_logOff = (Button) findViewById(R.id.bloglist_log_off);

		blogSortFragment = new BlogSortFragment();
		pcMainFragment = new PCMainFragment();
		photoFragment = new PhotoFragment();

		ivIcon=(ImageView)findViewById(R.id.iv_slidemenu_icon);
		tvUserName=(TextView)findViewById(R.id.tv_slidemenu_name);

		UserBean userBean= (UserBean) getIntent().getSerializableExtra("userData");
		/*if(userBean!=null){
			Picasso.with(this).load(ConstantData.URL_ICON+userBean.getmImage()).into(ivIcon);
			tvUserName.setText(userBean.getmNickName());
		}
*/
		tvBlog = (TextView) findViewById(R.id.tv_slidemenu_blog);
		tvPhoto = (TextView) findViewById(R.id.tv_slidemenu_photo);
		tvPc = (TextView) findViewById(R.id.tv_slidemenu_pc);
		ivBlog = (ImageView) findViewById(R.id.iv_slidemenu_blog);
		ivPhoto = (ImageView) findViewById(R.id.iv_slidemenu_photo);
		ivPc = (ImageView) findViewById(R.id.iv_slidemenu_pc);


		menuFragmentList = new ArrayList<>();
		menuFragmentList.add(blogSortFragment);
		menuFragmentList.add(photoFragment);
		menuFragmentList.add(pcMainFragment);

	}

	private void innitListener() {
		llPcIcon.setOnClickListener(this);
		linear_blogList.setOnClickListener(this);
		linear_pictureWall.setOnClickListener(this);
		linear_person.setOnClickListener(this);
		btn_logOff.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.bloglist_ll_pc:
				moveToPc();
				break;
			case R.id.linear_blog:
				moveToLinerBlog();
				break;
			case R.id.linear_picturewall:
				moveToLinearPictureWall();
				break;
			case R.id.linear_person:
				moveToLinearPerson();
				break;
			case R.id.bloglist_log_off:
				startActivity(new Intent(this, LoginActivity.class));
				finish();
				break;
		}
	}

	private void moveToPc() {
		Intent pcIntent=new Intent(BlogListActivity.this,PCInfoAct.class);
		startActivity(pcIntent);
	}

	/**
	 * 跳转博客Fragment
	 */
	private void moveToLinerBlog() {
		linear_blogList.setBackgroundColor(getResources().getColor(R.color.slidingMenuSelected));
		linear_pictureWall.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		linear_person.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		tvBlog.setTextColor(getResources().getColor(R.color.white));
		tvPhoto.setTextColor(getResources().getColor(R.color.slideMenu_text));
		tvPc.setTextColor(getResources().getColor(R.color.slideMenu_text));
		ivBlog.setImageResource(R.drawable.iv_slidemenu_blog_light);
		ivPhoto.setImageResource(R.drawable.iv_slidemenu_photo_black);
		ivPc.setImageResource(R.drawable.iv_slidemenu_pc_black);
		viewPager_slidingMenu.setCurrentItem(0, false);
		slidingMenu.toggle();
	}

	/**
	 * 跳转照片墙Fragment
	 */
	private void moveToLinearPictureWall() {
		linear_blogList.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		linear_pictureWall.setBackgroundColor(getResources().getColor(R.color.slidingMenuSelected));
		linear_person.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		tvBlog.setTextColor(getResources().getColor(R.color.slideMenu_text));
		tvPhoto.setTextColor(getResources().getColor(R.color.white));
		tvPc.setTextColor(getResources().getColor(R.color.slideMenu_text));
		ivBlog.setImageResource(R.drawable.iv_slidemenu_blog_black);
		ivPhoto.setImageResource(R.drawable.iv_slidemenu_photo_light);
		ivPc.setImageResource(R.drawable.iv_slidemenu_pc_black);
		viewPager_slidingMenu.setCurrentItem(1, false);
		slidingMenu.toggle();
	}

	/**
	 * 跳转个人中心Fragment
	 */
	private void moveToLinearPerson() {
		linear_blogList.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		linear_pictureWall.setBackgroundColor(getResources().getColor(R.color.slideingMenuUnselected));
		linear_person.setBackgroundColor(getResources().getColor(R.color.slidingMenuSelected));
		tvBlog.setTextColor(getResources().getColor(R.color.slideMenu_text));
		tvPhoto.setTextColor(getResources().getColor(R.color.slideMenu_text));
		tvPc.setTextColor(getResources().getColor(R.color.white));
		ivBlog.setImageResource(R.drawable.iv_slidemenu_blog_black);
		ivPhoto.setImageResource(R.drawable.iv_slidemenu_photo_black);
		ivPc.setImageResource(R.drawable.iv_slidemenu_pc_light);
		viewPager_slidingMenu.setCurrentItem(2, false);
		slidingMenu.toggle();
	}

	/**
	 * 双击退出程序
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (System.currentTimeMillis() - exitTime > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出哦~", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
