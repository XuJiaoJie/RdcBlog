package com.android.rdc.rdcblog.personalcenter.util;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class PCToastUtil {

    public static void showMidToast(Context context, String str){
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);//设置显示位置
        LinearLayout layout = (LinearLayout)toast.getView();
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.pc_iv_collect);
        layout.addView(imageView);
        TextView tv = (TextView)toast.getView().findViewById(android.R.id.message);
        tv.setTextColor(Color.YELLOW);
        toast.show();
    }
    public static void showNormalToast(Context context, String str){
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.show();
//        LinearLayout layout = (LinearLayout)toast.getView();
//        ImageView imageView = new ImageView(context);
//        imageView.setImageResource(R.drawable.pc_iv_collect);
//        layout.addView(imageView);
//        TextView tv = (TextView)toast.getView().findViewById(android.R.id.message);
//        tv.setTextColor(Color.YELLOW);
//        toast.show();
    }
    public static void showCustomToast(Context context, String str){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pc_custom_toast, null);
        TextView tvMsg = (TextView)view.findViewById(R.id.pc_tv_toast_msg);
        tvMsg.setText(str);
        Toast toast = new Toast(context);
//        toast.setGravity(Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

}
