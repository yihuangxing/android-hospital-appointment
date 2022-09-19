package com.app.hospital.intment.entity;

import java.util.List;

public class DoctorInfoList {
    private List<DoctorInfo> list;

    public DoctorInfoList(List<DoctorInfo> list) {
        this.list = list;
    }

    public List<DoctorInfo> getList() {
        return list;
    }

    public void setList(List<DoctorInfo> list) {
        this.list = list;
    }
}
