package com.app.hospital.intment.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.lzy.okgo.OkGo;

public class UpdatePwdActivity extends BaseActivity {
    private EditText password, new_password;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pwd;
    }

    @Override
    protected void initView() {
        password = findViewById(R.id.password);
        new_password = findViewById(R.id.new_password);

    }

    @Override
    protected void setListener() {

        //点击修改
        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwd = password.getText().toString();
                String new_pwd = new_password.getText().toString();
                if (TextUtils.isEmpty(pwd) || TextUtils.isEmpty(new_pwd)) {
                    showToast("请完善信息");
                    return;
                }
                if (!pwd.equals(new_pwd)) {
                    showToast("两次密码不一致");
                    return;
                }

                updatePwd(pwd);

            }
        });

    }

    private void updatePwd(String pwd) {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (null != userInfo) {
            OkGo.<String>post(ApiConstants.EDIT_URL)
                    .params("uid", userInfo.getUid())
                    .params("password", pwd)
                    .execute(new HttpStringCallback(this) {
                        @Override
                        protected void onSuccess(String msg, String response) {
                            showToast(msg);
                            setResult(6000);
                            finish();
                        }

                        @Override
                        protected void onError(String response) {
                            showToast(response);
                        }
                    });
        }
    }

    @Override
    protected void initData() {

    }
}