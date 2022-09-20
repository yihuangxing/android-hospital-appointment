package com.app.hospital.intment.activity;


import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.SelectDoctorListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.entity.UserInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;


/**
 * 选择医生
 */
public class SelectDoctorActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private SelectDoctorListAdapter mSelectDoctorListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_doctor;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mSelectDoctorListAdapter = new SelectDoctorListAdapter();
        recyclerView.setAdapter(mSelectDoctorListAdapter);
    }

    @Override
    protected void setListener() {

        mSelectDoctorListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                UserInfo userInfo = mSelectDoctorListAdapter.getData().get(position);

                Intent intent = getIntent();
                intent.putExtra("doctor_name", userInfo.getUsername());
                setResult(5000, intent);
                finish();
            }
        });

    }

    @Override
    protected void initData() {
        getRegisterDoctorList();
    }

    /**
     * 获取医生注册数据
     */

    private void getRegisterDoctorList() {
        OkGo.<String>get(ApiConstants.REGISTER_DOCTOR_URL)
                .params("register_type", 1)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        UserInfoList userInfoList = GsonUtils.parseJson(response, UserInfoList.class);
                        mSelectDoctorListAdapter.setList(userInfoList.getList());
                    }

                    @Override
                    protected void onError(String response) {

                    }
                });
    }
}