package com.android.rdc.rdcblog.login.presenter.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.rdc.rdcblog.bloglist.view.activity.BlogListActivity;
import com.android.rdc.rdcblog.login.model.bean.UserBean;
import com.android.rdc.rdcblog.login.model.biz.IUser;
import com.android.rdc.rdcblog.login.model.biz.JsonUserUtil;
import com.android.rdc.rdcblog.login.model.biz.OnLoginListener;
import com.android.rdc.rdcblog.login.model.biz.UserImp;
import com.android.rdc.rdcblog.login.view.ILoginView;
import com.android.rdc.rdcblog.login.view.SignUpActivity;
import com.tencent.tauth.Tencent;

/**
 * Time:2016/7/24 15:05
 * Created By:ThatNight
 */
public class LoginPresenterImp implements ILoginPresenter {


	private ILoginView iLoginView;
	private IUser iUser;

	private Context mContext;
	private JsonUserUtil jsonUserUtil;

	private LoginSina loginSina;
	private LoginQQ loginQQ;

	private SharedPreferences sPef;
	private SharedPreferences.Editor editor;





	public LoginPresenterImp(ILoginView iLoginView, Context context) {
		this.iLoginView = iLoginView;
		iUser = new UserImp();
		mContext = context;
		jsonUserUtil=new JsonUserUtil();
		sPef=context.getSharedPreferences("pcInfo",Context.MODE_PRIVATE);

	}

	private Handler mHander = new Handler(){
		@Override
		public void handleMessage(Message msg) {

		}
	};


	@Override
	public void login() {
		iLoginView.setPbLogin(View.VISIBLE);
		iLoginView.setInputMethod();
		iUser.login(iLoginView.getName(), iLoginView.getPassword(), jsonUserUtil,mHander,new OnLoginListener() {
			@Override
			public void loginSuccess(final UserBean userBean) {
				mHander.post(new Runnable() {
					@Override
					public void run() {
						iLoginView.showToast("登录成功");
						Intent intent=new Intent(mContext, BlogListActivity.class);
						if(userBean!=null){
							Bundle bundle=new Bundle();
							bundle.putSerializable("userData",userBean);
							intent.putExtras(bundle);

							//保存用户信息
							editor=sPef.edit();
							editor.putString("nickname",userBean.getmNickName());
							editor.putString("email",userBean.getmEmail());
							editor.putInt("gender",userBean.getmGender());
							editor.putString("grade",userBean.getmGrade());
							editor.putString("field",userBean.getmField());
							editor.putString("direction",userBean.getmDirection());
							editor.putString("phone",userBean.getmPhone());
							editor.putString("icon",userBean.getmImage());
							editor.putString("workplace",userBean.getmWorkPlace());
							editor.putString("website",userBean.getmWebsite());
							editor.apply();
						}
						mContext.startActivity(intent);
						iLoginView.loginSuccess();
					}
				});
			}

			@Override
			public void loginFailed() {
				mHander.post(new Runnable() {
					@Override
					public void run() {
						iLoginView.loginFailed();
						iLoginView.setPbLogin(View.INVISIBLE);
						iLoginView.showToast("请检查用户名或密码");
					}
				});
			}
		});

	}


	@Override
	public void clear() {


	}

	@Override
	public void loginSina() {
		loginSina = new LoginSina(iLoginView,mContext);
		loginSina.thirdSinaLogin();
	}

	@Override
	public void loginQQ(Tencent mTencent) {
		loginQQ = new LoginQQ(mContext, mTencent);
		loginQQ.loginQQ();
	}

	@Override
	public void signUp() {
		Intent intent = new Intent(mContext, SignUpActivity.class);
		mContext.startActivity(intent);
	}

}
