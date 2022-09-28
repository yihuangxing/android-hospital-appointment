package com.app.hospital.intment.adapter;

import androidx.annotation.NonNull;

import com.app.hospital.intment.R;
import com.app.hospital.intment.entity.UserInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * desc   :
 */
public class UserListAdapter extends BaseQuickAdapter<UserInfo, BaseViewHolder> {
    public UserListAdapter() {
        super(R.layout.user_list_item);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UserInfo userInfo) {
        baseViewHolder.setText(R.id.username, "用户名：" + userInfo.getUsername());
        baseViewHolder.setText(R.id.mobile, "手机号：" + userInfo.getMobile());


    }
}
