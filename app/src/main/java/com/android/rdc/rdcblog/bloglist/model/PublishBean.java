package com.android.rdc.rdcblog.bloglist.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by PC on 2017/2/28.
 */

public class PublishBean extends BmobObject{
    private String title;
    private String type;
    private String content;
    private String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
