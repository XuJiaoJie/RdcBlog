package com.android.rdc.rdcblog.blogdetail.model;

import com.android.rdc.rdcblog.blogdetail.adapter.BlogdetailAdapter;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentItemListBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjz on 2016/7/29.
 */
public class BlogdetailCommentModel implements  IBlogdetailCommentModel {

    public ArrayList<CommentBean> mlist;

    public BlogdetailCommentModel(ArrayList<String> list){
        mlist = new ArrayList<CommentBean>();

    }

    @Override
    public void read() {

    }

    @Override
    public void writeIn(String text , String time) {
        CommentBean commentBean = new CommentBean();
        commentBean.setCommenttext(text);
        commentBean.setCommentTime(time);
        CommentListBean.getInstance().mlist.add(commentBean);
        CommentListListBean.getInstance().mlist.add(new CommentItemListBean());
    }
}
