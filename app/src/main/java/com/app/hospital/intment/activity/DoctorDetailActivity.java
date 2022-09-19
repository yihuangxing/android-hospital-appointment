package com.app.hospital.intment.activity;


import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.SchedulListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.SchedulInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;


/**
 * 排班
 */
public class DoctorDetailActivity extends BaseActivity {
    private DoctorInfo info;

    private TextView doctor_name;
    private TextView depart_name;
    private TextView good_at;

    private RecyclerView recyclerView;

    private SchedulListAdapter mSchedulListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_detail;
    }

    @Override
    protected void initView() {
        info = (DoctorInfo) getIntent().getSerializableExtra("info");

        doctor_name = findViewById(R.id.doctor_name);
        depart_name = findViewById(R.id.depart_name);
        good_at = findViewById(R.id.good_at);
        recyclerView = findViewById(R.id.recyclerView);

        if (null != info) {
            doctor_name.setText(info.getDoctor_name());
            depart_name.setText("科室：" + info.getDepart_name());
            good_at.setText("擅长：" + info.getGood_at());
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

        mSchedulListAdapter = new SchedulListAdapter();
        recyclerView.setAdapter(mSchedulListAdapter);

        if (null != info) {
            getSchedulListData(info.getDoctor_name());
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
}