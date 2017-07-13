package com.android.rdc.rdcblog.login.model.biz;

import android.os.Handler;
import android.util.Log;

import com.android.rdc.rdcblog.config.ConstantData;
import com.android.rdc.rdcblog.login.model.bean.UserBean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Time:2016/7/24 15:23
 * Created By:ThatNight
 */
public class UserImp implements IUser {


	private static final String TAG = "UserImp";
	private UserBean userBean;

	@Override
	public void login(final String name, final String password, final JsonUserUtil jsonUserUtil, final Handler handler, final OnLoginListener loginListener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				/*String data = "username=" + name + "&password=" + password;
				HttpURLConnection conn = null;
				URL url = null;
				String result = "";
				OutputStream os = null;
				InputStream is = null;
				try {
					Thread.sleep(350);
					url = new URL(ConstantData.urlLogin);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.getOutputStream();
					os = conn.getOutputStream();
					os.write(data.getBytes());
					os.flush();
					is = conn.getInputStream();
					BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is));
					String line = "";
					while ((line = bufferReader.readLine()) != null) {
						result += line;
					}
					Log.d("response", result);
					userBean = jsonUserUtil.jsonPaser(result);

					Thread.sleep(100);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
					try {
						if (os != null) {
							os.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				if ("error".equals(result)) {
					loginListener.loginFailed();
				} else if (userBean != null) {
					loginListener.loginSuccess(userBean);
				}*/
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				loginListener.loginSuccess(null);
			}
		}).start();
	}

	@Override
	public void sighUp(final String name, final String password, final String email, final String inviteCode, final OnSignUpListener onSignUpListener) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String data = "username=" + name + "&password=" + password + "&email=" + email + "&inviteCode=" + inviteCode;
				String result = "";
				URL url = null;
				HttpURLConnection conn = null;
				try {
					Thread.sleep(350);
					url = new URL(ConstantData.URL_SIGNUP);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("POST");
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
				} catch (Exception e) {
					e.printStackTrace();
				}
				if ("error".equals(result)) {
					onSignUpListener.signUpFailed(result);
				} else if (result != null) {
					ConstantData.userID = Integer.parseInt(result);
					onSignUpListener.signUpSuccess();
				}
			}
		}).start();
	}
}
