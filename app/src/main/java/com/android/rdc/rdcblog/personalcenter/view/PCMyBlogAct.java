package com.android.rdc.rdcblog.personalcenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.presenter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/3 0003.
 */
public class PCMyBlogAct extends Activity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private ImageView ivBlogBack;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
    private View mBlogPageView, mSortView, mColumnView;

    private ListView mBlogList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pc_my_blog_main_layout);
        initView();

        bindTabAndPager();
    }
    private void initView(){
        mTabLayout = (TabLayout)findViewById(R.id.pc_tb_my_blog);
        mViewPager = (ViewPager)findViewById(R.id.pc_vp_my_blog);
        ivBlogBack = (ImageView)findViewById(R.id.pc_iv_my_blog_back);
        ivBlogBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PCMyBlogAct.this.finish();
            }
        });

        mInflater = LayoutInflater.from(this);
        mBlogPageView = mInflater.inflate(R.layout.pc_my_blog_list_layout, null);
        mSortView = mInflater.inflate(R.layout.pc_my_blog_list_layout, null);
        mColumnView = mInflater.inflate(R.layout.pc_comment_layout, null);

        mTitleList.add("我的博客");
        mTitleList.add("分类");
        mTitleList.add("专栏");
        mViewList.add(mBlogPageView);
        mViewList.add(mSortView);
        mViewList.add(mColumnView);
    }


    private void bindTabAndPager(){
        MyPagerAdapter mAdapter = new MyPagerAdapter(mViewList, mTitleList);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mAdapter);
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, PCMyBlogAct.class);
        context.startActivity(intent);
    }
}
