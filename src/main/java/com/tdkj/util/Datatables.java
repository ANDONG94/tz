package com.tdkj.util;

import java.util.List;

/**
 * Created by len on 2018-03-09.
 */
public class Datatables<T> {
    //每页显示集合
    private List<T> data;//LIST（结果集）
    //总记录数
    private long recordsTotal;

    private long recordsFiltered;
    //请求次数
    private long draw;

    public Datatables() {

    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }
}
