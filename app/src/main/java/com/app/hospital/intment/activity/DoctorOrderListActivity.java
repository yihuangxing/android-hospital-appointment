package com.app.hospital.intment.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.OrderListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.OrderInfoList;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.lzy.okgo.OkGo;

/***
 * 医生的预约记录
 */
public class DoctorOrderListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private OrderListAdapter mOrderListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_order_list;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mOrderListAdapter = new OrderListAdapter();
        recyclerView.setAdapter(mOrderListAdapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (null != userInfo) {
            queryOrderList(userInfo.getUsername());
        }
    }

    private void queryOrderList(String username) {
        OkGo.<String>get(ApiConstants.QUERY_ORDER_LIST_URL)
                .params("doctor_name", username)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        OrderInfoList orderInfoList = GsonUtils.parseJson(response, OrderInfoList.class);
                        mOrderListAdapter.setList(orderInfoList.getList());
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }
}