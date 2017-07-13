package com.android.rdc.rdcblog.personalcenter.presenter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.rdc.rdcblog.personalcenter.model.Favorite;
import com.android.rdc.rdcblog.personalcenter.util.CircleImageView;

import java.util.List;


/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class FavoriteAdapter extends ArrayAdapter<Favorite>{
    private int resourceId;

    public FavoriteAdapter(Context context, int favorResoriteId, List<Favorite> favoriteList){
        super(context, favorResoriteId, favoriteList);
        resourceId = favorResoriteId;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Favorite favorite = getItem(position);
//        View view;
//        ViewHolder viewHolder;
//        if(convertView == null){
//            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
//            viewHolder = new ViewHolder();
//            viewHolder.FavorIconIv = (CircleImageView)view.findViewById(R.id.personal_tv_favourite);
//        }else {
//
//        }
//
//        return  view;
//    }
    class ViewHolder{
        CircleImageView FavorIconIv;
        TextView favorNameTv;
        TextView favorPassageName;
    }
}
