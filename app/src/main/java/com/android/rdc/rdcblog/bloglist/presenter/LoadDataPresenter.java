package com.android.rdc.rdcblog.bloglist.presenter;

import android.util.Log;

import com.android.rdc.rdcblog.bloglist.model.BlogSortListItem;
import com.android.rdc.rdcblog.bloglist.model.http.ILoadData;
import com.android.rdc.rdcblog.bloglist.model.http.ILoadDataListener;
import com.android.rdc.rdcblog.bloglist.model.http.LoadDataImple;
import com.android.rdc.rdcblog.bloglist.model.http.LoadDataUtils;
import com.android.rdc.rdcblog.bloglist.view.ILoadDataView;

import java.util.List;

/**
 * Created by PC on 2016/9/3.
 */
public class LoadDataPresenter implements ILoadDataListener {

    private static final String TAG = "LoadDataPresenter";
    private ILoadData iLoadData;
    private ILoadDataView iLoadDataView;
    private LoadDataUtils loadDataUtils;
    private List<BlogSortListItem> listItems;
    private Boolean isAddLoad = false;

    public LoadDataPresenter(ILoadDataView iLoadDataView){
        this.iLoadDataView = iLoadDataView;
        iLoadData = new LoadDataImple();
        loadDataUtils = new LoadDataUtils();
    }

    public void loadData(String address){
        iLoadData.loadData(address,this);
    }

    public void loadData(String address , Boolean isAddLoad){
        this.isAddLoad= isAddLoad;
        iLoadData.loadData(address,this);
    }

    @Override
    public void onSuccess(String response) {
        listItems = loadDataUtils.handleBlogResponse(response);
        if (isAddLoad == true){
            iLoadDataView.showAddBlog(listItems,listItems.size());
            isAddLoad = false;
        }else {
            Log.e(TAG, "onSuccess: ");
            iLoadDataView.showBlogList(listItems);
        }
    }

    @Override
    public void onErrer(Exception e) {
        iLoadDataView.showToast("加载失败");
    }
}
