package com.android.rdc.rdcblog.personalcenter.model;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class MCollection {
    private String collectPassageName;
    private String collectTime;
    private String collectFavorNum;
    private String collectcommentNum;
    private String collectTag;
    private int passageImageId;
    private int userIocnId;


    public String getCollectTag() {
        return collectTag;
    }

    public void setCollectTag(String collectTag) {
        this.collectTag = collectTag;
    }

    public MCollection(String collectPassageName, String collectTime, String collectFavorNum, String collectCommentNum, String collectTag, int passageImageId, int userIconId) {
        this.collectPassageName = collectPassageName;
        this.collectTime = collectTime;
        this.collectFavorNum = collectFavorNum;
        this.collectcommentNum = collectCommentNum;
        this.collectTag = collectTag;
        this.passageImageId = passageImageId;
        this.userIocnId = userIconId;
    }

    public MCollection(String collectPassageName, String collectTime, String collectFavorNum, String collectCommentNum, int passageImageId, int userIconId) {
        this.collectPassageName = collectPassageName;
        this.collectTime = collectTime;
        this.collectFavorNum = collectFavorNum;
        this.collectcommentNum = collectCommentNum;
        this.passageImageId = passageImageId;
        this.userIocnId = userIconId;

    }

    public String getCollectPassageName() {
        return collectPassageName;
    }

    public void setCollectPassageName(String collectPassageName) {
        this.collectPassageName = collectPassageName;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public String getCollectFavorNum() {
        return collectFavorNum;
    }

    public void setCollectFavorNum(String collectFavorNum) {
        this.collectFavorNum = collectFavorNum;
    }

    public String getCollectcommentNum() {
        return collectcommentNum;
    }

    public void setCollectcommentNum(String collectcommentNum) {
        this.collectcommentNum = collectcommentNum;
    }

    public int getPassageImageId() {
        return passageImageId;
    }

    public void setPassageImageId(int passageImageId) {
        this.passageImageId = passageImageId;
    }

    public int getUserIocnId() {
        return userIocnId;
    }

    public void setUserIocnId(int userIocnId) {
        this.userIocnId = userIocnId;
    }
}
