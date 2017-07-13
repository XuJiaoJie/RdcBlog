package com.android.rdc.rdcblog.bloglist.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.presenter.BlogSortAdapter;
import com.android.rdc.rdcblog.bloglist.view.activity.BlogPublishActivity;
import com.android.rdc.rdcblog.bloglist.view.activity.BlogSearchActivity;
import com.android.rdc.rdcblog.bloglist.view.defined.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/7/27.
 */
public class BlogSortFragment extends Fragment implements View.OnClickListener{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BlogSortAdapter blogSortAdapter;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private ImageButton backMenu;
    private ImageButton searchBlog;
    private ImageButton writeBlog;
    private FloatingActionButton writeBlogFloat;
    private SlidingMenu slidingMenu;
    private SingleFragment newFragemnt ,frontFragment ,androidFragemnt ,backstageFragment ,cFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bloglist_sort_layout,container,false);
        initControls(view);
        return view;
    }

    private void initControls(View view){
        tabLayout = (TabLayout)view.findViewById(R.id.tab_layout_title);
        viewPager = (ViewPager)view.findViewById(R.id.view_pager_single_blog);
        backMenu = (ImageButton)view.findViewById(R.id.imbtm_back_menu);
        slidingMenu = (SlidingMenu)getActivity().findViewById(R.id.sliding_menu_contol);
        searchBlog = (ImageButton)view.findViewById(R.id.imbtm_search_blog);
        writeBlog = (ImageButton)view.findViewById(R.id.imbtm_add_blog);
        writeBlogFloat = (FloatingActionButton)view.findViewById(R.id.bloglist_floating_action_button);

        backMenu.setOnClickListener(this);
        searchBlog.setOnClickListener(this);
        writeBlog.setOnClickListener(this);
        writeBlogFloat.setOnClickListener(this);

        newFragemnt = SingleFragment.newInstance("全部");
        frontFragment = SingleFragment.newInstance("web前端");
        androidFragemnt = SingleFragment.newInstance("安卓");
        backstageFragment = SingleFragment.newInstance("java后台");
        cFragment = SingleFragment.newInstance("c++");

        //将fragment装进列表
        fragmentList = new ArrayList<>();
        fragmentList.add(newFragemnt);
        fragmentList.add(frontFragment);
        fragmentList.add(androidFragemnt);
        fragmentList.add(backstageFragment);
        fragmentList.add(cFragment);

        //将名称加载进tab列表
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("前端");
        titleList.add("安卓");
        titleList.add("后台");
        titleList.add("C++");

        //设置TabLayout的模式和为TabLayout添加tab名称
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(4)));

        blogSortAdapter = new BlogSortAdapter(getActivity().getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(blogSortAdapter);
        tabLayout.setupWithViewPager(viewPager);
        //强制关闭侧滑栏，解决点击tab后侧滑栏弹出问题
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                slidingMenu.closeMenuForce();
            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imbtm_back_menu:
                slidingMenu.toggle();
                break;
            case R.id.imbtm_search_blog:
                startActivity(new Intent(getActivity(),BlogSearchActivity.class));
                break;
            case R.id.imbtm_add_blog:
                Toast.makeText(getActivity(),"写博客功能尚未开发",Toast.LENGTH_SHORT).show();
                break;
            case R.id.bloglist_floating_action_button:
                startActivity(new Intent(getActivity(), BlogPublishActivity.class));
                break;
        }
    }

}
