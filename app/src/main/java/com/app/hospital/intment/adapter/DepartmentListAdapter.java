package com.app.hospital.intment.adapter;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.DepartmentInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class DepartmentListAdapter extends BaseQuickAdapter<DepartmentInfo, BaseViewHolder> {
    public DepartmentListAdapter() {
        super(R.layout.department_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, DepartmentInfo departmentInfo) {

        baseViewHolder.setText(R.id.department_name, departmentInfo.getDepartment_name());

    }
}
