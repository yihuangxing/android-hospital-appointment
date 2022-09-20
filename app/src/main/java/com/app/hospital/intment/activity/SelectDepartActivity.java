package com.app.hospital.intment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.DepartmentListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DepartmentInfo;
import com.app.hospital.intment.entity.DepartmentInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

/**
 * 选择科室
 */
public class SelectDepartActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private DepartmentListAdapter mDepartmentListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_depart;
    }

    @Override
    protected void initView() {

        recyclerView = findViewById(R.id.recyclerView);
        mDepartmentListAdapter = new DepartmentListAdapter();
        recyclerView.setAdapter(mDepartmentListAdapter);


        //点击事件
        mDepartmentListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DepartmentInfo departmentInfo = (DepartmentInfo) adapter.getData().get(position);

                Intent intent = getIntent();
                intent.putExtra("depart_name", departmentInfo.getDepartment_name());
                setResult(3000, intent);
                finish();
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        getDepartmentList();
    }

    /**
     * 获取科室列表
     */

    private void getDepartmentList() {
        OkGo.<String>get(ApiConstants.DEPARTMENT_URL)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        DepartmentInfoList departmentInfoList = GsonUtils.parseJson(response, DepartmentInfoList.class);
                        mDepartmentListAdapter.setList(departmentInfoList.getList());

                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }
}