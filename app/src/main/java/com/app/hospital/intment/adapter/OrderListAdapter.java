package com.app.hospital.intment.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.OrderInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class OrderListAdapter extends BaseQuickAdapter<OrderInfo, BaseViewHolder> {
    public OrderListAdapter() {
        super(R.layout.order_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, OrderInfo orderInfo) {
        baseViewHolder.setText(R.id.doctor_name, "预约医生：" + orderInfo.getDoctor_name());
        baseViewHolder.setText(R.id.department_name, "科室：" + orderInfo.getDepart_name());
        baseViewHolder.setText(R.id.order_time, "就诊时间：" + orderInfo.getOrder_time() + "【" + orderInfo.getTime_state() + "】");
        baseViewHolder.setText(R.id.create_time, orderInfo.getCreate_time());
        baseViewHolder.setText(R.id.order_number, "就诊号：" + orderInfo.getOrder_number());
        baseViewHolder.setText(R.id.username, "就诊人：" + orderInfo.getUsername());
        baseViewHolder.setText(R.id.order_price, "就诊费用：￥" + orderInfo.getOrder_price());
        baseViewHolder.setText(R.id.age, "年龄：" + orderInfo.getAge() + "(岁)");
        baseViewHolder.setText(R.id.gender, "性别：" + orderInfo.getGender() + "");

        if (orderInfo.getOrder_state() == 0) {
            baseViewHolder.setText(R.id.pay_state, "待支付");
            baseViewHolder.setBackgroundColor(R.id.pay_state, Color.parseColor("#FF9800"));

            baseViewHolder.setTextColor(R.id.state, Color.parseColor("#018786"));
        } else if (orderInfo.getOrder_state() == 1) {
            baseViewHolder.setText(R.id.pay_state, "支付成功");
            baseViewHolder.setBackgroundColor(R.id.pay_state, Color.parseColor("#018786"));

            baseViewHolder.setTextColor(R.id.state, Color.parseColor("#018786"));
        } else {
            baseViewHolder.setText(R.id.pay_state, "预约取消");
            baseViewHolder.setBackgroundColor(R.id.pay_state, Color.parseColor("#999999"));

            baseViewHolder.setTextColor(R.id.state, Color.parseColor("#666666"));
        }


    }
}
