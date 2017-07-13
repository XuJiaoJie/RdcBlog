package com.android.rdc.rdcblog.blogdetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.blogdetail.model.bean.CommentListBean;
import com.android.rdc.rdcblog.blogdetail.presenter.BlogCommentPresenter;

import java.sql.Time;
import java.util.Calendar;
import java.util.List;

/**
 * Created by zjz on 2016/7/29.
 */
public class BlogdetailAdapter extends BaseAdapter {

    private static BlogdetailAdapter blogdetailAdapter;

    private Context context;
    private List mlist;


    private BlogdetailAdapter(Context context, List list) {
        this.context = context;
        this.mlist = list;
    }

    public static BlogdetailAdapter getInstance(Context context, List list) {
        if (blogdetailAdapter == null) {
            blogdetailAdapter = new BlogdetailAdapter(context, list);
        }
        return blogdetailAdapter;
    }

    @Override
    public int getCount() {
        if (mlist.size() == 0) {
            return 1;
        } else {
            return mlist.size();
        }

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

        int position = mlist.size()-1-i;

        View view;
        if (mlist.size() == 0) {
            view = LayoutInflater.from(context).inflate(R.layout.blogdetail_blogcontent_pictureitem, null);
            view.setFocusable(false);
            return view;
        } else {

            view = LayoutInflater.from(context).inflate(R.layout.blogdetail_comment_item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv_comment_item);
            textView.setText(CommentListBean.getInstance().mlist.get(position).getCommenttext());
            TextView time = (TextView) view.findViewById(R.id.tv_detail_time);
            time.setText(CommentListBean.getInstance().mlist.get(position).getCommentTime());

            return view;
        }

    }

    public String getBlogDetail() {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_blog_detail, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_detail_content);


        return (String) textView.getText();
    }
}
