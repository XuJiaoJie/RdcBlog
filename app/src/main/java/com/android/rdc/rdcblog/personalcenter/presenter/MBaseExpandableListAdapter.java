package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;

import com.android.rdc.rdcblog.bloglist.view.defined.RoundImageView;
import com.android.rdc.rdcblog.personalcenter.model.CollectionCategory;
import com.android.rdc.rdcblog.personalcenter.model.MCollection;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/30 0030.
 */
public class MBaseExpandableListAdapter extends BaseExpandableListAdapter {

    private ArrayList<CollectionCategory> gData;
    private ArrayList<ArrayList<MCollection>> iData;
    private Context mContext;
    public MBaseExpandableListAdapter(ArrayList<CollectionCategory> gData, ArrayList<ArrayList<MCollection>> iData, Context context) {
        this.gData = gData;
        this.iData = iData;
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return iData.get(groupPosition).size();
    }

    @Override
    public CollectionCategory getGroup(int groupPosition) {
        return gData.get(groupPosition);
    }

    @Override
    public MCollection getChild(int groupPosition, int childPosition) {
        return iData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        ViewHolderGroup groupHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.pc_collection_list_item_tag, null);
            groupHolder = new ViewHolderGroup();
            groupHolder.collectTagTv = (TextView)view.findViewById(R.id.pc_tv_collect_list_item_tag);
            view.setTag(groupHolder);
        }else {
            groupHolder = (ViewHolderGroup)view.getTag();
        }
        groupHolder.collectTagTv.setText(gData.get(i).getCategory());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.pc_collect_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.passageTitleTv = (TextView) view.findViewById(R.id.pc_tv_collect_title);
            viewHolder.collectedTimeTv = (TextView) view.findViewById(R.id.pc_tv_collect_blog_time);
            viewHolder.favorNumTv = (TextView) view.findViewById(R.id.pc_collect_favor_num);
            viewHolder.commentNumTv = (TextView) view.findViewById(R.id.pc_tv_commment_num);
            viewHolder.userIconRiv = (RoundImageView) view.findViewById(R.id.pc_iv_collect_user_icon);
            viewHolder.passagePictureIv = (ImageView) view.findViewById(R.id.pc_iv_collect_picture);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.passageTitleTv.setText(iData.get(i).get(i1).getCollectPassageName());
        viewHolder.collectedTimeTv.setText(iData.get(i).get(i1).getCollectTime());
        viewHolder.favorNumTv.setText(iData.get(i).get(i1).getCollectFavorNum());
        viewHolder.commentNumTv.setText(iData.get(i).get(i1).getCollectcommentNum());
//        viewHolder.userIconRiv.setImageResource(iData.get(i).get(i1).getUserIocnId());
//        viewHolder.passagePictureIv.setImageResource(iData.get(i).get(i1).getPassageImageId());

        return view;
    }
    class ViewHolderGroup{
        TextView collectTagTv;
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

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

}
