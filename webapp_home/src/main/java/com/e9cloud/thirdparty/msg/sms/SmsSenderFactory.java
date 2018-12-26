package com.e9cloud.thirdparty.msg.sms;

/**
 * Created by Administrator on 2016/1/11.
 */
public class SmsSenderFactory {

    /**
     * 服务邮箱
     */
    private static SimpleSmsSender serviceSms = null;

    /**
     * 获取邮箱
     *
     *            邮箱类型
     * @return 符合类型的邮箱
     */
    public static SimpleSmsSender getSender() {
        if (serviceSms == null) {
            serviceSms = new SimpleSmsSender("http://gateway.iems.net.cn/GsmsHttp", "69715:admin", "94182464", "001", 113);
        }
        return serviceSms;
    }

}
