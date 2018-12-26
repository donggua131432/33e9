package com.e9cloud.core.page.datatable;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/19.
 */
public class Search implements Serializable {

    private String value;

    private Boolean regex;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getRegex() {
        return regex;
    }

    public void setRegex(Boolean regex) {
        this.regex = regex;
    }
}
