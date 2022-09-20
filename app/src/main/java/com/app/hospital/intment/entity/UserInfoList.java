package com.app.hospital.intment.entity;

import java.util.List;

/**
 * desc   :
 */
public class UserInfoList {
    private List<UserInfo> list;

    public UserInfoList(List<UserInfo> list) {
        this.list = list;
    }

    public List<UserInfo> getList() {
        return list;
    }

    public void setList(List<UserInfo> list) {
        this.list = list;
    }
}
