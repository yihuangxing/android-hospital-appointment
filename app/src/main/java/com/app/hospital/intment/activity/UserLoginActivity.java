package com.app.hospital.intment.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;

/**
 * 用户登录
 */
public class UserLoginActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText mobile;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);

    }

    @Override
    protected void setListener() {

        //注册
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this, UserRegisterActivity.class));
            }
        });


        //登录
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_str = username.getText().toString().trim();
                String password_str = password.getText().toString().trim();
                if (TextUtils.isEmpty(username_str) || TextUtils.isEmpty(password_str)) {
                    showToast("请完善登录信息");
                } else {
                    login(username_str, password_str);
                }
            }
        });

        //切换
        findViewById(R.id.switchover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this, DoctorLoginActivity.class));
            }
        });


    }

    @Override
    protected void initData() {

    }

    /**
     * 登录
     */
    private void login(String username, String password) {
        OkGo.<String>get(ApiConstants.LOGIN_URL)
                .params("username", username)
                .params("password", password)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        UserInfo userInfo = GsonUtils.parseJson(response, UserInfo.class);
                        ApiConstants.setUserInfo(userInfo);
                        if (userInfo != null) {
                            if (userInfo.getRegister_type() == 0) {
                                startActivity(new Intent(UserLoginActivity.this, UserMainActivity.class));
                            } else if (userInfo.getRegister_type() == 1) {
                                startActivity(new Intent(UserLoginActivity.this, DoctorMainActivity.class));
                            }
                        }
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }

}