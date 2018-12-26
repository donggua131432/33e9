package com.e9cloud.demo.cb;

/**
 * Created by Administrator on 2016/2/17.
 */
public class TLV
{
    private  int type ;
    private  int length;
    private  String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int tlvLen()
    {
        return  4+ 4 + value.getBytes().length;
    }
}
