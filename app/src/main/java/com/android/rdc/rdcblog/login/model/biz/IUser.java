package com.android.rdc.rdcblog.login.model.biz;

import android.os.Handler;

/**
 * Time:2016/7/24 14:46
 * Created By:ThatNight
 */
public interface IUser {

	void login(String name, String password, JsonUserUtil jsonUserUtil,Handler handler,OnLoginListener loginListener);

	void sighUp(String name,String password,String email,String inviteCode,OnSignUpListener onSignUpListener);
}
