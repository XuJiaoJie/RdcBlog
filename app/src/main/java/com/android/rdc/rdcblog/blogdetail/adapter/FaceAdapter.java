package com.android.rdc.rdcblog.blogdetail.adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.android.rdc.rdcblog.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by zjz on 2016/9/8.
 */
public class FaceAdapter extends BaseAdapter {
    private static FaceAdapter faceAdapter ;
    Context context ;
    ArrayList<String> facesName ;

    private FaceAdapter(Context context ,ArrayList<String> facesName){
        this.context = context;
        this.facesName = facesName;
    }
    public static FaceAdapter getInstance(Context context , ArrayList<String> facesName){
        if (faceAdapter == null) {
          faceAdapter = new FaceAdapter(context,facesName);
        }
        return faceAdapter;
    }
    @Override
    public int getCount() {
        return facesName.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.blogdetail_writecomment_faceitem,null);
        ImageView imageView ;
        imageView = (ImageView) view.findViewById(R.id.iv_writecomment_face);
        Field field;
        try {
             field= (Field)R.drawable.class.getDeclaredField(facesName.get(position));
            int j = field.getInt(R.drawable.class);
            imageView.setImageResource(j);
        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }
}
