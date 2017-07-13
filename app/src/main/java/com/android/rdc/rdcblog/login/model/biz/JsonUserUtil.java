package com.android.rdc.rdcblog.login.model.biz;

import com.android.rdc.rdcblog.login.model.bean.UserBean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Time:2016/9/10 16:25
 * Created By:ThatNight
 */
public class JsonUserUtil {

	UserBean userBean;

	public JsonUserUtil() {
		userBean=new UserBean();
	}

	public UserBean jsonPaser(String result) throws JSONException {
		JSONObject userObject=new JSONObject(result);
		userBean.setUserName(userObject.getString("username"));
		userBean.setmEmail(userObject.getString("email"));
		userBean.setmNickName(userObject.getString("nickname"));
		userBean.setmGender(userObject.getInt("gender"));
		userBean.setmGrade(userObject.getString("grade"));
		userBean.setmField(userObject.getString("field"));
		userBean.setmDirection(userObject.getString("direction"));
		userBean.setmPhone(userObject.getString("phone"));
		userBean.setmImage(userObject.getString("image"));
		userBean.setmWorkPlace(userObject.getString("workPlace"));
		userBean.setmWebsite(userObject.getString("website"));
		return userBean;
	}
}
