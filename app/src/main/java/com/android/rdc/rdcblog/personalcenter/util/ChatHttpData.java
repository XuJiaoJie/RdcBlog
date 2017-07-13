package com.android.rdc.rdcblog.personalcenter.util;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.rdc.rdcblog.config.ConstantData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Time:2016/9/3 22:06
 * Created By:ThatNight
 */
public class ChatHttpData {

	JsonContent jsonContent;

	public ChatHttpData() {
		jsonContent = new JsonContent();
	}

	public void getMsg(final int sendId, final int receiveId, final Handler handler, final boolean isConnect) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				URL url = null;
				HttpURLConnection conn = null;
				try {

					while (isConnect) {
						url = new URL("http://10.21.20.114:8080/rdc_blog/msg/showMsg/" + ConstantData.userID + "/" + receiveId);
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						InputStream is = conn.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = "";
						String result = "";
						while ((line = br.readLine()) != null) {
							result += line;
						}
						jsonContent.jsonUtil(result, handler);
						Thread.sleep(5000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();
				}
			}
		}).start();
	}

	public void sendMsg(final String content,final int receiveId, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String result = "";
				URL url = null;
				HttpURLConnection conn = null;
				try {
					String data = "message=" + URLEncoder.encode(content, "utf-8") + "&send.userId=" + ConstantData.userID + "&receive.userId=" +receiveId ;
					Thread.sleep(350);
					url = new URL(ConstantData.URL_CHAT_POST);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					Log.d("asd", conn.getRequestMethod());
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setDoOutput(true);
					conn.setDoInput(true);
					OutputStream os = conn.getOutputStream();
					os.write(data.getBytes());
					os.flush();
					InputStream is = conn.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line = "";
					while ((line = br.readLine()) != null) {
						result += line;
					}
					Log.d("result", result);
					JSONObject jsonObject = new JSONObject(result);
					if ("success".equals(jsonObject.getString("status"))) {
						handler.sendEmptyMessage(ConstantData.SEND_SUCCESS);
					}


				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null)
						conn.disconnect();

				}

			}
		}).start();
	}


	public void getUser(final int userId, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {

				URL url = null;
				HttpURLConnection conn = null;
				try {
					Thread.sleep(350);
					url = new URL(ConstantData.URL_CHAT_GET + userId);
					conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setDoOutput(true);
					conn.setDoInput(true);

					while (true) {
						InputStream is = conn.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(is));
						String line = "";
						String result = "";
						while ((line = br.readLine()) != null) {
							result += line;
							Message msg = new Message();
							msg.what = 1;
							msg.obj = line;
							handler.sendMessage(msg);
							Log.d("result", result);
						}
						Thread.sleep(2000);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
