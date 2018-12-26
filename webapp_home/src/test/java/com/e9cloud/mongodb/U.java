package com.e9cloud.mongodb;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/2/1.
 */
public class U implements Serializable{

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
