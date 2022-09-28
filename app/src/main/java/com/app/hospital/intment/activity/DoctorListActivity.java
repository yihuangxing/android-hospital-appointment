package com.app.hospital.intment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.DoctorListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DoctorInfo;
import com.app.hospital.intment.entity.DoctorInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

/**
 * 科室下的医生列表
 */
public class DoctorListActivity extends BaseActivity {
    private String depart_name;

    private DoctorListAdapter mDoctorListAdapter;
    private RecyclerView recyclerView;

    private EditText doctor_name;

    private List<DoctorInfo> seachListData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_list;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        doctor_name = findViewById(R.id.doctor_name);
        depart_name = getIntent().getStringExtra("depart_name");
    }

    @Override
    protected void setListener() {
        //搜索
        findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = doctor_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    showToast("不能为空");
                } else {
                    queryListData(name);
                }
            }
        });

        doctor_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("-----------------", "onTextChanged: 2222222222222222222222");
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str = editable.toString();
                if (TextUtils.isEmpty(str)) {
                    getDoctorListData(depart_name);
                }

            }
        });

    }

    private void queryListData(String name) {
        if (null != mDoctorListAdapter) {
            seachListData.clear();
            List<DoctorInfo> data = mDoctorListAdapter.getData();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getDoctor_name().contains(name)) {
                    seachListData.add(data.get(i));
                }

            }

            mDoctorListAdapter.setList(seachListData);
        }
    }

    @Override
    protected void initData() {
        mDoctorListAdapter = new DoctorListAdapter();
        //列表点击事件
        mDoctorListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DoctorInfo doctorInfo = (DoctorInfo) adapter.getData().get(position);
                Intent intent = new Intent(DoctorListActivity.this, DoctorDetailActivity.class);
                intent.putExtra("info", doctorInfo);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mDoctorListAdapter);

        getDoctorListData(depart_name);

    }

    private void getDoctorListData(String depart_name) {
        OkGo.<String>get(ApiConstants.DOCTORLIST_URL)
                .params("depart_name", depart_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        DoctorInfoList doctorInfoList = GsonUtils.parseJson(response, DoctorInfoList.class);
                        mDoctorListAdapter.setList(doctorInfoList.getList());

                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }
}