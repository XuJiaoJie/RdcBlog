package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.Comments;
import com.android.rdc.rdcblog.personalcenter.util.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
//ListView适配器
public class CommentAdapter extends ArrayAdapter<Comments> {

    private int resourceId;

    public CommentAdapter(Context context, int commentResourceId, List<Comments> list){
        super(context,commentResourceId ,list);
        resourceId = commentResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comments comments = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.reviewerIcon = (CircleImageView)view.findViewById(R.id.pc_msg_sender_icon);
            viewHolder.reviewerNameTv = (TextView)view.findViewById(R.id.msg_sender_nickname);
            viewHolder.reviewContentTv = (TextView)view.findViewById(R.id.pc_msg_content);
            viewHolder.reviewTimeTv = (TextView)view.findViewById(R.id.personal_tv_comment_time);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.reviewerIcon.setImageResource(comments.getReviewerIconId());
        viewHolder.reviewerNameTv.setText(comments.getReviewerName());
        viewHolder.reviewContentTv.setText(comments.getReviewContent());
        viewHolder.reviewTimeTv.setText(comments.getReviewTime());

        return view;
    }

    class ViewHolder{
        CircleImageView reviewerIcon;
        TextView reviewerNameTv;
        TextView reviewContentTv;
        TextView reviewTimeTv;
    }
}