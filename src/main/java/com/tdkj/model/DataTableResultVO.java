package com.tdkj.model;

import java.util.List;

/**
 * Created by len on 2019-04-28.
 */
public class DataTableResultVO<T> {
    private int draw;//请求的次数
    private int recordsTotal;//返回的记录总数
    private int recordsFiltered; //过滤后的数据总数
    private List<T> data; // 显示到页面的数据
    private String thname;//返回的列名

    public DataTableResultVO() {}

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(int recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(int recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getThname() {
        return thname;
    }

    public void setThname(String thname) {
        this.thname = thname;
    }
}
