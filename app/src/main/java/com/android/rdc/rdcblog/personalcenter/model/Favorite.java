package com.android.rdc.rdcblog.personalcenter.model;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Favorite {
    private int favorIcon;//点赞者的头像
    private String favorNickname;//点赞者名字
    private String passageName;//被赞的的文章

    public int getFavorIcon() {
        return favorIcon;
    }

    public String getPassageName() {
        return passageName;
    }

    public void setFavorIcon(int favorIcon) {
        this.favorIcon = favorIcon;
    }

    public void setPassageName(String passageName) {
        this.passageName = passageName;
    }
}
