package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.view.defined.RoundImageView;
import com.android.rdc.rdcblog.personalcenter.model.MCollection;

import java.util.List;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MCollectionAdapter extends ArrayAdapter<MCollection> {
    private static final String TAG = "MCollectionAdapter";
    private int resourceId;
    private List<MCollection> itemList;
    private List<String> tagItemList;
    public MCollectionAdapter(Context context, int resource, List<MCollection> collectionList, List<String> tagList) {
        super(context, resource, collectionList);
        itemList = collectionList;
        tagItemList = tagList;
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MCollection mCollection;

        ViewHolder viewHolder;
        mCollection = getItem(position);
        if(tagItemList.contains(mCollection.getCollectTag())){
//            if(convertView == null){
//                view = LayoutInflater.from(getContext()).inflate(R.layout.pc_collection_list_item_tag, null);
//                viewHolder = new ViewHolder();
//                viewHolder.tagItemTv = (TextView)view.findViewById(R.id.pc_tv_collect_list_item_tag);
//                view.setTag(viewHolder);
//            }else{
//                view = convertView;
//                viewHolder = (ViewHolder)view.getTag();
//            }
            View view1 = LayoutInflater.from(getContext()).inflate(R.layout.pc_collection_list_item_tag, null);
            TextView catagory = (TextView)view1.findViewById(R.id.pc_tv_collect_list_item_tag);
            catagory.setText(mCollection.getCollectTag());
//            viewHolder.tagItemTv.setText(mCollection.getCollectTag());
            Log.d(TAG, "getView: tagItem"+getItem(position));
            return view1;
        }else {
            View view;
            if (convertView == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.pc_collect_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.passageTitleTv = (TextView)view.findViewById(R.id.pc_tv_collect_title);
                viewHolder.collectedTimeTv = (TextView)view.findViewById(R.id.pc_tv_collect_blog_time);
                viewHolder.favorNumTv = (TextView)view.findViewById(R.id.pc_collect_favor_num);
                viewHolder.commentNumTv = (TextView)view.findViewById(R.id.pc_tv_commment_num);
                viewHolder.userIconRiv = (RoundImageView)view.findViewById(R.id.pc_iv_collect_user_icon);
                viewHolder.passagePictureIv = (ImageView)view.findViewById(R.id.pc_iv_collect_picture);
                view.setTag(viewHolder);
            }else {
//                view = convertView;
//                viewHolder = (ViewHolder)view.getTag();
                view = LayoutInflater.from(getContext()).inflate(R.layout.pc_collect_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.passageTitleTv = (TextView)view.findViewById(R.id.pc_tv_collect_title);
                viewHolder.collectedTimeTv = (TextView)view.findViewById(R.id.pc_tv_collect_blog_time);
                viewHolder.favorNumTv = (TextView)view.findViewById(R.id.pc_collect_favor_num);
                viewHolder.commentNumTv = (TextView)view.findViewById(R.id.pc_tv_commment_num);
                viewHolder.userIconRiv = (RoundImageView)view.findViewById(R.id.pc_iv_collect_user_icon);
                viewHolder.passagePictureIv = (ImageView)view.findViewById(R.id.pc_iv_collect_picture);
            }
            Log.d(TAG, "getView: listItem"+(MCollection)getItem(position));
            viewHolder.passageTitleTv.setText(mCollection.getCollectPassageName());
            viewHolder.collectedTimeTv.setText(mCollection.getCollectTime());
            viewHolder.favorNumTv.setText(mCollection.getCollectFavorNum());
            viewHolder.commentNumTv.setText(mCollection.getCollectcommentNum());
//        viewHolder.userIconRiv.setImageResource(mCollection.getUserIocnId());
//        viewHolder.passagePictureIv.setImageResource(mCollection.getPassageImageId());
            return view;
        }
    }

    class ViewHolder {
        TextView passageTitleTv;
        TextView collectedTimeTv;
        TextView favorNumTv;
        TextView commentNumTv;
        RoundImageView userIconRiv;
        ImageView passagePictureIv;

        TextView tagItemTv;
    }
}