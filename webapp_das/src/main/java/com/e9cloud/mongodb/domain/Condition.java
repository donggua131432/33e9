package com.e9cloud.mongodb.domain;

import com.e9cloud.core.util.Tools;
import org.springframework.data.mongodb.core.query.Criteria;

import java.io.Serializable;
import java.lang.reflect.Array;

/**
 * mongo 条件查询
 * Created by Administrator on 2016/8/4.
 */
public class Condition implements Serializable {

    // 列名
    private String key;

    // 多个 key 时
    private String keys;

    // 值
    private String value;

    // 区间值 （小）
    private String min;

    // 区间值 （大）
    private String max;

    // 条件（大于(gt)，小于(lt)，等于(is), gte, lte, ne，in, nin 。。。）
    private String symbol = "is";

    // 值得类型 (datetime(yyyy-MM-dd hh:mm:ss), date(yyyy-MM-dd), time(hh:mm:ss), number, string)
    private String type = "string";

    // 空值转换
    private String ifNull;

    public Condition() {}

    public Condition(String key, String value) {
        this.key = key;
        this.value = value;
    }

    // Condition 转换为 Criteria
    public Criteria toCriteria(){
        Criteria criteria = new Criteria();

        String[] keyArr = converterKey();
        Criteria[] criterias = new Criteria[keyArr.length];

        if (Tools.isNotNullArray(keyArr)) {
            for (int i = 0; i < keyArr.length; i ++) {
                Criteria cri = new Criteria(keyArr[i]);
                fillCri(cri);
                criterias[i] = cri;
            }
        }

        return criterias.length > 1 ? criteria.orOperator(criterias) : criterias[0];
    }

    private void fillCri(Criteria criteria) {
        switch (getSymbol()) {
            case "lt" : criteria.lt(converterValue(max));break;
            case "gt" : criteria.gt(converterValue(min));break;
            case "lte" : criteria.lte(converterValue(max));break;
            case "gte" : criteria.gte(converterValue(min));break;
            case "between" : criteria.gte(converterValue(min)).lte(converterValue(max));break;
            case "in" :
                Object o = converterValue(value);
                Object[] objs = toObjectArray(o);
                if (objs.length > 1) {
                    criteria.in(objs);
                } else {
                    criteria.in(objs[0]);
                }
                break;
            case "nin" :
                Object oo = converterValue(value);
                Object[] oobjs = toObjectArray(oo);
                if (oobjs.length > 1) {
                    criteria.nin(oobjs);
                } else {
                    criteria.nin(oobjs[0]);
                }
                break;
            default : criteria.is(converterValue(value));
        }
    }

    // key 转换
    private String[] converterKey(){
        String[] keyArr = null;
        if (Tools.isNotNullStr(key)) {
            keyArr = new String[]{ key };
        } else if (Tools.isNotNullStr(keys)){
            keyArr = Tools.stringToArray(keys, ",");
        }
        return keyArr;
    }

    // 值转换
    private Object converterValue(String v){

        switch (type) {
            case "datetime" : return Tools.parseDate(v);
            case "date" : return Tools.parseDate(v, VType.date.format());
            case "time" : return Tools.parseDate(v, VType.time.format());
            case "number" : return Integer.parseInt(v);
            case "float" : return Double.parseDouble(v);
            case "array" : if (v.contains(",")) return Tools.stringToArray(v, ","); return v;
            case "boolean" : return Boolean.parseBoolean(v);
        }

        return v;
    }

    private Object[] toObjectArray(Object o){

        if (o.getClass().isArray()){
            int length = Array.getLength(o);
            Object[] objs = new Object[length];
            for (int i=0; i<length; i++) {
                objs[i] = Array.get(o, i);
            }
            return objs;
        }
        return new Object[]{o};
    }

    /* ----------------------- 以下是setter和getter -------------------------- */

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys == null ? null : keys.trim();
    }

    public String getValue() {
        return value;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min == null ? null : min.trim();
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max == null ? null : max.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getIfNull() {
        return ifNull;
    }

    public void setIfNull(String ifNull) {
        this.ifNull = ifNull;
    }

    public String getSymbol() {
        if (Tools.isNotNullStr(max) && Tools.isNotNullStr(min)){
            symbol = "between";
        }
        if (Tools.isNotNullStr(max) && Tools.isNullStr(min)){
            symbol = "lte";
        }
        if (Tools.isNullStr(max) && Tools.isNotNullStr(min)){
            symbol = "gte";
        }
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNull() {
        return Tools.isNullStr(getValue()) && Tools.isNullStr(getMin()) && Tools.isNullStr(getMax());
    }
}
