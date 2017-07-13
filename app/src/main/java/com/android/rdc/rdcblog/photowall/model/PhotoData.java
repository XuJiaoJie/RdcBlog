package com.android.rdc.rdcblog.photowall.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Time:2016/7/31 17:36
 * Created By:ThatNight
 */
public class PhotoData implements Parcelable, Serializable {

	private String mImage;
	private String mDetail;
	private String mComment;
	private String mTime;
	private String mName;
	private String mIcon;
	private int mOpen;
	private int mUserId;


	public String getmImage() {
		return mImage;
	}

	public void setmImage(String mImage) {
		this.mImage = mImage;
	}

	public String getmDetail() {
		return mDetail;
	}

	public void setmDetail(String mDetail) {
		this.mDetail = mDetail;
	}

	public String getmComment() {
		return mComment;
	}

	public void setmComment(String mComment) {
		this.mComment = mComment;
	}

	public String getmTime() {
		return mTime;
	}

	public void setmTime(String mTime) {
		this.mTime = mTime;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public String getmIcon() {
		return mIcon;
	}

	public void setmIcon(String mIcon) {
		this.mIcon = mIcon;
	}

	public static final Creator<PhotoData> CREATOR = new Creator<PhotoData>() {
		@Override
		public PhotoData createFromParcel(Parcel in) {
			PhotoData photoData = new PhotoData();
			photoData.mImage = in.readString();
			photoData.mDetail = in.readString();
			photoData.mTime = in.readString();
			photoData.mOpen = in.readInt();
			photoData.mIcon=in.readString();
			return photoData;
		}

		@Override
		public PhotoData[] newArray(int size) {
			return new PhotoData[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(mImage);
		parcel.writeString(mDetail);
		parcel.writeString(mTime);
		parcel.writeInt(mOpen);
		parcel.writeString(mIcon);

	}

	public int getmOpen() {
		return mOpen;
	}

	public void setmOpen(int mOpen) {
		this.mOpen = mOpen;
	}

	public int getmUserId() {
		return mUserId;
	}

	public void setmUserId(int mUserId) {
		this.mUserId = mUserId;
	}
}
