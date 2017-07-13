package com.android.rdc.rdcblog.bloglist.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.view.BlogDetailActivity;
import com.android.rdc.rdcblog.bloglist.model.BlogSortListItem;
import com.android.rdc.rdcblog.bloglist.presenter.BlogSortListViewAdapter;
import com.android.rdc.rdcblog.bloglist.presenter.LoadDataPresenter;
import com.android.rdc.rdcblog.bloglist.presenter.OnLoadListener;
import com.android.rdc.rdcblog.bloglist.presenter.OnRefreshListener;
import com.android.rdc.rdcblog.bloglist.view.ILoadDataView;
import com.android.rdc.rdcblog.bloglist.view.defined.RefreshListView;
import com.android.rdc.rdcblog.config.ConstantData;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by PC on 2016/7/27.
 */
public class SingleFragment extends Fragment implements OnRefreshListener,OnLoadListener,ILoadDataView {

    private static final String TAG = "SingleFragment";
    private View view;
    private List<BlogSortListItem> listItems;
    private RefreshListView listView;
    private BlogSortListViewAdapter blogSortListViewAdapter = null;
    private String type;       //请求的博客类型
    private int pageNO = 1;    //请求的url中的页数
    private int resultSize;    //加载下一页返回的条数
    private String urlType = null;     //转码字符串
    private LoadDataPresenter loadDataPresenter;

    /**
     *创建Fragment，并传入参数
     */
    public static SingleFragment newInstance(String type){
        SingleFragment singleFragment = new SingleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("blogType",type);
        singleFragment.setArguments(bundle);
        return singleFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bloglist_single_blog,null);
        type = getArguments().getString("blogType");     //获取参数
        init();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getActivity(), BlogDetailActivity.class));
            }
        });
        return view;
    }

    private void init(){
        loadDataPresenter = new LoadDataPresenter(this);
        try {
            urlType = URLEncoder.encode(type,"utf8");
        }catch (Exception e){
            e.printStackTrace();
        }
        loadDataPresenter.loadData(ConstantData.HeadUrl+pageNO+ ConstantData.MidUrl+urlType);
        listView = (RefreshListView) view.findViewById(R.id.list_view_single_blog);
        listView.setOnRefreshListener(this);
        listView.setOnLoadListener(this);
    }

    private void freshAdapter(){
        if(blogSortListViewAdapter == null){
            blogSortListViewAdapter = new BlogSortListViewAdapter(getActivity(),listItems,listView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(blogSortListViewAdapter);
                }
            });
        }else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    blogSortListViewAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    /**
     * 更新数据
     */
    @Override
    public void onRefresh() {
        pageNO = 1;
        loadDataPresenter.loadData(ConstantData.HeadUrl+pageNO+ ConstantData.MidUrl+urlType);
    }
    /**
     * 加载数据
     */
    @Override
    public void onLoad() {
        pageNO++;
        loadDataPresenter.loadData(ConstantData.HeadUrl+pageNO+ ConstantData.MidUrl+urlType,true);
    }


    @Override
    public void showToast(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
            }
        });
        Log.e("url_data",msg);
    }

    /**
     *博客List列表展示
     */
    @Override
    public void showBlogList(final List<BlogSortListItem> listItems) {
        blogSortListViewAdapter = null;
        this.listItems = listItems;
        Log.e(TAG, "showBlogList: 1    "+listItems.size());
        freshAdapter();
        Log.e(TAG, "showBlogList: 2");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.onRefreshComplete();
                listView.onLoadComplete(listItems.size());
            }
        });
        Log.e(TAG, "showBlogList: 3");
    }

    /**
     *下一页ListView加载
     */
    @Override
    public void showAddBlog(List<BlogSortListItem> listItems, int itemCount) {
        this.listItems.addAll(listItems);
        resultSize = itemCount;
        freshAdapter();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.onLoadComplete(resultSize);
            }
        });
    }
}
