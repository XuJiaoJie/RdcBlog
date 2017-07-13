package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.model.Msg;
import com.android.rdc.rdcblog.personalcenter.util.CircleImageView;

import java.util.List;


/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class MsgAdapter extends ArrayAdapter<Msg> {
    private int resourceId;
    public MsgAdapter(Context context, int msgResourceId, List<Msg> msgList){
        super(context, msgResourceId, msgList);
        resourceId = msgResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Msg msg = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.senderIconIv = (CircleImageView)view.findViewById(R.id.pc_msg_sender_icon);
            viewHolder.senderContentTv = (TextView)view.findViewById(R.id.pc_msg_content);
            viewHolder.senderNickNameTv = (TextView)view.findViewById(R.id.msg_sender_nickname);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.senderIconIv.setImageResource(msg.getMsgSenderIconId());
        viewHolder.senderNickNameTv.setText(msg.getSenderNickname());
        viewHolder.senderContentTv.setText(msg.getSenderContent());

        return view;
    }

    class ViewHolder{
        private CircleImageView senderIconIv;
        private TextView senderNickNameTv;
        private TextView senderContentTv;
    }
}
