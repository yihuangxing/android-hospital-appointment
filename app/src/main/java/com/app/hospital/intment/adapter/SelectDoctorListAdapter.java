package com.app.hospital.intment.adapter;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class SelectDoctorListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    public SelectDoctorListAdapter() {
        super(R.layout.department_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UserInfo userInfo) {
        baseViewHolder.setText(R.id.department_name, userInfo.getUsername());
    }
}
