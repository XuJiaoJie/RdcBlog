package com.android.rdc.rdcblog.login.view;

/**
 * Time:2016/7/28 23:18
 * Created By:ThatNight
 */
public interface  ISignUpView {

	String getName();
	String getPassword();
	String getEmail();
	String getInviteCode();


	void setNameError(String error);
	void setPasswordError(String error);
	void setEmailError(String error);
	void setCodeError(String error,boolean see);

	void signUpSuccess();
	void signUpFailed();
	void setInputMethod();
	void setPbVisiblity(int visible);


}
