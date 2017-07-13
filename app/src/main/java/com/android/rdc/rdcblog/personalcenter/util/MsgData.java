package com.android.rdc.rdcblog.personalcenter.util;

/**
 * Time:2016/9/3 21:49
 * Created By:ThatNight
 */
public class MsgData  {

	private String mName;
	private String mDate;
	private String mContent;
	private String mIcon;
	private boolean isComeMsg=true;


	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmDate() {
		return mDate;
	}

	public void setmDate(String mDate) {
		this.mDate = mDate;
	}

	public String getmContent() {
		return mContent;
	}

	public void setmContent(String mContent) {
		this.mContent = mContent;
	}

	public boolean isComeMsg() {
		return isComeMsg;
	}

	public void setComeMsg(boolean comeMsg) {
		isComeMsg = comeMsg;
	}

	public String getmIcon() {
		return mIcon;
	}

	public void setmIcon(String mIcon) {
		this.mIcon = mIcon;
	}
}
