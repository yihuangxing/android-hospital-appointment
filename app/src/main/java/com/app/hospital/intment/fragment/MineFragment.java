package com.app.hospital.intment.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.activity.AgreementActivity;
import com.app.hospital.intment.activity.CollectionActivity;
import com.app.hospital.intment.activity.UpdatePwdActivity;
import com.app.hospital.intment.base.BaseFragment;
import com.app.hospital.intment.entity.UserInfo;

public class MineFragment extends BaseFragment {
    private TextView mobile, username;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mobile = rootView.findViewById(R.id.mobile);
        username = rootView.findViewById(R.id.username);
    }

    @Override
    protected void setListener() {

        rootView.findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确定要退出登录吗？");
                builder.setMessage("退出登录，将清空用户登录数据");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showToast("退出登录成功");
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
            }
        });

        rootView.findViewById(R.id.agreement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgreementActivity.class));
            }
        });

        rootView.findViewById(R.id.pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), UpdatePwdActivity.class), 6000);
            }
        });

        //收藏
        rootView.findViewById(R.id.collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getActivity(), CollectionActivity.class));
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        UserInfo userInfo = ApiConstants.getUserInfo();
        if (null != userInfo) {
            username.setText(userInfo.getUsername());
            mobile.setText("手机号：" + userInfo.getMobile());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 6000 && null != getActivity()) {
            getActivity().finish();
        }
    }
}