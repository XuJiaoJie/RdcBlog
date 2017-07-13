package com.android.rdc.rdcblog.login.model.bean;

import java.io.Serializable;

/**
 * Time:2016/7/24 14:46
 * Created By:ThatNight
 */
public class UserBean implements Serializable{


	private String userName;
	private String passWord;
	private String mEmail;
	private String mNickName;
	private int    mGender;
	private String mGrade;
	private String mField;
	private String mDirection;
	private String mPhone;
	private String mImage;
	private String mWorkPlace;
	private String mWebsite;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getmEmail() {
		return mEmail;
	}

	public void setmEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getmNickName() {
		return mNickName;
	}

	public void setmNickName(String mNickName) {
		this.mNickName = mNickName;
	}


	public String getmGrade() {
		return mGrade;
	}

	public void setmGrade(String mGrade) {
		this.mGrade = mGrade;
	}

	public String getmField() {
		return mField;
	}

	public void setmField(String mField) {
		this.mField = mField;
	}

	public String getmDirection() {
		return mDirection;
	}

	public void setmDirection(String mDirection) {
		this.mDirection = mDirection;
	}

	public String getmPhone() {
		return mPhone;
	}

	public void setmPhone(String mPhone) {
		this.mPhone = mPhone;
	}

	public String getmImage() {
		return mImage;
	}

	public void setmImage(String mImage) {
		this.mImage = mImage;
	}

	public String getmWorkPlace() {
		return mWorkPlace;
	}

	public void setmWorkPlace(String mWorkPlace) {
		this.mWorkPlace = mWorkPlace;
	}

	public String getmWebsite() {
		return mWebsite;
	}

	public void setmWebsite(String mWebsite) {
		this.mWebsite = mWebsite;
	}

	public int getmGender() {
		return mGender;
	}

	public void setmGender(int mGender) {
		this.mGender = mGender;
	}
}
