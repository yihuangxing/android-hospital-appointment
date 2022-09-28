package com.app.hospital.intment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lzy.okgo.OkGo;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理远端    所有医生列表
 */
public class DoctorAllListActivity extends BaseActivity {

    private DoctorListAdapter mDoctorListAdapter;
    private RecyclerView recyclerView;

    private EditText doctor_name;

    private List<DoctorInfo> seachListData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_all_list;
    }

    @Override
    protected void initView() {

        recyclerView = findViewById(R.id.recyclerView);
        doctor_name = findViewById(R.id.doctor_name);

    }

    @Override
    protected void setListener() {
        mDoctorListAdapter = new DoctorListAdapter();
        recyclerView.setAdapter(mDoctorListAdapter);
        //长按删除事件
        mDoctorListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorAllListActivity.this);
                builder.setTitle("确定要删除吗？");
                builder.setMessage("删除后的数据将无法恢复");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DoctorInfo doctorInfo = mDoctorListAdapter.getData().get(position);
                        delDepart(doctorInfo.getUid(), position);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
                return true;
            }
        });

        //点击修改事件
        mDoctorListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DoctorInfo doctorInfo = mDoctorListAdapter.getData().get(position);
                Intent intent = new Intent(DoctorAllListActivity.this, UpdateDoctorActivity.class);
                intent.putExtra("title", "修改医生数据");
                intent.putExtra("doctorInfo", doctorInfo);
                startActivityForResult(intent, 2000);
            }
        });


        //添加和修改医生点击事件
        findViewById(R.id.add_depart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DoctorAllListActivity.this, UpdateDoctorActivity.class);
                intent.putExtra("title", "添加医生数据");
                startActivityForResult(intent, 2000);
            }
        });

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
                    getDoctorListData();
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
        getDoctorListData();
    }


    /**
     * 获取所有医生数据
     */
    private void getDoctorListData() {
        OkGo.<String>get(ApiConstants.DOCTORLIST_URL)
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

    /**
     * 删除医生信息
     */
    private void delDepart(int uid, int position) {
        OkGo.<String>get(ApiConstants.DEL_DOCTOR_URL)
                .params("uid", uid)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        mDoctorListAdapter.removeAt(position);

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
        if (resultCode == 2000) {
            initData();
        }
    }
}