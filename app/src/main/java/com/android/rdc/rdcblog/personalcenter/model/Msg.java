package com.android.rdc.rdcblog.personalcenter.model;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class Msg {
    private int msgSenderIconId;
    private String senderNickname;
    private String senderContent;

    public int getMsgSenderIconId() {
        return msgSenderIconId;
    }

    public String getSenderNickname() {
        return senderNickname;
    }

    public String getSenderContent() {
        return senderContent;
    }

    public void setMsgSenderIconId(int msgSenderIconId) {
        this.msgSenderIconId = msgSenderIconId;
    }

    public void setSenderNickname(String senderNickname) {
        this.senderNickname = senderNickname;
    }

    public void setSenderContent(String senderContent) {
        this.senderContent = senderContent;
    }
}
