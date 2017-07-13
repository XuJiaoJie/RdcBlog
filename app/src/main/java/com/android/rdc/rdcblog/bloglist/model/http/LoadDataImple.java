package com.android.rdc.rdcblog.bloglist.model.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PC on 2016/9/3.
 */
public class LoadDataImple implements ILoadData{
    private static final String TAG = "LoadDataImple";
    @Override
    public void loadData(final String address, final ILoadDataListener iLoadDataListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                Log.e(TAG, "run: 1");
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    Log.e(TAG, "run: 2");
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    Log.e(TAG, "run:3 ");
                    InputStream in = connection.getInputStream();
                    Log.e(TAG, "run:4 ");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    Log.e(TAG, "run:5 ");
                    String line;
                    while ((line = reader.readLine())!=null){
                        response.append(line);
                    }
                    Log.e(TAG, "run: 6"+response.toString());
                    if (iLoadDataListener != null){
                        Log.e(TAG, "run: 6.5");
                        iLoadDataListener.onSuccess(response.toString());
                        Log.e(TAG, "run: 7");
                    }
                }catch (Exception e){
                    if (iLoadDataListener != null){
                        iLoadDataListener.onErrer(e);
                        Log.e(TAG, "run:8 ");
                        e.printStackTrace();
                    }
                }finally {
                    if (connection != null){
                        connection.disconnect();
                        Log.e(TAG, "run: 9");
                    }
                }
            }
        }).start();
    }
}
