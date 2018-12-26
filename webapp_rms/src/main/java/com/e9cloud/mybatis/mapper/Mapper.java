package com.e9cloud.mybatis.mapper;

/**
 * 类作用：mapper的工具类，记录mapper文件的sessionId
 *
 * @author wzj 使用说明：
 */
public class Mapper {

    private Mapper() {
    }

    /** demo 事例 */
    public static final class Hello_Mapper {

        /** 插入用户的基本信息 */
        public static final String selectValueByKey = "Hello_Mapper.selectValueByKey";

    }

    /** 字典表相关操作 */
    public static final class DicData_Mapper {

        /** 根据字典表类型查询字典信息 */
        public static final String findDicListByType = "DicData_Mapper.findDicListByType";

        /** 根据父类ID查询字典信息 */
        public static final String findDicListByPid = "DicData_Mapper.findDicListByPid";

        /** 根据CODE查询 **/
        public static final String findDicByCode = "DicData_Mapper.findDicByCode";

        /** 根据CODE模糊查询 **/
        public static final String findDicLikeCode = "DicData_Mapper.findDicLikeCode";
    }

    /** 用户相关 */
    public static final class User_Mapper {

        /** 用户的基本信息 */
        public static final String selectByLoginName = "User_Mapper.selectByLoginName";
        /**添加用户*/
        public static final String insertSelective = "User_Mapper.insertSelective";
        /**删除用户*/
        public static final String deleteByPrimaryKey = "User_Mapper.deleteByPrimaryKey";
        /**修改用户信息*/
        public static final String updateByPrimaryKeySelective = "User_Mapper.updateByPrimaryKeySelective";
        /**查询用户信息*/
        public static final String selectByLoginList = "User_Mapper.selectByLoginList";
        /**分页查询用户信息*/
        public static final String pageUser = "User_Mapper.pageUser";
        /** 获取用户信息 **/
        public static final String getUserInfo = "User_Mapper.getUserInfo";
        /** 获取用户信息（包括角色）**/
        public static final String getUserRoleInfo = "User_Mapper.getUserRoleInfo";
        /** 校验用户是否存在 **/
        public static final String checkUserInfo = "User_Mapper.checkUserInfo";

        /** 添加用户角色关联数据 **/
        public static final String insertUserRole = "User_Mapper.insertUserRole";
        /** 删除用户角色关联数据 **/
        public static final String deleteUserRole = "User_Mapper.deleteUserRole";
        /** 根据用户ID查找用户角色关联信息 **/
        public static final String selectUserRoleByUserId = "User_Mapper.selectUserRoleByUserId";

        public static final String selectUserAction = "User_Mapper.selectUserAction";

    }

    /**add by DuKai 20160128 **/
    public static  final class CallRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectCallRateByFeeId = "CallRate_Mapper.selectCallRateByFeeId";
        /**添加费率信息*/
        public static final String insertCallRate = "CallRate_Mapper.insertCallRate";
        /**修改费率信息*/
        public static final String updateCallRate = "CallRate_Mapper.updateCallRate";
        /**联表查询费率信息*/
        public static final String selectCallRateUnionInfo = "CallRate_Mapper.selectCallRateUnionInfo";
        /**分页联表查询费率信息**/
        public static final String selectPageCallRateUnion = "CallRate_Mapper.selectPageCallRateUnion";
    }

    /**add by DuKai 20160128 **/
    public static  final class RestRate_Mapper {
        /** 根据费率Id查找专线语音费率信息 */
        public static final String selectRestRateByFeeId = "RestRate_Mapper.selectRestRateByFeeId";
        /**添加专线语音费率信息*/
        public static final String insertRestRate = "RestRate_Mapper.insertRestRate";
        /**修改专线语音费率信息*/
        public static final String updateRestRate = "RestRate_Mapper.updateRestRate";
        /**联表查询专线语音费率信息*/
        public static final String selectRestRateUnionInfo = "RestRate_Mapper.selectRestRateUnionInfo";
        /**分页联表查询专线语音费率信息**/
        public static final String selectPageRestRateUnion = "RestRate_Mapper.selectPageRestRateUnion";
    }

    /**add by DuKai 20160613 **/
    public static  final class MaskRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectMaskRateByFeeId = "MaskRate_Mapper.selectMaskRateByFeeId";
        /**添加费率信息*/
        public static final String insertMaskRate = "MaskRate_Mapper.insertMaskRate";
        /**修改费率信息*/
        public static final String updateMaskRate = "MaskRate_Mapper.updateMaskRate";
        /**联表查询费率信息*/
        public static final String selectMaskRateUnionInfo = "MaskRate_Mapper.selectMaskRateUnionInfo";
        /**分页联表查询费率信息**/
        public static final String selectPageMaskRateUnion = "MaskRate_Mapper.selectPageMaskRateUnion";
    }

    /**add by lixin 20161028 **/
    public static  final class SipphonRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectPhoneRateByFeeId = "SipphonRate_Mapper.selectPhoneRateByFeeId";
        /**添加费率信息*/
        public static final String insertPhoneRate = "SipphonRate_Mapper.insertSelective";
        /**修改费率信息*/
        public static final String updatePhoneRate = "SipphonRate_Mapper.updateByPrimaryKeySelective";
        /**联表查询费率信息*/
        public static final String selectPhoneRateUnionInfo = "SipphonRate_Mapper.selectPhoneRateUnionInfo";
        /**分页联表查询费率信息**/
        public static final String selectPagePhoneRateUnion = "SipphonRate_Mapper.selectPagePhoneRateUnion";
    }


    /**add by hzd 20160921 **/
    public static final class VoiceRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectVoiceRateByFeeId = "VoiceRate_Mapper.selectVoiceRateByFeeId";
        /**添加费率信息*/
        public static final String insertVoiceRate = "VoiceRate_Mapper.insertVoiceRate";
        /**分页联表查询语音通知费率信息**/
        public static final String selectPageVoiceRateUnion = "VoiceRate_Mapper.selectPageVoiceRateUnion";
        /**联表查询费率信息*/
        public static final String selectVoiceRateUnionInfo = "VoiceRate_Mapper.selectVoiceRateUnionInfo";
        /**修改费率信息*/
        public static final String updateVoiceRate = "VoiceRate_Mapper.updateVoiceRate";

    }

    /**语音验证码费率相关 add by hzd 20170502 **/
    public static final class VoiceVerifyRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectVoiceVerifyRateByFeeId = "VoiceVerifyRate_Mapper.selectVoiceVerifyRateByFeeId";
        /**添加费率信息*/
        public static final String insertVoiceVerifyRate = "VoiceVerifyRate_Mapper.insertVoiceVerifyRate";

        public static final String selectPageVoiceVerifyRateUnion = "VoiceVerifyRate_Mapper.selectPageVoiceVerifyRateUnion";

        public static final String selectVoiceVerifyRateUnionInfo = "VoiceVerifyRate_Mapper.selectVoiceVerifyRateUnionInfo";

        public static final String updateVoiceVerifyRate = "VoiceVerifyRate_Mapper.updateVoiceVerifyRate";

    }


    /**add by lixin 20170418 **/
    public static  final class AxbRate_Mapper {
        /** 根据费率Id查找专线语音费率信息 */
        public static final String selectAxbRateByFeeId = "AxbRate_Mapper.selectAxbRateByFeeId";

        /**添加专线语音费率信息*/
        public static final String insertAxbRate = "AxbRate_Mapper.insertSelective";
        /**修改专线语音费率信息*/
        public static final String updateAxbRate = "AxbRate_Mapper.updateByPrimaryKeySelective";

        public static final String selectPageAxbRateUnion = "AxbRate_Mapper.selectPageAxbRateUnion";

        public static final String selectAxbRateUnionInfo = "AxbRate_Mapper.selectAxbRateUnionInfo";
    }



    /** 菜单管理相关sql add by wzj 20160216 **/
    public static final class Menu_Mapper {

        /**分页查询菜单列表*/
        public static final String page = "Menu_Mapper.page";
        /**根据父菜单id查询菜单列表*/
        public static final String selectMenusByParentId = "Menu_Mapper.selectMenusByParentId";
        /**根据PK删除一个菜单*/
        public static final String deleteByPrimaryKey = "Menu_Mapper.deleteByPrimaryKey";
        /**插入一个菜单*/
        public static final String insertSelective = "Menu_Mapper.insertSelective";
        /**修改一条记录*/
        public static final String updateByPrimaryKeySelective = "Menu_Mapper.updateByPrimaryKeySelective";
        /**根据主键选择一条记录*/
        public static final String selectByPrimaryKey = "Menu_Mapper.selectByPrimaryKey";

        /**根据父菜单id、sysType查询菜单列表*/
        public static final String selectMenusByMap = "Menu_Mapper.selectMenusByMap";

        /** 分页查询 */
        public static final String pageMenuWithAction = "Menu_Mapper.pageMenuWithAction";

        /** 统计菜单 */
        public static final String countMenuWithAction = "Menu_Mapper.countMenuWithAction";

        /** 选择用户所属的权限列表 */
        public static final String selectMenusByUserID = "Menu_Mapper.selectMenusByUserID";

    }

    public static  final class UserExternInfo_Mapper{
        /** 保存用户扩展信息 */
        public static final String saveUserExtern = "UserExternInfo_Mapper.insertSelective";

        /** 根据uid查询扩展信息 */
        public static final String selectUserExternInfoByUid = "UserExternInfo_Mapper.selectUserExternInfoByUid";
    }

    public static final class AuthenCompany_Mapper{
        /** 根据参数查询用户（公司）认证信息 */
        public static final String seelctAuthenCompanyByObject = "AuthenCompany_Mapper.selectAuthenCompanyByObject";

        /** 根据参数Id查询用户（公司）认证信息 */
        public static final String seelctAuthenCompanyById = "AuthenCompany_Mapper.selectAuthenCompanyById";

        /** 保存公司认证信息 */
        public static final String insertSelective = "AuthenCompany_Mapper.insertSelective";

        /** 修改公司认证信息 */
        public static final String updateByPrimaryKeySelective = "AuthenCompany_Mapper.updateByPrimaryKeySelective";

        /** 统计公司证件类型的合法性*/
        public static final String countCompanyByInfo = "AuthenCompany_Mapper.countCompanyByInfo";
        /** x修改时统计公司证件类型的合法性*/
        public static final String countCompany = "AuthenCompany_Mapper.countCompany";

        public static final String getAuthenCompanyById = "AuthenCompany_Mapper.getAuthenCompanyById";

    }

    /** 官网用户相关sql */
    public static final class UserAdmin_Mapper{

        /** 添加官网用户 */
        public static final String insertSelective = "UserAdmin_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "UserAdmin_Mapper.updateByPrimaryKeySelective";

        public static final String selectBySID = "UserAdmin_Mapper.selectBySID";

        public static final String selectByPrimaryKey = "UserAdmin_Mapper.selectByPrimaryKey";

        /**分页查询**/
        public static final String selectBalanceListPage = "UserAdmin_Mapper.selectUserAdminPage";

        public static final String selectUserAdminByObject = "UserAdmin_Mapper.selectUserAdminByObject";

        /**下载列表**/
        public static final String selectBalanceListDownload = "UserAdmin_Mapper.selectUserAdmindownload";

        /** 用户信息联合查询 **/
        public static final String selectUserAdminUnionCompanyInfo = "UserAdmin_Mapper.selectUserAdminUnionCompanyInfo";

        /** 根据邮箱或者手机号统计用户数量 */
        public static final String countUserAdminByEmailOrMobile = "UserAdmin_Mapper.countUserAdminByEmailOrMobile";

        /** 到公司名和sid下拉列表 */
        public static final String findCompanyNameAndSidByPage = "UserAdmin_Mapper.findCompanyNameAndSidByPage";

        /** 分页查询开发者列表 */
        public static final String pageUserAdminList = "UserAdmin_Mapper.pageUserAdminList";

        /** 导出开发者列表信息 */
        public static final String getPageUserAdminList = "UserAdmin_Mapper.getPageUserAdminList";

        /** 修改用户的状态 */
        public static final String updateUserAdminStatusByUid = "UserAdmin_Mapper.updateUserAdminStatusByUid";

        /** 查询所有的用户 **/
        public static final String selectAll = "UserAdmin_Mapper.selectAll";

        /** 查询所有的用户和用户类型 **/
        public static final String selectAllWithBusTypes = "UserAdmin_Mapper.selectAllWithBusTypes";
    }

    /** 历史余额 */
    public static final class UserAdminHistory_Mapper {
        /** 历史余额列表 */
        public static final String selectBalanceHistoryListPage = "UserAdminHistory_Mapper.selectBalanceHistoryListPage";

        public static final String selectBalanceHistoryLisDownload = "UserAdminHistory_Mapper.selectBalanceHistoryLisDownload";
    }

    public static final class Recharge_Records{
        public static final String insert = "Recharge_Records.insert";

        public static final String selectRechargeRecordsPage = "Recharge_Records.selectRechargeRecordsPage";

        public static final String selectRechargeRecordsDownload = "Recharge_Records.selectRechargeRecordsDownload";
    }

    public static final class Payment_Records{
        public static final String insert = "Payment_Records.insert";

        public static final String selectPaymentRecordsPage = "Payment_Records.selectPaymentRecordsPage";

        public static final String selectPaymentRecordsDownload = "Payment_Records.selectPaymentRecordsDownload";
    }

    /** 日志操作相关sql add by wzj 20160216 **/
    public static final class OperationLog_Mapper {

        /** 根据主键选择一条日志信息 */
        public static final String selectByPrimaryKey = "OperationLog_Mapper.selectByPrimaryKey";

        /** 统计日志数量 */
        public static final String countLog = "OperationLog_Mapper.countLog";

        /** 分页日志 */
        public static final String pageLog = "OperationLog_Mapper.pageLog";

        /** 插入一条日志信息 */
        public static final String insertSelective = "OperationLog_Mapper.insertSelective";
    }

    /** 应用相关类型 add by wzj 20160216 **/
    public static final class AppInfo_Mapper {

        /** 分页选取智能云调度应用列表 */
        public static final String pageAppListForZnydd = "AppInfo_Mapper.pageAppListForZnydd";

        /** 分页选取 SIP业务 应用列表 */
        public static final String pageAppListForSip = "AppInfo_Mapper.pageAppListForSip";

        /** 导出 SIP业务 应用列表 */
        public static final String getpageAppListForSip = "AppInfo_Mapper.getpageAppListForSip";

        /** 添加应用信息 */
        public static final String insertSelective = "AppInfo_Mapper.insertSelective";

        /** 根据appid选择一个应用 */
        public static final String findZnyddAppInfoByAppId = "AppInfo_Mapper.findZnyddAppInfoByAppId";

        /** 根据appid查询   SIP 应用 */
        public static final String findSipAppInfoByAppId = "AppInfo_Mapper.findSipAppInfoByAppId";

        /** 根据条件选择一个应用 */
        public static final String findAppInfoByObj = "AppInfo_Mapper.findAppInfoByObj";

        /** 更改应用的状态 */
        public static final String updateAppStatus = "AppInfo_Mapper.updateAppStatus";

        /** 更改应用的状态 */
        public static final String updateByPrimaryKeySelective = "AppInfo_Mapper.updateByPrimaryKeySelective";

        /** 分页选取云话机应用列表 **/
        public static final String pageAppListForSipPhone = "AppInfo_Mapper.pageAppListForSipPhone";

        public static final String pageAppListForRest = "AppInfo_Mapper.pageAppListForRest";

        /** 得到 云话机的appinfo信息 **/
        public static final String selectSipPhoneAppInfoByAppId = "AppInfo_Mapper.selectSipPhoneAppInfoByAppId";

        public static final String getAppInfoByMap = "AppInfo_Mapper.getAppInfoByMap";

    }

    /** 应用扩展信息 **/
    public static final class AppInfoExtra_Mapper {
        public static final String updateByPrimaryKeySelective = "AppInfoExtra_Mapper.updateByPrimaryKeySelective";

        public static final String updateByAppidSelective = "AppInfoExtra_Mapper.updateByAppidSelective";

        /** 应用创建 **/
        public static final String saveAppExtra = "AppInfoExtra_Mapper.insertSelective";

        public static final String countBusinessType = "AppInfoExtra_Mapper.countBusinessType";
    }

    /** 应用中继相关 add by hzd 20160909 **/
    public static final class AppRelay_Mapper {

        /** 添加应用中继 */
        public static final String insertSelective = "AppRelay_Mapper.insertSelective";

        /** 删除应用中继 */
        public static final String deleteAppRelay = "AppRelay_Mapper.deleteAppRelay";

        /** 根据条件选择一个应用中继 */
        public static final String findAppRelayByObj = "AppRelay_Mapper.findAppRelayByObj";

        /** 根据appid选择应用中继 */
        public static final String findAppRelayListByAppid = "AppRelay_Mapper.findAppRelayListByAppid";

    }

    /** 号段相关类型 add by 彭春臣 20160801 **/
    public static final class TelnoInfo_Mapper {

        /** 分页选取智能云调度号段列表 */
        public static final String pageTelnoInfoList = "TelnoInfo_Mapper.pageTelnoInfoList";
        /** 保存号段 */
        public static final String saveTelnoInfo = "TelnoInfo_Mapper.saveTelnoInfo";
        /** 删除号段 */
        public static final String delTelnoInfo = "TelnoInfo_Mapper.delTelnoInfo";
        /** 修改号段 */
        public static final String updateTelnoInfo = "TelnoInfo_Mapper.updateTelnoInfo";
        /** 根据id查找一条号段信息 */
        public static final String queryTelnoInfoById = "TelnoInfo_Mapper.queryTelnoInfoById";
        /** 校验号段存在性 */
        public static final String countTelnoInfo = "TelnoInfo_Mapper.countTelnoInfo";
        /** 根据城市编号查询号段信息 */
        public static final String queryTelnoInfoByCcode = "TelnoInfo_Mapper.queryTelnoInfoByCcode";

        public static final String selectTelnoInfoByTelno = "TelnoInfo_Mapper.selectTelnoInfoByTelno";

    }

    /** 运营商表 add by 彭春臣 20160803 **/
    public static final class TelnoOperator_Mapper {

        /** 查询所用运营商 */
        public static final String queryOperatorList = "TelnoOperator_Mapper.queryOperatorList";

    }

    /** 呼叫中心相关类型 add by 彭春臣 20160805 **/
    public static final class CcInfo_Mapper {

        /** 分页选取智能云调度呼叫中心列表 */
        public static final String pageCcInfoList = "CcInfo_Mapper.pageCcInfoList";
        /** 保存呼叫中心 */
        public static final String saveCcInfo = "CcInfo_Mapper.saveCcInfo";
        /** 删除呼叫中心 */
        public static final String delCcInfo = "CcInfo_Mapper.delCcInfo";
        /** 修改呼叫中心 */
        public static final String updateCcInfo = "CcInfo_Mapper.updateCcInfo";
        /** 修改呼叫中心状态 */
        public static final String updateCcInfodefaultCc = "CcInfo_Mapper.updateCcInfodefaultCc";
        /** 修改呼叫中心默认 */
        public static final String updateCcInfoStatus = "CcInfo_Mapper.updateCcInfoStatus";
        /** 根据id查找一条呼叫中心信息 */
        public static final String queryCcInfoById = "CcInfo_Mapper.queryCcInfoById";
        /** 校验呼叫中心存在性 */
        public static final String countCcInfo = "CcInfo_Mapper.countCcInfo";
        /** 根据sid查找呼叫中心列表 */
        public static final String queryCcInfoBySid = "CcInfo_Mapper.queryCcInfoBySid";


    }

    /** 话务调度相关类型 add by 彭春臣 20160808 **/
    public static final class CcDispatch_Mapper {

        /** 分页选取智能云调度话务调度列表 */
        public static final String pageCcDispatchList = "CcDispatch_Mapper.pageCcDispatchList";
        /** 保存话务调度 */
        public static final String saveCcDispatch = "CcDispatch_Mapper.saveCcDispatch";
        /** 修改话务调度 */
        public static final String updateCcDispatch = "CcDispatch_Mapper.updateCcDispatch";
        /** 根据id查找一条话务调度信息 */
        public static final String queryCcDispatchById = "CcDispatch_Mapper.queryCcDispatchById";
        /** 得到公司名和sid下拉列表 */
        public static final String findCompanyNameAndSidByPage = "CcDispatch_Mapper.findCompanyNameAndSidByPage";
        /** 统计sid下某个调度名称的的个数 */
        public static final String countDispatchBySidAndDispatchName = "CcDispatch_Mapper.countDispatchBySidAndDispatchName";
        /** 统计areaid下的话务调度 */
        public static final String getDispatchByAreaId = "CcDispatch_Mapper.getDispatchByAreaId";
        /** 统计subId下的话务调度 */
        public static final String getDispatchBySubId = "CcDispatch_Mapper.getDispatchBySubId";

    }
    /** 话务调度呼叫中心相关类型 add by 彭春臣 20160810 **/
    public static final class CcDispatchInfo_Mapper {

        /** 保存话务调度呼叫中心 */
        public static final String addCcDispatchInfoList = "CcDispatchInfo_Mapper.addCcDispatchInfoList";
        /** 删除话务调度呼叫中心 */
        public static final String delCcDispatchInfo = "CcDispatchInfo_Mapper.delCcDispatchInfo";
        /** 根据调度ID查询话务调度呼叫中心 */
        public static final String queryListByDId = "CcDispatchInfo_Mapper.queryListByDId";
        /** 校验话务调度呼叫中心存在性 */
        public static final String countCcDispatchInfo = "CcDispatchInfo_Mapper.countCcDispatchInfo";
        /** 删除话务调度时删除关联呼叫中心记录 */
        public static final String deleteByDispatchId = "CcDispatchInfo_Mapper.deleteByDispatchId";

    }
    /** 调度区域表 add by 彭春臣 20160809**/
    public static final class CcArea_Mapper {

        /** 查询所有调度区域 */
        public static final String queryCcAreaList = "CcArea_Mapper.queryCcAreaList";
        public static final String queryCcAreaList1 = "CcArea_Mapper.queryCcAreaList1";

        public static final String selectByAreaId = "CcArea_Mapper.selectByAreaId";

        /** 分页查询 区域列表 */
        public static final String pageCcArea = "CcArea_Mapper.pageCcArea";

        public static final String insertSelective = "CcArea_Mapper.insertSelective";

        public static final String updateByAreaId = "CcArea_Mapper.updateByAreaId";

        public static final String countAreaBySidAndAreaId = "CcArea_Mapper.countAreaBySidAndAreaId";

        public static final String deleteCcArea = "CcArea_Mapper.deleteCcArea";

    }

    /** 区域配置 **/
    public static final class CcAreaCity_Mapper {

        /** 省份 - 城市 树 */
        public static final String atree = "CcAreaCity_Mapper.atree";

        /** 某个区域 的 城市 树 */
        public static final String pctree = "CcAreaCity_Mapper.pctree";

        public static final String insertSelective = "CcAreaCity_Mapper.insertSelective";

        public static final String deleteByAreaId = "CcAreaCity_Mapper.deleteByAreaId";

        public static final String countByAreaCity = "CcAreaCity_Mapper.countByAreaCity";

        public static final String filteAreaCity = "CcAreaCity_Mapper.filteAreaCity";

        public static final String selectSelectedNodes = "CcAreaCity_Mapper.selectSelectedNodes";

        public static final String selectByCityCode = "CcAreaCity_Mapper.selectByCityCode";

    }

    public static final class AuthenCompanyRecords_Mapper{

        /** 分页查询公司认证信息 */
        public static final String selectAuthenCompanyRecordsPage = "AuthenCompanyRecords_Mapper.selectAuthenCompanyRecordsPage";

        public static final String getPageAuthenCompanyRecords = "AuthenCompanyRecords_Mapper.getPageAuthenCompanyRecords";

        /** 根据ID查询公司认证信息 */
        public static final String selectAuthenCompanyRecordsById = "AuthenCompanyRecords_Mapper.selectAuthenCompanyRecordsById";

        /** 更新公司认证信息的审核状态 */
        public static final String updateByPrimaryKeySelective = "AuthenCompanyRecords_Mapper.updateByPrimaryKeySelective";

        public static final String insertSelective = "AuthenCompanyRecords_Mapper.insertSelective";
    }

    /** 应用铃声相关类型 add by wj 20160324 **/
    public static final class AppVoice_Mapper{
        /** 分页查询应用认证铃声 */
        public static final String pageAppVoiceList = "AppVoice_Mapper.pageAppVoiceList";

        /** 根据id查询应用铃声审核记录 */
        public static final String selectByPrimaryKey = "AppVoice_Mapper.selectByPrimaryKey";

        /** 选择性字段更新应用铃声审核记录 */
        public static final String updateByPrimaryKeySelective = "AppVoice_Mapper.updateByPrimaryKeySelective";

        public static final String getmobileByAppid = "AppVoice_Mapper.getmobileByAppid";

        public static final String getremarkByAppid = "AppVoice_Mapper.getremarkByAppid";

    }

    /** 应用铃声相关类型 add by wzj 20160324 **/
    public static final class SecretVoice_Mapper{
        /** 分页查询应用认证铃声 */
        public static final String pageSecretVoiceList = "SecretVoice_Mapper.pageSecretVoiceList";

        /** 根据id查询应用铃声审核记录 */
        public static final String selectByPrimaryKey = "SecretVoice_Mapper.selectByPrimaryKey";

        /** 选择性字段更新应用铃声审核记录 */
        public static final String updateByPrimaryKeySelective = "SecretVoice_Mapper.updateByPrimaryKeySelective";

        public static final String getmobileByAppid = "SecretVoice_Mapper.getmobileByAppid";

        public static final String getremarkByAppid = "SecretVoice_Mapper.getremarkByAppid";

    }

    public static final class AppNumber_Mapper{
        public static final String pageAppNumberList = "AppNumber_Mapper.pageAppNumberList";

        public static final String selectByPrimaryKey = "AppNumber_Mapper.selectByPrimaryKey";

        public static final String updateByPrimaryKeySelective = "AppNumber_Mapper.updateByPrimaryKeySelective";

        public static final String getNumRemarkByAppid = "AppNumber_Mapper.getNumRemarkByAppid";

        public static final String getAppNumbersByIds = "AppNumber_Mapper.getAppNumbersByIds";
    }

    public static final class AppNumberRest_Mapper{
        public static final String listInsert = "AppNumberRest_Mapper.listInsert";

        public static final String insert = "AppNumberRest_Mapper.insert";
    }

    /** Sip业务全局号码池相关业务处理 add by lixin **/
    public static final class SipAppNumPool_Mapper{

        public static final String selectSipNumberByNumber = "SipAppNumPool_Mapper.selectSipNumberByNumber";

        public static final String insetSIPNumberPool = "SipAppNumPool_Mapper.insetSIPNumberPool";

        public static final String selectSipNumberList = "SipAppNumPool_Mapper.selectSipNumberList";

        public static final String selectSipNumberListDownload = "SipAppNumPool_Mapper.selectSipNumberListDownload";

        public static final String updateSipNumber = "SipAppNumPool_Mapper.updateByPrimaryKeySelective";

        public static final String deleteSipNumber = "SipAppNumPool_Mapper.deleteByPrimaryKey";

        public static final String checkSipNumUnique = "SipAppNumPool_Mapper.checkSipNumUnique";

        public static final String getSipNumInfoById = "SipAppNumPool_Mapper.selectByPrimaryKey";

        public static final String deleteStatusBylink = "SipAppNumPool_Mapper.deleteStatusBylink";

        public static final String getSipNumList = "SipAppNumPool_Mapper.getSipNumList";
        //根据number获取全局and子账号全部号码
        public static final String getSipNumPoolByNumber = "SipAppNumPool_Mapper.getSipNumPoolByNumber";

    }

    /** 私密专线相关业务处理 add by dukai **/
    public static final class MaskLine_Mapper{
        /** 获取隐私号码池列表 **/
        public static final String selectMaskNumberPoolList = "MaskNumPool_Mapper.selectMaskNumberPoolList";
        /** 根据条件获取隐私号码池信息 **/
        public static final String selectMaskNumberPoolByObj = "MaskNumPool_Mapper.selectMaskNumberPoolByObj";
        /** 添加隐私号码池信息 **/
        public static final String insetMaskNumberPool = "MaskNumPool_Mapper.insetMaskNumberPool";
        /** 删除隐私号码池 **/
        public static final String deleteMaskNumberPool = "MaskNumPool_Mapper.deleteMaskNumberPool";

        /** 获取隐私号列表 **/
        public static final String selectMaskNumberList = "MaskNum_Mapper.selectMaskNumberList";
        /** 根据号码获取隐私号信息 **/
        public static final String selectMaskNumberByNumber = "MaskNum_Mapper.selectMaskNumberByNumber";
        /** 根据条件获取隐私号信息 **/
        public static final String selectMaskNumberByObj = "MaskNum_Mapper.selectMaskNumberByObj";
        /** 根据状态获取隐私号信息 **/
        public static final String selectMaskNumberByStatus = "MaskNum_Mapper.selectMaskNumberByStatus";
        /** 获取隐私号信息 **/
        public static final String selectMaskNumberListUnion = "MaskNum_Mapper.selectMaskNumberListUnion";
        /** 添加隐私号信息 **/
        public static final String insetMaskNumber = "MaskNum_Mapper.insetMaskNumber";
        /** 删除隐私号 **/
        public static final String deleteMaskNumber = "MaskNum_Mapper.deleteMaskNumber";
        /** 修改隐私号状态 **/
        public static final String updateMaskNumber = "MaskNum_Mapper.updateMaskNumber";
        /** 修改根据Aapp Id隐私号状态 **/
        public static final String updateMaskNumberByAppId = "MaskNum_Mapper.updateMaskNumberByAppId";
    }

    /** 城市区号信息 **/
    public static final class CityAreaCode_Mapper{
        /** 获取城市区号信息列表 **/
        public static final String selectCityAreaCodeList = "CityAreaCode_Mapper.selectCityAreaCodeList";

        /** 根据区号获取城市区号信息列表 **/
        public static final String selectCityAreaCodeByAreaCode = "CityAreaCode_Mapper.selectCityAreaCodeByAreaCode";
    }

    /** 私密专线相关业务处理 add by wzj **/
    public class MaskNum_Mapper {
        /** 分页查询隐私号 **/
        public static final String pageMaskNumber = "MaskNum_Mapper.pageMaskNumber";
    }

    /** 号码黑名单 **/
    public class NumberBlack_Mapper{
        /** 根据ID查找号码黑名单信息 **/
        public static final String selectNumberBlackById = "NumberBlack_Mapper.selectNumberBlackById";

        /** 根据多个ID查找号码黑名单信息 **/
        public static final String selectNumberBlackListByNumbers = "NumberBlack_Mapper.selectNumberBlackListByNumbers";

        /** 保存号码黑名单信息 **/
        public static final String saveNumberBlack = "NumberBlack_Mapper.saveNumberBlack";

        /** 删除号码黑名单信息 **/
        public static final String deleteNumberBlack = "NumberBlack_Mapper.deleteNumberBlack";

        /** 分页查询号码黑名单信息 **/
        public static final String pageNumberBlack = "NumberBlack_Mapper.pageNumberBlack";
    }

    /** 客户管理 **/
    public class CustomerManager_Mapper{
        /** 根据ID查找客户信息 **/
        public static final String selectCustomerManagerById = "CustomerManager_Mapper.selectCustomerManagerById";

        /** 根据AccountId查找客户信息 **/
        public static final String selectCustomerManagerByAccountId = "CustomerManager_Mapper.selectCustomerManagerByAccountId";

        /** 保存号客户信息 **/
        public static final String saveCustomerManager = "CustomerManager_Mapper.saveCustomerManager";

        /** 修改客户信息 **/
        public static final String updateCustomerManager = "CustomerManager_Mapper.updateCustomerManager";

        /** 删除客户信息 **/
        public static final String deleteCustomerManager = "CustomerManager_Mapper.deleteCustomerManager";

        /** 分页查询客户信息 **/
        public static final String pageCustomerManagerList = "CustomerManager_Mapper.pageCustomerManagerList";
    }

    /** 消息管理相关 */
    public static final class SysMessage_Mapper {

        /** 分页查询消息 **/
        public static final String pageMsgTemp = "SysMessage_Mapper.pageMsgTemp";

        /** 发送一条消息 **/
        public static final String insertSelective = "SysMessage_Mapper.insertSelective";

        /** 插入一条消息 **/
        public static final String insertTempSelective = "SysMessage_Mapper.insertTempSelective";

        /** 根据主键查询一条消息 **/
        public static final String selectTempByPrimaryKey = "SysMessage_Mapper.selectTempByPrimaryKey";

        /** 查询所有的待发送消息 **/
        public static final String selectAllTobeSentMsg = "SysMessage_Mapper.selectAllTobeSentMsg";

        public static final String updateMsgTempStatus = "SysMessage_Mapper.updateMsgTempStatus";

    }

    /** 中继相关sql */
    public static final class SipBasic_Mapper {

        /** 分页查询 **/
        public static final String pageRelay = "SipBasic_Mapper.pageRelay";

        /** 查询中继列表 **/
        public static final String selectRelays = "SipBasic_Mapper.selectRelays";

        /** 查询未占用且正常中继列表 **/
        public static final String selectRelaysByLimit = "SipBasic_Mapper.selectRelaysByLimit";

        /** 查询中继方向为为（10 出中继 11双向中继）类型的中继群 且正常中继列表 **/
        public static final String selectRelaysByRelayType = "SipBasic_Mapper.selectRelaysByRelayType";

        public static final String getRelaysByRelayType = "SipBasic_Mapper.getRelaysByRelayType";

        /** 导出中继列表 **/
        public static final String pageDownloadRelay = "SipBasic_Mapper.pageDownloadRelay";
        /** 插入中继信息 **/
        public static final String insertSelective = "SipBasic_Mapper.insertSelective";
        /** 唯一性判断 **/
        public static final String countSipBasicByNum = "SipBasic_Mapper.countSipBasicByNum";

        /** 中继是否被引用 **/
        public static final String flagSipBasic = "SipBasic_Mapper.flagSipBasic";

        public static final String editcountSipBasicByNum = "SipBasic_Mapper.editcountSipBasicByNum";
        /** 修改中继列表 **/
        public static final String updateSipBasicInfo = "SipBasic_Mapper.updateByPrimaryKeySelective";
        /** 查看中继列表 **/
        public static final String getRelayInfoById = "SipBasic_Mapper.selectByPrimaryKey";

        /** 更改中继的状态 **/
        public static final String updateRelayStatusByRelayNum = "SipBasic_Mapper.updateRelayStatusByRelayNum";

        /** 判断中继是否可用 */
        public static final String countEnableRelays = "SipBasic_Mapper.countEnableRelays";

        /**  */
        public static final String selectFrozenRelay = "SipBasic_Mapper.selectFrozenRelay";

        /** 查看中继列表 **/
        public static final String getSipBasicByRelayNum = "SipBasic_Mapper.getSipBasicByRelayNum";

        /** 中继资源下拉列表 */
        public static final String selectRelaysForRes = "SipBasic_Mapper.selectRelaysForRes";

        /** 根据中继编号选择中继分配情况 */
        public static final String selectLimitStatusByRelayNum = "SipBasic_Mapper.selectLimitStatusByRelayNum";

        // 呼叫中心代答中继列表
        public static final String selectRelaysForAnswerTrunk = "SipBasic_Mapper.selectRelaysForAnswerTrunk";

        // 判断中继是否要加入任务列表
        public static String countRelayForTask = "SipBasic_Mapper.countRelayForTask";
    }

    /** 中继强显号码池相关sql **/
    public static final class RelayNumPool_Mapper {
        /** 获取中继强显号基本信息 **/
        public static final String getRelayNumList = "RelayNumPool_Mapper.getRelayNumList";
        /** 新增号码池 **/
        public static final String insertRelayNumber = "RelayNumPool_Mapper.insertRelayNumber";
        /** 根据号码查询号码信息 **/
        public static final String selectRelayNumberByNumber = "RelayNumPool_Mapper.selectRelayNumberByNumber";
        /** 修改号码信息 **/
        public static final String updateRelayNumber = "RelayNumPool_Mapper.updateByPrimaryKeySelective";
        /** 根据ID查询号码信息 **/
        public static final String selectNumByID = "RelayNumPool_Mapper.selectByPrimaryKey";
        /** 删除号码池号码 **/
        public static final String deleteRelayNumber = "RelayNumPool_Mapper.deleteByPrimaryKey";
        /** 判断号码是否删除过 **/
        public static final String checkRelayNumUnique = "RelayNumPool_Mapper.checkRelayNumUnique";;

    }
    /** 中继子账号相关sql **/
    public static final class SipRelayInfo_Mapper {
        /** 分页查询中继子账号信息 */
        public static final String pageRelayInfo = "SipRelayInfo_Mapper.pageRelayInfo";

        /** 保存中继-子账号信息 */
        public static final String insertSelective = "SipRelayInfo_Mapper.insertSelective";

        /** 根据subid选择中继-子账号信息 */
        public static final String selectRelayInfoBySubid = "SipRelayInfo_Mapper.selectRelayInfoBySubid";

        /*** 根据subid修改中继-子账号信息 */
        public static final String updateBySubidSelective = "SipRelayInfo_Mapper.updateBySubidSelective";

    }

    /** 中继费用 **/
    public static final class SipRate_Mapper {

        /** 根据主账号查询费率配置 **/
        public static final String selectSipRetaBySubid = "SipRate_Mapper.selectByPrimaryKey";

        /** 保存中继费用信息 */
        public static final String insertSelective = "SipRate_Mapper.insertSelective";

        /** 修改中继费用信息 */
        public static final String updateByPrimaryKeySelective = "SipRate_Mapper.updateByPrimaryKeySelective";
    }

    /** 子帐号信息 **/
    public static final class SubApp_Mapper {

        /** 获取子帐号基本信息 **/
        public static final String selectSubAppBySubid = "SipRelayInfo_Mapper.selectRelayBySubId";
    }

    /** 子帐号号码池 **/
    public static final class SubPool_Mapper {

        /** 获取子帐号基本信息 **/
        public static final String getSubNumberList = "SubPool_Mapper.getSubNumberList";
        /** 子账号号码池导出 **/
        public static final String selectSupNumberListDownload = "SipAppNumPool_Mapper.selectSupNumberListDownload";
        /** 新增号码池 **/
        public static final String insertSubNumber = "SubPool_Mapper.insertSelective";
        /** 根据号码查询号码信息 **/
        public static final String selectSubNumberByNumber = "SubPool_Mapper.selectSubNumberByNumber";
        /** 根据号码查询号码信息 **/
        public static final String selectSubNumberByNumber1 = "SubPool_Mapper.selectSubNumberByNumber1";
        /** 修改号码信息 **/
        public static final String updateSubNumber = "SubPool_Mapper.updateByPrimaryKeySelective";
        /** 根据ID查询号码信息 **/
        public static final String selectNumByID = "SubPool_Mapper.selectByPrimaryKey";
        /** 删除号码池号码 **/
        public static final String deleteSubNumber = "SubPool_Mapper.deleteByPrimaryKey";
        /** 删除号码池号码 **/
        public static final String deleteSubNumberBySubid = "SubPool_Mapper.deleteBySubid";
        /** 判断号码是否删除过 **/
        public static final String checkSubNumUnique = "SubPool_Mapper.checkSubNumUnique";

        public static final String deleteStatusBylink = "SubPool_Mapper.deleteStatusBylink";

    }

    /** 号码黑名单 **/
    public class SubWhiteBlack_Mapper{
        /** 根据ID查找号码黑白名单信息 **/
        public static final String selectNumberById = "SubWhiteBlack_Mapper.selectNumberById";

        /** 根据多个ID查找号码黑白名单信息 **/
        public static final String selectNumberListByNumbers = "SubWhiteBlack_Mapper.selectNumberListByNumbers";

        /** 保存号码黑白名单信息 **/
        public static final String saveNumber = "SubWhiteBlack_Mapper.saveNumber";

        /** 删除号码黑白名单信息 **/
        public static final String deleteNumber = "SubWhiteBlack_Mapper.deleteNumber";

        /** 分页查询号码黑白名单信息 **/
        public static final String pageNumber = "SubWhiteBlack_Mapper.pageNumber";
    }


    /** 城市管理 **/
    public class CityManager_Mapper{
        /** 根据ID查找城市信息 **/
        public static final String selectCityManagerById = "CityManager_Mapper.selectCityManagerById";

        /** 根据名称查找城市信息 **/
        public static final String selectCityManagerByName = "CityManager_Mapper.selectCityManagerByName";

        /** 保存号城市信息 **/
        public static final String saveCityManager = "CityManager_Mapper.saveCityManager";

        /** 修改城市信息 **/
        public static final String updateCityManager = "CityManager_Mapper.updateCityManager";

        /** 删除城市信息 **/
        public static final String deleteCityManager = "CityManager_Mapper.deleteCityManager";

        /** 分页查询城市信息 **/
        public static final String pageCityManagerList = "CityManager_Mapper.pageCityManagerList";

        /** 查找最大ID **/
        public static final String getMaxId = "CityManager_Mapper.getMaxId";

        /** 根据城市的编号查找城市信息**/
        public static final String selectCityManagerByCode = "CityManager_Mapper.selectCityManagerByCode";

        /** 根据城市的区号查找城市信息**/
        public static final String selectCityManagerByAreaCode = "CityManager_Mapper.selectCityManagerByAreaCode";

        /** 根据省份编号查询所有城市 */
        public static final String queryCityList = "CityManager_Mapper.queryCityList";

        /** 根据省份编码选择城市列表 */
        public static final String selectCitysByPcode = "CityManager_Mapper.selectCitysByPcode";

        public static final String selectTelnoCityByAreaCode = "CityManager_Mapper.selectTelnoCityByAreaCode";
    }

    /** 省份管理 **/
    public class ProvinceManager_Mapper{
        /** 根据ID查找省份信息 **/
        public static final String selectProvinceManagerById = "ProvinceManager_Mapper.selectProvinceManagerById";

        /** 根据名称查找省份信息 **/
        public static final String selectProvinceManagerByCode = "ProvinceManager_Mapper.selectProvinceManagerByCode";

        /** 保存号省份信息 **/
        public static final String saveProvinceManager = "ProvinceManager_Mapper.saveProvinceManager";

        /** 修改省份信息 **/
        public static final String updateProvinceManager = "ProvinceManager_Mapper.updateProvinceManager";

        /** 删除省份信息 **/
        public static final String deleteProvinceManager = "ProvinceManager_Mapper.deleteProvinceManager";

        /** 分页查询省份信息 **/
        public static final String pageProvinceManagerList = "ProvinceManager_Mapper.pageProvinceManagerList";

        /** 查询所有省份 */
        public static final String queryProvinceList = "ProvinceManager_Mapper.queryProvinceList";

        /** 根据省份名称模糊查询省份 */
        public static final String queryProvinceListByPname = "ProvinceManager_Mapper.queryProvinceListByPname";

    }


    /** 业务开通服务 **/
    public class BusinessType_Mapper{
        /** 根据UID查找智能云调度信息 **/
        public static final String checkBusinessInfo = "BusinessType_Mapper.checkBusinessInfo";

        public static final String insert = "BusinessType_Mapper.insert";

        public static final String updateStatus = "BusinessType_Mapper.updateStatus";

        public static final String countApp = "BusinessType_Mapper.countApp";

        public static final String countAxb = "BusinessType_Mapper.countAxb";

        public static final String count1 = "BusinessType_Mapper.count1";

        public static final String count3 = "BusinessType_Mapper.count3";

        public static final String countAppForbidden = "BusinessType_Mapper.countAppForbidden";

        public static final String getBusinessTypeInfo = "BusinessType_Mapper.getBusinessTypeInfo";

    }

    /** 增值服务 **/
    public class ExtraType_Mapper{
        /** 根据UID查找智能云调度信息 **/
        public static final String checkExtraInfo = "ExtraType_Mapper.checkExtraInfo";

        public static final String checkExtraBphone = "ExtraType_Mapper.checkExtraBphone";

        public static final String insert = "ExtraType_Mapper.insert";

        public static final String updateStatus = "ExtraType_Mapper.updateStatus";

        public static final String count1 = "ExtraType_Mapper.count1";

    }

    /** 供应商管理 **/
    public class Supplier_Mapper{
        /** 根据ID查找供应商信息 **/
        public static final String selectSupplierById = "Supplier_Mapper.selectSupplierById";

        /** 根据名称查找供应商信息 **/
        public static final String selectSupplierByName = "Supplier_Mapper.selectSupplierByName";

        /** 保存号供应商信息 **/
        public static final String saveSupplier = "Supplier_Mapper.saveSupplier";

        /** 修改供应商信息 **/
        public static final String updateSupplier = "Supplier_Mapper.updateSupplier";

        /** 删除供应商信息 **/
        public static final String deleteSupplier = "Supplier_Mapper.deleteSupplier";

        /** 分页查询供应商信息 **/
        public static final String pageSupplierList = "Supplier_Mapper.pageSupplierList";


        /** 根据供应商的编号查找供应商信息**/
        public static final String selectSupplierByCode = "Supplier_Mapper.selectSupplierByCode";

    }

    /**特例表 */
    public static final class AppointLink_Mapper{

        public static final String pageAppointLinkList = "AppointLink_Mapper.pageAppointLinkList";

        public static final String saveAppointLink = "AppointLink_Mapper.saveAppointLink";

        /** 根据id查找一条特例信息 */
        public static final String queryAppointLinkInfoById = "AppointLink_Mapper.queryAppointLinkInfoById";

        /** 全条件查找一条选线特例信息 */
        public static final String queryAppointLinkInfo = "AppointLink_Mapper.queryAppointLinkInfo";

        /** 修改特例 */
        public static final String updateAppointLinkInfo = "AppointLink_Mapper.updateAppointLinkInfo";

        /** 删除特例 */
        public static final String delAppointLinkInfo = "AppointLink_Mapper.delAppointLinkInfo";
    }


    /** 角色管理 **/
    public class Role_Mapper{
        /** 根据ID查找角色信息 **/
        public static final String selectRoleById = "Role_Mapper.selectRoleById";

        /** 根据名称查找角色信息 **/
        public static final String selectRoleByName = "Role_Mapper.selectRoleByName";

        /** 保存号角色信息 **/
        public static final String saveRole = "Role_Mapper.saveRole";

        /** 修改角色信息 **/
        public static final String updateRoleById = "Role_Mapper.updateRoleById";

        /** 删除角色信息 **/
        public static final String deleteRoleById = "Role_Mapper.deleteRoleById";

        /** 分页查询角色信息 **/
        public static final String pageRoleList = "Role_Mapper.pageRoleList";

        /** 查询所有角色信息 **/
        public static final String allRoleList = "Role_Mapper.allRoleList";

    }

    /** 权限管理 **/
    public class Action_Mapper {

        /** 分页查询action信息 **/
        public static final String pageAction = "Action_Mapper.pageAction";

        /** 得到权限树 **/
        public static final String selectATree = "Action_Mapper.selectATree";

        /** 插入一条记录 **/
        public static final String insertSelective = "Action_Mapper.insertSelective";

        /** 插入一条记录 **/
        public static final String deleteActionByMenuId = "Action_Mapper.deleteActionByMenuId";

        /** 根据mid选择 **/
        public static final String selectByMId = "Action_Mapper.selectByMId";

    }

    /** 中继群To头域号段 **/
    public class RelayGroup1_Mapper {

        public static final String pageGroup1 = "RelayGroup1_Mapper.pageGroup1";

        /** 根据ID查找中继群To头域号段信息 **/
        public static final String selectRelayGroup1ById = "RelayGroup1_Mapper.selectRelayGroup1ById";

        /** 根据名称查找中继群To头域号段信息 **/
        public static final String selectRelayGroup1ByName = "RelayGroup1_Mapper.selectRelayGroup1ByName";

        /** 保存号中继群To头域号段信息 **/
        public static final String saveRelayGroup1 = "RelayGroup1_Mapper.saveRelayGroup1";

        /** 修改中继群To头域号段信息 **/
        public static final String updateRelayGroup1ById = "RelayGroup1_Mapper.updateRelayGroup1ById";

        /** 删除中继群To头域号段信息 **/
        public static final String deleteByPrimaryKey = "RelayGroup1_Mapper.deleteByPrimaryKey";

        /** 分页查询中继群To头域号段信息 **/
        public static final String pageRelayGroup1List = "RelayGroup1_Mapper.pageRelayGroup1List";

        /** 查询所有中继群To头域号段信息 **/
        public static final String allRelayGroup1List = "RelayGroup1_Mapper.allRelayGroup1List";

    }

    /** 中继群运营商被叫前缀 **/
    public class RelayGroup2_Mapper {

        public static final String pageGroup2 = "RelayGroup2_Mapper.pageGroup2";

        /** 根据ID查找中继群运营商被叫前缀信息 **/
        public static final String selectRelayGroup2ById = "RelayGroup2_Mapper.selectRelayGroup2ById";

        /** 根据名称查找中继群运营商被叫前缀信息 **/
        public static final String selectRelayGroup2ByName = "RelayGroup2_Mapper.selectRelayGroup2ByName";

        /** 保存号中继群运营商被叫前缀信息 **/
        public static final String saveRelayGroup2 = "RelayGroup2_Mapper.saveRelayGroup2";

        /** 修改中继群运营商被叫前缀信息 **/
        public static final String updateRelayGroup2ById = "RelayGroup2_Mapper.updateRelayGroup2ById";

        /** 删除中继群运营商被叫前缀信息 **/
        public static final String deleteByPrimaryKey = "RelayGroup2_Mapper.deleteByPrimaryKey";

        /** 分页查询中继群运营商被叫前缀信息 **/
        public static final String pageRelayGroup2List = "RelayGroup2_Mapper.pageRelayGroup2List";

        /** 查询所有中继群运营商被叫前缀信息 **/
        public static final String allRelayGroup2List = "RelayGroup2_Mapper.allRelayGroup2List";
    }

    /** 中继群主叫强显号段表 **/
    public class RelayGroup3_Mapper {

        public static final String pageGroup3 = "RelayGroup3_Mapper.pageGroup3";
        /** 根据ID查找中继群主叫强显号段信息 **/
        public static final String selectRelayGroup3ById = "RelayGroup3_Mapper.selectRelayGroup3ById";

        /** 根据名称查找中继群主叫强显号段信息 **/
        public static final String selectRelayGroup3ByName = "RelayGroup3_Mapper.selectRelayGroup3ByName";

        /** 保存号中继群主叫强显号段信息 **/
        public static final String saveRelayGroup3 = "RelayGroup3_Mapper.saveRelayGroup3";

        /** 修改中继群主叫强显号段信息 **/
        public static final String updateRelayGroup3ById = "RelayGroup3_Mapper.updateRelayGroup3ById";

        /** 删除中继群主叫强显号段信息 **/
        public static final String deleteByPrimaryKey = "RelayGroup3_Mapper.deleteByPrimaryKey";

        /** 分页查询中继群主叫强显号段信息 **/
        public static final String pageRelayGroup3List = "RelayGroup3_Mapper.pageRelayGroup3List";

        /** 查询所有中继群主叫强显号段信息 **/
        public static final String allRelayGroup3List = "RelayGroup3_Mapper.allRelayGroup3List";
    }

    /** eyw **/
    public class CodeType_Mapper {

        public static final String pageCodeType = "CodeType_Mapper.pageCodeType";

        public static final String updateStatus = "CodeType_Mapper.updateByPrimaryKeySelective";
    }

    /** 角色-权限关联 **/
    public class RoleAction_Mapper {
        public static final String deleteByRoleId = "RoleAction_Mapper.deleteByRoleId";

        /**  **/
        public static final String insertSelective = "RoleAction_Mapper.insertSelective";
    }

    /** 用户-角色关联 **/
    public class UserRole_Mapper {
        public static final String deleteByRoleId = "UserRole_Mapper.deleteByRoleId";

        /**  **/
        public static final String insertSelective = "RoleAction_Mapper.insertSelective";
    }


    /** 语音通知模板审核 **/
    public class TempVoice_Mapper {

        /** 分页查询语音通知 **/
        public static final String pageVoiceList = "TempVoice_Mapper.pageVoiceList";

        /** 根据主键选择一个语音模板 **/
        public static final String selectByPrimaryKey = "TempVoice_Mapper.selectByPrimaryKey";

        /** 审核 **/
        public static final String updateByPrimaryKeySelective = "TempVoice_Mapper.updateByPrimaryKeySelective";

        /** 得到语音模板的一些信息 如 sid，客户名称,appid 等 */
        public static final String selectTVInfoByTempId = "TempVoice_Mapper.selectTVInfoByTempId";

        /**获取未转换为语音文件的文本模版*/
        public static final String getNotTransTempVoice ="TempVoice_Mapper.getNotTransTempVoice";
    }



    /** 语音验证码模板审核 **/
    public class VoiceVerify_Mapper {

        /** 分页查询语音通知 **/
        public static final String pageVoiceList = "VoiceVerify_Mapper.pageVoiceList";

        /** 根据主键选择一个语音模板 **/
        public static final String selectByPrimaryKey = "VoiceVerify_Mapper.selectByPrimaryKey";

        /** 审核 **/
        public static final String updateByPrimaryKeySelective = "VoiceVerify_Mapper.updateByPrimaryKeySelective";

        /** 得到语音模板的一些信息 如 sid，客户名称,appid 等 */
        public static final String selectTVInfoByTempId = "VoiceVerify_Mapper.selectTVInfoByTempId";

        /**获取未转换为语音文件的文本模版*/
        public static final String getNotTransTempVoice ="VoiceVerify_Mapper.getNotTransTempVoice";
    }



    /** 公共号码资源池 **/

    /** sip 号码 */
    public class SipPhone_Mapper {

        /** 选择一定数量的sip 号码 （未分配的） */
        public static final String selectSipPhonesByAllot = "SipPhone_Mapper.selectSipPhonesByAllot";

        public static final String updateByPrimaryKeySelective = "SipPhone_Mapper.updateByPrimaryKeySelective";

        public static final String selectIPPortList = "SipPhone_Mapper.selectIPPortList";


        /** 分页获取SIP号码资源池基本信息 **/
        public static final String pageSipPhoneList = "SipPhone_Mapper.pageSipPhoneList";
        /** 导出公共号码资源池基本信息 **/
        public static final String downloadSipPhoneList = "SipPhone_Mapper.downloadSipPhoneList";
        /** 新增号码池 **/
        public static final String insertSipPhone = "SipPhone_Mapper.insertSipPhone";
        /**批量删除sip号码**/
        public static final String deleteSipPhoneByIds = "SipPhone_Mapper.deleteSipPhoneByIds";
        /** 根据ID查询号码信息 **/
        public static final String getSipPhoneById = "SipPhone_Mapper.getSipPhoneById";
        /** 根据号码查询SIP号码信息 **/
        public static final String getSipPhoneBySipPhone = "SipPhone_Mapper.getSipPhoneBySipPhone";
        /** 判断sipphone是否注册 **/
        public static final String checkRegisterSipphone = "SipPhone_Mapper.checkRegisterSipphone";
    }

    /** sip 号码 */
    public class FixPhone_Mapper {

        /** 选择一定数量的sip 号码 （未分配的） */
        public static final String selectFixPhonesByAllot = "FixPhone_Mapper.selectFixPhonesByAllot";

        public static final String updateByPrimaryKeySelective = "FixPhone_Mapper.updateByPrimaryKeySelective";

        /** 分页获取SIP号码资源池基本信息 **/
        public static final String pageFixPhoneList = "FixPhone_Mapper.pageFixPhoneList";
        /** 导出固话号码资源池基本信息 **/
        public static final String downloadFixPhoneList = "FixPhone_Mapper.downloadFixPhoneList";
        /** 新增号码池 **/
        public static final String insertFixPhone = "FixPhone_Mapper.insertFixPhone";
        /**批量删除固话号码**/
        public static final String deleteFixPhoneByIds = "FixPhone_Mapper.deleteFixPhoneByIds";
        /** 根据ID查询号码信息 **/
        public static final String getFixPhoneById = "FixPhone_Mapper.getFixPhoneById";
        /** 根据号码查询固话号码信息 **/
        public static final String getFixPhoneByNum = "FixPhone_Mapper.getFixPhoneByNum";

    }

    /** 云话机外显号码审核 **/
    public static final class ShowNumApply_Mapper{
        /**分页查询外显号码审核列表**/
        public static final String pageShowNumApplyList = "ShowNumApply_Mapper.pageShowNumApplyList";
        /**根据ids查询多个spApplyNum**/
        public static final String getSpApplyNumByIds = "ShowNumApply_Mapper.getSpApplyNumByIds";
        /**批量审核外显号码**/
        public static final String updateSpApplyNums = "ShowNumApply_Mapper.updateSpApplyNums";
        /**根据ids查询多个审批记录**/
        public static final String getSpNumAuditByIds = "ShowNumApply_Mapper.getSpNumAuditByIds";
        /**审核通过批量更新tb_sp_apply_num表信息**/
        public static final String updateAppNumberList = "ShowNumApply_Mapper.updateAppNumberList";
        /**审核通过批量更新tb_sp_num_audio表信息**/
        public static final String updateSpNumAudio = "ShowNumApply_Mapper.updateSpNumAudio";

        public static final String insertNumAuditSelective = "ShowNumApply_Mapper.insertNumAuditSelective";

        public static final String getSpNumAuditByShowNumId = "ShowNumApply_Mapper.getSpNumAuditByShowNumId";

    }

    /** 云话机申请记录 **/
    public static final class SipPhoneApply_Mapper {

        /**分页查询云话机申请记录*/
        public static final String pageApplyList = "SipPhoneApply_Mapper.pageApplyList";

        /** 查询申请信息包含城市和省份名称 */
        public static final String selectApplyWithCity = "SipPhoneApply_Mapper.selectApplyWithCity";

        /** 保存一条审计记录 */
        public static final String insertSelective = "SipPhoneApply_Mapper.insertSelective";

        /** 保存一条审计记录 */
        public static final String updateByPrimaryKeySelective = "SipPhoneApply_Mapper.updateByPrimaryKeySelective";

        public static final String countAllot = "SipPhoneApply_Mapper.countAllot";
    }

    /** 云话机申请号码 */
    public static final class SpApplyNum_Mapper {
        public static final String pageNumList = "SpApplyNum_Mapper.pageNumList";

        //删除外显号（批量）
        public static final String updateShowNumStatus = "SpApplyNum_Mapper.updateShowNumStatus";

        // 保存外显号
        public static final String insertSelective = "SpApplyNum_Mapper.insertSelective";

        public static final String insertSelectiveAndCopyShowNum = "SpApplyNum_Mapper.insertSelectiveAndCopyShowNum";

        public static final String checkSipphone = "SpApplyNum_Mapper.checkSipphone";

        public static final String checkFixphone = "SpApplyNum_Mapper.checkFixphone";

        public static final String checkShowNum = "SpApplyNum_Mapper.checkShowNum";

        public static final String selectShowNumWithSipFixPhoneByPK = "SpApplyNum_Mapper.selectShowNumWithSipFixPhoneByPK";

        public static final String updateByPrimaryKeySelective = "SpApplyNum_Mapper.updateByPrimaryKeySelective";

        public static final String selectByPrimaryKey = "SpApplyNum_Mapper.selectByPrimaryKey";

        public static final String countSpApplyNumByFixId = "SpApplyNum_Mapper.countSpApplyNumByFixId";

        public static final String updateSipStatus = "SpApplyNum_Mapper.updateSipStatus";
    }

    // sipphone 内网外网映射关系
    public static final class SPIPMapper_Mapper {

        // 根据外网ip选择一条映射关系
        public static final String selectByOuterIpAndEnv = "SPIPMapper_Mapper.selectByOuterIpAndEnv";

        public static final String selectRegAddInfo = "SPIPMapper_Mapper.selectRegAddInfo";

        public static final String selectRegDelInfo = "SPIPMapper_Mapper.selectRegDelInfo";

        public static final String updateShowNumRegStatus = "SPIPMapper_Mapper.updateShowNumRegStatus";
    }

    // 云话机注册任务列表
    public static final class SpRegTask_Mapper {

        // 选择代办任务列表
        public static final String selectToDoTaskList = "SpRegTask_Mapper.selectToDoTaskList";

        // 根据主键更新任务状态
        public static final String updateStatusByPK = "SpRegTask_Mapper.updateStatusByPK";

        // 锁定状态
        public static final String updateTaskStatusForLocked = "SpRegTask_Mapper.updateTaskStatusForLocked";

        public static final String insertSelective = "SpRegTask_Mapper.insertSelective";
    }

    // 供应商线路资源
    public class RelayRes_Mapper {

        public static final String selectByPrimaryKey = "RelayRes_Mapper.selectByPrimaryKey";

        public static final String insertSelective = "RelayRes_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "RelayRes_Mapper.updateByPrimaryKeySelective";

        public static final String pageRes = "RelayRes_Mapper.pageRes";

        public static final String updateForDelByPK = "RelayRes_Mapper.updateForDelByPK";

        public static final String selectResWithPersAndCityById = "RelayRes_Mapper.selectResWithPersAndCityById";
    }

    // 供应商线路资源 -- 价格详细
    public class RelayResPer_Mapper {
        public static final String insertSelective = "RelayResPer_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "RelayResPer_Mapper.updateByPrimaryKeySelective";

        public static final String delByResId = "RelayResPer_Mapper.delByResId";
    }


    // Http任务相关
    public class TaskHttp_Mapper {
        public static final String insertSelective = "TaskHttp_Mapper.insertSelective";

        public static final String pageTaskList = "TaskHttp_Mapper.pageTaskList";

        public static final String delTaskHttpInfo = "TaskHttp_Mapper.delTaskHttpInfo";

        public static final String updateTaskHttp = "TaskHttp_Mapper.updateByPrimaryKeySelective";

        public static final String queryTaskHttpById = "TaskHttp_Mapper.queryTaskHttpById";

        public static final String updateTaskHttpList = "TaskHttp_Mapper.updateTaskHttpList";


    }

    // Http任务相关URL表业务
    public class UrlHttp_Mapper {

        public static final String insertSelective = "UrlHttp_Mapper.insertSelective";

        public static final String pageURLList = "UrlHttp_Mapper.pageURLList";

        public static final String queryListByDId = "UrlHttp_Mapper.queryListByDId";

        public static final String updateUrlHttp = "UrlHttp_Mapper.updateByPrimaryKeySelective";

        public static final String delUrlHttpInfo = "UrlHttp_Mapper.deleteByPrimaryKey";


    }


    // 任务执行相关
    public class ExecuteHttp_Mapper {

        public static final String insertSelective = "ExecuteHttp_Mapper.insertSelective";

        public static final String queryExecuteHttpById = "ExecuteHttp_Mapper.queryExecuteHttpById";
    }

    /** 中继--区号业务相关sql */
    public static final class RelayBusinessAreaCode_Mapper {

        public static final String insertSelective = "RelayBusinessAreaCode_Mapper.insertSelective";

        public static final String deleteByRelayId = "RelayBusinessAreaCode_Mapper.deleteByRelayId";

        public static final String findListByRelayId = "RelayBusinessAreaCode_Mapper.findListByRelayId";
    }

    /** 中继--0前缀业务相关sql */
    public static final class RelayBusinessZero_Mapper {

        public static final String insertSelective = "RelayBusinessZero_Mapper.insertSelective";

        public static final String deleteByRelayId = "RelayBusinessZero_Mapper.deleteByRelayId";

        public static final String findListByRelayId = "RelayBusinessZero_Mapper.findListByRelayId";
    }

    /** ecc 非sip号码 */
    public class EccFixPhone_Mapper {
        /** 分页获取ecc非SIP号码资源池基本信息 **/
        public static final String pageEccFixPhoneList = "EccFixPhone_Mapper.pageEccFixPhoneList";

        public static final String selectEccFixphoneByNumber = "EccFixPhone_Mapper.selectEccFixphoneByNumber";

        public static final String insertSelective = "EccFixPhone_Mapper.insertSelective";

        public static final String deleteByPrimaryKey = "EccFixPhone_Mapper.deleteByPrimaryKey";
    }

    /** 资源池 -- sip号码*/
    public class EccSipphone_Mapper {

        /**分页获取SIP号码资源池基本信息**/
        public static final String pageEccSipPhoneList = "EccSipphone_Mapper.pageEccSipphoneList";
        /** 新增号码池 **/
        public static final String insertSipPhone = "EccSipphone_Mapper.insertSelective";
        /**批量删除sip号码**/
        public static final String deleteSipPhoneByIds = "EccSipphone_Mapper.deleteSipPhoneByIds";
        /** 根据ID查询号码信息 **/
        public static final String getSipPhoneById = "EccSipphone_Mapper.selectByPrimaryKey";
        /** 根据号码查询SIP号码信息 **/
        public static final String getSipPhoneBySipPhone = "EccSipphone_Mapper.getSipPhoneBySipPhone";
        /** 导出公共号码资源池基本信息 **/
        public static final String downloadSipPhoneList = "EccSipphone_Mapper.downloadSipPhoneList";

        public static final String selectIPPortList = "EccSipphone_Mapper.selectIPPortList";

        public static final String updateByPrimaryKeySelective = "EccSipphone_Mapper.updateByPrimaryKeySelective";
    }

    /** 资源池 -- 外显号码*/
    public class EccShowNum_Mapper {
        /**分页获取外显号码资源池基本信息**/
        public static final String pageEccShowNumList = "EccShowNum_Mapper.pageEccShowNumList";
        /** 根据号码查询SIP号码信息 **/
        public static final String getEccShowNumByNumber = "EccShowNum_Mapper.getEccShowNumByNumber";
        /** 新增外显号码 **/
        public static final String insertEccShowNum = "EccShowNum_Mapper.insertSelective";
        /** 根据ID查询号码信息 **/
        public static final String getEccShowNumById = "EccShowNum_Mapper.selectByPrimaryKey";
        /**批量删除外显号码**/
        public static final String deleteEccShowNumByIds = "EccShowNum_Mapper.deleteEccShowNumByIds";
        /**批量删除外显号码**/
        public static final String updateEccShowNumById = "EccShowNum_Mapper.updateByPrimaryKeySelective";
        /** 根据ID查询号码信息包括省份城市 **/
        public static final String getEccShowNum = "EccShowNum_Mapper.selectById";
        /** 导出公共号码资源池基本信息 **/
        public static final String downloadEccShowNumList = "EccShowNum_Mapper.downloadEccShowNumList";
    }

    /** 资源池 -- 总机号码*/
    public class EccSwitchBoard_Mapper {
        /**分页获取总机号码资源池基本信息**/
        public static final String pageEccSwitchBoardList = "EccSwitchboard_Mapper.pageEccSwitchboardList";
        /** 根据号码查询总机号码信息 **/
        public static final String getEccSwitchBoardByNumber = "EccSwitchboard_Mapper.getEccSwitchBoardByNumber";
        /** 新增总机号码 **/
        public static final String insertEccSwitchBoard = "EccSwitchboard_Mapper.insertSelective";
        /** 根据ID查询总机号码信息 **/
        public static final String getEccSwitchBoardById = "EccSwitchboard_Mapper.selectByPrimaryKey";
        /**批量删除总机号码**/
        public static final String deleteEccSwitchBoardByIds = "EccSwitchboard_Mapper.deleteEccSwitchBoradByIds";
        /**批量删除总机号码**/
        public static final String updateEccSwitchBoardById = "EccSwitchboard_Mapper.updateByPrimaryKeySelective";
        /** 根据ID查询总机号码信息包括省份城市 **/
        public static final String getEccSwitchBoard = "EccSwitchboard_Mapper.selectById";
        /** 导出公共号码资源池总机号码基本信息 **/
        public static final String downloadEccSwitchBoardList = "EccSwitchboard_Mapper.downloadEccSwitchBoardList";
    }

    /**add by dukai 20170213 云总机费率信息操作**/
    public static  final class IvrRate_Mapper {
        /** 根据费率Id查找费率信息 */
        public static final String selectIvrRateByFeeId = "IvrRate_Mapper.selectIvrRateByFeeId";
        /**添加费率信息*/
        public static final String insertIvrRate = "IvrRate_Mapper.insertSelective";
        /**修改费率信息*/
        public static final String updateIvrRate = "IvrRate_Mapper.updateByPrimaryKeySelective";
        /**联表查询费率信息*/
        public static final String selectIvrRateUnionInfo = "IvrRate_Mapper.selectIvrRateUnionInfo";
        /**分页联表查询费率信息**/
        public static final String selectPageIvrRateUnion = "IvrRate_Mapper.selectPageIvrRateUnion";
    }

    /** ECC 分机号**/
    public class EccExtention_Mapper {

        /**根据appid分页查询分机号**/
        public static final String pageExtention = "EccExtention_Mapper.pageExtention";

        // 开启禁用长途、开启禁用号码
        public static final String updateSubNumStatus = "EccExtention_Mapper.updateSubNumStatus";

        public static final String updateByPrimaryKeySelective = "EccExtention_Mapper.updateByPrimaryKeySelective";

        public static final String selectExtentionByPK = "EccExtention_Mapper.selectExtentionByPK";

        //
        public static final String countSubNumByMap = "EccExtention_Mapper.countSubNumByMap";

        public static final String insertSelective = "EccExtention_Mapper.insertSelective";

        public static final String deleteByPrimaryKey = "EccExtention_Mapper.deleteByPrimaryKey";

        public static final String countExtentionByEccId = "EccExtention_Mapper.countExtentionByEccId";


        public static final String getExtentionNumList = "EccExtention_Mapper.getExtentionNumList";
    }

    // ECC 配置信息
    public class EccAppInfo_Mapper {
        public static final String selectEccAppInfoByAppid = "EccAppInfo_Mapper.selectEccAppInfoByAppid";

        public static final String selectByPrimaryKey = "EccAppInfo_Mapper.selectByPrimaryKey";

        public static final String insertSelective = "EccAppInfo_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "EccAppInfo_Mapper.updateByPrimaryKeySelective";
    }

    // sip url
    public class Sipurl_Mapper {

        public static final String pageSipurl = "Sipurl_Mapper.pageSipurl";

        public static final String insertSelective = "Sipurl_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "Sipurl_Mapper.updateByPrimaryKeySelective";

        // 校验号码的唯一性
        public static final String countNum = "Sipurl_Mapper.countNum";

        // 根据主键删除一条记录
        public static final String deleteByPrimaryKey = "Sipurl_Mapper.deleteByPrimaryKey";

        public static final String selectByPrimaryKey = "Sipurl_Mapper.selectByPrimaryKey";
    }

    /** tb_cb_task**/
    public static final class CbTask_Mapper {

        /** 新增 **/
        public static final String insertSelective = "CbTask_Mapper.insertSelective";
    }

    /** tb_cb_voice_code*/
    public static final class CbVoiceCode_Mapper {

        /** 新增 **/
        public static final String insertSelective = "CbVoiceCode_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "CbVoiceCode_Mapper.updateByPrimaryKeySelective";

        public static final String findCbVoiceCodeByBusCode = "CbVoiceCode_Mapper.selectCbVoiceCodeByBusCode";
    }

    /** Axb虚拟小号-公共号码池 **/
    public class AxbNumResPool_Mapper{

        public static final String selectCitys = "AxbNumResPool_Mapper.selectCitys";

        public static final String pageAxbNumList = "AxbNumResPool_Mapper.pageAxbNumList";

        public static final String getAxbPhoneByAxbPhone = "AxbNumResPool_Mapper.getAxbPhoneByAxbPhone";

        public static final String insertAxbPhone = "AxbNumResPool_Mapper.insertAxbPhone";

        public static final String getAxbPhoneById = "AxbNumResPool_Mapper.getAxbPhoneById";

        public static final String deleteAxbPhoneByIds = "AxbNumResPool_Mapper.deleteAxbPhoneByIds";

        public static final String updateAxbPhoneByStatus = "AxbNumResPool_Mapper.updateAxbPhoneByStatus";

        public static final String checkAxbNumberMatchAreacode = "AxbNumResPool_Mapper.checkAxbNumberMatchAreacode";

    }

    /** Axb虚拟小号-客户号码池 **/
    public class AxbCustNumResPool_Mapper{

        public static final String selectAxbAppPoolList = "AxbCustNumResPool_Mapper.selectAxbAppPoolList";

        public static final String insetAxbCustNumberPool = "AxbCustNumResPool_Mapper.insetAxbCustNumberPool";

        public static final String selectAxbCustNumberPoolByObj = "AxbCustNumResPool_Mapper.selectAxbCustNumberPoolByObj";

        public static final String selectAxbCustNumberList = "AxbCustNumResPool_Mapper.selectAxbCustNumberList";

        public static final String selectAxbAppPoolByObj = "AxbCustNumResPool_Mapper.selectAxbAppPoolByObj";

        public static final String insetAxbCustAppPool = "AxbCustNumResPool_Mapper.insetAxbCustAppPool";

        public static final String checkHasNumber = "AxbCustNumResPool_Mapper.checkHasNumber";

        public static final String deleteAxbCUstAppPool = "AxbCustNumResPool_Mapper.deleteAxbCUstAppPool";

        public static final String getAxbNumRelationByNum = "AxbCustNumResPool_Mapper.getAxbNumRelationByNum";

        public static final String deleteAxbCustNum = "AxbCustNumResPool_Mapper.deleteAxbCustNum";

        public static final String updateAxbCustNumByStatus = "AxbCustNumResPool_Mapper.updateAxbCustNumByStatus";

        public static final String updateAxbNumRelationByObj = "AxbCustNumResPool_Mapper.updateAxbNumRelationByObj";
    }

    /** 语音验证码-应用号码池 **/
    public class VoiceVerifyApp_Mapper{

        public static final String selectVoiceAppPoolList = "VoiceVerifyApp_Mapper.selectVoiceAppPoolList";

        public static final String insetVoiceCustAppPool = "VoiceVerifyApp_Mapper.insertSelective";

    }


    /** 语音验证码-客户号码池 **/
    public class VoiceVerCustNum_Mapper{

        public static final String getVoiceCustAppPoolByObj = "VoiceVerCustNum_Mapper.getVoiceCustAppPoolByObj";

        public static final String insetVoiceCustNumberPool = "VoiceVerCustNum_Mapper.insertSelective";

        public static final String checkHasNumber = "VoiceVerCustNum_Mapper.checkHasNumber";

        public static final String deleteVoiceCustAppPool = "VoiceVerCustNum_Mapper.deleteVoiceCustAppPool";

        public static final String selectVoiceCustNumberList = "VoiceVerCustNum_Mapper.selectVoiceCustNumberList";

        public static final String selectVoiceCustNumberPoolByObj = "VoiceVerCustNum_Mapper.selectVoiceCustNumberPoolByObj";

        public static final String deleteVoiceCustNum = "VoiceVerCustNum_Mapper.updateByPrimaryKeySelective";


    }


    /** 语音验证码公共资源号码池 **/
    public class VoiceVerifyNumPool_Mapper {

        // 分页查询
        public static final String pageVoiceVerifyNumPool = "VoiceVerifyNumPool_Mapper.pageVoiceVerifyNumPool";

        public static final String getVoiceNumPoolByPhone = "VoiceVerifyNumPool_Mapper.getVoiceNumPoolByPhone";

        public static final String updateVoicePhoneByStatus = "VoiceVerifyNumPool_Mapper.updateVoicePhoneByStatus";

        public static final String getVoicePhoneById = "VoiceVerifyNumPool_Mapper.getVoicePhoneById";

        public static final String updateVoicePhoneNumByStatus = "VoiceVerifyNumPool_Mapper.updateByPrimaryKeySelective";

        // 根据id选择号码
        public static final String selectNumByIds = "VoiceVerifyNumPool_Mapper.selectNumByIds";

        // 标注为删除状体
        public static final String updateToDelStatus = "VoiceVerifyNumPool_Mapper.updateToDelStatus";

        // 保存一条记录
        public static final String insertSelective = "VoiceVerifyNumPool_Mapper.insertSelective";

        // 根据号码统计
        public static final String countNumbyNumber = "VoiceVerifyNumPool_Mapper.countNumbyNumber";

    }
}
