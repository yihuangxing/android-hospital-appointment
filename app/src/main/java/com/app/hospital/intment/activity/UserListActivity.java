package com.app.hospital.intment.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.app.hospital.intment.ApiConstants;
import com.app.hospital.intment.R;
import com.app.hospital.intment.adapter.UserListAdapter;
import com.app.hospital.intment.base.BaseActivity;
import com.app.hospital.intment.entity.UserInfo;
import com.app.hospital.intment.entity.UserInfoList;
import com.app.hospital.intment.http.HttpStringCallback;
import com.app.hospital.intment.utils.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.lzy.okgo.OkGo;

/**
 * 用户管理
 */
public class UserListActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private UserListAdapter mUserListAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mUserListAdapter = new UserListAdapter();
        mRecyclerView.setAdapter(mUserListAdapter);
    }

    @Override
    protected void setListener() {

        mUserListAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                UserInfo userInfo = mUserListAdapter.getData().get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(UserListActivity.this);
                builder.setTitle("确定要删除吗？");
                builder.setMessage("删除后的数据将无法恢复");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delUser(userInfo.getUid(), position);
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

    private void delUser(int uid, int position) {
        OkGo.<String>get(ApiConstants.DEL_USER_URL)
                .params("uid", uid)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        showToast(msg);
                        mUserListAdapter.removeAt(position);
                    }

                    @Override
                    protected void onError(String response) {
                        showToast(response);
                    }
                });
    }

    @Override
    protected void initData() {
        queryListData();
    }

    private void queryListData() {
        OkGo.<String>get(ApiConstants.USER_LIST_URL)
                .execute(new HttpStringCallback(this) {
                    @Override
                    protected void onSuccess(String msg, String response) {
                        UserInfoList userInfoList = GsonUtils.parseJson(response, UserInfoList.class);
                        mUserListAdapter.setList(userInfoList.getList());
                    }

                    @Override
                    protected void onError(String response) {

                    }
                });
    }
}