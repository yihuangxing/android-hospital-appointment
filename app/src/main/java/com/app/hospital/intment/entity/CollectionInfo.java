package com.app.hospital.intment.entity;

/**
 * desc   :
 */
public class CollectionInfo {
    private int uid;
    private String doctor_name;
    private String depart_name;
    private String good_at;
    private String doctor_avatar;
    private String username;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDepart_name() {
        return depart_name;
    }

    public void setDepart_name(String depart_name) {
        this.depart_name = depart_name;
    }

    public String getGood_at() {
        return good_at;
    }

    public void setGood_at(String good_at) {
        this.good_at = good_at;
    }

    public String getDoctor_avatar() {
        return doctor_avatar;
    }

    public void setDoctor_avatar(String doctor_avatar) {
        this.doctor_avatar = doctor_avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
