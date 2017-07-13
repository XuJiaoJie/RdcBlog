package com.android.rdc.rdcblog.login.view;

import android.graphics.Bitmap;

/**
 * Time:2016/7/24 15:03
 * Created By:ThatNight
 */
public interface ILoginView {

	String getName();

	String getPassword();

	void clear();

	void showToast(String str);

	void setIcon(Bitmap bitmap);

	void setPbLogin(int visiblity);


	void loginSuccess();
	void loginFailed();

	void setInputMethod();
}
