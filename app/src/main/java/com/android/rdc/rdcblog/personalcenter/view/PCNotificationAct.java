package com.android.rdc.rdcblog.personalcenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.Comments;
import com.android.rdc.rdcblog.personalcenter.model.Msg;
import com.android.rdc.rdcblog.personalcenter.presenter.CommentAdapter;
import com.android.rdc.rdcblog.personalcenter.presenter.MsgAdapter;
import com.android.rdc.rdcblog.personalcenter.presenter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PCNotificationAct extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView notificationBackIv;
    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View commentView, favouriteView, msgView;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    /*评论界面ListView*/
    private ListView commentListView;
    private CommentAdapter commentAdapter;//评论的适配器
    private List<Comments> commentList;//评论的集合
    /*私信界面ListView*/
    private ListView msgListView;
    private MsgAdapter msgAdapter;
    private List<Msg> msgList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pc_notification_main_layout);
		initView();
		//返回键的监听
		notificationBackIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				PCNotificationAct.this.finish();
			}
		});
		initCommentData();
		initMsgData();
		bindTabAndPager();
		//根据进入前点击的选项显示相应的界面
		switchPage();
	}

	private void initView() {
		//加载view
		mInflater = LayoutInflater.from(this);
		commentView = mInflater.inflate(R.layout.pc_comment_layout, null);
		favouriteView = mInflater.inflate(R.layout.pc_favorite_layout, null);
		msgView = mInflater.inflate(R.layout.pc_msg_layout, null);

		notificationBackIv = (ImageView) findViewById(R.id.pc_iv_notification_back);
		mViewPager = (ViewPager) findViewById(R.id.personal_center_vp_view);
		mTabLayout = (TabLayout) findViewById(R.id.pesonal_center_tabs);
		msgListView = (ListView) msgView.findViewById(R.id.msg_list_view);
		commentListView = (ListView) commentView.findViewById(R.id.personal_comment_list_view);

		//添加页卡视图
		mViewList.add(commentView);
		mViewList.add(favouriteView);
		mViewList.add(msgView);
		//添加页卡标题
		mTitleList.add("评论");
		mTitleList.add("赞同");
		mTitleList.add("私信");
	}

	/*初始化私信的测试数据*/
	private void initMsgData() {
		msgList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			Msg msg = new Msg();
			msg.setMsgSenderIconId(R.drawable.pc_iv_head_icon);
			msg.setSenderContent("hello I am Lily， How are you？");
			msg.setSenderNickname("Lily");
			msgList.add(msg);
		}
		/*私信ListView的配置*/
		msgAdapter = new MsgAdapter(this, R.layout.pc_msg_list_item, msgList);
		msgListView.setAdapter(msgAdapter);
	}

	/*初始化评论的测试数据*/
	private void initCommentData() {
		commentList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			Comments comments = new Comments();
			comments.setReviewerIconId(R.drawable.pc_iv_head_icon);
			comments.setReviewerName("Jack");
			comments.setReviewContent("Thank you for your share");
			comments.setReviewTime("7-27");
			commentList.add(comments);
		}
        /* commentListView的配置 */
		commentAdapter = new CommentAdapter(this, R.layout.pc_comment_list_item, commentList);
		commentListView.setAdapter(commentAdapter);
	}

	/*MyViewPager与TabLayout的配置*/
	private void bindTabAndPager() {

        /*初始化私信的测试数据*/
		msgList = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			Msg msg = new Msg();
			msg.setSenderContent("hello I am Lily， How are you？");
			msg.setSenderNickname("Lily");
			msgList.add(msg);
		}

        /*私信ListView的配置*/
		msgListView = (ListView) msgView.findViewById(R.id.msg_list_view);
		msgAdapter = new MsgAdapter(this, R.layout.pc_msg_list_item, msgList);
		msgListView.setAdapter(msgAdapter);

		msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				Intent intent = new Intent(PCNotificationAct.this, ChatActivity.class);
				startActivity(intent);
			}
		});

        /*MyViewPager与TabLayout的配置*/
		MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList, mTitleList);
		mTabLayout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
		mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
		mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
		mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

		mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
		mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
		mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
	}

	//根据进入前点击的选项显示相应的界面
	private void switchPage() {
		Intent intent = getIntent();
		int clickItem = intent.getIntExtra("clickItem", 0);
		switch (clickItem) {
			case 0:
				mViewPager.setCurrentItem(0);
				break;
			case 1:
				mViewPager.setCurrentItem(1);
				break;
			case 2:
				mViewPager.setCurrentItem(2);
				break;
		}
	}

	public static void actionStart(Context context, int data) {
		Intent intent = new Intent(context, PCNotificationAct.class);
		intent.putExtra("clickItem", data);
		context.startActivity(intent);
	}
}