package com.app.hospital.intment.fragment;

import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.activity.DoctorListActivity;
import com.app.hospital.intment.adapter.DepartmentListAdapter;
import com.app.hospital.intment.base.BaseFragment;
import com.app.hospital.intment.entity.DepartmentInfo;
import com.app.hospital.intment.entity.DepartmentInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

public class HomeFragment extends BaseFragment {
    private DepartmentListAdapter mDepartmentListAdapter;

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        recyclerView = rootView.findViewById(R.id.recyclerView);

        mDepartmentListAdapter = new DepartmentListAdapter();

        recyclerView.setAdapter(mDepartmentListAdapter);

    }

    @Override
    protected void setListener() {


        //列表点击事件
        mDepartmentListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DepartmentInfo departmentInfo = (DepartmentInfo) adapter.getData().get(position);
                Intent intent = new Intent(getActivity(), DoctorListActivity.class);
                intent.putExtra("depart_name", departmentInfo.getDepartment_name());
                startActivity(intent);
            }
        });

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
                .execute(new HttpStringCallback(getActivity()) {
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