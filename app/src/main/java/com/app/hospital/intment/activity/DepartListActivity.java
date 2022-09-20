package com.app.hospital.intment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lzy.okgo.OkGo;

/**
 * 科室列表
 */
public class DepartListActivity extends BaseActivity {

    private DepartmentListAdapter mDepartmentListAdapter;

    private RecyclerView recyclerView;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_depart_list;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mDepartmentListAdapter = new DepartmentListAdapter();

        //删除长按点击事件
        mDepartmentListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DepartListActivity.this);
                builder.setTitle("确定要删除吗？");
                builder.setMessage("删除后的数据将无法恢复");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DepartmentInfo departmentInfo = mDepartmentListAdapter.getData().get(position);
                        delDepart(departmentInfo.getUid(), position);
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
        mDepartmentListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                DepartmentInfo departmentInfo = mDepartmentListAdapter.getData().get(position);
                Intent intent = new Intent(DepartListActivity.this, UpdateDepartActivity.class);
                intent.putExtra("title", "修改科室数据");
                intent.putExtra("departmentInfo", departmentInfo);
                startActivityForResult(intent, 1000);
            }
        });



        recyclerView.setAdapter(mDepartmentListAdapter);
    }


    @Override
    protected void setListener() {

        //添加科室按钮点击事件
        findViewById(R.id.add_depart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DepartListActivity.this, UpdateDepartActivity.class);
                intent.putExtra("title", "添加科室数据");
                startActivityForResult(intent, 1000);

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


    /**
     * 删除科室信息
     */
    private void delDepart(int uid, int position) {
        OkGo.<String>get(ApiConstants.DELDEPARTMENT_URL)
                .params("uid", uid)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        mDepartmentListAdapter.removeAt(position);

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
        if (resultCode == 1000) {
            initData();
        }
    }
}