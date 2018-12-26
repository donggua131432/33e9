package com.e9cloud.core.util;

/**
 * 类作用：常量类
 *
 * @author wzj
 */
public class Constants {

    // 私有化，禁止外部实例化
    private Constants() {
    }

    /** -----------------------铃声的类型 start-------------------------- */

    public static final String VOICE_COMMON = "common"; // 正常铃音
    public static final String VOICE_MASK = "mask"; // 隐私专号铃音

    /** -----------------------后台用户状态 start-------------------------- */

    public static final String RMS_USER_STATUS_OK = "0"; // 正常
    public static final String RMS_USER_STATUS_DISABLED = "1"; // 禁用

    /** -----------------------后台用户状态 start-------------------------- */


    // app的业务类型
    public static final String APP_BUS_TYPE_CC = "01"; // 智能云调度
    public static final String APP_BUS_TYPE_REST = "02"; // 专线语音
    public static final String APP_BUS_TYPE_SIP = "03"; // sip业务
    public static final String APP_BUS_TYPE_SIP_PHONE = "05"; // 云话机
    public static final String APP_BUS_TYPE_SWITCHBOARD = "06"; // 云话机


    /** -----------------------用户状态 start-------------------------- */

    public static final String USER_STATUS_NO_ACT = "0"; // 用户状态：注册未激活
    public static final String USER_STATUS_OK = "1"; // 注册完成
    public static final String USER_STATUS_FROZEN = "2"; // 冻结
    public static final String USER_STATUS_DISABLED = "3"; // 禁用
    public static final String USER_STATUS_LOCK = "5"; // 锁定
    public static final String USER_STATUS_CLOSED  = "6"; // 关闭

    /** -----------------------用户默认密码 end-------------------------- */
    public static final String USER_DEFULT_PWD  = "e9888888";

    public static final String RMS_USER_SYS_TYPE_R = "R"; // 运营管理平台用户
    public static final String RMS_USER_SYS_TYPE_D = "D"; // 数据报表平台用户

    /** -----------------------用户业务类型 end-------------------------- */
    public static final String USER_TYPE_OPEN_INTERFACE  = "1"; //开发接口用户
    public static final String USER_TYPE_ZZYDD  = "2"; //智能云账户用户

    /** -----------------------企业认证状态 end-------------------------- */
    public static final String COMPANY_AUTH_NO  = "0"; //未认证
    public static final String COMPANY_AUTH_PROGRESS  = "1"; //未认证
    public static final String COMPANY_AUTH_YES  = "2"; //已认证

    /** -----------------------用户类型 end-------------------------- */
    public static final String USER_TYPE_PERSION  = "P"; //个人用户
    public static final String USER_TYPE_ENTERPRISE  = "E"; //企业用户

    /** -----------------------认证图片的格式和大小 start-------------------------- */
    public static final String AUTH_IMG_REG = "gif|jpg|png|jpeg"; // 未认证
    public static final long AUTH_IMG_SIZE = 2097152; // 审核中
    public static final long AUTH_IMG_NULL = 0; // 重新认证时不上传
    public static final String RESULT__OK = "00"; // 状态:上传正常
    /** -----------------------认证图片的格式和大小 end-------------------------- */

    /** -----------------------认证图片的格式和大小 start-------------------------- */
    public static final String ECC_VIDEO_REG = "mp3|wav"; //
    public static final long ECC_VIDEO_SIZE = 2097152; // 审核中
    public static final String ECC_VIDEO_PRE = "zj_ivr_";
    /** -----------------------认证图片的格式和大小 end-------------------------- */

    /** -----------------------用户认证状态 start-------------------------- */
    public static final String USER_AUTH_STATUS_YES = "2";//已认证
    public static final String USER_AUTH_STATUS_NO = "0";//未认证
    public static final String USER_AUTH_STATUS_PROGRESS = "1";//认证中
    /** -----------------------用户认证状态 end-------------------------- */


    /** -----------------------发送手机号模版 start-------------------------- */
    public static final String RESULT_STATUS_OK = "ok"; // 状态:正常
    public static final String RESULT_STATUS_ERROR = "error"; // 状态:错误

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
