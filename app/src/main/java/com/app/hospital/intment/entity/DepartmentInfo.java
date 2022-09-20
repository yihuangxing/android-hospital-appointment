package com.app.hospital.intment.entity;

import java.io.Serializable;

/**
 * desc   :
 */
public class DepartmentInfo implements Serializable {
    private int uid;
    private String department_name;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
