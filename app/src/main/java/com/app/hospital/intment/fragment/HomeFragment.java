package com.app.hospital.intment.fragment;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.activity.DoctorListActivity;
import com.app.hospital.intment.adapter.DepartmentListAdapter;
import com.app.hospital.intment.base.BaseFragment;
import com.app.hospital.intment.entity.DepartmentInfo;
import com.app.hospital.intment.entity.DepartmentInfoList;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {
    private DepartmentListAdapter mDepartmentListAdapter;
    private EditText depart_name;
    private List<DepartmentInfo> seachListData = new ArrayList<>();
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
        depart_name = rootView.findViewById(R.id.depart_name);

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

        //搜索
        rootView.findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = depart_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("不能为空");
                } else {
                    queryListData(name);
                }
            }
        });

        depart_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (TextUtils.isEmpty(str)) {
                    getDepartmentList();
                }

            }
        });

    }
    private void queryListData(String name) {
        if (null != mDepartmentListAdapter) {
            seachListData.clear();
            List<DepartmentInfo> data = mDepartmentListAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getDepartment_name().contains(name)) {
                    seachListData.add(data.get(i));
                }
            }
            mDepartmentListAdapter.setList(seachListData);
        }
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