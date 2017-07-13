package com.android.rdc.rdcblog.personalcenter.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Administrator on 2016/9/17 0017.
 */
public class ImageHelper {

    public static File getTempImage(){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File tempFile = new File(Environment.getExternalStorageDirectory(), "temp.png");
            try{
                tempFile.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
            return tempFile;
        }
        return null;
    }

}
