package com.android.rdc.rdcblog.blogdetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentBean;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentItemListBean;

import java.util.List;

/**
 * Created by zjz on 2016/8/5.
 */
public class BlogdetailCommentAdaper extends BaseAdapter {
    private static BlogdetailCommentAdaper blogdetailCommentAdaper ;

    private  Context context ;
    private static  List<CommentBean> list ;

    private BlogdetailCommentAdaper(Context context , List list) {
        this.context = context;
       BlogdetailCommentAdaper.list = list;
    }

    public static BlogdetailCommentAdaper getInstance (Context context , List list) {
        if(blogdetailCommentAdaper == null) {
            blogdetailCommentAdaper = new BlogdetailCommentAdaper(context , list);
        }
        BlogdetailCommentAdaper.list = list;
        return blogdetailCommentAdaper;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        int position = list.size() - i -1;
        View view ;
         view = LayoutInflater.from(context).inflate(R.layout.blogdetail_comment_item_item,null);
        TextView tv  = (TextView) view.findViewById(R.id.tv_comment_item_content);
        tv.setText(list.get(position).getCommenttext());
        return view;
    }
}
