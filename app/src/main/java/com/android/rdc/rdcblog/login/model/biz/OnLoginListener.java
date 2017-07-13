package com.android.rdc.rdcblog.login.model.biz;

import com.android.rdc.rdcblog.login.model.bean.UserBean;

/**
 * Time:2016/7/24 15:35
 * Created By:ThatNight
 */
public interface OnLoginListener {
	void loginSuccess(UserBean userBean);
	void loginFailed();

}
