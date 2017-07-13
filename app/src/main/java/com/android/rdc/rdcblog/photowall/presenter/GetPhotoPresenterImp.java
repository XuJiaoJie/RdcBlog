package com.android.rdc.rdcblog.photowall.presenter;

import android.os.Handler;
import android.os.Message;

import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.photowall.model.PhotoData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Time:2016/8/30 22:00
 * Created By:ThatNight
 */
public class GetPhotoPresenterImp implements IGetPhotoPresenter{





	@Override
	public void downLoad(final String url, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn;
				URL url1;
				try {
					Thread.sleep(1000);
					url1=new URL(url);
					conn=(HttpURLConnection)url1.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(8000);

					InputStreamReader in=new InputStreamReader(conn.getInputStream());
					BufferedReader bf=new BufferedReader(in);
					StringBuilder result=new StringBuilder();
					String len;
					while ((len=bf.readLine())!=null){
						result.append(len);
					}
					jsonUtil(result.toString(),handler);
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendEmptyMessage(ConstantData.FAILED_CONNECT);

				}
			}
		}).start();
	}

	@Override
	public void jsonUtil(String result, Handler handler) {
		List<PhotoData> photoDatas=new ArrayList<PhotoData>();
		try {
			JSONArray jsonArray=new JSONArray(result);
			int i=0;
			while(!jsonArray.isNull(i)){
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				PhotoData photoData=new PhotoData();
				photoData.setmImage(ConstantData.URL_PHOTO_DOWNLOAD_GET+jsonObject.getString("photoPath"));
				photoData.setmDetail(jsonObject.getString("description"));
				String dateTime=jsonObject.getString("uploadDate");
				String[] timeFormat=dateTime.split(" ");
				timeFormat[1]=timeFormat[1].substring(0,timeFormat[1].lastIndexOf(":"));
				photoData.setmTime(timeFormat[1]+" "+timeFormat[0]);
				photoData.setmOpen(jsonObject.getInt("open"));
				JSONObject userObject=jsonObject.getJSONObject("user");
				photoData.setmIcon(ConstantData.URL_ICON+userObject.getString("image"));
				photoDatas.add(photoData);
				i++;
			}
			Message msg=new Message();
			msg.what= ConstantData.DOWNLOAD_SUCCESS;
			msg.obj=photoDatas;
			handler.sendMessage(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
