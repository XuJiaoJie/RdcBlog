package com.android.rdc.rdcblog.bloglist.model.http;

import android.util.Log;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by PC on 2017/2/28.
 */

public class PublishBlogData {
    private static final String TAG = "PublishBlogData";
    private static final String mBitmapTag = "<img";
    private static final String mBitmapTag2 = "img>";
    private String mPicturePath;
    private String content;

    /**
     * 截取正文中图片先行上传
     */
    public void upLoadPicture(final String content){
        int i = 0;
        int indexBegin = -1 , indexLast = -1;
        while (true){
            indexBegin = content.indexOf(mBitmapTag+i);
            if(indexBegin != -1){
                indexLast = content.indexOf(mBitmapTag2 , indexBegin);
                mPicturePath = content.substring(indexBegin+4,indexLast);
                //先行上传一张图片
                final BmobFile bmobFile = new BmobFile(new File(mPicturePath));
                bmobFile.uploadblock(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            //bmobFile.getFileUrl()--返回的上传文件的完整地址
                            content.replace(mPicturePath , bmobFile.getFileUrl());
                            Log.e(TAG, "done: " + bmobFile.getFileUrl());
                        }else {
                            Log.e(TAG, "done:  图片先行上传失败"  );
                        }
                    }
                });
            }else {
                break;
            }
        }
        this.content = content;
    }



}
