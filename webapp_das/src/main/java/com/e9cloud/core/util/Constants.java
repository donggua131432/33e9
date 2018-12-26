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

    /////////////////////////////////中继类型////////////////////////////////////////
    // 用途类型：00 客户，01 供应商，02 平台内部
    public static final String RELAY_USE_TYPE_CUSTOM = "00";
    public static final String RELAY_USE_TYPE_SUPPLY = "01";
    public static final String RELAY_USE_TYPE_INNER = "02";
    /////////////////////////////////中继类型////////////////////////////////////////

    /** -----------------------铃声的类型 start-------------------------- */

    public static final String VOICE_COMMON = "common"; // 正常铃音
    public static final String VOICE_MASK = "mask"; // 隐私专号铃音

    /** -----------------------后台用户状态 start-------------------------- */

    public static final String RMS_USER_STATUS_OK = "0"; // 正常
    public static final String RMS_USER_STATUS_DISABLED = "1"; // 禁用

    public static final String RMS_USER_SYS_TYPE_R = "R"; // 运营管理平台用户
    public static final String RMS_USER_SYS_TYPE_D = "D"; // 数据报表平台用户

    /** -----------------------用户状态 start-------------------------- */

    public static final String USER_STATUS_NO_ACT = "0"; // 用户状态：注册未激活
    public static final String USER_STATUS_OK = "1"; // 注册完成
    public static final String USER_STATUS_FROZEN = "2"; // 冻结
    public static final String USER_STATUS_DISABLED = "3"; // 禁用
    public static final String USER_STATUS_LOCK = "5"; // 锁定
    public static final String USER_STATUS_CLOSED  = "6"; // 关闭

    /** -----------------------用户默认密码 end-------------------------- */
    public static final String USER_DEFULT_PWD  = "e9888888";


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

    public static final String datetime = "yyyy-MM-dd HH:mm:ss";
    public static final String date = "yyyy-MM-dd";
    public static final String time = "HH:mm:ss";

}
