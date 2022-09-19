package com.app.hospital.intment.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.http.HttpStringCallback;
import com.lzy.okgo.OkGo;

public class DoctorRegisterActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText mobile;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_register;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);

    }

    @Override
    protected void setListener() {
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_str = username.getText().toString().trim();
                String password_str = password.getText().toString().trim();
                String mobile_str = mobile.getText().toString().trim();

                if (TextUtils.isEmpty(username_str) || TextUtils.isEmpty(password_str) || TextUtils.isEmpty(mobile_str)) {
                    showToast("请完善注册信息");
                } else {
                    register(username_str, password_str, mobile_str);
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    /***
     * 注册
     */
    public void register(String username, String password, String mobile) {
        OkGo.<String>get(ApiConstants.REGISTER_URL)
                .params("username", username)
                .params("password", password)
                .params("mobile", mobile)
                .params("register_type", 1)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        //注册成功跳转到登录页面登录
                        finish();
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }
}