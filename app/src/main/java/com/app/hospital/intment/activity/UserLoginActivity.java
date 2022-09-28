package com.app.hospital.intment.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.CodeUtils;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;

/**
 * 用户登录
 */
public class UserLoginActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText show_code;
    private ImageView img_code;
    private String realCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        img_code = findViewById(R.id.img_code);
        show_code = findViewById(R.id.show_code);


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
                String code_str = show_code.getText().toString().trim();
                if (TextUtils.isEmpty(username_str) || TextUtils.isEmpty(password_str) || TextUtils.isEmpty(code_str)) {
                    showToast("请完善登录信息");
                } else if (!code_str.equals(realCode)) {
                    showToast("验证码错误");
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


        //超级管理员
        findViewById(R.id.super_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this, SuperManagerLoginActivity.class));
            }
        });

        findViewById(R.id.img_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_code.setImageBitmap(CodeUtils.getInstance().createBitmap());
                realCode = CodeUtils.getInstance().getCode().toLowerCase();
            }
        });


    }

    @Override
    protected void initData() {

        img_code.setImageBitmap(CodeUtils.getInstance().createBitmap());
        realCode = CodeUtils.getInstance().getCode().toLowerCase();
    }

    /**
     * 登录
     */
    private void login(String username, String password) {
        OkGo.<String>get(ApiConstants.LOGIN_URL)
                .params("username", username)
                .params("password", password)
                .params("register_type", 0)
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