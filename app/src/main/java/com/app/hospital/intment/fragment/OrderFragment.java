package com.app.hospital.intment.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.activity.PayDialogActivity;
import com.app.hospital.intment.adapter.OrderListAdapter;
import com.app.hospital.intment.base.BaseFragment;
import com.app.hospital.intment.entity.OrderInfo;
import com.app.hospital.intment.entity.OrderInfoList;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lzy.okgo.OkGo;

public class OrderFragment extends BaseFragment {
    private OrderListAdapter mOrderListAdapter;
    private RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initView() {
        recyclerView = rootView.findViewById(R.id.recyclerView);
    }

    @Override
    protected void setListener() {
        mOrderListAdapter = new OrderListAdapter();
        recyclerView.setAdapter(mOrderListAdapter);


        //点击事件
        mOrderListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                OrderInfo orderInfo = (OrderInfo) adapter.getData().get(position);
                if (orderInfo.getOrder_state() == 0) {
                    new XPopup.Builder(getContext())
                            .isDestroyOnDismiss(true) //对于只使用一次的弹窗，推荐设置这个
                            .asBottomList("请选择支付渠道", new String[]{"支付宝支付", "微信支付"},
                                    null,
                                    new OnSelectListener() {
                                        @Override
                                        public void onSelect(int position, String text) {
                                            Intent intent = new Intent(getActivity(), PayDialogActivity.class);
                                            intent.putExtra("orderInfo", orderInfo);
                                            startActivityForResult(intent, 8000);
                                        }
                                    })
                            .show();


                }
            }
        });

        mOrderListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OrderInfo orderInfo = (OrderInfo) adapter.getData().get(position);
                if (orderInfo.getOrder_state() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("操作");
                    builder.setMessage("确定需要取消预约码？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelOrder(orderInfo.getUid(), -1);
                        }
                    });
                    builder.show();
                } else if (orderInfo.getOrder_state() == -1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("警告");
                    builder.setMessage("您已取消，确认是否再次预约？");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancelOrder(orderInfo.getUid(), 0);
                        }
                    });
                    builder.show();
                }

                return true;
            }
        });
    }

    @Override
    protected void initData() {

        queryOrderList();

    }

    private void queryOrderList() {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (userInfo != null) {
            OkGo.<String>get(ApiConstants.QUERY_ORDER_LIST_URL)
                    .params("username", userInfo.getUsername())
                    .execute(new HttpStringCallback(getActivity()) {
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

    public void refreshData() {
        initData();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8000) {
            initData();
        }
    }

    /**
     * 取消预约
     */
    private void cancelOrder(int _uid, int order_state) {
        OkGo.<String>get(ApiConstants.PAY_ORDER_URL)
                .params("uid", _uid)
                .params("order_state", order_state)
                .execute(new HttpStringCallback(getActivity()) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        initData();
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }
}