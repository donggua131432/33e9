package com.e9cloud.core.page.datatable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class DataTableWrapper implements Serializable{

    private Integer draw;

    private Long recordsTotal;

    private Long recordsFiltered;

    private List<?> aaData;

    private String error;

    private Object DT_RowId;

    private Object DT_RowClass;

    private Object DT_RowData;

    private Object DT_RowAttr;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<?> getAaData() {
        return aaData;
    }

    public void setAaData(List<?> aaData) {
        this.aaData = aaData;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getDT_RowId() {
        return DT_RowId;
    }

    public void setDT_RowId(Object DT_RowId) {
        this.DT_RowId = DT_RowId;
    }

    public Object getDT_RowClass() {
        return DT_RowClass;
    }

    public void setDT_RowClass(Object DT_RowClass) {
        this.DT_RowClass = DT_RowClass;
    }

    public Object getDT_RowData() {
        return DT_RowData;
    }

    public void setDT_RowData(Object DT_RowData) {
        this.DT_RowData = DT_RowData;
    }

    public Object getDT_RowAttr() {
        return DT_RowAttr;
    }

    public void setDT_RowAttr(Object DT_RowAttr) {
        this.DT_RowAttr = DT_RowAttr;
    }
}
