package com.android.rdc.rdcblog.bloglist.view;

import com.android.rdc.rdcblog.bloglist.model.BlogSortListItem;

import java.util.List;

/**
 * Created by PC on 2016/9/3.
 */
public interface ILoadDataView {

    //Toast
    void showToast(String msg);

    //博客List列表展示
    void showBlogList(List<BlogSortListItem> listItems);

    //下一页ListView加载
    void showAddBlog(List<BlogSortListItem> listItems , int itemCount);

}
