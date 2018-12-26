package com.e9cloud.demo.cb;

/**
 * Created by Administrator on 2016/2/17.
 */
public class TVObject
{

    private  int type;

    private  String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "TVObject{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
