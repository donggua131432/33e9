package com.e9cloud.mongodb.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ConditionWrapper {

    // mongo db 分页 条件
    private List<Condition> conds = Lists.newArrayList();

    public List<Condition> getConds() {
        return conds;
    }

    public void setConds(List<Condition> conds) {
        this.conds = conds;
    }
}
