package com.e9cloud.mybatis.domain;

import java.io.Serializable;

/**
 * 中继群
 */
public class RelayGroup implements Serializable {

    // 中继群类型
    private String g;

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public RelayGroup () {
    }

    public static RelayGroup newInstance(String g) {
        if ("1".equals(g)) {
            return new RelayGroup1();
        }

        if ("2".equals(g)) {
            return new RelayGroup2();
        }

        if ("3".equals(g)) {
            return new RelayGroup3();
        }

        return new RelayGroup();
    }

    public <T> T converto(Class<T> entityClass){
        return (T)this;
    }

    public <T> T converto(){
        if ("1".equals(g)) {
            return (T)converto(RelayGroup1.class);
        }
        if ("2".equals(g)) {
            return (T)converto(RelayGroup2.class);
        }
        if ("3".equals(g)) {
            return (T)converto(RelayGroup3.class);
        }
        return (T)this;
    }
}