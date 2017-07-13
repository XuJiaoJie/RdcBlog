package com.android.rdc.rdcblog.personalcenter.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class PCUserBean {

    private String name;
    private String nickname;
    private String workPlace;
    private Bitmap iconBmp;

    public PCUserBean() {

    }

    public PCUserBean(String name, String nickname, String workPlace) {
        this.name = name;
        this.nickname = nickname;
        this.workPlace = workPlace;
    }

    public PCUserBean(String name, String nickname, String workPlace, Bitmap iconBmp) {
        this.name = name;
        this.nickname = nickname;
        this.workPlace = workPlace;
        this.iconBmp = iconBmp;
    }

    public Bitmap getIconBmp() {
        return iconBmp;
    }

    public void setIconBmp(Bitmap iconBmp) {
        this.iconBmp = iconBmp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getWorkPlace() {
        return workPlace;
    }
}
