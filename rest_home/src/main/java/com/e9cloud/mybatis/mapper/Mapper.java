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

		public static final String selectUserByEmail = "User_Mapper.selectUserByEmail";

	}

	/** 用户相关操作 */
	public static final class User_Mapper {

		/** 根据主键查找用户 */
		public static final String selectByPrimaryKey = "User_Mapper.selectByPrimaryKey";

		/** 根据主键查找用户 */
		public static final String selectBySid = "User_Mapper.selectBySid";

		/** 根据邮箱统计用户数量 */
		public static final String countUserByEmail = "User_Mapper.countUserByEmail";

		/** 根据手机号统计用户数量 */
		public static final String countUserByMobile = "User_Mapper.countUserByMobile";

		/* 查询用户基本信息及扩展信息 */
		public static final String selectUserAndExternInfoForUid = "User_Mapper.selectUserAndExternInfoForUid";

		/* 修改用户基本信息 */
		public static final String updateUserInfo = "User_Mapper.updateByPrimaryKeySelective";

		/* 修改用户外部信息 */
		public static final String updateUserExternInfo = "UserExternInfo_Mapper.updateByPrimaryKeySelective";
		/* 根据email查询user信息 */
		public static final String selectuser = "User_Mapper.selectuser";
		/* 重置密码 */
		public static final String updatepassword = "User_Mapper.updatepassword";

		/* 保存用户外部信息 */
		public static final String saveUserExternInfo = "UserExternInfo_Mapper.insertSelective";
		/* 验证用户外部信息是否存在 */
		public static final String isExistUserExtern = "UserExternInfo_Mapper.isExistUserExtern";

	}

	/** 应用相关操作 */
	public static final class AppInfo_Mapper {

		/** 根据appid查找应用 */
		public static final String selectByAppid = "AppInfo_Mapper.selectByAppid";
		/** 查询所有app数据 */
		public static final String findAll = "AppInfo_Mapper.findAll";

	}

	/** 中继显号池相关操作 */
	public static final class RelayNumpool_Mapper {

		/** 系统默认显号池列表*/
		public static final String selectRelayNumpoolsByZero = "RelayNumpool_Mapper.selectRelayNumpoolsByZero";
		/** 自定义中继显号池列表 */
		public static final String selectRelayNumpoolsByOne = "RelayNumpool_Mapper.selectRelayNumpoolsByOne";

	}

	/** 子账号相关操作 */
	public static final class SubApp_Mapper {

		/** 根据subid查找子账号 */
		public static final String findSubAppBySubid = "SubApp_Mapper.selectByPrimaryKey";
		/** 根据map查找子账号 */
		public static final String findSubAppByMap = "SubApp_Mapper.findSubAppByMap";
		/** 修改子账号信息 */
		public static final String updateSubApp = "SubApp_Mapper.updateByPrimaryKey";
		/** 创建子账号 */
		public static final String saveSubApp = "SubApp_Mapper.insertSelective";
		/** 删除子账号 */
		public static final String deleteSubApp = "SubApp_Mapper.deleteByPrimaryKey";
		/** 查询子账号 */
		public static final String findSubAppListByAppid = "SubApp_Mapper.findSubAppListByAppid";

	}

	/** 增值服务相关操作 */
	public static final class ExtraService_Mapper {

		/** 根据map查找增值服务 */
		public static final String findExtraServiceByMap = "ExtraService_Mapper.findExtraServiceByMap";

	}

	/** 物联网卡绑定手机号相关操作 */
	public static final class CardPhone_Mapper {

		/** 查询物联网卡绑定号码基本信息 */
		public static final String findCardPhoneByMap = "CardPhone_Mapper.findCardPhoneByMap";
		/** 绑定物联网卡手机号 */
		public static final String bindCardPhone = "CardPhone_Mapper.bindCardPhone";
		/** 解绑物联网卡手机号 */
		public static final String unbindCardPhone = "CardPhone_Mapper.unbindCardPhone";
		/** 获取物联网卡绑定的号码列表集合 */
		public static final String findCardPhoneListBycard = "CardPhone_Mapper.findCardPhoneListBycard";

	}

	/** 显号相关操作 */
	public static final class AppNumberRest_Mapper {

		/** 根据appid查询所有的显号集合 */
		public static final String findAppNumberRestListByAppid = "AppNumberRest_Mapper.findAppNumberRestListByAppid";

	}

	/** 电话号码操作 */
	public static final class Telno_Mapper {

		/** 查询所有号码表 */
		public static final String findAllTelno = "Telno_Mapper.findAll";
		/** 查询区号省份表 */
		public static final String findAllAreacodePcode = "AreacodePcodeMapper.findAll";
		/** 查询所有指定链路表 */
		public static final String findAllAppointLink = "AppointLinkMapper.findAll";
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

	/** 隐私号映射 */
	public static final class Mask_Mapper {
		/** 插入隐私号映射 */
		public static final String insert = "MaskNumRelationMapper.insertSelective";
		/** 查询隐私号映射列表 */
		public static final String select = "MaskNumRelationMapper.select";
		/** 更新隐私号 */
		public static final String update = "MaskNumRelationMapper.update";
		/** 删除隐私号 */
		public static final String delete = "MaskNumRelationMapper.delete";
		/** 根据状态获取隐私号信息 **/
		public static final String selectMaskNumberByStatus = "MaskNum_Mapper.selectMaskNumberByStatus";
		/** 根据状态获取隐私号信息 **/
		public static final String selectCityAreaCode = "CityAreaCode_Mapper.selectByPrimaryKey";
	}

	/** 黑名单号码 */
	public static final class NumberBlack_Mapper {

		/** 查询所有的黑名单集合 */
		public static final String findList = "NumberBlack_Mapper.findList";

	}

	/** 虚拟号业务 */
	public static final class VN_Mapper {

		/** 虚拟号业务 */
		public static final String existsVn = "VN_Mapper.findVnByObj";

		public static final String saveVn = "VN_Mapper.insertSelective";

		public static final String saveWhiteNum = "VnWhite_Mapper.insertSelective";

		public static final String selectStatVnDayRecordByMap = "StatVnDayRecord_Mapper.selectStatVnDayRecordByMap";


	}
	/** 语音通知业务 */
	public static final class VOICE_Mapper {

		/** 修改 */
		public static final String updateVoice = "VoiceReq_Mapper.updateVoice";

		/** 查询语音通知 */
		public static final String queryVoice = "VoiceReq_Mapper.queryVoice";

		/** 新增 */
		public static final String insertVoice = "VoiceReq_Mapper.insertSelective";
		/** 查询所有未同步的对象 */
		public static final String selectVoiceList = "VoiceReq_Mapper.selectVoiceList";

		/** 查询语音模板 **/
		public static final String selectVoiceTempById = "VoiceTemp_Mapper.selectVoiceTempById";
	}

	/** 业务类型 */
	public static final class Code_Type_Mapper{
		public static final String findAll = "CodeType_Mapper.findAll";
	}

	/** app应用扩展信息 **/
	public static final class AppInfoExtra_Mapper{
		public static final String selectAppInfoExtraByAppId = "AppInfoExtra_Mapper.selectAppInfoExtraByAppId";
	}

	/** sipPhone业务 **/
	public static final class SpApplyNum_Mapper{
		public static final String selectSipPhoneByAppId = "SpApplyNum_Mapper.selectSipPhoneByAppId";
		public static final String updateSipPhoneStat = "SpApplyNum_Mapper.updateSipPhoneStat";
		public static final String selectSipPhoneStat = "SpApplyNum_Mapper.selectSipPhoneStat";
	}

	/** 语音编码业务 **/
	public static final class CbVoiceCode_Mapper{
		public static final String selectVoiceCodeByBusCode = "CbVoiceCode_Mapper.selectVoiceCodeByBusCode";
	}

	/** 虚拟小号Rest接口业务 **/
	public static final class AxbNumber_Mapper{
		public static final String selectNumXByAppId = "AxbNumber_Mapper.selectNumXByAppId";
		public static final String selectNumXByParam = "AxbNumber_Mapper.selectNumXByParam";
		public static final String selectNumXPool = "AxbNumber_Mapper.selectNumXPool";

		public static final String selectNumXByUinJoin = "AxbNumber_Mapper.selectNumXByUinJoin";
	}

	/** 业务类型 **/
	public static final class BusinessType_Mapper{
		public static final String selectBusinessTypeByParam = "BusinessType_Mapper.selectBusinessTypeByParam";
	}


	/** y语音验证码 **/
	public static final class VoiceVerifyTemp_Mapper{
		public static final String selectVoiceVerifyTemp = "VoiceVerifyTemp_Mapper.selectVoiceVerifyTemp";
		public static final String selectDisplayNumPool = "VoiceVerifyTemp_Mapper.selectDisplayNumPool";
	}
}
