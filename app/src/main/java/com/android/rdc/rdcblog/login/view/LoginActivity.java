package com.android.rdc.rdcblog.login.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.rdc.rdcblog.R;
import com.android.rdc.rdcblog.bloglist.view.defined.RoundImageView;
import com.android.rdc.rdcblog.login.presenter.login.ILoginPresenter;
import com.android.rdc.rdcblog.login.presenter.login.LoginPresenterImp;
import com.tencent.tauth.Tencent;

import cn.sharesdk.framework.ShareSDK;

/**
 * 登陆activity
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {


    private boolean firstOpen = false;
    private EditText etName, etPassword;
    private TextInputLayout tilName, tilPassword;
    private ProgressBar pbLogin;
    private ILoginPresenter loginPresenter;
    private DynamicButton btnLogin;
    private RoundImageView rivIcon;
    private TextView tvForget;
    private ImageView ivLaunch;
    private static final String TAG = "LoginActivity";
    private static Tencent mTencent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ShareSDK.initSDK(this);
        mTencent = Tencent.createInstance("1105565512", this.getApplicationContext());
        taskTransparent();
        setContentView(R.layout.activity_login);
        init();
    }


    //沉浸式任务栏
    private void taskTransparent() {
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
    }


    @Override
    protected void onDestroy() {
        ShareSDK.stopSDK(this);
        super.onDestroy();
    }

    //初始化控件
    private void init() {
        tvForget = (TextView) findViewById(R.id.tv_login_forget);
        tilName = (TextInputLayout) findViewById(R.id.til_login_name);
        tilPassword = (TextInputLayout) findViewById(R.id.til_login_pw);
        etName = tilName.getEditText();
        etPassword = tilPassword.getEditText();
        loginPresenter = new LoginPresenterImp(this, this);
        pbLogin = (ProgressBar) findViewById(R.id.pb_login_login);
        btnLogin = (DynamicButton) findViewById(R.id.btn_login);
        //rivIcon=(RoundImageView)findViewById(R.id.riv_login_icon);
        etName.addTextChangedListener(new NameTextWatcher());
        ivLaunch = (ImageView) findViewById(R.id.iv_launch);
        ActivityCollector.addActivity(this);
    }


    //监听EditText
    private class NameTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (s.length() < 6 && s.length() > 0 && etName.isFocused()) {
                tilName.setErrorEnabled(true);
                tilName.setError("用户名不能小于6位");
            } else {
                tilName.setErrorEnabled(false);
            }
            if (etName.isFocused()) {
                tvForget.setVisibility(View.VISIBLE);
            } else {
                tvForget.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {


        }
    }


    public void onLoginClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                loginPresenter.login();
                btnLogin.loginAnim(btnLogin);
                break;
            case R.id.btn_login_weibo:
                loginPresenter.loginSina();
                break;
            case R.id.btn_login_qq:
                loginPresenter.loginQQ(mTencent);
                break;
            case R.id.btn_login_signtext:
                loginPresenter.signUp();
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
    public void clear() {

    }

    @Override
    public void showToast(String str) {
        Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setIcon(Bitmap bitmap) {
        rivIcon.setImageBitmap(bitmap);
    }

    public void setPbLogin(int visiblity) {
        pbLogin.setVisibility(visiblity);
    }

    @Override
    public void loginSuccess() {
        finish();
    }

    @Override
    public void loginFailed() {
        btnLogin.failedAnim(btnLogin);
    }

    @Override
    public void setInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
    }


}
