package com.app.hospital.intment.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.DepartmentInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.lzy.okgo.OkGo;

/***
 * 添加和修改科室数据
 */
public class UpdateDepartActivity extends BaseActivity {
    private EditText department_name;
    private Button update;
    private DepartmentInfo departmentInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_depart;
    }

    @Override
    protected void initView() {
        department_name = findViewById(R.id.department_name);
        update = findViewById(R.id.update);
    }

    @Override
    protected void setListener() {

        findViewById(R.id.update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String depart_name = department_name.getText().toString().trim();
                if (TextUtils.isEmpty(depart_name)) {
                    showToast("科室数据不能为空");
                } else {
                    if (departmentInfo != null) {
                        updateDepart(departmentInfo.getUid(), depart_name);
                    } else {
                        addDepart(depart_name);
                    }
                }

            }
        });

    }

    /***
     * 修改
     */
    private void updateDepart(int uid, String depart_name) {
        OkGo.<String>get(ApiConstants.UPDATEDEPARTMENT_URL)
                .params("uid", uid)
                .params("department_name", depart_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        setResult(1000);
                        finish();
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }

    /**
     * 添加
     */

    private void addDepart(String depart_name) {
        OkGo.<String>get(ApiConstants.ADDDEPARTMENT_URL)
                .params("department_name", depart_name)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        setResult(1000);
                        finish();
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }

    @Override
    protected void initData() {
        String title = getIntent().getStringExtra("title");
        departmentInfo = (DepartmentInfo) getIntent().getSerializableExtra("departmentInfo");
        toolbar.setTitle(title);

        if (null != departmentInfo) {
            department_name.setText(departmentInfo.getDepartment_name());
            update.setText("修改");
        } else {
            update.setText("添加");
        }

    }
}