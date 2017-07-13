package com.android.rdc.rdcblog.login.view;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Time:2016/7/31 16:38
 * Created By:ThatNight
 */
public class ActivityCollector {

	public static List<Activity> activities=new ArrayList<Activity>();

	public static void addActivity(Activity activity){
		activities.add(activity);
	}

	public static void removeActivity(Activity activity){
		activities.remove(activity);
	}

	public static void finishAll(){
		for(Activity activity:activities){
			if(!activity.isFinishing()){
				activity.finish();
			}
		}
	}
}
