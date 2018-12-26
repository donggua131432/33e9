package com.e9cloud.core.freemarker;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/16.
 */
public class DataModelWrapper implements Serializable {

    private String stringTemplate;

    private Map<String,Object> dataModel;

    public DataModelWrapper(){}

    public DataModelWrapper(String stringTemplate,Map<String,Object> dataModel){
        this.stringTemplate = stringTemplate;
        this.dataModel = dataModel;
    }

    public String getStringTemplate() {
        return stringTemplate;
    }

    public void setStringTemplate(String stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    public Map<String, Object> getDataModel() {
        return dataModel;
    }

    public void setDataModel(Map<String, Object> dataModel) {
        this.dataModel = dataModel;
    }
}
