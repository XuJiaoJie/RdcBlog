package com.android.rdc.rdcblog.personalcenter.util;

import android.util.Log;

import com.android.rdc.rdcblog.personalcenter.model.PCUserBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class JsonUtil {

    private static final String TAG = "JsonUtil";
    private static final PCUserBean pcUserBean = new PCUserBean();
    public static PCUserBean parseJson(String json){

        try {
            JSONObject jsonObject = new JSONObject(json);

            String username = jsonObject.getString("username");
            String nickname = jsonObject.getString("nickname");
            String workPlace = jsonObject.getString("workPlace");
//            Bitmap iconBmp = jsonObject.getString("image");
            Log.e(TAG, "parseJson: " +username+ " "+nickname+" " +workPlace);

            pcUserBean.setName(username);
            pcUserBean.setNickname(nickname);
            pcUserBean.setWorkPlace(workPlace);

//            pcUserBean = new PCUserBean(username, nickname, workPlace);
            Log.e(TAG, "parseJson: "+pcUserBean.getName()+pcUserBean.getNickname() );
        } catch (JSONException e){
            e.printStackTrace();
        }
        return pcUserBean;

    }
}
