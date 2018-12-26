package com.e9cloud.core.util;

/**
 * 类作用：常量类 (集成了google-kaptcha)
 *
 * @author wzj
 */
public class Constants extends com.google.code.kaptcha.Constants{

    // 私有化，禁止外部实例化
    private Constants() {
    }

    /** -----------------------认证图片的格式和大小 start-------------------------- */
    public static final String AUTH_IMG_REG = "gif|jpg|png|jpeg"; // 未认证
    public static final long AUTH_IMG_SIZE = 2097152; // 审核中
    public static final long AUTH_IMG_NULL = 0; // 重新认证时不上传
    /** -----------------------认证图片的格式和大小 end-------------------------- */

    /** -----------------------应用铃声格式和大小 start-------------------------- */
    public static final String VOICE_UP_REG = "wav"; // 格式只支持wav
    public static final long VOICE_UP_SIZE = 2097152; // 审核中（最大支持2M）
    public static final long VOICE_TEMP_SIZE = 10485760; // 审核中（最大支持10M）——语音模板上传
    public static final String VOICE_NULL_ERROR = "null"; // 状态: url为空
    public static final String VOICE_ERROR = "error"; // 铃声过大或格式不正确
    public static final String VOICE_SIZE_ERROR = "size";

    /** -----------------------应用铃声格式和大小 end-------------------------- */


    /** -----------------------用户的认证状态 start-------------------------- */
    public static final String COMPANY_AUTH_STATUS_NO = "0"; // 未认证
    public static final String COMPANY_AUTH_STATUS_AUDIT = "1"; // 审核中
    public static final String COMPANY_AUTH_STATUS_SUC = "2"; // 认证成功
    /** -----------------------用户的认证状态 end-------------------------- */

    /** -----------------------用户的业务类型start-------------------------- */
    public static final String USER_BUSTYPE_KFJK = "1"; // 开发接口用户
    public static final String USER_BUSTYPE_ZNYDD = "2"; //智能云调度用户
    /** -----------------------用户的业务类型start end-------------------------- */

    /** -----------------------用户角色类型start-------------------------- */
    public static final String USER_ROLE_ZNYDD = "znydd"; //智能云调度用户
    /** -----------------------用户角色类型end-------------------------- */

    /** -----------------------redis 中存储东西的head start-------------------------- */

    public static final String REDIS_H_REG_MOBILE = "CODE:"; // 注册时手机验证码
    public static final String REDIS_H_REG_EMAIL = "REG:"; // 注册时邮箱验证码
    public static final String REDIS_H_PWD_EMAIL = "PWDE:"; // 要回密码时邮箱验证码
    public static final String REDIS_H_UPDATE_EMAIL = "UPDATE_EMAIL:"; // 要回密码时邮箱验证码
    /** -----------------------redis 中存储东西的head end-------------------------- */

    /** -----------------------返回状态 start-------------------------- */

    public static final String RESULT_STATUS_OK = "ok"; // 状态:正常
    public static final String RESULT_STATUS_ERROR = "error"; // 状态:错误
    public static final String RESULT_STATUS_HAS_REG = "hasReg"; // 已经注册

    /** -----------------------返回状态 end-------------------------- */

    /** -----------------------用户状态 start-------------------------- */

    public static final String USER_STATUS_NO_ACT = "0"; // 用户状态：注册未激活
    public static final String USER_STATUS_OK = "1"; // 注册完成
    public static final String USER_STATUS_FROZEN = "2"; // 冻结
    public static final String USER_STATUS_DISABLED = "3"; // 禁用
    public static final String USER_STATUS_LOCK = "5"; // 锁定
    public static final String USER_STATUS_CLOSED  = "6"; // 关闭

    /** -----------------------用户状态 end-------------------------- */

    /** -----------------------用户类型(bus_type) start-------------------------- */
    public static final String BUS_TYPE_REST  = "02"; // 回拨REST
    public static final String BUS_TYPE_STATUS_OK  = "00"; // 正常

    /** -----------------------用户类型(bus_type) end-------------------------- */

    /** -----------------------应用状态 start-------------------------- */

    public static final String APP_STATUS_NORMAL= "00"; //正常
    public static final String APP_STATUS_FREEZE = "01"; // 冻结
    public static final String APP_STATUS_DEL = "02"; // 禁用

    public static final String APP_BUS_955= "01"; // 955XX
    public static final String APP_BUS_REST = "02"; // REST
    public static final String APP_BUS_SIP_PHONE = "05"; // 云话机
    public static final String VOICE_STATUS_ING = "00"; // 铃声审核中


    /** -----------------------应用状态 end-------------------------- */

    /** -----------------------语音验证码审核状态 start-------------------------- */
    public static final String VOICEVERIFY_AUDIT_STATUS_ING = "00"; // 铃声审核中
    public static final String VOICEVERIFY_STATUS_DEL = "01"; // 铃声删除

    /** -----------------------语音验证码审核状态 end-------------------------- */

    /** -----------------------语音验证码格式和大小 start-------------------------- */

    public static final long VOICEVERIFY_TEMP_SIZE = 10485760; // 审核中（最大支持10M）——语音模板上传
    public static final String VOICEVERIFY_SIZE_ERROR = "size";
    public static final String VOICEVERIFY_ERROR = "error"; // 铃声过大或格式不正确
    public static final String VOICEVERIFY_NULL_ERROR = "null"; // 状态: url为空
//    public static final String VOICE_UP_REG = "wav"; // 格式只支持wav
//    public static final long VOICE_UP_SIZE = 2097152; // 审核中（最大支持2M）

//    public static final String VOICE_ERROR = "error"; // 铃声过大或格式不正确

    /** -----------------------语音验证码格式和大小 end-------------------------- */


    /** -----------------------发送手机号模版 start-------------------------- */

    //vcode代表要替换的参数
    public static final String SEND_SMS_MOBILECODE = "您的验证码是:{vcode}"; // 手机验证码发送

    public static final String SEND_SMS_ANTH_SUCCESS = "亲爱的用户，恭喜您的企业资质认证已通过，详情登录玖云平台官网进行查看。"; // 认证信息审核通过
    public static final String SEND_SMS_ANTH_ERROR = "亲爱的用户，您的企业资质认证未通过，详情登录玖云平台官网进行查看。"; // 认证信息审核失败

    public static final String SEND_SMS_RING_SUCCESS = "亲爱的用户，恭喜您的铃声审核已通过，详情登录玖云平台官网进行查看。"; // 铃声审核通过
    public static final String SEND_SMS_RING_ERROR = "亲爱的用户，您的铃声审核未通过，详情登录玖云平台官网进行查看。"; // 铃声审核失败

    public static final String SEND_SMS_NUM_SUCCESS = "亲爱的用户，恭喜您的号码审核已通过，详情登录玖云平台官网进行查看。"; // 号码审核通过
    public static final String SEND_SMS_NUM_ERROR = "亲爱的用户，您的号码审核未通过，详情登录玖云平台官网进行查看。"; // 号码审核失败

    public static final String SEND_SMS_IN_MONEY = "亲爱的用户，您已成功充值{inMoney}元，如款项未即时到账，请稍后查询，谢谢！"; // 充值成功
    public static final String SEND_SMS_OUT_MONEY = "亲爱的用户，您的账户于{outTimes}扣款{outMoney}元，如需了解详情，请联系客服。"; // 扣款成功

    /** -----------------------发送手机号模版 end-------------------------- */
}
