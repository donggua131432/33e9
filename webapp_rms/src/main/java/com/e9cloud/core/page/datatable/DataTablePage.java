package com.e9cloud.core.page.datatable;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/19.
 */
public class DataTablePage implements Serializable {

    private int draw;

    private List<Column> columns = new ArrayList<>();

    private int start;

    private int length;

    private Search search;

    private long _t;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public long get_t() {
        return _t;
    }

    public void set_t(long _t) {
        this._t = _t;
    }
}
