package com.android.rdc.rdcblog.personalcenter.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created by Administrator on 2016/8/31 0031.
 */
public class FileImageUploadUtil {
    private static final String TAG = "FileImageUploadUtil";
    private static final int TIME_OUT = 10 * 10000000;
    private static final String CHARSET = "utf-8";
    private static final String SUCCESS = "1";
    private static final String FAILURE = "0";

    public static String uploadFile(File file, String RequestURL){
        String BOUNDARY = UUID.randomUUID().toString();//边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
        String LINE_END = UUID.randomUUID().toString();
        String PREFIX = UUID.randomUUID().toString();
        String CONTENT_TYPE = "multipart/form-data";

        try{
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setRequestMethod("post");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);//不允许缓存
            conn.setRequestProperty("Charset", CHARSET);
            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary = " + BOUNDARY);
            if (file !=null){
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputStream = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputStream);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX)
                        .append(BOUNDARY)
                        .append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-DisPosition: form-data; name = \"img\";filename=\""+file.getName()+"\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=\"+CHARSET+LINE_END");
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1){
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                int resCode = conn.getResponseCode();
                if (resCode == 200){
                    return SUCCESS;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return FAILURE;
    }
}
