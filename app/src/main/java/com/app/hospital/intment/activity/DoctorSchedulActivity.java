package com.app.hospital.intment.activity;

import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.SchedulListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.SchedulInfoList;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;

/**
 * 医生自己的排班
 */
public class DoctorSchedulActivity extends BaseActivity {

    private RecyclerView recyclerView;

    private SchedulListAdapter mSchedulListAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_schedul;
    }

    @Override
    protected void initView() {

        recyclerView = findViewById(R.id.recyclerView);

        mSchedulListAdapter = new SchedulListAdapter();
        recyclerView.setAdapter(mSchedulListAdapter);

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (null != userInfo) {
            getSchedulListData(userInfo.getUsername());
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