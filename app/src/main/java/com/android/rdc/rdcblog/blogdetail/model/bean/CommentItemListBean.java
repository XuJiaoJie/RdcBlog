package com.android.rdc.rdcblog.blogdetail.model.bean;

import java.util.ArrayList;

/**
 * Created by zjz on 2016/8/5.
 */
public class CommentItemListBean {
  //  private static  CommentItemListBean  commentItemListBean;
    public ArrayList<CommentBean> mlist;

    public CommentItemListBean() {
        mlist  = new ArrayList<CommentBean>();
    }

  /*  public static CommentItemListBean getInstance() {
        if(commentItemListBean == null) {
            commentItemListBean = new CommentItemListBean();
        }
        return commentItemListBean;
    }*/

}
