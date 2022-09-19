package com.app.hospital.intment.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.SchedulInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class SchedulListAdapter extends BaseQuickAdapter<SchedulInfo, BaseViewHolder> {
    public SchedulListAdapter() {
        super(R.layout.schedul_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, SchedulInfo schedulInfo) {

        baseViewHolder.setText(R.id.day, schedulInfo.getDoctor_day());

        if (schedulInfo.getAm_state() == 0) {
            baseViewHolder.setText(R.id.time_am, schedulInfo.getTime_am() + "\n(可预约)");
            baseViewHolder.setBackgroundColor(R.id.time_am, Color.parseColor("#ffffff"));
            baseViewHolder.setTextColor(R.id.time_am,Color.parseColor("#018786"));
        } else {
            baseViewHolder.setText(R.id.time_am, schedulInfo.getTime_am() + "\n(已满)");
            baseViewHolder.setBackgroundColor(R.id.time_am, Color.parseColor("#f5f5f5"));
            baseViewHolder.setTextColor(R.id.time_am,Color.parseColor("#999999"));
        }

        if (schedulInfo.getPm_state() == 0) {
            baseViewHolder.setText(R.id.time_pm, schedulInfo.getTime_pm() + "\n(可预约)");
            baseViewHolder.setBackgroundColor(R.id.time_pm, Color.parseColor("#ffffff"));
            baseViewHolder.setTextColor(R.id.time_pm,Color.parseColor("#018786"));
        } else {
            baseViewHolder.setText(R.id.time_pm, schedulInfo.getTime_pm() + "\n(已满)");
            baseViewHolder.setBackgroundColor(R.id.time_pm, Color.parseColor("#f5f5f5"));
            baseViewHolder.setTextColor(R.id.time_pm,Color.parseColor("#999999"));
        }
    }
}
