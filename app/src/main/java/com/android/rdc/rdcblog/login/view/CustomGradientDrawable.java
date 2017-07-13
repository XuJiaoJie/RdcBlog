package com.android.rdc.rdcblog.login.view;

import android.graphics.drawable.GradientDrawable;

/**
 * 主要是为了使用属性动画而设计的类
 * Created by tom on 15/10/31.
 */
public class CustomGradientDrawable extends GradientDrawable {



    private float mRadius;
    private int mColor;


    public CustomGradientDrawable(){

    }


    public void setRadius(float mRadius){
        this.mRadius=mRadius;
        setCornerRadius(mRadius);
    }

    public float getRadius(){
        return mRadius;
    }

    public void setColor(int mColor){
        super.setColor(mColor);
        this.mColor=mColor;
    }

    public int getmColor() {
        return mColor;
    }
}
