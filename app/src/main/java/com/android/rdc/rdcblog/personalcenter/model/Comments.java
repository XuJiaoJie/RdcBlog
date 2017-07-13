package com.android.rdc.rdcblog.personalcenter.model;

/**
 * Created by Administrator on 2016/7/26 0026.
 */
public class Comments {
    private int reviewerIconId;
    private String reviewerName;
    private String reviewContent;
    private String reviewTime;

    public int getReviewerIconId() {
        return reviewerIconId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewerIconId(int reviewerIconId) {
        this.reviewerIconId = reviewerIconId;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }
}
