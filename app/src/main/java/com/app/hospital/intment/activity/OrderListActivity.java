package com.app.hospital.intment.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.OrderListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.OrderInfo;
import com.app.hospital.intment.entity.OrderInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lzy.okgo.OkGo;

public class OrderListActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private OrderListAdapter mOrderListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        mOrderListAdapter = new OrderListAdapter();

        recyclerView.setAdapter(mOrderListAdapter);
    }

    @Override
    protected void setListener() {

        mOrderListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderInfo orderInfo = mOrderListAdapter.getData().get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderListActivity.this);
                builder.setTitle("确认删吗吗？");
                builder.setMessage("删除后的数据将无法恢复");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delOrder(orderInfo.getUid(), position);
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

    }

    @Override
    protected void initData() {
        queryOrderList();
    }

    private void queryOrderList() {
        OkGo.<String>get(ApiConstants.QUERY_ORDER_LIST_URL)
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

    private void delOrder(int uid, int position) {
        OkGo.<String>get(ApiConstants.DEL_ORDER_URL)
                .params("uid", uid)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        mOrderListAdapter.removeAt(position);
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });

    }
}