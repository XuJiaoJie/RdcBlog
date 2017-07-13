package com.android.rdc.rdcblog.login.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.view.activity.BlogListActivity;
import com.android.rdc.rdcblog.login.presenter.signup.ISignUpPresenter;
import com.android.rdc.rdcblog.login.presenter.signup.SignUpPresenterImp;

public class SignUpActivity extends Activity implements ISignUpView{

	private boolean nameError,psError,emailError,codeError;
	private TextInputLayout tilName,tilPassword,tilEmail,tilInviteCode;
	private EditText etName,etPassword,etEmail,etCode;
	private DynamicButton btnSignUp;
	private ProgressBar pbSignUp;
	private ISignUpPresenter suPresenter;
	private Button btnBackUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//判断是否全屏，任务栏沉浸式
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
			window.setNavigationBarColor(Color.TRANSPARENT);
		}
		setContentView(R.layout.activity_sign_up);
		init();
	}

	private void init() {

		tilName=(TextInputLayout)findViewById(R.id.til_signup_name);
		tilPassword=(TextInputLayout)findViewById(R.id.til_signup_password);
		tilEmail=(TextInputLayout)findViewById(R.id.til_signup_email);
		tilInviteCode=(TextInputLayout)findViewById(R.id.til_signup_invitecode);

		etName=tilName.getEditText();
		etPassword=tilPassword.getEditText();
		etEmail=tilEmail.getEditText();
		etCode=tilInviteCode.getEditText();

		btnSignUp=(DynamicButton)findViewById(R.id.btn_signup);
		suPresenter=new SignUpPresenterImp(this,this);
		pbSignUp=(ProgressBar)findViewById(R.id.pb_signup);
		btnBackUp=(Button)findViewById(R.id.btn_signup_backlogin);
		ActivityCollector.addActivity(this);

		codeError=emailError=codeError=nameError=false;
		setNameError("");
		setPasswordError("");
		setEmailError("");
		setCodeError("",true);

	}


	public void onSignUpClick(View view) {
		switch (view.getId()) {
			case R.id.btn_signup_backlogin:
				finish();
				break;
			case R.id.btn_signup:
				if(etName.getText().toString().equals("")||etPassword.getText().toString().equals("")
						||etEmail.getText().toString().equals("")||etCode.getText().toString().equals(""))
				{
					btnSignUp.signUpFailedAnim(btnSignUp);

				}else if(!nameError&&!psError&&!emailError&&!codeError) {
					suPresenter.signUp();
					btnSignUp.loginAnim(btnSignUp);
				}else {
					btnSignUp.signUpFailedAnim(btnSignUp);
				}
				break;
		}
	}

	@Override
	public String getName() {
		return etName.getText().toString().trim();
	}

	@Override
	public String getPassword() {
		return etPassword.getText().toString().trim();

	}

	@Override
	public String getEmail() {
		return etEmail.getText().toString().trim();
	}

	@Override
	public String getInviteCode() {
		return etCode.getText().toString().trim();
	}

	@Override
	public void setNameError(String error) {
		if(!"用户名已存在".equals(error)){
			etName.addTextChangedListener(new TextWatcher() {
				@Override
				public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

				}

				@Override
				public void onTextChanged(CharSequence s, int i, int i1, int i2) {
					if(!s.toString().matches("^[a-z[A-Z]0-9_-]{6,16}")){
						tilName.setErrorEnabled(true);
						tilName.setError("用户名不小于6位,由数字、字母和下划线组成");
						nameError=true;
					}else {
						nameError=false;
						tilName.setErrorEnabled(false);
					}
					if(s.length()>0&&btnBackUp.getVisibility()==View.VISIBLE){
						btnBackUp.setVisibility(View.GONE);
					}
				}

				@Override
				public void afterTextChanged(Editable editable) {

				}
			});
		}else {
			nameError=true;
			tilName.setErrorEnabled(true);
			tilName.setError(error);
		}
	}

	@Override
	public void setPasswordError(String error) {
		etPassword.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence s, int i, int i1, int i2) {
				if(s.length()<6||s.length()>16){
					psError=true;
					tilPassword.setErrorEnabled(true);
					tilPassword.setError("密码不能小于6位，大于16位");
				}else {
					psError=false;
					tilPassword.setErrorEnabled(false);
				}
				if(s.length()>0&&btnBackUp.getVisibility()==View.VISIBLE){
					btnBackUp.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	@Override
	public void setEmailError(String error) {
		etEmail.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence s, int i, int i1, int i2) {
				if(!s.toString().matches("^\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?")){
					emailError=true;
					tilEmail.setErrorEnabled(true);
					tilEmail.setError("请输入正确的邮箱地址");
				}else {
					emailError=false;
					tilEmail.setErrorEnabled(false);
				}if(s.length()>0&&btnBackUp.getVisibility()==View.VISIBLE){
					btnBackUp.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}

	@Override
	public void setCodeError(final String error, final boolean see) {
		tilInviteCode.setErrorEnabled(see);
		if(see){
			codeError=true;
			tilInviteCode.setError(error);
		}else{
			codeError=false;
		}
		etCode.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence s, int i, int i1, int i2) {
				if(s.length()>0&&btnBackUp.getVisibility()==View.VISIBLE){
					btnBackUp.setVisibility(View.GONE);
				}
				if(see==true) {
					codeError=false;
					tilInviteCode.setErrorEnabled(false);
				}
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		});
	}


	@Override
	public void signUpSuccess() {
		Intent intent=new Intent(SignUpActivity.this,BlogListActivity.class);
		startActivity(intent);
		ActivityCollector.finishAll();
		Toast.makeText(SignUpActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void signUpFailed() {
		btnSignUp.signUpFailedAnim(btnSignUp);
		Toast.makeText(SignUpActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void setInputMethod() {
		InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etPassword.getWindowToken(),0);
	}

	@Override
	public void setPbVisiblity(int visible) {
		pbSignUp.setVisibility(visible);
	}

}
