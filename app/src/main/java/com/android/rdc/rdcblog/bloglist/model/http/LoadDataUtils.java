package com.android.rdc.rdcblog.bloglist.model.http;

import android.util.Log;

import com.android.rdc.rdcblog.bloglist.model.BlogSortListItem;
import com.android.rdc.rdcblog.config.ConstantData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2016/9/4.
 */
public class LoadDataUtils {
    private static final String TAG = "LoadDataUtils";
    private List<BlogSortListItem> blogSortList;
    public List<BlogSortListItem> handleBlogResponse(String response){
        try {
            blogSortList = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(response);
            Log.e(TAG, "handleBlogResponse:1 ");
            JSONArray jsonArray = jsonObject.getJSONArray("blogs");
            Log.e(TAG, "handleBlogResponse: 2");
            for (int i = 0; i<jsonArray.length();i++){
                Log.e(TAG, "handleBlogResponse:3 ");
                BlogSortListItem blogSortListItem = new BlogSortListItem();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                Log.e(TAG, "handleBlogResponse: 4");
                blogSortListItem.setBlogTitle(jsonObject1.getString("title"));
                Log.e(TAG, "handleBlogResponse: 5");
                blogSortListItem.setBlogTime(jsonObject1.getString("date"));
                blogSortListItem.setBlogGood(jsonObject1.getString("likeCount"));
                Log.e(TAG, "handleBlogResponse:6 ");
                blogSortListItem.setBlogComment(jsonObject1.getString("commentCount"));
                Log.e(TAG, "handleBlogResponse: 7");
                JSONObject jsonObject2 = jsonObject1.getJSONObject("user");
                blogSortListItem.setUser(jsonObject2.getString("username"));
                Log.e(TAG, "handleBlogResponse: 8"   +jsonObject2.getString("image"));
                if(!jsonObject2.getString("image").equals("null")){
                    blogSortListItem.setUserIcon(ConstantData.PicHeadUrl+jsonObject2.getString("image"));
                }else {
                    blogSortListItem.setUserIcon(ConstantData.PicHeadUrl+"default.jpg");
                }
                Log.e(TAG, "handleBlogResponse: 8.5");
                blogSortListItem.setBlogPic(ConstantData.imageUrls[i]);
                Log.e(TAG, "handleBlogResponse:   "+ blogSortListItem.getUserIcon());
                Log.e(TAG, "handleBlogResponse:9 ");
                blogSortList.add(blogSortListItem);
                Log.e(TAG, "handleBlogResponse:10 ");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "handleBlogResponse:11 ");
        }
        Log.e(TAG, "handleBlogResponse:      "+blogSortList.size());
        return blogSortList;
    }
}
