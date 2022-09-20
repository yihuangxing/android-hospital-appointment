package com.app.hospital.intment.entity;

import java.io.Serializable;

public class SchedulInfo implements Serializable {
    private int uid;
    private int doctor_id;
    private String doctor_name;
    private String doctor_day;
    private String time_am;
    private String time_pm;
    private String free_am;
    private String free_pm;
    private int am_state;
    private int pm_state;

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

    public String getDoctor_day() {
        return doctor_day;
    }

    public void setDoctor_day(String doctor_day) {
        this.doctor_day = doctor_day;
    }

    public String getTime_am() {
        return time_am;
    }

    public void setTime_am(String time_am) {
        this.time_am = time_am;
    }

    public String getTime_pm() {
        return time_pm;
    }

    public void setTime_pm(String time_pm) {
        this.time_pm = time_pm;
    }

    public String getFree_am() {
        return free_am;
    }

    public void setFree_am(String free_am) {
        this.free_am = free_am;
    }

    public String getFree_pm() {
        return free_pm;
    }

    public void setFree_pm(String free_pm) {
        this.free_pm = free_pm;
    }

    public int getAm_state() {
        return am_state;
    }

    public void setAm_state(int am_state) {
        this.am_state = am_state;
    }

    public int getPm_state() {
        return pm_state;
    }

    public void setPm_state(int pm_state) {
        this.pm_state = pm_state;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }
}
