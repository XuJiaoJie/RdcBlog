package com.android.rdc.rdcblog.blogdetail.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by zjz on 2016/10/4.
 */
public class BlogDetailDownloader {

    public static String request(String httpUrl, String httpArgBlogId,String httpArgUserId) {
        Log.d("hehe","hahahaha2");
        BufferedReader mBufferdReader = null;
        String mResult = null;
        StringBuilder sbf = new StringBuilder();//字符串变量

        try {
            //中文内容，通过URLEncoder.encode转码
        //    httpArgBlogId=  URLEncoder.encode(httpArgBlogId,"utf-8");
            httpUrl = httpUrl + httpArgBlogId +"?userId="+ httpArgUserId;
            URL url = new URL(httpUrl);
            Log.d("hehe","hahahaha3");
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            Log.d("hehe","hahahaha4");
            connection.setRequestMethod("GET");
            connection.connect();
            Log.d("hehe","hahahaha5");
            InputStream is = connection.getInputStream();

            mBufferdReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            Log.d("hehe","hahahaha6");
            while ((strRead = mBufferdReader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\n");
            }
            mBufferdReader.close();
            Log.d("hehe","hahahaha7");
            mResult = sbf.toString();
            Log.d("hehe","hahahaha3");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return mResult;
    }

}