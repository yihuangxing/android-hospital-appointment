package com.app.hospital.intment.entity;

import java.util.List;

public class OrderInfoList {
    private List<OrderInfo> list;

    public OrderInfoList(List<OrderInfo> list) {
        this.list = list;
    }

    public List<OrderInfo> getList() {
        return list;
    }

    public void setList(List<OrderInfo> list) {
        this.list = list;
    }
}
