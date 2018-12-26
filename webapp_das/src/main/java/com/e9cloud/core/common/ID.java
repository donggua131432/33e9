package com.e9cloud.core.common;

import org.apache.commons.lang3.StringUtils;

/**
 * 类作用：产生各种表的主键
 *
 * @author wzj 使用说明：
 */
public class ID {

    /**
     * 32 位UUID,移除了"-"
     *
     * @return UUID
     */
    public static String randomUUID() {
        String uuid = java.util.UUID.randomUUID().toString();
        return StringUtils.remove(uuid, '-');
    }

    /**
     * 36 位UUID,移除了"-",并添加四位表头
     *
     * @return UUID
     */
    public static String randomUUIDWithHead(String idType) {
        return StringUtils.join(idType, randomUUID());
    }

}
