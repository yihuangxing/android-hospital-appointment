package com.app.hospital.intment.activity;


import android.view.View;
import android.widget.TextView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.OrderInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.lzy.okgo.OkGo;

public class PayDialogActivity extends BaseActivity {
    private OrderInfo orderInfo;
    private TextView discount_price;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_dialog;
    }

    @Override
    protected void initView() {

        discount_price = findViewById(R.id.discount_price);
    }

    @Override
    protected void setListener() {

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != orderInfo) {
                    payOrder(orderInfo.getUid());
                }

            }
        });

    }

    private void payOrder(int _uid) {
        OkGo.<String>get(ApiConstants.PAY_ORDER_URL)
                .params("uid", _uid)
                .params("order_state", 1)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        setResult(8000);
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

        orderInfo = (OrderInfo) getIntent().getSerializableExtra("orderInfo");
        if (null != orderInfo) {
            discount_price.setText("￥" + orderInfo.getOrder_price());
        }
    }
}