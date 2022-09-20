package com.app.hospital.intment.activity;

import android.content.Intent;
import android.view.View;

import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;

/**
 * 超级管理员
 */
public class SuperManagerActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_super_manager;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void setListener() {

        findViewById(R.id.depart_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SuperManagerActivity.this, DepartListActivity.class));
            }
        });

        findViewById(R.id.doctor_manager).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SuperManagerActivity.this, DoctorAllListActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}