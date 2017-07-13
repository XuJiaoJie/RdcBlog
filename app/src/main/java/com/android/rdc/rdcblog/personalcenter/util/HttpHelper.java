package com.android.rdc.rdcblog.personalcenter.util;

import android.util.Log;

import com.android.rdc.rdcblog.personalcenter.model.PCUserBean;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;


/**
 * Created by Administrator on 2016/9/9 0009.
 */
public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static final String requestUrl = "http://10.21.20.120:8080/rdc_blog/updateUser";
//    private static final String requestUrl = "http://yashonzhou.cn/rdc_/updateUser";
    private static final int TIME_OUT = 8 * 1000;                          //超时时间
    private static final String CHARSET = "utf-8";
    private static final String PREFIX = "--";                            //前缀
    private static final String BOUNDARY = UUID.randomUUID().toString();  //边界标识随机生成
    private static final String CONTENT_TYPE = "multipart/form-data";     //内容类型
    private static final String LINE_END = "\r\n";                        //换行

    /**
     * get请求方法
     * */
    public static PCUserBean getRequest(final String urlStr) {

        final PCUserBean pcUserBean = new PCUserBean();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(requestUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setReadTimeout(TIME_OUT);
                    conn.setConnectTimeout(TIME_OUT);
                    Log.e(TAG, "getMethodResponseCode() = "+conn.getResponseCode()  );
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line = null;
                        StringBuilder response = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        PCUserBean netPcUserBean = JsonUtil.parseJson(response.toString());
                        pcUserBean.setName(netPcUserBean.getName());
                        pcUserBean.setNickname(netPcUserBean.getNickname());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return pcUserBean;
    }

    /**
     * post请求方法
     * */
    public static void postRequest(final Map<String, String> strParams, final Map<String, File> fileParams) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(requestUrl);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setReadTimeout(TIME_OUT);
                    conn.setConnectTimeout(TIME_OUT);
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    //设置请求头参数
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);
                    /**
                     * 请求体
                     */
                    //上传参数
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes( getStrParams(strParams).toString() );
                    dos.flush();

                    //文件上传
                    StringBuilder fileSb = new StringBuilder();
                    for (Map.Entry<String, File> fileEntry: fileParams.entrySet()){
                        fileSb.append(PREFIX)
                                .append(BOUNDARY)
                                .append(LINE_END)
                                /**
                                 * 这里重点注意： name里面的值为服务端需要的key 只有这个key 才可以得到对应的文件
                                 * filename是文件的名字，包含后缀名的 比如:abc.png
                                 */
                                .append("Content-Disposition: form-data; name=\"file\"; filename=\""
                                        + fileEntry.getKey() + "\"" + LINE_END)
                                .append("Content-Type: image/jpg" + LINE_END) //此处的ContentType不同于 请求头中的conn.setRequestProperty("Content-Type", CONTENT_TYPE+";boundary=" + BOUNDARY);
                                .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                                .append(LINE_END);
                        dos.writeBytes(fileSb.toString());
                        dos.flush();
                        InputStream is = new FileInputStream(fileEntry.getValue());
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while ((len = is.read(buffer)) != -1){
                            dos.write(buffer,0,len);
                        }
                        is.close();
                        dos.writeBytes(LINE_END);
                    }
                    //请求结束标志
                    dos.writeBytes(PREFIX + BOUNDARY + PREFIX + LINE_END);
                    dos.flush();
                    dos.close();
                    Log.e(TAG, "postResponseCode() = "+conn.getResponseCode() );
                    //读取服务器返回信息
                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line = null;
                        StringBuilder response = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        Log.e(TAG, "run: " + response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    if (conn!=null){
                        conn.disconnect();
                    }
                }
            }
        }).start();
    }

    /**
     * 对post参数进行编码处理
     * */
    private static StringBuilder getStrParams(Map<String,String> strParams){
        StringBuilder strSb = new StringBuilder();
        for (Map.Entry<String, String> entry : strParams.entrySet() ){
            strSb.append(PREFIX)
                    .append(BOUNDARY)
                    .append(LINE_END)
                    .append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END)
                    .append("Content-Type: text/plain; charset=" + CHARSET + LINE_END)
                    .append("Content-Transfer-Encoding: 8bit" + LINE_END)
                    .append(LINE_END)
                    .append(entry.getValue())
                    .append(LINE_END);
        }
        return strSb;
    }

}



