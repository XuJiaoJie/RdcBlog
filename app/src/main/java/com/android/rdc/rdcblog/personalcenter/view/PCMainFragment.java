package com.android.rdc.rdcblog.personalcenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.view.defined.SlidingMenu;
import com.android.rdc.rdcblog.photowall.view.SeePhotoActivity;


/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class PCMainFragment extends Fragment implements View.OnClickListener{
    private LinearLayout pcInfoLayout;
    private LinearLayout pcNotifiLayout;
    private LinearLayout pcCollectLayout;
    private LinearLayout pcHistoryLayout;
    private LinearLayout pcSettingLayout;
    private LinearLayout pcAboutLayout;
    private LinearLayout pcCommentLl;
    private LinearLayout pcFavoriteLl;
    private LinearLayout pcMsgLl;
    private LinearLayout pcMyBlogLayout;
    private LinearLayout pcGalleryLayout;
    private TextView pcCommentTv;
    private TextView pcFavorTv;
    private TextView pcMsgTv;

    private ImageView pcMenuIv;
    private SlidingMenu slidingMenu;
    private static final String TAG = "PCMainFragment";
    Exception e = new Exception();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pc_main_layout,null);
        Log.d(TAG, "onCreateView:======== ");

        initPcView(view);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pc_info_layout_setting:
                PCInfoAct.actionStart(getActivity());
                break;
            case R.id.pc_layout_gallery:
//                Intent intent = new Intent(getActivity(), SeePhotoActivity.class);
//                startActivity(intent);
//                getActivity().finish();
                break;
            case R.id.pc_layout_my_blog:
                PCMyBlogAct.actionStart(getActivity());
                break;
            case R.id.pc_ll_comment:
                PCNotificationAct.actionStart(getActivity(),0);
                break;
            case R.id.pc_ll_favorite:
                PCNotificationAct.actionStart(getActivity(), 1);
                break;
            case R.id.pc_ll_msg:
                PCNotificationAct.actionStart(getActivity(), 2);
                break;
            case R.id.pc_layout_collection:
                PCCollectionAct.actionStart(getActivity());
                break;
            case R.id.pc_layout_history:
                PCHistoryAct.actionStart(getActivity());
                break;
            case R.id.pc_layout_about:
                PCAboutAct.actionStart(getActivity());
                break;
            case R.id.pc_layout_setting:
                PCSettingsAct.actionStart(getActivity());
                break;
            case R.id.pc_iv_menu:
                slidingMenu.toggle();
                break;

            default:
                break;
        }
    }

    public void initPcView(View view){
        pcInfoLayout = (LinearLayout)view.findViewById(R.id.pc_info_layout_setting);
        pcNotifiLayout = (LinearLayout)view.findViewById(R.id.pc_layout_notification);
        pcGalleryLayout = (LinearLayout)view.findViewById(R.id.pc_layout_gallery);
        pcMyBlogLayout = (LinearLayout)view.findViewById(R.id.pc_layout_my_blog);
        pcCollectLayout = (LinearLayout)view.findViewById(R.id.pc_layout_collection);
        pcHistoryLayout = (LinearLayout)view.findViewById(R.id.pc_layout_history);
        pcSettingLayout = (LinearLayout)view.findViewById(R.id.pc_layout_setting);
        pcAboutLayout = (LinearLayout)view.findViewById(R.id.pc_layout_about);
        pcCommentTv = (TextView)view.findViewById(R.id.pc_tv_comment);
        pcFavorTv = (TextView)view.findViewById(R.id.pc_tv_favourite);
        pcMsgTv = (TextView)view.findViewById(R.id.pc_tv_message);
        pcMsgLl = (LinearLayout)view.findViewById(R.id.pc_ll_msg);
        pcFavoriteLl = (LinearLayout)view.findViewById(R.id.pc_ll_favorite);
        pcCommentLl = (LinearLayout)view.findViewById(R.id.pc_ll_comment);
        pcMenuIv = (ImageView)view.findViewById(R.id.pc_iv_menu);

        slidingMenu = (SlidingMenu)getActivity().findViewById(R.id.sliding_menu_contol);
        pcInfoLayout.setOnClickListener(this);
        pcNotifiLayout.setOnClickListener(this);
        pcMyBlogLayout.setOnClickListener(this);
        pcGalleryLayout.setOnClickListener(this);
        pcCollectLayout.setOnClickListener(this);
        pcHistoryLayout.setOnClickListener(this);
        pcSettingLayout.setOnClickListener(this);
        pcAboutLayout.setOnClickListener(this);
        pcCommentTv.setOnClickListener(this);
        pcFavoriteLl.setOnClickListener(this);
        pcMsgLl.setOnClickListener(this);
        pcCommentLl.setOnClickListener(this);
        pcMenuIv.setOnClickListener(this);
    }

    public static void actionStart(Context context ){
        Intent intent = new Intent(context, PCMainFragment.class);
        context.startActivity(intent);
    }
}
