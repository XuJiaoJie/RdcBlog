package com.android.rdc.rdcblog.login.presenter.signup;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.rdc.rdcblog.login.model.biz.IUser;
import com.android.rdc.rdcblog.login.model.biz.OnSignUpListener;
import com.android.rdc.rdcblog.login.model.biz.UserImp;
import com.android.rdc.rdcblog.login.view.ISignUpView;

/**
 * Time:2016/7/28 22:02
 * Created By:ThatNight
 */
public class SignUpPresenterImp implements ISignUpPresenter {

	private ISignUpView iSignUpView;
	private Context mContext;
	private IUser user;
	private Handler mHandler=new Handler();


	public SignUpPresenterImp(ISignUpView iSignUpView, Context context) {
		mContext=context;
		this.iSignUpView=iSignUpView;
		user=new UserImp();
	}



	@Override
	public void signUp() {
		iSignUpView.setPbVisiblity(View.VISIBLE);
		iSignUpView.setInputMethod();
		user.sighUp(iSignUpView.getName(),
				iSignUpView.getPassword(),
				iSignUpView.getEmail(),
				iSignUpView.getInviteCode(),
				new OnSignUpListener() {
			@Override
			public void signUpSuccess() {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						iSignUpView.signUpSuccess();
						iSignUpView.setPbVisiblity(View.INVISIBLE);
					}
				});

			}

			@Override
			public void signUpFailed(final String result) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						iSignUpView.signUpFailed();
						Log.d("failedResult",result);
						if ("error:inviteCode".equals(result)){
							iSignUpView.setCodeError("邀请码错误",true);
						}else{
							iSignUpView.setCodeError("",false);
						}
						 if ("error:userExist".equals(result)) {
							 iSignUpView.setNameError("用户名已存在");
						}
						iSignUpView.setPbVisiblity(View.INVISIBLE);
					}
				});

			}
		});
	}
}
