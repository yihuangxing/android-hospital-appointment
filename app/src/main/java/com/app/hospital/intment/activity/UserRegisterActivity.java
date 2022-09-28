package com.app.hospital.intment.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.http.HttpStringCallback;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lzy.okgo.OkGo;

/**
 * 用户端注册
 */
public class UserRegisterActivity extends BaseActivity {
    private EditText username;
    private EditText password;
    private EditText mobile;
    private EditText card;
    private EditText age;
    private TextView gender;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_register;
    }

    @Override
    protected void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        mobile = findViewById(R.id.mobile);
        card = findViewById(R.id.card);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
    }

    @Override
    protected void setListener() {
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username_str = username.getText().toString().trim();
                String password_str = password.getText().toString().trim();
                String mobile_str = mobile.getText().toString().trim();
                String card_str = card.getText().toString().trim();
                String age_str = age.getText().toString().trim();
                String gender_str = gender.getText().toString().trim();

                if (TextUtils.isEmpty(username_str) || TextUtils.isEmpty(password_str) || TextUtils.isEmpty(mobile_str) || TextUtils.isEmpty(card_str) || TextUtils.isEmpty(age_str) || TextUtils.isEmpty(gender_str)) {
                    showToast("请完善注册信息");
                } else {
                    register(username_str, password_str, mobile_str, card_str, age_str, gender_str);
                }
            }
        });

        findViewById(R.id.gender).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new XPopup.Builder(UserRegisterActivity.this)
                        .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                        .asBottomList("请选择性别", new String[]{"男", "女"},
                                null,
                                new OnSelectListener() {
                                    @Override
                                    public void onSelect(int position, String text) {
                                        gender.setText(text);
                                    }
                                })
                        .show();
            }
        });

    }

    @Override
    protected void initData() {

    }

    /***
     * 注册
     */
    public void register(String username, String password, String mobile, String card, String age, String gender) {
        OkGo.<String>get(ApiConstants.REGISTER_URL)
                .params("username", username)
                .params("password", password)
                .params("mobile", mobile)
                .params("register_type", 0)
                .params("card", card)
                .params("age", age)
                .params("gender", gender)
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