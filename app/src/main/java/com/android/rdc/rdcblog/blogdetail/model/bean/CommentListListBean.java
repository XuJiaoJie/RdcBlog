package com.android.rdc.rdcblog.blogdetail.model.bean;

import java.util.ArrayList;

/**
 * Created by zjz on 2016/8/7.
 */
public class CommentListListBean {

    private static CommentListListBean commentListListBean ;
    public ArrayList<CommentItemListBean> mlist ;
  //  private int position;

    private CommentListListBean( ){
        mlist = new ArrayList<CommentItemListBean>();
    //    this.position = position;
    }

    public static CommentListListBean getInstance() {
        if (commentListListBean  == null) {
            commentListListBean = new CommentListListBean();
        }
        return  commentListListBean;
    }
}
