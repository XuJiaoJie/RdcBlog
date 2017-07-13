package com.android.rdc.rdcblog.bloglist.presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.model.BlogSortListItem;
import com.android.rdc.rdcblog.bloglist.model.ViewHolder;
import com.android.rdc.rdcblog.bloglist.view.defined.RefreshListView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//import com.android.rdc.rdcblog.bloglist.model.AsyncImageTask;
//import com.android.rdc.rdcblog.bloglist.model.ImageCallback;

/**
 * Created by PC on 2016/7/28.
 */
public class BlogSortListViewAdapter extends BaseAdapter{
    private static final String TAG = "BlogSortListViewAdapter";

    private LayoutInflater layoutInflater = null;
    private List<BlogSortListItem> blogSortListItems;
    private RefreshListView listView;

    private Context context;
    private LruCache<String,BitmapDrawable> mImageCache;

    public BlogSortListViewAdapter(Context context , List<BlogSortListItem> blogSortListItems,
                                   ListView listView) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.blogSortListItems = blogSortListItems;
        this.listView = (RefreshListView) listView;
        Fresco.initialize(context);

        int maxCache = (int)Runtime.getRuntime().maxMemory();
        int cacheSize = maxCache/4;
        mImageCache = new LruCache<String,BitmapDrawable>(cacheSize){
            @Override
            protected int sizeOf(String key,BitmapDrawable value) {
                return super.sizeOf(key, value);
            }
        };
    }

    @Override
    public int getCount() {
        return blogSortListItems.size();
    }

    @Override
    public Object getItem(int i) {
        return blogSortListItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if (view == null){
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            view = layoutInflater.inflate(R.layout.bloglist_single_blog_item,null);
            holder.blogTitle = (TextView)view.findViewById(R.id.bloglist_blog_title);
            holder.blogTime = (TextView)view.findViewById(R.id.bloglist_blog_time);
            holder.goodSum = (TextView)view.findViewById(R.id.bloglist_good);
            holder.commentSum = (TextView)view.findViewById(R.id.bloglist_comment_sum);
            holder.blogPicture = (ImageView)view.findViewById(R.id.bloglist_single_item_icon);
            holder.userIcon = (SimpleDraweeView) view.findViewById(R.id.bloglist_collect_user_icon);
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();
        }
        BlogSortListItem blogSortListItem = blogSortListItems.get(i);
        holder.blogTitle.setText(blogSortListItem.getBlogTitle());
        holder.blogTime.setText(blogSortListItem.getUser()+"分享于  "+blogSortListItem.getBlogTime());
        holder.goodSum.setText(blogSortListItem.getBlogGood());
        holder.commentSum.setText(blogSortListItem.getBlogComment());
        holder.blogPicture.setTag(blogSortListItem.getBlogPic());
        holder.userIcon.setTag(blogSortListItem.getUserIcon());

        //(博客图片) 如果本地已有缓存，就从本地读取，否则从网络请求数据
        if(mImageCache.get(blogSortListItem.getBlogPic())!=null){
            holder.blogPicture.setImageDrawable(mImageCache.get(blogSortListItem.getBlogPic()));
        }else {
            ImageTask it = new ImageTask();
            it.execute(blogSortListItem.getBlogPic());
        }


        holder.userIcon.setImageURI(Uri.parse(blogSortListItem.getUserIcon()));
        return view;
    }

    class ImageTask extends AsyncTask<String,Void,BitmapDrawable>{
        private String imageUrl;

        @Override
        protected BitmapDrawable doInBackground(String... strings) {
            imageUrl = strings[0];
            Bitmap bitmap = downloadImage();
            BitmapDrawable db = new BitmapDrawable(context.getResources(),bitmap);
            // 如果本地还没缓存该图片，就缓存
            if(mImageCache.get(imageUrl)==null){
                mImageCache.put(imageUrl,db);
            }
            return db;
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            // 通过Tag找到我们需要的ImageView，如果该ImageView所在的item已被移出页面，就会直接返回null
            ImageView iv = (ImageView) listView.findViewWithTag(imageUrl);
            if (iv != null && bitmapDrawable != null){
                iv.setImageDrawable(bitmapDrawable);
            }
        }
        /**
         * 根据url从网络上下载图片
         */
        private Bitmap downloadImage(){
            HttpURLConnection con = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection)url.openConnection();
                con.setConnectTimeout(5*1000);
                con.setReadTimeout(10*1000);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 4;
                bitmap = BitmapFactory.decodeStream(con.getInputStream(),null,options);
            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if (con != null){
                    con.disconnect();
                }
            }
            return bitmap;
        }
    }

}


