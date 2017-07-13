package com.android.rdc.rdcblog.bloglist.presenter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by PC on 2016/7/27.
 */
public class BlogSortAdapter extends FragmentPagerAdapter{

    private List<Fragment> list_fragment;
    private List<String>  list_title;
    public BlogSortAdapter(FragmentManager fm,List<Fragment> list_fragment,
                           List<String> list_title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_title = list_title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_title.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_title.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }


}
