package com.e9cloud.core.office.excel.model;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/2/4.
 */
public class DataModel {

    String excelName;

    List<TH> titles;

    List<Map<String, Object>> data; // 表单数据

}
