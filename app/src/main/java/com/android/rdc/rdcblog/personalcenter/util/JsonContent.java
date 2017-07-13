package com.android.rdc.rdcblog.personalcenter.util;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.rdc.rdcblog.config.ConstantData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Time:2016/9/6 21:22
 * Created By:ThatNight
 */
public class JsonContent {


	List<MsgData> datas;


	public void jsonUtil(String result, Handler handler){

		try {
			JSONArray jsonArray=new JSONArray(result);
			int i=0;
			datas=new ArrayList<>();
			while(!jsonArray.isNull(i)){
				JSONObject contentObject=jsonArray.getJSONObject(i);
				MsgData msgData=new MsgData();
				JSONObject sendObject=contentObject.getJSONObject("send");
				if(String.valueOf(ConstantData.userID).equals(sendObject.getString("userId"))){
					msgData.setComeMsg(false);
				}else {
					msgData.setComeMsg(true);
				}
				msgData.setmContent(contentObject.getString("message"));
				msgData.setmDate(contentObject.getString("date"));
				msgData.setmIcon(ConstantData.URL_ICON +sendObject.getString("image"));
				msgData.setmName(sendObject.getString("username"));
				datas.add(msgData);
				i++;
			}
			Log.d("result",result);
			Message msg=new Message();
			msg.what= ConstantData.GET_SUCCESS;
			msg.obj=datas;
			handler.sendMessage(msg);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
