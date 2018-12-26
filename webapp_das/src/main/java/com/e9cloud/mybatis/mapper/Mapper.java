package com.e9cloud.mybatis.mapper;


/**
 * 类作用：mapper的工具类，记录mapper文件的sessionId
 *
 * @author wzj 使用说明：
 */
public class Mapper {

    private Mapper() {
    }

    /**
     * 字典表相关操作
     */
    public static final class DicData_Mapper {

        /**
         * 根据字典表类型查询字典信息
         */
        public static final String findDicListByType = "DicData_Mapper.findDicListByType";

        /**
         * 根据父类ID查询字典信息
         */
        public static final String findDicListByPid = "DicData_Mapper.findDicListByPid";

        /**
         * 根据CODE查询
         **/
        public static final String findDicByCode = "DicData_Mapper.findDicByCode";

        /**
         * 根据CODE模糊查询
         **/
        public static final String findDicLikeCode = "DicData_Mapper.findDicLikeCode";
    }

    /**
     * 用户相关
     */
    public static final class User_Mapper {
        /**
         * 用户的基本信息
         */
        public static final String selectByLoginName = "User_Mapper.selectByLoginName";
        /**
         * 添加用户
         */
        public static final String insertSelective = "User_Mapper.insertSelective";
        /**
         * 删除用户
         */
        public static final String deleteByPrimaryKey = "User_Mapper.deleteByPrimaryKey";
        /**
         * 修改用户信息
         */
        public static final String updateByPrimaryKeySelective = "User_Mapper.updateByPrimaryKeySelective";
        /**
         * 查询用户信息
         */
        public static final String selectByLoginList = "User_Mapper.selectByLoginList";
        /**
         * 分页查询用户信息
         */
        public static final String pageUser = "User_Mapper.pageUser";
        /**
         * 获取用户信息
         **/
        public static final String getUserInfo = "User_Mapper.getUserInfo";

        /**
         * 获取用户信息（包括角色）
         **/
        public static final String getUserRoleInfo = "User_Mapper.getUserRoleInfo";
        /** 根据用户ID查找用户角色关联信息 **/
        public static final String selectUserRoleByUserId = "User_Mapper.selectUserRoleByUserId";
        /** 校验用户是否存在 **/
        public static final String checkUserInfo = "User_Mapper.checkUserInfo";
        /** 添加用户角色关联数据 **/
        public static final String insertUserRole = "User_Mapper.insertUserRole";
        /** 删除用户角色关联数据 **/
        public static final String deleteUserRole = "User_Mapper.deleteUserRole";

    }

    /**
     * 菜单管理相关sql add by wzj 20160216
     **/
    public static final class Menu_Mapper {

        /**
         * 分页查询菜单列表
         */
        public static final String page = "Menu_Mapper.page";
        /**
         * 根据父菜单id查询菜单列表
         */
        public static final String selectMenusByParentId = "Menu_Mapper.selectMenusByParentId";
        /**
         * 根据PK删除一个菜单
         */
        public static final String deleteByPrimaryKey = "Menu_Mapper.deleteByPrimaryKey";
        /**
         * 插入一个菜单
         */
        public static final String insertSelective = "Menu_Mapper.insertSelective";
        /**
         * 修改一条记录
         */
        public static final String updateByPrimaryKeySelective = "Menu_Mapper.updateByPrimaryKeySelective";
        /**
         * 根据主键选择一条记录
         */
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

    /**
     * 官网用户相关sql
     */
    public static final class UserAdmin_Mapper {

        /**
         * 添加官网用户
         */
        public static final String insertSelective = "UserAdmin_Mapper.insertSelective";

        public static final String updateByPrimaryKeySelective = "UserAdmin_Mapper.updateByPrimaryKeySelective";

        public static final String selectBySID = "UserAdmin_Mapper.selectBySID";

        public static final String selectByPrimaryKey = "UserAdmin_Mapper.selectByPrimaryKey";

        /**
         * 分页查询
         **/
        public static final String selectBalanceListPage = "UserAdmin_Mapper.selectUserAdminPage";

        public static final String selectUserAdminByObject = "UserAdmin_Mapper.selectUserAdminByObject";

        /**
         * 下载列表
         **/
        public static final String selectBalanceListDownload = "UserAdmin_Mapper.selectUserAdmindownload";

        /**
         * 用户信息联合查询
         **/
        public static final String selectUserAdminUnionCompanyInfo = "UserAdmin_Mapper.selectUserAdminUnionCompanyInfo";

        /**
         * 根据邮箱或者手机号统计用户数量
         */
        public static final String countUserAdminByEmailOrMobile = "UserAdmin_Mapper.countUserAdminByEmailOrMobile";

        /**
         * 到公司名和sid下拉列表
         */
        public static final String findCompanyNameAndSidByPage = "UserAdmin_Mapper.findCompanyNameAndSidByPage";

        /**
         * 到公司名和sid下拉列表
         */
        public static final String findCompanyNameAndSidForSelect2 = "UserAdmin_Mapper.findCompanyNameAndSidForSelect2";

        /**
         * 分页查询开发者列表
         */
        public static final String pageUserAdminList = "UserAdmin_Mapper.pageUserAdminList";

        /**
         * 导出开发者列表信息
         */
        public static final String getPageUserAdminList = "UserAdmin_Mapper.getPageUserAdminList";

        /**
         * 修改用户的状态
         */
        public static final String updateUserAdminStatusByUid = "UserAdmin_Mapper.updateUserAdminStatusByUid";

        /**
         * 查询所有的用户
         **/
        public static final String selectAll = "UserAdmin_Mapper.selectAll";
    }

    /**
     * 月话单详情下载
     **/
    public static final class StafMonthFile_Mapper {
        /**
         * 根据条件查询话单详情记录
         **/
        public static final String findStafMonthFileList = "StafMonthFile_Mapper.findStafMonthFileList";
    }

    /**
     * APP表相关操作
     */
    public static final class App_Mapper {

        /**
         * 查询用户列表集合
         */
        public static final String findAppListCountByMap = "AppInfo_Mapper.findAppListCountByMap";

        /**
         * 查询用户列表数量
         */
        public static final String findAppListByMap = "AppInfo_Mapper.findAppListByMap";

        /**
         * 查询应用列表信息
         */
        public static final String selectAppInfoListByMap = "AppInfo_Mapper.selectAppInfoListByMap";

        /**
         * 查询APP的详细信息
         */
        public static final String findAppInfoByAppId = "AppInfo_Mapper.findAppInfoByAppId";

        /**
         * 查询APP的详细信息根据ID
         */
        public static final String findAppInfoById = "AppInfo_Mapper.selectByPrimaryKey";

        /**
         * 应用信息和子应用信息联合查询
         **/
        public static final String selectAppInfoUnionSubApp = "AppInfo_Mapper.selectAppInfoUnionSubApp";

        /**
         * 查询所有应用信息
         **/
        public static final String selectAllAppInfo = "AppInfo_Mapper.selectAllAppInfo";

        /**
         * 查询所有应用信息
         **/
        public static final String findAllApp = "AppInfo_Mapper.findAllApp";


        public static final String getAllSpInfo = "AppInfo_Mapper.getAllSpInfo";


    }

    /** 大盘总览相关操作 */
    public static final class Overviews_Mapper {

        /** 统计消费用户数*/
        public static final String CountOverviewsUser = "Overviews_Mapper.CountOverviewsUser";
        /** 统计所有业务的总览情况*/
        public static final String selectOverviews = "Overviews_Mapper.selectOverviews";
        /** 统计各业务消费占比*/
        public static final String selectTypePercent = "Overviews_Mapper.selectTypePercent";
        /** 统计消费前10客户情况*/
        public static final String selectOverviewsTop = "Overviews_Mapper.selectOverviewsTop";
        /** 按月统计所有业务的总览情况*/
        public static final String selectOverviewsRev = "Overviews_Mapper.selectOverviewsRev";
        /** 按时间统计所有业务的充值金额总计情况*/
        public static final String selectOverviewsMon = "Overviews_Mapper.selectOverviewsMon";
        /** 注册用户数*/
        public static final String selectOverviewsCon = "Overviews_Mapper.selectOverviewsCon";
        /** 新增用户数*/
        public static final String selectOverviewsConNew = "Overviews_Mapper.selectOverviewsConNew";
        /** 活跃用户数*/
        public static final String selectOverviewsConActive = "Overviews_Mapper.selectOverviewsConActive";
        /** 按月统计消费前10客户情况*/
        public static final String selectOverviewsTopRev = "Overviews_Mapper.selectOverviewsTopRev";
        /** 统计专线语音Rest*/
        public static final String selectRest= "RestStatDayRecord_Mapper.selectRest";
        /** 统计专线语音录音比*/
        public static final String selectRcdBillRate = "RestStatDayRecord_Mapper.selectRcdBillRate";
        /** 统计智能云调度cc*/
        public static final String selectCc = "StatCcDayRecord_Mapper.selectCc";
        /** 统计sip*/
        public static final String selectSip = "StatSipDayRecord_Mapper.selectSip";
        /** 统计sip中继数(日报)*/
        public static final String selectSipNumByDay = "StatSipDayRecord_Mapper.selectSipNumByDay";
        /** 统计sip中继数(月报)*/
        public static final String selectSipNumByMonth = "StatSipDayRecord_Mapper.selectSipNumByMonth";
        /** 统计语音通知voice*/
        public static final String selectVoice = "StatVoiceDayRecord_Mapper.selectVoice";
        /** 统计专号通mask*/
        public static final String selectMask = "StatMaskDayRecord_Mapper.selectMask";
        /** 统计隐私号数*/
        public static final String selectMaskNum = "StatMaskDayRecord_Mapper.selectMaskNum";
        /** 统计专号通录音比A路*/
        public static final String selectRcdBillRateA = "StatMaskDayRecord_Mapper.selectRcdBillRateA";
        /** 统计专号通录音比C路*/
        public static final String selectRcdBillRateC = "StatMaskDayRecord_Mapper.selectRcdBillRateC";
        /** 统计云话机sipp*/
        public static final String selectSipp = "StatSpDayRecord_Mapper.selectSipp";
        /** 统计云总机ecc*/
        public static final String selectEcc = "StatIvrDayRecord_Mapper.selectEcc";
        /** 统计语音验证码voiceverify*/
        public static final String selectVoiceverify = "StatVoiceverifyDayRecord_Mapper.selectVoiceverify";
        /** Rest相关消费统计*/
        public static final String selectRestFeeSum = "Overviews_Mapper.selectRestFeeSum";
        public static final String selectRestAblineSum = "Overviews_Mapper.selectRestAblineSum";
        public static final String selectRestCount = "Overviews_Mapper.selectRestCount";

        /** Mask相关消费统计*/
        public static final String selectMaskFeeSum = "Overviews_Mapper.selectMaskFeeSum";
        public static final String selectMaskAblineSum = "Overviews_Mapper.selectMaskAblineSum";
        public static final String selectMaskNumFee = "Overviews_Mapper.selectMaskNumFee";
        public static final String selectMaskRent = "Overviews_Mapper.selectMaskRent";
        public static final String selectMaskCount = "Overviews_Mapper.selectMaskCount";

        /** Voice相关消费统计*/
        public static final String selectVoiceFeeSum = "Overviews_Mapper.selectVoiceFeeSum";
        public static final String selectVoiceCount = "Overviews_Mapper.selectVoiceCount";

        /** SIP相关消费统计*/
        public static final String selectSipFeeSum = "Overviews_Mapper.selectSipFeeSum";
        public static final String selectSipAblineSum = "Overviews_Mapper.selectSipAblineSum";
        public static final String selectSipCount = "Overviews_Mapper.selectSipCount";


        /** 智能云调度相关消费统计*/
        public static final String selectCcFeeSum = "Overviews_Mapper.selectCcFeeSum";
        public static final String selectCcAblineSum = "Overviews_Mapper.selectCcAblineSum";
        public static final String selectCcNumFee = "Overviews_Mapper.selectCcNumFee";
        public static final String selectCcCount = "Overviews_Mapper.selectCcCount";


        /** 云话机相关消费统计*/
        public static final String selectSpFeeSum = "Overviews_Mapper.selectSpFeeSum";
        public static final String selectSpAblineSum = "Overviews_Mapper.selectSpAblineSum";
        public static final String selectSpCount = "Overviews_Mapper.selectSpCount";


        /** 云总机相关消费统计*/
        public static final String selectEccFeeSum = "Overviews_Mapper.selectEccFeeSum";
        public static final String selectEccAblineSum = "Overviews_Mapper.selectEccAblineSum";
        public static final String selectEccCount = "Overviews_Mapper.selectEccCount";
        public static final String selectEccZjRent = "Overviews_Mapper.selectEccZjRent";
        public static final String selectEccSipRent = "Overviews_Mapper.selectEccSipRent";
        public static final String selectEccMinCost = "Overviews_Mapper.selectEccMinCost";

        /** 语音验证码相关消费统计*/
        public static final String selectVoiceverifyFeeSum = "Overviews_Mapper.selectVoiceverifyFeeSum";
        public static final String selectVoiceverifyCount = "Overviews_Mapper.selectVoiceverifyCount";
        public static final String selectVoiceverifyPjSum = "Overviews_Mapper.selectVoiceverifyPjSum";


    }

    /**
     * sip子应用(中继)信息相关
     */
    public static final class SipRelayInfo_Mapper {

        public static final String selectSipRateByFeeid = "SipRelayInfo_Mapper.selectSipRateByFeeid";

        public static final String selectSipRelayUnionRateBySubid = "SipRelayInfo_Mapper.selectSipRelayUnionRateBySubid";

        public static final String selectSipRelayInfoListBySid = "SipRelayInfo_Mapper.selectSipRelayInfoListBySid";

        public static final String selectSipRelayInfoByObj = "SipRelayInfo_Mapper.selectSipRelayInfoByObj";

        public static final String selectSipRelayInfoBySid = "SipRelayInfo_Mapper.selectSipRelayInfoBySid";

    }

    /**
     * 话务报表
     **/
    public static final class RestDayRecord_Mapper {

        /**
         * 分页获取话务报表列表
         */
        public static final String pageRestDayRecordList = "RestDayRecord_Mapper.pageRestDayRecordList";

        public static final String downloadRestDayRecord = "RestDayRecord_Mapper.downloadRestDayRecord";

        // 按天统计 专线语音 的话务情况
        public static final String countRestScanByDay = "RestDayRecord_Mapper.countRestScanByDay";

    }
    /**
     * 话务报表--语音通知
     **/
    public static final class VoiceRecord_Mapper {
        /** 分页获取话务报表列表 **/
        public static final String pageVoiceRecordList = "StatVoiceDayRecord_Mapper.pageVoiceRecordList";
        /** 导出话务报表 **/
        public static final String downloadVoiceRecord = "StatVoiceDayRecord_Mapper.downloadVoiceRecord";
    }
    /**
     * 话务报表--智能云调度
     **/
    public static final class CcRecord_Mapper {

        public static final String countCcRecordList = "StatCcDayRecord_Mapper.countCcRecordList";

        /** 分页获取话务报表列表 **/
        public static final String pageCcRecordList = "StatCcDayRecord_Mapper.pageCcRecordList";
        /** 导出话务报表 **/
        //public static final String downloadCcRecord = "StatCcDayRecord_Mapper.downloadCcRecord";
    }

    /**
     * 话务报表--云话机
     **/
    public static final class SpRecord_Mapper {
        /** 分页获取话务报表列表 **/
        public static final String pageSpRecordList = "StatSpDayRecord_Mapper.pageSpRecordList";
        /** 导出话务报表 **/
        public static final String downloadSpRecord = "StatSpDayRecord_Mapper.downloadSpRecord";
    }

    /**
     * 话务报表--语音验证码
     **/
    public static final class VoiceverifyRecord_Mapper {
        /** 分页获取话务报表列表 **/
        public static final String pageVoiceverifyRecordList = "StatVoiceverifyDayRecord_Mapper.pageVoiceverifyRecordList";
        /** 导出话务报表 **/
        public static final String downloadVoiceverifyRecord = "StatVoiceverifyDayRecord_Mapper.downloadVoiceverifyRecord";
    }

    /**
     * 话务报表
     **/
    public static final class RestStatMinuteRecord_Mapper {

        // 按天统计 专线语音 的话务情况
        public static final String countRestScanByMin = "RestStatMinuteRecord_Mapper.countRestScanByMin";

    }

    public static final class StatRestResponse_Mapper {

        // 按天统计 专线语音 的话务情况
        public static final String countRestResponseByMin = "StatRestResponse_Mapper.countRestResponseByMin";
    }


    /**
     * 专线语音 -- 小时统计
     **/
    public static final class RestHourRecord_Mapper {
        // 按天统计 专线语音 的话务情况
        public static final String countRestScanByDay = "StatRestHourRecord_Mapper.countRestScanByDay";

        public static final String pageRestHourRecordList = "StatRestHourRecord_Mapper.pageRestHourRecordList";

        public static final String downloadHourTeleReport = "StatRestHourRecord_Mapper.downloadHourTeleReport";
    }

    /**中继线路故障统计 */
    public static final class RelayFault_Mapper{

        public static final String getRelayFaultList = "RelayFault_Mapper.getRelayFaultList";

        public static final String relayFaultList = "RelayFault_Mapper.relayFaultList";

        public static final String getRelayFault = "RelayFault_Mapper.getRelayFault";

    }

    /** 各个业务的标准费用信息 **/
    public static final class Rate_Mapper {

        /** 获取rest费用信息 **/
        public static final String selectRestRateByFeeId = "RestRate_Mapper.selectRestRateByFeeId";

    }

    /** 获取rest统计数据 **/
    public static final class RestStatDayRecord_Mapper {

        /** 获取月统计数据 **/
        public static final String selectMonthRestOverview = "RestStatDayRecord_Mapper.selectMonthRestOverview";
        /** 根据日期范围获取消费概况 **/
        public static final String selectRestDayRangeRecordInfo = "RestStatDayRecord_Mapper.selectRestDayRangeRecordInfo";
        /** 根据日期范围获取客户消费Top10 **/
        public static final String selectRestDayRangeRecordTopTen = "RestStatDayRecord_Mapper.selectRestDayRangeRecordTopTen";

        /** 获取专线语音日报表信息 **/
        public static final String selectRestRecordDayReportList = "RestStatDayRecord_Mapper.selectRestRecordDayReportList";
        /** 获取专线语音月报表信息 **/
        public static final String selectRestRecordMonthReportList = "RestStatDayRecord_Mapper.selectRestRecordMonthReportList";

        /** 获取专线语音日报表应付款总额 **/
        public static final String selectRestRecordDayTotalFee = "RestStatDayRecord_Mapper.selectRestRecordDayTotalFee";
        /** 获取专线语音月报表应付款总额 **/
        public static final String selectRestRecordMonthTotalFee = "RestStatDayRecord_Mapper.selectRestRecordMonthTotalFee";

    }

    /** 呼叫中心 */
    public static final class CallCenter_Mapper {
        /** 查询所有的记录 **/
        public static final String selectAll = "CallCenter_Mapper.selectAll";

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

        /** 根据sid及时间查找呼叫中心 */
        public static final String selectCcInfoBySid = "CcInfo_Mapper.selectCcInfoBySid";

    }


    /** 省份列表 */
    public static final class Province_Mapper {
        /** 查询所有的记录 **/
        public static final String selectAll = "Province_Mapper.selectAll";
        public static final String selectAllCity = "Province_Mapper.selectAllCity";

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


    /** 话单下载相关 **/
    public static final class DownLoadWrapper_Mapper {

        /** 插入 */
        public static final String insertDownSelective = "DownLoadWrapper_Mapper.insertSelective";
        /** 修改 */
        public static final String updateDownSelective = "DownLoadWrapper_Mapper.updateByPrimaryKeySelective";
        /** 查询未删除的话单 */
        public static final String findNotDelList = "DownLoadWrapper_Mapper.findNotDelList";
        /** 获取要下载的列表 */
        public static final String findDownList = "DownLoadWrapper_Mapper.findDownList";
        /** 检查文件名称是否存在 */
        public static final String checkFile = "DownLoadWrapper_Mapper.checkFile";
    }

    /** 智能云调度日统计表 **/
    public class StatCcDayRecord_Mapper {

        /** 按天统计 智能云调度 话务走势 的话务情况 */
        public static final String countCcScanByDay = "StatCcDayRecord_Mapper.countCcScanByDay";

        /** 按天统计(各运营商占比) 智能云调度 话务走势 的话务情况 */
        public static final String countCcScanByOperator = "StatCcDayRecord_Mapper.countCcScanByOperator";
    }

    /** 智能云调度小时统计表 **/
    public class StatCcHourRecord_Mapper {

        /** 按天统计 智能云调度 话务走势 的话务情况 */
        public static final String countCcScanByHour = "StatCcHourRecord_Mapper.countCcScanByHour";
    }

    /** 智能云调度分钟统计表 **/
    public class StatCcMinuteRecord_Mapper {

        /** 按分钟统计 智能云调度 话务走势 的话务情况 */
        public static final String countCcScanByMin = "StatCcMinuteRecord_Mapper.countCcScanByMin";

        /** 按省份统计 地区呼入分布图 */
        public static final String countCcScanByProv = "StatCcMinuteRecord_Mapper.countCcScanByProv";
    }

    /** 专号通日统计表 **/
    public class StatMaskDayRecord_Mapper {

        /** 按天统计 专号通 话务走势 的话务情况 */
        public static final String countMaskScanByDay = "StatMaskDayRecord_Mapper.countMaskScanByDay";
        /** 按时间统计  话务报表 */
        public static final String pageMaskReportList = "StatMaskDayRecord_Mapper.pageMaskReportList";
        /** 按时间导出 专号通话务报表情况 */
        public static final String downloadMaskReport = "StatMaskDayRecord_Mapper.downloadMaskReport";

    }

    /** 专号通小时统计表 **/
    public class StatMaskHourRecord_Mapper {

        /** 按小时统计 专号通 话务走势 的话务情况 */
        public static final String countMaskScanByHour = "StatMaskHourRecord_Mapper.countMaskScanByHour";
    }

    /** 专号通分钟统计表 **/
    public class StatMaskMinuteRecord_Mapper {

        /** 按分钟统计 专号通 话务走势 的话务情况 */
        public static final String countMaskScanByMin = "StatMaskMinuteRecord_Mapper.countMaskScanByMin";
    }

    /** Sip日统计表 **/
    public class StatSipDayRecord_Mapper {

        /** 按天统计 sip 话务走势 的话务情况 */
        public static final String countSipScanByDay = "StatSipDayRecord_Mapper.countSipScanByDay";
        /** 按时间统计  话务报表 */
        public static final String pageSipReportList = "StatSipDayRecord_Mapper.pageSipReportList";
        /** 按时间导出 专号通话务报表情况 */
        public static final String downloadSipReport = "StatSipDayRecord_Mapper.downloadSipReport";
    }

    /** SIP小时统计表 **/
    public class StatSipHourRecord_Mapper {

        /** 按小时统计 sip 话务走势 的话务情况 */
        public static final String countSipScanByHour = "StatSipHourRecord_Mapper.countSipScanByHour";
    }

    /** SIP分钟统计表 **/
    public class StatSipMinuteRecord_Mapper {

        /** 按分钟统计 sip 话务走势 的话务情况 */
        public static final String countSipScanByMin = "StatSipMinuteRecord_Mapper.countSipScanByMin";
    }

    /** 云话机日统计表 **/
    public class StatSpDayRecord_Mapper {

        /** 按天统计 云话机 话务走势 的话务情况 */
        public static final String countSpScanByDay = "StatSpDayRecord_Mapper.countSpScanByDay";
    }

    /** 云话机小时统计表 **/
    public class StatSpHourRecord_Mapper {

        /** 按小时统计 云话机 话务走势 的话务情况 */
        public static final String countSpScanByHour = "StatSpHourRecord_Mapper.countSpScanByHour";
    }

    /** 云话机分钟统计表 **/
    public class StatSpMinuteRecord_Mapper {

        /** 按分钟统计 云话机 话务走势 的话务情况 */
        public static final String countSpScanByMin = "StatSpMinuteRecord_Mapper.countSpScanByMin";
    }

    /** 语音通知日统计表 **/
    public class StatVoiceDayRecord_Mapper {

        /** 按天统计 语音通知 话务走势 的话务情况 */
        public static final String countVoiceScanByDay = "StatVoiceDayRecord_Mapper.countVoiceScanByDay";
    }

    /** 语音通知小时统计表 **/
    public class StatVoiceHourRecord_Mapper {


        /** 按小时统计 语音通知 话务走势 的话务情况 */
        public static final String countVoiceScanByHour = "StatVoiceHourRecord_Mapper.countVoiceScanByHour";
    }

    /** 语音通知分钟统计表 **/
    public class StatVoiceMinuteRecord_Mapper {

        /** 按分钟统计 语音通知 话务走势 的话务情况 */
        public static final String countVoiceScanByMin = "StatVoiceMinuteRecord_Mapper.countVoiceScanByMin";
    }

    /** ecc 云总机 小时统计 **/
    public class StatIvrHourRecord_Mapper {

        /** 小时统计 云总机 话务走势 的话务情况 */
        public static final String countEccScanByHour = "StatIvrHourRecord_Mapper.countEccScanByHour";
    }

    /** ecc 云总机 分钟统计 **/
    public class StatIvrMinuteRecord_Mapper {

        /** 分钟统计 云总机 话务走势 的话务情况 */
        public static final String countEccScanByMin = "StatIvrMinuteRecord_Mapper.countEccScanByMin";
    }

    /** ecc 云总机 天统计 **/
    public class StatIvrDayRecord_Mapper {

        /** 按天统计 云总机 话务走势 的话务情况 */
        public static final String countEccScanByDay = "StatIvrDayRecord_Mapper.countEccScanByDay";
        /** 分页获取云总机话务报表列表 **/
        public static final String pageIvrRecordList = "StatIvrDayRecord_Mapper.pageIvrRecordList";
        /** 导出云总机话务报表 **/
        public static final String downloadIvrRecord = "StatIvrDayRecord_Mapper.downloadIvrRecord";
    }

    /** REST接口分钟统计表 **/
    public class StatRestApiMinuteResp_Mapper {

        /** 按分钟统计  REST接口 的错误码情况 */
        public static final String countRestApiScanByMin = "StatRestApiMinuteResp_Mapper.countRestApiScanByMin";

        /** 统计一天的错误码 根据错误码分组统计 */
        public static final String countRestApiCodeStat = "StatRestApiMinuteResp_Mapper.countRestApiCodeStat";

    }

    /** 系统变量信息 **/
    public static final class SysAvar_Mapper {
        public static final String selectSysAvarByVar = "SysAvar_Mapper.selectSysAvarByVar";

    }

    public class RevenueReport_Mapper {
        public static final String pageDays = "RevenueReport_Mapper.pageDays";

        public static final String pageDaysCount = "RevenueReport_Mapper.pageDaysCount";

        public static final String pageMonth = "RevenueReport_Mapper.pageMonth";

        public static final String pageMonthCount = "RevenueReport_Mapper.pageMonthCount";

        public static final String restMonthDetails = "RevenueReport_Mapper.restMonthDetails";

        public static final String maskMonthDetails = "RevenueReport_Mapper.maskMonthDetails";

        public static final String ccMonthDetails = "RevenueReport_Mapper.ccMonthDetails";

        public static final String sipMonthDetails = "RevenueReport_Mapper.sipMonthDetails";

        public static final String spMonthDetails = "RevenueReport_Mapper.spMonthDetails";

        public static final String eccMonthDetails = "RevenueReport_Mapper.eccMonthDetails";

        public static final String selectRents = "RevenueReport_Mapper.selectRents";

        public static final String monthByFeeid = "RevenueReport_Mapper.monthByFeeid";

        public static final String downloadDays = "RevenueReport_Mapper.downloadDays";

        public static final String downloadMonth = "RevenueReport_Mapper.downloadMonth";

        public static final String voiceVerifyMonthDetails = "RevenueReport_Mapper.voiceVerifyMonthDetails";
    }

    public class StatRelayResDayRecord_Mapper {
        // 按天统计
        public static final String pageRecord = "StatRelayResDayRecord_Mapper.pageRecord";
        // 按天统计
        public static final String countRecord = "StatRelayResDayRecord_Mapper.countRecord";

    }

    public class RelayRes_Mapper {
        public static final String selectAllRelayRes = "RelayRes_Mapper.selectAllRelayRes";
    }

    public class Supplier_Mapper {
        public static final String selectAllSupplier = "Supplier_Mapper.selectAllSupplier";
    }

    public class SipBasic_Mapper {
        public static final String selectAllRelay = "SipBasic_Mapper.selectAllRelay";
    }

    /** 语音验证码日统计表 **/
    public class StatVoiceverifyDayRecord_Mapper {

        /** 按天统计 语音验证码 话务走势 的话务情况 */
        public static final String countVoiceverifyScanByDay = "StatVoiceverifyDayRecord_Mapper.countVoiceverifyScanByDay";
    }

    /** 语音验证码小时统计表 **/
    public class StatVoiceverifyHourRecord_Mapper {


        /** 按小时统计 语音验证码 话务走势 的话务情况 */
        public static final String countVoiceverifyScanByHour = "StatVoiceverifyHourRecord_Mapper.countVoiceverifyScanByHour";
    }

    /** 语音验证码分钟统计表 **/
    public class StatVoiceverifyMinuteRecord_Mapper {

        /** 按分钟统计 语音验证码 话务走势 的话务情况 */
        public static final String countVoiceverifyScanByMin = "StatVoiceverifyMinuteRecord_Mapper.countVoiceverifyScanByMin";
    }
}
