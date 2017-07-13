package com.android.rdc.rdcblog.personalcenter.model;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public interface ILoadInfoListener {
    void onSuccess(String response);
    void onError(Exception e);
}
