package com.app.hospital.intment.entity;

import java.util.List;

/**
 * desc   :
 */
public class CollectionInfoList {
    private List<CollectionInfo> list;

    public CollectionInfoList(List<CollectionInfo> list) {
        this.list = list;
    }

    public List<CollectionInfo> getList() {
        return list;
    }

    public void setList(List<CollectionInfo> list) {
        this.list = list;
    }
}
