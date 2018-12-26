package com.e9cloud.mongodb.domain;

import com.e9cloud.core.util.Constants;

/**
 * Created by Administrator on 2016/8/6.
 */
public enum VType {

    datetime(Constants.datetime),
    date(Constants.date),
    time(Constants.time),
    string(""),
    number("");

    private String format;

    VType(String format) {
        this.format = format;
    }

    public String format() {
        return format;
    }
}
