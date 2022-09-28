package com.app.hospital.intment.activity;


import android.content.Intent;
import android.view.View;

import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;


/**
 * 用户端注册
 */
public class DoctorMainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {
        findViewById(R.id.my_schedul).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorMainActivity.this, DoctorSchedulActivity.class));
            }
        });

        findViewById(R.id.doctor_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorMainActivity.this, DoctorOrderListActivity.class));
            }
        });

    }

    @Override
    protected void initData() {

    }
}