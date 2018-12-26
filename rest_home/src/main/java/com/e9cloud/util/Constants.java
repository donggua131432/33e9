package com.e9cloud.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shenliang on 2016/9/30.
 */
public class Constants {

    public enum BusinessCode {
        REST("01", "专线语音 "),
        SIP("02", "SIP接口业务"),
        MASK("03", "私密专线"),
        CCENTER("04", "智能云调度"),
        CLOUDPHONE("05","云话机"),
        VOICE("06","语音通知"),
        VOICEVALIDATE("07","语音验证码");

        private String code;
        private String name;

        BusinessCode(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    public enum StatLogCode{
        //接口类型： 1、通用接口   2、专线语音  3、专号通  4、物联网卡  5、虚拟号   6、语音通知  7、SipPhone
        Calls_QueryOnlineConcurrent("1","瞬时在线并发通话数"),
        Calls_CancelCallback("1","取消通话"),
        Records_query("1","通话记录查询"),

        Calls_callBack("2","专线语音"),

        //Calls_sfForwardUpdate(""),
        Calls_maskCallback("3","专号通"),
        MaskNumber_apply("3","专号通号码申请"),
        MaskNumber_release("3","专号通号码释放"),
        MaskNumber_query("3","专号通号码查询"),
        MaskNumber_queryAll("3","应用映射号码查询"),

        Numbers_bind("4","物联网卡号码绑定"),
        Numbers_unBind("4","物联网卡号码解绑"),
        Numbers_queryBindInfo("4","物联网卡号码查询"),

        //VnRecord_query(""),
        VirtualNumber_apply("5","虚拟号码申请"),
        White_operate("5","虚拟号白名单"),

        VoiceNotify_request("6","语音通知下发"),
        VoiceNotify_cancel("6","取消预约语音通知"),
        VoiceNotify_queryRecord("6","语音通知记录查询"),

        SipPhone_callback("7","SipPhone回拨"),

        Voice_Validate("8","语音验证码");

        //Subid_create(""),
        //Subid_modify(""),
        //Subid_delete(""),
        //Subid_query(""),

        private String interfaceType;
        private String interfaceName;

        StatLogCode(String interfaceType, String interfaceName) {
            this.interfaceType = interfaceType;
            this.interfaceName = interfaceName;
        }
        public String getInterfaceType() {
            return interfaceType;
        }

        public String getInterfaceName() {
            return interfaceName;
        }
    }


    public static Map<String, String> getLogTypeAndName(String funStr){

        Map<String, String> map = new HashMap<>();
        String strType = "";
        String strName = "";
        if("Calls_QueryOnlineConcurrent".equals(funStr)){
            strType = Constants.StatLogCode.Calls_QueryOnlineConcurrent.getInterfaceType();
            strName = Constants.StatLogCode.Calls_QueryOnlineConcurrent.getInterfaceName();
        }else if("Calls_CancelCallback".equals(funStr)){
            strType = Constants.StatLogCode.Calls_CancelCallback.getInterfaceType();
            strName = Constants.StatLogCode.Calls_CancelCallback.getInterfaceName();
        }else if("Records_query".equals(funStr)){
            strType = Constants.StatLogCode.Records_query.getInterfaceType();
            strName = Constants.StatLogCode.Records_query.getInterfaceName();

        }else if("Calls_callBack".equals(funStr)){
            strType = Constants.StatLogCode.Calls_callBack.getInterfaceType();
            strName = Constants.StatLogCode.Calls_callBack.getInterfaceName();


        }else if("Calls_maskCallback".equals(funStr)){
            strType = Constants.StatLogCode.Calls_maskCallback.getInterfaceType();
            strName = Constants.StatLogCode.Calls_maskCallback.getInterfaceName();
        }else if("MaskNumber_apply".equals(funStr)){
            strType = Constants.StatLogCode.MaskNumber_apply.getInterfaceType();
            strName = Constants.StatLogCode.MaskNumber_apply.getInterfaceName();
        }else if("MaskNumber_release".equals(funStr)){
            strType = Constants.StatLogCode.MaskNumber_release.getInterfaceType();
            strName = Constants.StatLogCode.MaskNumber_release.getInterfaceName();
        }else if("MaskNumber_query".equals(funStr)){
            strType = Constants.StatLogCode.MaskNumber_query.getInterfaceType();
            strName = Constants.StatLogCode.MaskNumber_query.getInterfaceName();
        }else if("MaskNumber_queryAll".equals(funStr)){
            strType = Constants.StatLogCode.MaskNumber_queryAll.getInterfaceType();
            strName = Constants.StatLogCode.MaskNumber_queryAll.getInterfaceName();


        }else if("Numbers_bind".equals(funStr)){
            strType = Constants.StatLogCode.Numbers_bind.getInterfaceType();
            strName = Constants.StatLogCode.Numbers_bind.getInterfaceName();
        }else if("Numbers_unBind".equals(funStr)){
            strType = Constants.StatLogCode.Numbers_unBind.getInterfaceType();
            strName = Constants.StatLogCode.Numbers_unBind.getInterfaceName();
        }else if("Numbers_queryBindInfo".equals(funStr)){
            strType = Constants.StatLogCode.Numbers_queryBindInfo.getInterfaceType();
            strName = Constants.StatLogCode.Numbers_queryBindInfo.getInterfaceName();


        }else if("VirtualNumber_apply".equals(funStr)){
            strType = Constants.StatLogCode.VirtualNumber_apply.getInterfaceType();
            strName = Constants.StatLogCode.VirtualNumber_apply.getInterfaceName();
        }else if("White_operate".equals(funStr)){
            strType = Constants.StatLogCode.White_operate.getInterfaceType();
            strName = Constants.StatLogCode.White_operate.getInterfaceName();


        }else if("VoiceNotify_request".equals(funStr)){
            strType = Constants.StatLogCode.VoiceNotify_request.getInterfaceType();
            strName = Constants.StatLogCode.VoiceNotify_request.getInterfaceName();
        }else if("VoiceNotify_cancel".equals(funStr)){
            strType = Constants.StatLogCode.VoiceNotify_cancel.getInterfaceType();
            strName = Constants.StatLogCode.VoiceNotify_cancel.getInterfaceName();
        }else if("VoiceNotify_queryRecord".equals(funStr)){
            strType = Constants.StatLogCode.VoiceNotify_queryRecord.getInterfaceType();
            strName = Constants.StatLogCode.VoiceNotify_queryRecord.getInterfaceName();


        }else if("SipPhone_callback".equals(funStr)){
            strType = Constants.StatLogCode.SipPhone_callback.getInterfaceType();
            strName = Constants.StatLogCode.SipPhone_callback.getInterfaceName();

        }else if("VoiceValidate_requestCall".equals(funStr)){
            strType = Constants.StatLogCode.Voice_Validate.getInterfaceType();
            strName = Constants.StatLogCode.Voice_Validate.getInterfaceName();
        }

        map.put("strType", strType);
        map.put("strName", strName);

        return map;
    }
}
