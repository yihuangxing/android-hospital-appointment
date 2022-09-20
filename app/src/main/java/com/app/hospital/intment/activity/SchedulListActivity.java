package com.app.hospital.intment.activity;


import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.ManagerSchedulListAdapter;
import com.app.hospital.intment.adapter.SchedulListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.SchedulInfo;
import com.app.hospital.intment.entity.SchedulInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

/**
 * 管理员端       医生排班信息
 */
public class SchedulListActivity extends BaseActivity {
    private DoctorInfo doctorInfo;

    private RecyclerView recyclerView;
    private ManagerSchedulListAdapter mSchedulListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_schedul_list;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mSchedulListAdapter = new ManagerSchedulListAdapter();
        recyclerView.setAdapter(mSchedulListAdapter);
    }

    @Override
    protected void setListener() {

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SchedulListActivity.this, UpdateSchedulListActivity.class);
                intent.putExtra("title", "添加排班");
                intent.putExtra("doctorInfo", doctorInfo);
                startActivityForResult(intent, 3000);
            }
        });

        mSchedulListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                SchedulInfo schedulInfo = (SchedulInfo) adapter.getData().get(position);
                Intent intent = new Intent(SchedulListActivity.this, UpdateSchedulListActivity.class);
                intent.putExtra("title", "修改排班");
                intent.putExtra("schedulInfo", schedulInfo);
                startActivityForResult(intent, 3000);
            }
        });

    }

    @Override
    protected void initData() {

        doctorInfo = (DoctorInfo) getIntent().getSerializableExtra("doctorInfo");
        if (doctorInfo != null) {
            toolbar.setTitle(doctorInfo.getDoctor_name() + "的排班");
            getSchedulListData(doctorInfo.getDoctor_name());
        }
    }


    /**
     * 获取排班数据
     */

    private void getSchedulListData(String doctor_name) {
        OkGo.<String>get(ApiConstants.SCHEDULLIST_URL)
                .params("doctor_name", doctor_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        SchedulInfoList schedulInfoList = GsonUtils.parseJson(response, SchedulInfoList.class);
                        mSchedulListAdapter.setList(schedulInfoList.getList());

                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 3000) {
            initData();
        }
    }
}