package com.app.hospital.intment.adapter;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.DoctorInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class DoctorListAdapter extends BaseQuickAdapter<DoctorInfo, BaseViewHolder> {
    public DoctorListAdapter() {
        super(R.layout.doctor_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, DoctorInfo doctorInfo) {

        baseViewHolder.setText(R.id.doctor_name, doctorInfo.getDoctor_name());
        baseViewHolder.setText(R.id.depart_name, "科室：" + doctorInfo.getDepart_name());
        baseViewHolder.setText(R.id.good_at, "擅长：" + doctorInfo.getGood_at());

    }
}
