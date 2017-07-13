package com.android.rdc.rdcblog.blogdetail.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjz on 2016/7/31.
 */
public class CommentListBean {
    private static  CommentListBean commentListBean ;
    public  ArrayList<CommentBean> mlist;

    private CommentListBean() {
        mlist  = new ArrayList<CommentBean>();
    }

    public static CommentListBean getInstance() {
        if(commentListBean == null) {
            commentListBean = new CommentListBean();
        }
        return commentListBean;
    }

}
