package com.android.rdc.rdcblog.login.presenter.login;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONObject;

/**
 * Time:2016/7/26 13:57
 * Created By:ThatNight
 */
public class LoginQQ {

	private static final String TAG = "LoginQQ";
	private UserInfo mInfo;
	private static Tencent mTencent;
	/** IWXAPI 是第三方app和微信通信的openapi接口 */
	private static boolean isServerSideLogin = false;
	private String openid = null;
	private Context mContext;


	public LoginQQ(Context context,Tencent tencent){
		mContext=context;
		mTencent=tencent;

	}


	public void loginQQ(){
		/** 判断是否登陆过 */
		if (!mTencent.isSessionValid()){
			mTencent.login((Activity)mContext, "all",loginListener);
		}/** 登陆过注销之后在登录 */
		else {
			mTencent.logout(mContext);
			mTencent.login((Activity) mContext, "all",loginListener);
		}
	}
	IUiListener loginListener = new BaseUiListener() {
		@Override
		protected void doComplete(JSONObject values) {
			initOpenidAndToken(values);
			updateUserInfo();
		}
	};
	/** QQ登录第二步：存储token和openid */
	public static void initOpenidAndToken(JSONObject jsonObject) {
		try {
			String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
			String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
			String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
			if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
				mTencent.setAccessToken(token, expires);
				mTencent.setOpenId(openId);
			}
		} catch(Exception e) {
		}
	}
	/** QQ登录第三步：获取用户信息 */
	private void updateUserInfo() {
		if (mTencent != null && mTencent.isSessionValid()) {
			IUiListener listener = new IUiListener() {
				@Override
				public void onError(UiError e) {
					Message msg = new Message();
					msg.obj = "把手机时间改成获取网络时间";
					msg.what = 1;
					mHandler.sendMessage(msg);
				}

				@Override
				public void onComplete(final Object response) {
					Message msg = new Message();
					msg.obj = response;
					msg.what = 0;
					mHandler.sendMessage(msg);
				}
				@Override
				public void onCancel() {
					Message msg = new Message();
					msg.obj = "获取用户信息失败";
					msg.what = 2;
					mHandler.sendMessage(msg);
				}
			};
			mInfo = new UserInfo(mContext, mTencent.getQQToken());
			mInfo.getUserInfo(listener);
		} else {

		}
	}
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				JSONObject response = (JSONObject) msg.obj;
				if (response.has("nickname")) {
					try {
						Log.d(TAG, "获取用户信息成功，返回结果："+response.toString());
						Log.d("QQ","登录成功\n"+"昵称:"+response.getString("nickname")+"\n头像地址:"+response.get("figureurl_qq_1"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else if(msg.what == 1){
				//mThirdLoginResult.setText(msg+"");
			}else if(msg.what == 2){
				//mThirdLoginResult.setText(msg+"");
			}
		}

	};
	/** QQ登录第一步：获取token和openid */
	private class BaseUiListener implements IUiListener {
		@Override
		public void onComplete(Object response) {
			if (null == response) {
				Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				Toast.makeText(mContext, "登录失败", Toast.LENGTH_SHORT).show();
				return;
			}

			Log.d(TAG, "QQ登录成功返回结果-" + response.toString());
			doComplete((JSONObject)response);
		}
		protected void doComplete(JSONObject response) {
			Log.d("QQSuccess","QQsuccess"+response.toString());
		}


		@Override
		public void onError(UiError e) {
			/*Util.toastMessage(MainActivity.this, "onError: " + e.errorDetail);
			Util.dismissDialog();*/
		}
		@Override
		public void onCancel() {
//			Util.toastMessage(MainActivity.this, "onCancel: ");
//			Util.dismissDialog();
			if (isServerSideLogin) {
				isServerSideLogin = false;
			}
		}
	}
	/** -------------------------QQ第三方登录结束-------------------- */




}
