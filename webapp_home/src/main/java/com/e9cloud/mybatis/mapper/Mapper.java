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

        public static final String selectValueByKey = "Hello_Mapper.selectValueByKey";

        public static final String selectDataByPage = "Hello_Mapper.selectDataByPage";

    }

    /** 账户相关管理 */
    public static final class Account_Mapper {

        public static final String updateAccountByPrimaryKeySelective = "User_Mapper.updateAccountByPrimaryKeySelective";

        /** 得到用户包括salt和password */
        public static final String selectAccountForAuthentication = "User_Mapper.selectAccountForAuthentication";

        /** 得到用户包括salt和password */
        public static final String selectAccountByUid = "User_Mapper.selectAccountByUid";

        /** 注册时保存用户信息 */
        public static final String insertUserForReg = "User_Mapper.insertUserForReg";

        /** 注册时初始化sys_type表 */
        public static final String insertUserForBusType = "User_Mapper.insertUserForBusType";

        public static final String selectUserByEmail = "User_Mapper.selectUserByEmail";

        public static final String getAuthStatusByUid = "User_Mapper.getAuthStatusByUid";

        /** 更改用户的认证状态 */
        public static final String updateAuthStatus = "User_Mapper.updateAuthStatus";
    }

    /** 用户相关操作 */
    public static final class User_Mapper {

        /** 根据主键查找用户 */
        public static  final String selectByPrimaryKey = "User_Mapper.selectByPrimaryKey";

        /** 根据邮箱统计用户数量 */
        public static final String countUserByEmail = "User_Mapper.countUserByEmail";

        /** 根据手机号统计用户数量 */
        public static final String countUserByMobile = "User_Mapper.countUserByMobile";

        /*查询用户基本信息及扩展信息*/
        public static final String selectUserAndExternInfoForUid = "User_Mapper.selectUserAndExternInfoForUid";

        /*修改用户基本信息*/
        public static final String updateUserInfo = "User_Mapper.updateByPrimaryKeySelective";

        /*修改用户外部信息*/
        public static final String updateUserExternInfo = "UserExternInfo_Mapper.updateByPrimaryKeySelective";
        /*根据email查询user信息*/
        public static final String selectuser="User_Mapper.selectuser";
        /*重置密码*/
        public static final String updatepassword="User_Mapper.updatepassword";

        /*保存用户外部信息*/
        public static final String saveUserExternInfo = "UserExternInfo_Mapper.insertSelective";
        /*验证用户外部信息是否存在*/
        public static final  String isExistUserExtern = "UserExternInfo_Mapper.isExistUserExtern";

    }

    /** 字典表相关操作 */
    public static final class DicData_Mapper {

        /** 根据字典表类型查询字典信息 */
        public static final String findDicListByType = "DicData_Mapper.findDicListByType";

        /** 根据父类ID查询字典信息 */
        public static final String findDicListByPid = "DicData_Mapper.findDicListByPid";
    }

    /** 感兴趣的产品 */
    public static final class Interest_Mapper {
        /** 保存用户感兴趣的产品 */
        public static final String insert = "Interest_Mapper.insert";
    }

    /** 充值记录相关sql */
    public static final class RechargeRecord_Mapper {
        /** 分页查询充值记录 */
        public static final String pageRechargeRecord = "RechargeRecord_Mapper.pageRechargeRecord";
        /** excel导出充值记录 */
        public static final String selectRechargeRecordList = "RechargeRecord_Mapper.selectRechargeRecordList";
    }

    /** 资费信息相关sql */
    public static  final  class RestRate_Mapper{
        /** 查询资费信息*/
        public static final String selectRestRateForFeeid = "RestRate_Mapper.selectRestRateForFeeid";

    }

    public static  final  class CallRate_Mapper{
        /** 查询资费信息call*/
        public static final String selectCallRateForFeeid = "CallRate_Mapper.selectCallRateForFeeid";
    }

    public static  final  class MaskRate_Mapper{
        /** 查询资费信息call*/
        public static final String selectMaskRateForFeeid = "MaskRate_Mapper.selectMaskRateForFeeid";

        public static final String selectSipphonRateForFeeid = "SipphonRate_Mapper.selectSipphonRateForFeeid";

        public static final String findVoiceRateByFeeid = "VoiceRate_Mapper.findVoiceRateByFeeid";

        public static final String countCc = "MaskRate_Mapper.countCc";

        public static final String countSipPhone = "MaskRate_Mapper.countSipPhone";

        public static final String countEcc = "MaskRate_Mapper.countEcc";

        public static final String countAxb = "MaskRate_Mapper.countAxb";

    }


    public static  final  class IvrRate_Mapper{
        /** 查询资费信息*/
        public static final String findIvrRateRateByFeeid = "IvrRate_Mapper.findIvrRateRateByFeeid";

    }

    public static  final  class AxbRate_Mapper{
        /** 查询资费信息*/
        public static final String findAxbRateByFeeid = "AxbRate_Mapper.findAxbRateByFeeid";

    }



    /** APP表相关操作 */
    public static final class App_Mapper {

        /** 查询用户列表集合*/
        public static final String findAppListCountByMap = "AppInfo_Mapper.findAppListCountByMap";

        /** 查询云话机应用正常分配的号码数量*/
        public static final String findAppNumCountByAppID = "AppInfo_Mapper.findAppNumCountByAppID";

        /** 查询用户列表数量*/
        public static final String findAppListByMap = "AppInfo_Mapper.findAppListByMap";

        /** 查询应用列表信息*/
        public static final String selectAppInfoListByMap = "AppInfo_Mapper.selectAppInfoListByMap";

        /**  查询APP的详细信息*/
        public static final String findAppInfoByAppId = "AppInfo_Mapper.findAppInfoByAppId";

        /**  查询APP的详细信息根据ID*/
        public static final String findAppInfoById = "AppInfo_Mapper.selectByPrimaryKey";

        public static final String findAppInfoBySIdAndBusType = "AppInfo_Mapper.findAppInfoBySIdAndBusType";

        /**  创建应用*/
        public static final String saveApp = "AppInfo_Mapper.insertSelective";

        /**  创建应用*/
        public static final String saveSubApp = "SubApp_Mapper.insertSelective";

        /**  修改应用*/
        public static final String updateApp = "AppInfo_Mapper.updateByPrimaryKeySelective";

        /** 应用信息和子应用信息联合查询 **/
        public static final String selectAppInfoUnionSubApp = "AppInfo_Mapper.selectAppInfoUnionSubApp";

        /** 查询所有应用信息 **/
        public static final String selectAllAppInfo = "AppInfo_Mapper.selectAllAppInfo";

    }

    /** 云话机——-应用列表 **/
    public static final class AppInfoExtra_Mapper {
        /** 应用创建 **/
        public static final String saveAppExtra = "AppInfoExtra_Mapper.insertSelective";
        /** 应用查看 **/
        public static final String findAppExtraByAppId = "AppInfoExtra_Mapper.findAppExtraByAppId";

        public static final String updateAppExtra = "AppInfoExtra_Mapper.updateByPrimaryKeySelective";
    }



    /** Rest消费统计相关查询 **/
    public static final class RestStafDayRecord_Mapper {
        /** 根据条件获取统计信息 **/
        public static final String selectRestStafDayRecordByObj = "RestStafDayRecord_Mapper.selectRestStafDayRecordByObj";

        /** 近期消费走势（月消费走势） **/
        public static final String selectConsumeTrendView = "RestStafDayRecord_Mapper.selectConsumeTrendView";

        /** 应用消费概况联表查询 **/
        public static final String selectConsumeSurveyView = "RestStafDayRecord_Mapper.selectConsumeSurveyView";

        /** 获取每个月的消费总额 **/
        public static final String selectMonthConsumeTotal = "RestStafDayRecord_Mapper.selectMonthConsumeTotal";

        /** 获取每天的消费总额 **/
        public static final String selectRestDayConsumeTotal = "RestStafDayRecord_Mapper.selectRestDayConsumeTotal";

        /** 获取数据日报信息 **/
        public static final String getDataAnalysisList = "RestStafDayRecord_Mapper.getDataAnalysisList";

        public static final String getDataAnalysisCount = "RestStafDayRecord_Mapper.getDataAnalysisCount";

    }

    /** Mask消费统计相关查询 **/
    public static final class StatMaskDayRecord_Mapper {
        /** 获取每天的消费总额 **/
        public static final String selectMaskDayConsumeTotal = "StatMaskDayRecord_Mapper.selectMaskDayConsumeTotal";
    }



    /** 认证信息相关操作 **/
    public static final class UserCompany_Mapper {
        /**  添加认证信息*/
        public static final String saveAuth = "UserCompany_Mapper.insertSelective";

        /**  添加认证信息到记录表里*/
        public static final String saveAuthToRecords = "AuthenCompanyRecords_Mapper.insertSelective";

        /** 根据税务登记证号码统计用户数量 */
        public static final String countUserByTaxregNo = "UserCompany_Mapper.countUserByTaxregNo";

        public static final String countUserByName = "UserCompany_Mapper.countUserByName";
        /** 根据uid查询所有的认证信息 */
        public static final String findAuthInfoByUid = "UserCompany_Mapper.findAuthInfoByUid";
        /** 根据uid查认证状态 */
        public static final String getAuthStatusByUid = "UserCompany_Mapper.getAuthStatusByUid";

        /** 统计公司数量 */
        public static final String countUserByCompany = "UserCompany_Mapper.countUserByCompany";

        /** 统计公司数量 */
        public static final String countUserByCompanyForAll = "UserCompany_Mapper.countUserByCompanyForAll";

    }

    /** 审核认证信息记录表 **/
    public static final class AuthenCompanyRecords_Mapper {
        /** 根据uid查询所有的认证信息 */
        public static final String findAuthingInfoByUid = "AuthenCompanyRecords_Mapper.findAuthingInfoByUid";
    }



    /** 呼叫中心 */
    public static final class CallCenter_Mapper {
        /** 查询所有的记录 **/
        public static final String selectAll = "CallCenter_Mapper.selectAll";

    }

    /** 城市列表 */
    public static final class CityManager_Mapper {
        public static final String selectCitysByPcode = "CityManager_Mapper.selectCitysByPcode";

        public static final String findcityALL = "CityManager_Mapper.findcityALL";
    }

    /** 省份列表 */
    public static final class Province_Mapper {
        /** 查询所有的记录 **/
        public static final String selectAll = "Province_Mapper.selectAll";

    }

    /** 呼叫中心 */
    public static final class Area_Mapper {
        /** 查询所有的记录 **/
        public static final String selectAll = "Area_Mapper.selectAll";

    }


    /** 通话记录 */
    public static final class StafRecord_Mapper {
        public static final String pageRecentCall = "StafRecord_Mapper.pageRecentCall";
        public static final String insertSelective = "StafRecord_Mapper.insertSelective";
        public static final String procMonth = "StatCcDayRecord_Mapper.procMonth";
        public static final String getMonthRecordByCallCenter = "StafRecord_Mapper.getMonthRecordByCallCenter";
        public static final String getMonthRecordByDay = "StafRecord_Mapper.getMonthRecordByDay";
        public static final String getMonthRecordTotal = "StafRecord_Mapper.getMonthRecordTotal";

        public static final String pageCallIn = "StafRecord_Mapper.pageCallIn";
        public static final String pageCallOut = "StafRecord_Mapper.pageCallOut";

        /**呼入和呼出*/
        public static final String countAllInOutByDay = "StafRecord_Mapper.countAllInOutByDay";

        /**接通状态*/
        public static final String countAllConnByDay = "StafRecord_Mapper.countAllConnByDay";

        /**统计呼叫中心的呼叫量*/
        public static final String countAllByCC = "StafRecord_Mapper.countAllByCC";
    }

    /** 月话单详情下载 **/
    public static final class StafMonthFile_Mapper{
        /** 根据条件查询话单详情记录 **/
        public static final String findStafMonthFileList = "StafMonthFile_Mapper.findStafMonthFileList";
    }


    /** 铃声管理相关 **/

    public static final class AppVoice_Mapper{
        /** 根据条件查询铃声详情记录 **/
        public static final String voiceListBySid = "AppVoice_Mapper.voiceListBySid";

        public static final String voicePhoneListBySid = "AppVoice_Mapper.voicePhoneListBySid";

        public static final String findAppNameByID = "AppVoice_Mapper.findAppNameByID";

        public static final String findAppNameByAPPid = "AppVoice_Mapper.findAppNameByAPPid";

        public static final String findVoiceListByAppid = "AppVoice_Mapper.findVoiceListByAppid";

        public static final String saveVoice = "AppVoice_Mapper.insertSelective";

        public static final String delVoice = "AppVoice_Mapper.deleteByPrimaryKey";

        public static final String updateVoice = "AppVoice_Mapper.updateByPrimaryKey";

        public static final String updateVoiceName = "AppVoice_Mapper.updateVoiceNameByAppid";

        public static final String getAppvoiceURLByAPPid = "AppVoice_Mapper.getAppvoiceURLByAPPid";

        public static final String countVoiceByAppid = "AppVoice_Mapper.countVoiceByAppid";

        public static final String updateVoiceByAppid = "AppVoice_Mapper.updateVoiceByAppid";


    }
    /** 隐私号铃声管理相关 **/

    public static final class SecretVoice_Mapper{
        /** 根据条件查询铃声详情记录 **/
        public static final String voiceListBySid = "SecretVoice_Mapper.voiceListBySid";

        public static final String findAppNameByID = "SecretVoice_Mapper.findAppNameByID";

        public static final String findAppNameByAPPid = "SecretVoice_Mapper.findAppNameByAPPid";

        public static final String findVoiceListByAppid = "SecretVoice_Mapper.findVoiceListByAppid";

        public static final String saveVoice = "SecretVoice_Mapper.insertSelective";

        public static final String delVoice = "SecretVoice_Mapper.deleteByPrimaryKey";

        public static final String updateVoice = "SecretVoice_Mapper.updateByPrimaryKey";

        public static final String updateVoiceName = "SecretVoice_Mapper.updateVoiceNameByAppid";

        public static final String getAppvoiceURLByAPPid = "SecretVoice_Mapper.getAppvoiceURLByAPPid";

        public static final String countVoiceByAppid = "SecretVoice_Mapper.countVoiceByAppid";

        public static final String updateVoiceByAppid = "SecretVoice_Mapper.updateVoiceByAppid";


    }


    /** 号码管理 **/
    public static final class AppNumber_Mapper{
        /** 添加号码 **/
        public static final String insertAppNumbers = "AppNumber_Mapper.insertAppNumbers";
        /** 删除号码 **/
        public static final String deleteAppNumbers = "AppNumber_Mapper.deleteAppNumbers";
        /** 根据AppId清空审核通过的号码 **/
        public static final String clearAppNumberByAppId = "AppNumber_Mapper.clearAppNumberByAppId";
        /** 根据id修改号码 **/
        public static final String countNumByNumber = "AppNumber_Mapper.countNumByNumber";
        /** 查询号码 **/
        public static final String findAppNumberList = "AppNumber_Mapper.findAppNumberList";
        /** 根据APPID查询所有号码 **/
        public static final String pageNumber = "AppNumber_Mapper.pageNumber";
        /** 根据number查询号码个数 **/
        public static final String updateAppNumberById = "AppNumber_Mapper.updateAppNumberById";
        /** 单个增加 **/
        public static final String insertAppNumber = "AppNumber_Mapper.insertAppNumber";

        public static final String findNumByAppidAndNumber = "AppNumber_Mapper.findNumByAppidAndNumber";
        public static final String deleteAppNumber = "AppNumber_Mapper.deleteAppNumber";
        /** 根据id查询号码 **/
        public static final String getNumberInfo = "AppNumber_Mapper.getNumberInfo";
        /** 重新提交号码 **/
        public static final String reCommitAppNumber = "AppNumber_Mapper.reCommitAppNumber";
        /** 号码导出 **/
        public static final String downloadAppNumReport = "AppNumber_Mapper.downloadAppNumReport";
    }

    public static final class AppNumberRest_Mapper{
        /** 添加rest号码 **/
        public static final String insertAppNumberRest = "AppNumberRest_Mapper.insertAppNumberRest";
        /** 删除rest号码 **/
        public static final String deleteNumberRestByNumberIds = "AppNumberRest_Mapper.deleteNumberRestByNumberIds";
        /** 根据AppId清空rest号码 **/
        public static final String clearAppNumberRestByAppId = "AppNumberRest_Mapper.clearAppNumberRestByAppId";
        /** 根据APPID及号码码查询rest号码 **/
        public static final String selectAppNumberRestByNumbers = "AppNumberRest_Mapper.selectAppNumberRestByNumbers";
        /** 根据APPID查询审核通过号码 **/
        public static final String selectAppNumberRest = "AppNumberRest_Mapper.selectAppNumberRest";
        /** 单个删除号码 **/
        public static final String deleteAppNumberRest = "AppNumberRest_Mapper.deleteAppNumberRest";
    }


    /** 隐私号号码管理记录相关sql */
    public static final class MaskNum_Mapper {
        /** 分页查询号码记录 */
        public static final String pageMaskNum = "MaskNum_Mapper.pageMaskNum";
        /**导出查询号码记录 */
        public static final String getpageMaskNumList = "MaskNum_Mapper.getpageMaskNumList";

    }
    /** 查询城市信息 */
    public static final class CityCode_Mapper {
        /** 查询所有的城市 */
        public static final String findcityALL = "CityCode_Mapper.findcityALL";
    }

    /** 城市区号信息 **/
    public static final class CityAreaCode_Mapper{
        /** 获取城市区号信息列表 **/
        public static final String selectCityAreaCodeList = "CityAreaCode_Mapper.selectCityAreaCodeList";

        /** 根据区号获取城市区号信息列表 **/
        public static final String selectCityAreaCodeByAreaCode = "CityAreaCode_Mapper.selectCityAreaCodeByAreaCode";
    }

    /** 消息管理相关 */
    public static final class SysMessage_Mapper {

        /** 分页查询消息 **/
        public static final String pageMsg = "SysMessage_Mapper.pageMsg";

        /** 插入一条消息 **/
        public static final String insertSelective = "SysMessage_Mapper.insertSelective";

        public static final String countHadRead = "SysMessage_Mapper.countHadRead";

        public static final String countUnRead = "SysMessage_Mapper.countUnRead";

        public static final String updateByPrimaryKeySelective = "SysMessage_Mapper.updateByid";

        public static final String deleteStatusBylink = "SysMessage_Mapper.deleteStatusBylink";

        public static final String readUpStatus = "SysMessage_Mapper.readUpStatus";

        public static final String selectByPrimaryKey = "SysMessage_Mapper.selectByPrimaryKey";

    }


    /** sip(中继)基本信息相关 */
    public static final class SipBasic_Mapper {
        public static final String selectSipBasicByRelayNum = "SipBasic_Mapper.selectSipBasicByRelayNum";
    }

    /** sip子应用(中继)信息相关 */
    public static final class SipRelayInfo_Mapper {
        public static final String selectSipRateByFeeid = "SipRelayInfo_Mapper.selectSipRateByFeeid";
        public static final String selectSipRelayUnionRateBySubid = "SipRelayInfo_Mapper.selectSipRelayUnionRateBySubid";
        public static final String selectSipRelayInfoListBySid = "SipRelayInfo_Mapper.selectSipRelayInfoListBySid";
        public static final String selectSipRelayInfoByObj = "SipRelayInfo_Mapper.selectSipRelayInfoByObj";
    }

    /** sip子应用(中继)号码池相关 */
    public static final class SipRelayNumPool_Mapper {
        public static final String selectSipRelayNumPoolPage = "SipRelayNumPool_Mapper.selectSipRelayNumPoolPage";
    }

    /** sip(中继)应用全局号码池相关 */
    public static final class SipAppNumPool_Mapper {
        public static final String selectSipAppNumPoolPage = "SipAppNumPool_Mapper.selectSipAppNumPoolPage";
    }

    /** sip话务统计信息 **/
    public static final class SipDayRecord_Mapper {
        public static final String selectSipDayRecordPage = "SipDayRecord_Mapper.selectSipDayRecordPage";
        public static final String selectSipDayRecordList = "SipDayRecord_Mapper.selectSipDayRecordList";
        /** sip消费报表（日报） **/
        public static final String selectSipDayReportPage = "SipDayRecord_Mapper.selectSipDayReportPage";
        /** sip消费报表（月报） **/
        public static final String selectSipMonthReportPage = "SipDayRecord_Mapper.selectSipMonthReportPage";


        /** 获取SIP本月消费总和 **/
        public static final String selectSipCurrentMonthSumFee = "SipDayRecord_Mapper.selectSipCurrentMonthSumFee";
        /** 获取SIP昨日消费总和 **/
        public static final String selectSipYesterdaySumFee = "SipDayRecord_Mapper.selectSipYesterdaySumFee";


        /** 获取本月消费总和 **/
        public static final String selectCurrentMonthSumFee = "SipDayRecord_Mapper.selectCurrentMonthSumFee";
        /** 获取昨日消费总和 **/
        public static final String selectYesterdaySumFee = "SipDayRecord_Mapper.selectYesterdaySumFee";
    }


    /** 呼叫中心展示 */
    public static final class CcInfo_Mapper {
        /** 查询所有的记录 **/
        public static final String pageCallList = "CcInfo_Mapper.pageCallList";
        /** 查询所有的呼叫中心 **/
        public static final String selectAll = "CcInfo_Mapper.selectAll";

        public static final String selectAll1 = "CcInfo_Mapper.selectAll1";
        /** 是否缺省 **/
        public static final String checkDefaultCc = "CcInfo_Mapper.checkDefaultCc";
        public static final String getCcInfoByDefault = "CcInfo_Mapper.getCcInfoByDefault";
        /** 修改缺省 **/
        public static final String updateDefault = "CcInfo_Mapper.updateDefault";
        public static final String updateDefault2 = "CcInfo_Mapper.updateDefault2";

        public static final String setDefault = "CcInfo_Mapper.setDefault";

        /** 选择所有的呼叫中心包括已禁用的 */
        public static final String selectAllWithDelete = "CcInfo_Mapper.selectAllWithDelete";
    }

    /** 智能云调度日统计   */
    public static final class StatCcDayRecord_Mapper {
        /** 查询记录 **/
        public static final String procMonth = "StatCcDayRecord_Mapper.procMonth";
        /** 导出月结账单 **/
        public static final String downloadProcMonth = "StatCcDayRecord_Mapper.downloadProcMonth";
        /** 按照日账单消费记录显示**/
        public static final String getMonthRecordByDay = "StatCcDayRecord_Mapper.getMonthRecordByDay";
        /** 按照呼叫中心统计消费情况**/
        public static final String getMonthRecordByCallCenter = "StatCcDayRecord_Mapper.getMonthRecordByCallCenter";
        /** 按照日账单   每个月的消费统计**/
        public static final String getMonthRecordTotal = "StatCcDayRecord_Mapper.getMonthRecordTotal";


        /** 话务管理 **/
        public static final String selectCcDayRecordPage = "StatCcDayRecord_Mapper.selectCcDayRecordPage";
        public static final String selectCcMonthRecordPage = "StatCcDayRecord_Mapper.selectCcMonthRecordPage";
        public static final String selectCcCallInRecordList = "StatCcDayRecord_Mapper.selectCcCallInRecordList";
    }

    /** 智能云调度小时统计   */
    public static final class StatCcHourRecord_Mapper {
        /** 按照小时账单消费记录显示**/
        public static final String getDayRecordByHour = "StatCcHourRecord_Mapper.getDayRecordByHour";
        /** 按照呼叫中心统计消费情况**/
        public static final String getDayRecordByCallCenter = "StatCcHourRecord_Mapper.getDayRecordByCallCenter";
        /** 按照日账单   每天的消费统计**/
        public static final String getDayRecordTotal = "StatCcHourRecord_Mapper.getDayRecordTotal";

        /** 话务管理 **/
        public static final String selectCcHourRecordPage = "StatCcHourRecord_Mapper.selectCcHourRecordPage";

    }

    /** 运营商信息管理 **/
    public static final class TelnoOperator_Mapper {
        public static final String selectTelnoOperatorByObj = "TelnoOperator_Mapper.selectTelnoOperatorByObj";

    }
    /** 智能云调度话务管理 **/
    public static final class SmartCloudTrafficMgr_Mapper {
        public static final String insertDispatch = "CcDispatch_Mapper.insertDispatch";
        public static final String updateDispatch = "CcDispatch_Mapper.updateDispatch";
        public static final String deleteCcDispatchByDispatchId = "CcDispatch_Mapper.deleteCcDispatchByDispatchId";
        public static final String selectByDispatchId = "CcDispatch_Mapper.selectByDispatchId";
        public static final String selectDispatchPage = "CcDispatch_Mapper.selectDispatchPage";
        public static final String selectCcInfoListBySid = "CcInfo_Mapper.selectCcInfoListBySid";
        public static final String insertCcDispatchInfoList = "CcDispatchInfo_Mapper.insertCcDispatchInfoList";
        public static final String selectCcDispatchInfoList = "CcDispatchInfo_Mapper.selectCcDispatchInfoList";
        public static final String deleteCcDispatchInfoByDispatchId = "CcDispatchInfo_Mapper.deleteCcDispatchInfoByDispatchId";
        public static final String selectCcAreaBySid = "CcArea_Mapper.selectCcAreaBySid";
        public static final String countDispatchBySidAndDispatchName = "CcDispatch_Mapper.countDispatchBySidAndDispatchName";
        public static final String countCallCenterBySubId = "CcInfo_Mapper.countCallCenterBySubId";

        public static final String selectCcMinuteList = "StatCcMinuteRecord_Mapper.countCcScanByMin";

    }


    /** 系统变量信息 **/
    public static final class SysAvar_Mapper {
        public static final String selectSysAvarByVar = "SysAvar_Mapper.selectSysAvarByVar";

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


        /** 统计areaid下的话务调度 */
        public static final String getDispatchByAreaId = "CcDispatch_Mapper.getDispatchByAreaId";

    }

    /** 调度区域表 add by 彭春臣 20160809**/
    public static final class CcArea_Mapper {

        /** 查询所有调度区域 */
        public static final String queryCcAreaList = "CcArea_Mapper.queryCcAreaList";

        public static final String selectByAreaId = "CcArea_Mapper.selectByAreaId";

        /** 分页查询 区域列表 */
        public static final String pageCcArea = "CcArea_Mapper.pageCcArea";

        public static final String insertSelective = "CcArea_Mapper.insertSelective";

        public static final String updateByAreaId = "CcArea_Mapper.updateByAreaId";

        public static final String countAreaBySidAndAreaId = "CcArea_Mapper.countAreaBySidAndAreaId";

        public static final String deleteCcArea = "CcArea_Mapper.deleteCcArea";

    }

    /** 语音通知——模板管理相关 add  by lixin  2016/09/22**/
    public static final class TempVoice_Mapper {

        /** 添加语音 */
        public static final String saveTempVoice = "TempVoice_Mapper.insertSelective";

        /** 分页查询语音模板 */
        public static final String pageVoiceList = "TempVoice_Mapper.pageVoiceList";

        public static final String delTemp = "TempVoice_Mapper.updateByPrimaryKeySelective";

        public static final String getTempVoiceByID = "TempVoice_Mapper.getTempVoiceByID";

        public static final String updateTempVoice = "TempVoice_Mapper.updateByPrimaryKeySelective";

        public static final String getTempVoice = "TempVoice_Mapper.getTempVoice";

        public static final String countNameUnique = "TempVoice_Mapper.countNameUnique";

    }

    /** sip phone 相关 add by hzd 2016/10/26 **/
    public static final class SipPhone_Mapper{
        /** 申请记录列表 **/
        public static final String getAllApplyRecord = "ApplyRecord_Mapper.getAllApplyRecord";
        /** 根据id查询申请记录 **/
        public static final String getApplyRecordById = "ApplyRecord_Mapper.getApplyRecordById";
        /** 单个删除申请记录 **/
        public static final String delApplyRecord = "ApplyRecord_Mapper.delApplyRecord";
        /** 应用号码列表 **/
        public static final String getSipPhoneNumList = "SpApplyNum_Mapper.getSipPhoneNumList";
        /** 根据id查询号码 **/
        public static final String getSpApplyNumById = "SpApplyNum_Mapper.getSpApplyNumById";

        public static final String selectByPrimaryKey = "SpApplyNum_Mapper.selectByPrimaryKey";

        public static final String updateSipPhone = "SpApplyNum_Mapper.updateSipPhone";

        public static final String updateFixPhone = "SpApplyNum_Mapper.updateFixPhone";

        public static final String countSpApplyNumByFixId = "SpApplyNum_Mapper.countSpApplyNumByFixId";

        //删除外显号（批量）
        public static final String updateShowNumStatus = "SpApplyNum_Mapper.updateShowNumStatus";

        public static final String updateShowNum = "SpApplyNum_Mapper.updateShowNum";
        /** 导出sip号码 **/
        public static final String downloadSipNumReport = "SpApplyNum_Mapper.getSipPhoneNumList";
        /** 申请号码 **/
        public static final String saveSipPhoneApply = "ApplyRecord_Mapper.saveSipPhoneApply";
        /** 编辑外显号码时往审核表里插入新编辑外显号码 **/
        public static final String saveShowNumToAudio = "ApplyRecord_Mapper.saveShowNumToAudio";
        /** 删除外线号时往tb_sp_reg_task表插入数据 **/
        public static final String saveDelShowNumToTAsk = "SpApplyNum_Mapper.saveDelShowNumToTAsk";

        // 开启/关闭长途，开启/禁用号码
        public static final String updateSipStatus = "SpApplyNum_Mapper.updateSipStatus";
    }


    /** SpDayRecord 相关 add by wy2016/11/12 **/
    public static final class SpDayRecord_Mapper {
        /**
         * 数据统计
         **/
        public static final String pageSpDayRecordList = "SpDayRecord_Mapper.pageSpDayRecordList";
        /**
         * 导出数据统计
         **/
        public static final String downloadSpDayRecordList = "SpDayRecord_Mapper.downloadSpDayRecordList";

        /**
         * 消费统计--日报
         **/
        public static final String pageConsumptionStatistics = "SpDayRecord_Mapper.pageConsumptionStatistics";

        /**
         * 导出消费统计--日报
         **/
        public static final String downloadConsumptionStatisticsList = "SpDayRecord_Mapper.downloadConsumptionStatisticsList";

        /**
         * 消费统计--月报
         **/
        public static final String pageMonthConsumptionStatistics = "SpDayRecord_Mapper.pageMonthConsumptionStatistics";

        /**
         * 导出消费统计--月报
         **/
        public static final String downloadMonthConsumStatisticsList = "SpDayRecord_Mapper.downloadMonthConsumStatisticsList";

        /**
         * 导出消费统计--月报明细
         **/
        public static final String downloadStatSipphoneConsumeRecordList = "SpDayRecord_Mapper.downloadStatSipphoneConsumeRecordList";
    }

    /** ecc 月账单 相关 add by hzd2017/2/14 **/
    public static final class EccMonthBill_Mapper {
        /**
         * 列表
         **/
        public static final String pageMonthEccBill = "EccMonthBill_Mapper.pageMonthEccBill";

        /**
         * 列表导出
         **/
        public static final String downloadEccMonthBill = "EccMonthBill_Mapper.downloadEccMonthBill";
        /**
         * 详单导出
         **/
        public static final String downloadEccMonthBillRecordList = "EccMonthBill_Mapper.downloadEccMonthBillRecordList";

    }



    /** 云总机ECC——-应用列表 **/
    public static final class EccAppInfo_Mapper {
        /** 应用查看 **/
        public static final String findAppEccByAppId = "EccAppInfo_Mapper.findAppEccByAppId";

        public static final String findCityEccByAppId = "EccAppInfo_Mapper.findCityEccByAppId";

        public static final String pageEccNum = "EccAppInfo_Mapper.pageEccNum";

        public static final String downloadpageEccApp = "EccAppInfo_Mapper.downloadpageEccApp";

    }


    /** 云总机ECC——-分机表 **/
    public static final class EccExtention_Mapper {
        /** 修改号码、长途开关状态 **/
        public static final String updateEccstatus = "EccExtention_Mapper.updateEccstatus";
    }

    /** 云总机ECC——-日报表 **/
    public static final class IvrDayRecord_Mapper {
        /** 分页查询日报数据 **/
        public static final String pageIvrDayRecordList = "IvrDayRecord_Mapper.pageIvrDayRecordList";

        /** 导出日报数据 **/
        public static final String downloadIvrDayRecordList = "IvrDayRecord_Mapper.downloadIvrDayRecordList";
    }

    /** tb_cb_task**/
    public static final class CbTask_Mapper {
        /** 新增 **/
        public static final String insertSelective = "CbTask_Mapper.insertSelective";
    }

    /** AXB号码管理记录相关sql */
    public static final class AxbNum_Mapper {
        /** 分页查询号码记录 */
        public static final String pageAxbNum = "AxbNum_Mapper.pageAxbNum";
        /**导出查询号码记录 */
        public static final String getpageAxbNumList = "AxbNum_Mapper.getpageAxbNumList";

    }

    /** 语音验证码——模板管理相关 **/
    public static final class VoiceverifyTemp_Mapper {

        /** 添加语音 */
        public static final String insertSelective = "VoiceverifyTemp_Mapper.insertSelective";

        /** 分页查询语音验证码模板 */
        public static final String pageVoiceverifyList = "VoiceverifyTemp_Mapper.pageVoiceverifyList";

        public static final String countNameUnique = "VoiceverifyTemp_Mapper.countNameUnique";

        public static final String updateByPrimaryKeySelective = "VoiceverifyTemp_Mapper.updateByPrimaryKeySelective";

        public static final String getVoiceverifyTempByID = "VoiceverifyTemp_Mapper.getVoiceverifyTempByID";


    }

    /***用户号码池（语音验证码）*/
    public static final class VoiceverifyNum_Mapper {

        public static final String pageVoiceverifyNumList = "VoiceverifyNum_Mapper.pageVoiceverifyNumList";
        /**导出查询号码记录 */
        public static final String getpageVoiceverifyNumList = "VoiceverifyNum_Mapper.getpageVoiceverifyNumList";

        public static final String pageVoiceverifyList = "VoiceverifyNum_Mapper.pageVoiceverifyList";

        public static final String findListCountByMap = "VoiceverifyNum_Mapper.findListCountByMap";

        public static final String findListByMap = "VoiceverifyNum_Mapper.findListByMap";
    }

    public static  final  class VoiceverifyRate_Mapper{
        /** 查询资费信息*/
        public static final String findVoiceVerifyRateByFeeid = "VoiceVerifyRate_Mapper.findVoiceVerifyRateByFeeid";
    }
}
