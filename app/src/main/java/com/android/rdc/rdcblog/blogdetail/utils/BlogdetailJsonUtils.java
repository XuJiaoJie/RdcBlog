package com.android.rdc.rdcblog.blogdetail.utils;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zjz on 2016/10/4.
 */
public class BlogdetailJsonUtils {


    public static String getContent ( String jsonData) {//接收上下文

        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            String blog = jsonObject.getString("blog");
            JSONObject jsonObject1 = new JSONObject(blog);
            String content = jsonObject1.getString("content");


            //       String location  =jsonObject1.getString("location");
            //     String now = jsonObject1.getString("now");
//name已经放了进去了
            //  JSONObject jsonObjectLocation = new JSONObject(location);
            //    MainActivity.sCityList.get(paperNumber).setName(jsonObjectLocation.getString("name"));

            //    JSONObject jsonObjectNow = new JSONObject(now);

            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
