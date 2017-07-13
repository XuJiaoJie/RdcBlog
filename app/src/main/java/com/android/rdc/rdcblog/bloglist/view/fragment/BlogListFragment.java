package com.android.rdc.rdcblog.bloglist.view.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.presenter.MenuFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/7/26.
 */
//public class BlogListFragment extends Fragment implements View.OnClickListener ,ViewPager.OnPageChangeListener{
//
//    private static final String TAG = "BlogListFragment";
//
//    private View view;
//    private List<Fragment> blogFragmentList;
//    private MenuFragmentAdapter menuFragmentAdapter;
//    private ViewPager viewPagerBlog;
//    private Button newBlog ,frontBlog ,androidBlog ,backstageBlog , cBlog;
//    private ImageButton backMenu;
//    private View tabLine;
//    private PersonFragment personFragment1,personFragment2,personFragment3;
//    private PictureWallFragment pictureWallFragment1,pictureWallFragment2;
//    private SlidingMenu slidingMenu;
//    private int currentIndex;
//    private int screenWidth;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.activity_blog_list,null);
//        findById();
//        setOnclick();
//        init();
//        inttTabLineWidth();
//        return view;
//    }
//
//    private void findById(){
//        viewPagerBlog = (ViewPager)view.findViewById(R.id.viewPager_selected_blog);
//        newBlog = (Button)view.findViewById(R.id.btm_new_blog);
//        frontBlog = (Button)view.findViewById(R.id.btm_front_blog);
//        androidBlog = (Button)view.findViewById(R.id.btm_android_blog);
//        backstageBlog = (Button)view.findViewById(R.id.btm_backstage_blog);
//        cBlog = (Button)view.findViewById(R.id.btm_C_blog);
//        tabLine = view.findViewById(R.id.view_selected_line);
//        backMenu = (ImageButton)view.findViewById(R.id.imbtm_back_menu);
//        slidingMenu = (SlidingMenu)getActivity().findViewById(R.id.sliding_menu_contol);
//    }
//
//    private void setOnclick(){
//        newBlog.setOnClickListener(this);
//        frontBlog.setOnClickListener(this);
//        androidBlog.setOnClickListener(this);
//        backstageBlog.setOnClickListener(this);
//        cBlog.setOnClickListener(this);
//        backMenu.setOnClickListener(this);
//        viewPagerBlog.addOnPageChangeListener(this);
//    }
//
//    private void init(){
//        blogFragmentList = new ArrayList<>();
//        personFragment1 = new PersonFragment();
//        personFragment2 = new PersonFragment();
//        personFragment3 = new PersonFragment();
//        pictureWallFragment1 = new PictureWallFragment();
//        pictureWallFragment2 = new PictureWallFragment();
//        blogFragmentList.add(personFragment1);
//        blogFragmentList.add(pictureWallFragment1);
//        blogFragmentList.add(personFragment2);
//        blogFragmentList.add(pictureWallFragment2);
//        blogFragmentList.add(personFragment3);
//        menuFragmentAdapter = new MenuFragmentAdapter(this.getActivity().getSupportFragmentManager(),blogFragmentList);
//        viewPagerBlog.setAdapter(menuFragmentAdapter);
//        viewPagerBlog.setCurrentItem(0);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.imbtm_back_menu:
//                slidingMenu.toggle();
//            case R.id.btm_new_blog:
//                viewPagerBlog.setCurrentItem(0);
//                break;
//            case R.id.btm_front_blog:
//                viewPagerBlog.setCurrentItem(1);
//                break;
//            case R.id.btm_android_blog:
//                viewPagerBlog.setCurrentItem(2);
//                break;
//            case R.id.btm_backstage_blog:
//                viewPagerBlog.setCurrentItem(3);
//                break;
//            case R.id.btm_C_blog:
//                viewPagerBlog.setCurrentItem(4);
//        }
//    }
//
//    @Override
//    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tabLine.getLayoutParams();
//        double avgScreenWidth = screenWidth * 1.0/5;
//        if(currentIndex==0&&position==0){
//            lp.leftMargin = (int)(positionOffset * avgScreenWidth);
//        }else if(currentIndex==1&&position==0){
//            lp.leftMargin = (int)(-(1-positionOffset) * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==1&&position==1){
//            lp.leftMargin = (int)(positionOffset * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==2&&position==1){
//            lp.leftMargin = (int)(-(1-positionOffset) * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==2&&position==2){
//            lp.leftMargin = (int)(positionOffset * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==3&&position==2){
//            lp.leftMargin = (int)(-(1-positionOffset) * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==3&&position==3){
//            lp.leftMargin = (int)(positionOffset * avgScreenWidth + currentIndex * avgScreenWidth);
//        }else if(currentIndex==4&&position==3){
//            lp.leftMargin = (int)(-(1-positionOffset) * avgScreenWidth + currentIndex * avgScreenWidth);
//        }
//        tabLine.setLayoutParams(lp);
//    }
//
//    @Override
//    public void onPageSelected(int position) {
//        reSetButton();
//        switch (position){
//            case 0:
//                newBlog.setTextColor(Color.RED);
//                break;
//            case 1:
//                frontBlog.setTextColor(Color.RED);
//                break;
//            case 2:
//                androidBlog.setTextColor(Color.RED);
//                break;
//            case 3:
//                backstageBlog.setTextColor(Color.RED);
//                break;
//            case 4:
//                cBlog.setTextColor(Color.RED);
//                break;
//        }
//        currentIndex = position;
//    }
//
//    @Override
//    public void onPageScrollStateChanged(int state) {
//
//    }
//
//    /**
//     * 设置滑动条的宽度为屏幕的1/5
//     */
//    private void inttTabLineWidth(){
//        DisplayMetrics dm = getResources().getDisplayMetrics();
//        screenWidth = dm.widthPixels;
//        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)tabLine.getLayoutParams();
//        lp.width = screenWidth/5;
//        tabLine.setLayoutParams(lp);
//    }
//
//    private void reSetButton(){
//        newBlog.setTextColor(Color.BLACK);
//        frontBlog.setTextColor(Color.BLACK);
//        androidBlog.setTextColor(Color.BLACK);
//        backstageBlog.setTextColor(Color.BLACK);
//        cBlog.setTextColor(Color.BLACK);
//    }
//
//}
