package com.android.rdc.rdcblog.personalcenter.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.personalcenter.util.MsgData;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Time:2016/9/3 21:27
 * Created By:ThatNight
 */
public class ChatListViewAdapter extends BaseAdapter {


	private static final String TAG = "ChatListViewAdapter";
	private List<MsgData> datas;
	private Context mContext;
	private LayoutInflater mInflater;
	private int COME_MSG = 0;
	private int TO_MSG = 1;

	public ChatListViewAdapter(Context context, List<MsgData> datas) {
		mContext = context;
		this.datas = datas;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int i) {
		return datas.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public int getItemViewType(int position) {
		MsgData msgData = datas.get(position);
		if (msgData.isComeMsg()) {
			return COME_MSG;
		} else {
			return TO_MSG;
		}
	}


	/**
	 * List默认返回1，我们有2种布局
	 */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		MsgData msgData = datas.get(i);
		boolean isComMsg = msgData.isComeMsg();
		ViewHolder viewHolder = null;
		if (view == null) {
			viewHolder = new ViewHolder();
			if (isComMsg) {
				view = mInflater.inflate(R.layout.chat_left_item, null);
				//viewHolder.isComMsg = isComMsg;
			} else {
				view = mInflater.inflate(R.layout.chat_right_item, null);
				//viewHolder.isComMsg = isComMsg;
			}
			viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_chat);
			viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_icon);

			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		if (!"".equals(msgData.getmContent()) || msgData.getmContent() != null) {
			viewHolder.tvContent.setText(msgData.getmContent());
		}
		Picasso.with(mContext).load(msgData.getmIcon()).resize(50,50).into(viewHolder.imageView);
		return view;
	}


	private class ViewHolder {
		TextView tvContent;
		ImageView imageView;
		TextView tvName;
		boolean isComMsg = true;
	}
}
