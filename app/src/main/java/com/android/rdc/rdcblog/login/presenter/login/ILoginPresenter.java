package com.android.rdc.rdcblog.login.presenter.login;

import com.tencent.tauth.Tencent;

/**
 * Time:2016/7/24 15:05
 * Created By:ThatNight
 */
public interface ILoginPresenter {

	void login();
	void clear();

	void loginSina();
	void loginQQ(Tencent mTencent);

	void signUp();

}
