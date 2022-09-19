package com.app.hospital.intment.entity;

import java.util.List;

public class SchedulInfoList {
    private List<SchedulInfo> list;

    public SchedulInfoList(List<SchedulInfo> list) {
        this.list = list;
    }

    public List<SchedulInfo> getList() {
        return list;
    }

    public void setList(List<SchedulInfo> list) {
        this.list = list;
    }
}
