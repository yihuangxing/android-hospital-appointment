package com.app.hospital.intment.entity;

import java.util.List;

/**
 * desc   :
 */
public class DepartmentInfoList {
    private List<DepartmentInfo> list;

    public DepartmentInfoList(List<DepartmentInfo> list) {
        this.list = list;
    }

    public List<DepartmentInfo> getList() {
        return list;
    }

    public void setList(List<DepartmentInfo> list) {
        this.list = list;
    }
}
