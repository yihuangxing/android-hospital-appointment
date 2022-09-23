package com.app.hospital.intment.entity;

import java.io.Serializable;

public class DoctorInfo implements Serializable {
    private int uid;
    private String depart_name;
    private String doctor_name;
    private String doctor_avatar;
    private String good_at;


    public DoctorInfo(int uid, String depart_name, String doctor_name, String doctor_avatar, String good_at) {
        this.uid = uid;
        this.depart_name = depart_name;
        this.doctor_name = doctor_name;
        this.doctor_avatar = doctor_avatar;
        this.good_at = good_at;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_avatar() {
        return doctor_avatar;
    }

    public void setDoctor_avatar(String doctor_avatar) {
        this.doctor_avatar = doctor_avatar;
    }

    public String getGood_at() {
        return good_at;
    }

    public void setGood_at(String good_at) {
        this.good_at = good_at;
    }
}
