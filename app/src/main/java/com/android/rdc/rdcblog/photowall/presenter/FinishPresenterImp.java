package com.android.rdc.rdcblog.photowall.presenter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.photowall.model.PhotoData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Time:2016/8/29 22:19
 * Created By:ThatNight
 */
public class FinishPresenterImp implements IFinishPresenter {

	private Context mContext;
	private static final String CHARSET = "UTF-8";
	private static final String PREFIX = "--";
	private static final String LINE_END = "\r\n";
	private static final String BOUNDARY = UUID.randomUUID().toString();


	public FinishPresenterImp(Context context) {
		mContext = context;
	}

	public FinishPresenterImp() {
	}

	@Override
	public Intent saveData(String s, Bitmap mBitmap, String fileName, Handler handler) {
		PhotoData photoData = new PhotoData();
		photoData.setmDetail(s);
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putParcelable("PhotoData", photoData);
		intent.putExtras(bundle);

		File file = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			if (fos != null)
				fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		updateData(s, file, handler);
		return intent;
	}

	@Override
	public void updateData(final String s, final File file, final Handler handler) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpURLConnection conn = null;
				URL url = null;
				try {
					url = new URL(ConstantData.URL_PHOTO_UPLOAD);
					conn = (HttpURLConnection) url.openConnection();
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(8000);
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.setUseCaches(false);
					conn.setRequestMethod("POST");

					conn.setRequestProperty("Charset", CHARSET);
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + BOUNDARY);

					Map<String, String> params = new HashMap<String, String>();
					params.put("description", s);
					params.put("open", 1 + "");
					params.put("user.userId", ConstantData.userID + "");

					if (file != null) {
						DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
						StringBuffer sb = new StringBuffer();
						for (Map.Entry<String, String> entry : params.entrySet()) {
							sb.append(PREFIX);
							sb.append(BOUNDARY);
							sb.append(LINE_END);
							sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINE_END);
							sb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
							sb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
							sb.append(LINE_END);
							sb.append(entry.getValue());
							sb.append(LINE_END);
							Log.d("text", "params value: " + entry.getValue());
							dos.write(sb.toString().getBytes());
						}
						StringBuffer sbFile = new StringBuffer();
						sbFile.append(PREFIX);
						sbFile.append(BOUNDARY);
						sbFile.append(LINE_END);
						sbFile.append("Content-Disposition:form-data;name=\"files\";filename=\"" + file.getName() + "\"" + LINE_END);
						//sbFile.append("Content-Type: application/octet-stream;charset=" + CHARSET + LINE_END);
						sbFile.append("Content-Type: image/jpg" + LINE_END);
						sbFile.append(LINE_END);
						dos.write(sbFile.toString().getBytes());
						InputStream is = new FileInputStream(file);
						byte[] bytes = new byte[1024];
						int len = 0;
						while ((len = is.read(bytes)) != -1) {
							dos.write(bytes, 0, len);
						}
						is.close();

						dos.write(LINE_END.getBytes());
						byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
						dos.write(end_data);
						dos.flush();
						dos.close();

						InputStream in = conn.getInputStream();
						StringBuffer result = new StringBuffer();
						int rd;
						while ((rd = in.read()) != -1) {
							result.append((char) rd);
						}


						jsonUtil(result.toString(), handler);
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

	@Override
	public void jsonUtil(String result, Handler handler) {
		try {
			JSONObject json = new JSONObject(result);
			if ("error".equals(json.getString("status"))) {
				Message msg = new Message();
				msg.what = ConstantData.UPLOAD_ERROR;
				msg.obj = json.getString("reason");
				handler.sendMessage(msg);
			}
			if ("success".equals(json.getString("status"))) {
				handler.sendEmptyMessage(ConstantData.UPLOAD_SUCCESS);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}
}
