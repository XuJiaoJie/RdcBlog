package com.android.rdc.rdcblog.bloglist.model.http;

/**
 * Created by PC on 2016/9/3.
 */
public interface ILoadDataListener {

    void onSuccess(String response);

    void onErrer(Exception e);
}
