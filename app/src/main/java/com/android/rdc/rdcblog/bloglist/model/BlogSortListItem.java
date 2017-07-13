package com.android.rdc.rdcblog.bloglist.model;

/**
 * Created by PC on 2016/7/28.
 */
public class BlogSortListItem {
    String blogTitle;
    String blogTime;
    String blogGood;
    String blogComment;
    String blogPic;
    String user;
    String userIcon;

    public String getBlogTitle() {
        return blogTitle;
    }

    public String getBlogTime() {
        return blogTime;
    }

    public String getBlogGood() {
        return blogGood;
    }

    public String getBlogComment() {
        return blogComment;
    }

    public String getBlogPic() {
        return blogPic;
    }

    public void setBlogPic(String blogPic) {
        this.blogPic = blogPic;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public void setBlogTime(String blogTime) {
        this.blogTime = blogTime;
    }

    public void setBlogGood(String blogGood) {
        this.blogGood = blogGood;
    }

    public void setBlogComment(String blogComment) {
        this.blogComment = blogComment;
    }

    public String getUser() {
        return user;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
