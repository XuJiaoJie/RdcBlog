package com.android.rdc.rdcblog.bloglist.model;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by PC on 2016/8/5.
 */
//public class AsyncImageTask {
//    ExecutorService executorService = Executors.newCachedThreadPool();    //开线程池
//    private Map<String, SoftReference<Drawable>> imageMap;        //缓存图片 把图片的软引用放到map中
//
//    public AsyncImageTask() {
//        super();
//        this.imageMap = new HashMap<>();
//    }
//
//    /**
//     * ID为标记,标记哪条记录image
//     */
//    public Drawable loadImage(final int id, final String imageUrl, final ImageCallback callback) {
//        //先看看缓存（Map）中是否存在
//        if (imageMap.containsKey(imageUrl)) {
//            SoftReference<Drawable> softReference = imageMap.get(imageUrl);
//            if (softReference != null) {
//                Drawable drawable = softReference.get();
//                if (drawable != null) {
//                    callback.imageLoaded(drawable, id);
//                    return drawable;
//                }
//            }
//        }
//        //主线程更新图片
//        final Handler handler = new Handler() {
//            public void handleMessage(Message message) {
//                callback.imageLoaded((Drawable) message.obj, id);
//            }
//        };
//        //加载图片的线程
//        executorService.submit(
//                new Thread(){
//                    public void run(){
//                        Drawable drawable = AsyncImageTask.loadImageByUrl(imageUrl);
//                        imageMap.put(imageUrl,new SoftReference<Drawable>(drawable));
//                        //通知消息主线程更新UI  . 这里就是是否能异步刷新的留意点.
//                        Message message = handler.obtainMessage(0,drawable);
//                        handler.sendMessage(message);
//                    }
//                }
//        );
//        return null;
//    }
//
//    /**
//     * 根据图片地址加载图片，并保存为Drawable
//     */
//    public static Drawable loadImageByUrl(String imageUrl){
//        URL url = null;
//        InputStream inputStream = null;
//        try {
//            url = new URL(imageUrl);
//            inputStream = (InputStream)url.getContent();
//            Drawable drawable = Drawable.createFromStream(inputStream,"src");
//            return drawable;
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if (inputStream != null){
//                    inputStream.close();
//                }
//            }catch (IOException e){
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//}
//
