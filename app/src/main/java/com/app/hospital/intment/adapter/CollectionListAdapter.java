package com.app.hospital.intment.adapter;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.CollectionInfo;
import com.app.hospital.intment.utils.GlideEngine;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class CollectionListAdapter extends BaseQuickAdapter<CollectionInfo, BaseViewHolder> {
    public CollectionListAdapter() {
        super(R.layout.doctor_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, CollectionInfo collectionInfo) {
        baseViewHolder.setText(R.id.doctor_name, collectionInfo.getDoctor_name());
        baseViewHolder.setText(R.id.depart_name, "科室：" + collectionInfo.getDepart_name());
        baseViewHolder.setText(R.id.good_at, "擅长：" + collectionInfo.getGood_at());

        GlideEngine.createGlideEngine().loadGridImage(getContext().getApplicationContext(), collectionInfo.getDoctor_avatar(), baseViewHolder.getView(R.id.doctor_avatar));
    }
}
