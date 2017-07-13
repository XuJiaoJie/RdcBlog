package com.android.rdc.rdcblog.blogdetail.model;

import com.android.rdc.rdcblog.blogdetail.model.bean.CommentBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentItemListBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListListBean;

/**
 * Created by zjz on 2016/8/6.
 */
public class CommentModel implements ICommentModel {
    @Override
    public void writeIn(String text, String time , int position) {
        CommentBean commentBean = new CommentBean();
        commentBean.setCommenttext(text);
        commentBean.setCommentTime(time);
        CommentListListBean.getInstance().mlist.get(position).mlist.add(commentBean);
    }
}
