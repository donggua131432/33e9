package com.e9cloud.rest.vn;

import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.common.ID;
import com.e9cloud.mybatis.service.AccountService;
import com.e9cloud.mybatis.service.VnService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.obt.VnObj;
import com.e9cloud.rest.obt.VnWhite;
import com.e9cloud.util.Tools;
import com.e9cloud.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class VirtualNumService {
	private static final Logger logger = LoggerFactory.getLogger(VirtualNumService.class);
	@Resource
	JedisClusterUtil jedisClusterUtil;

	private static String VN_WHITE = "VN:WHITE_";//白名单
	private static String VN_VNM = "VN:VNM:";//虚拟号映射（客户虚拟号对应业务内容）
	@Autowired
	private VnService vnService;
	/**
	 * 添加虚拟号码关系映射
	 */
	public VnObj addVnNum(VnObj obj) throws Exception {
		Map map = findVn(obj);
		boolean status = (Boolean) map.get("status");
		if(status){
			String vn = getVn(obj.getSid());
			String key = VN_VNM+obj.getSid()+"_"+vn;
			obj.setVn(vn);
			obj.setVnid(ID.randomUUID());
			vnService.saveVn(obj);
			String value = this.genValue(obj);
			jedisClusterUtil.STRINGS.setEx(key,obj.getValidtime(),value);
			addVnWhite(obj.getSid(),obj.getCaller(),obj.getValidtime());
			return obj;
		}else{
			VnObj dbVn = (VnObj)map.get("vn");
			return dbVn;
		}
	}
	/**
	 * 判断VN是否存在
	 */
	public Map<String,Object> findVn(VnObj obj){
		Map map = new HashMap<String,Object>();
		VnObj vn = vnService.findVnByObj(obj);
		if (vn!=null) {
			map.put("status",false);
			map.put("vn",vn);
		}else{
			map.put("status",true);
		}
		return map;
	}

	/**
	 * 生成Vn
	 */
	public String getVn(String sid){
		String vn =  Tools.genRandom();
		VnObj obj = new VnObj();
		obj.setVn(vn);
		obj.setSid(sid);
		VnObj vnObj = vnService.findVnByObj(obj);
		if (vnObj==null) {
				return vn;
		}else{
			return getVn(sid);
		}
	}

	/**
	 * 拼装vn的值
	 */
	public String genValue(VnObj obj){
		return obj.toString();
	}

	/**
	 * 添加虚拟号白名单
	 */
	public boolean addVnWhite(String sid,String num,int validTime) {
		String dateV = String.valueOf(System.currentTimeMillis()+validTime*1000);
		VnWhite obj = new VnWhite();
		obj.setSid(sid);
		obj.setNum(num);
		obj.setNtype(1);
		obj.setCreatetime(new Date());
		obj.setValidtime(validTime);
		vnService.saveWhiteNum(obj);
		return jedisClusterUtil.HASH.hset(VN_WHITE+sid, num, dateV) == 1;
	}

	/**
	 * 添加白名单-标准接口使用
	 */
	public boolean addWhite(String sid,String num,int validTime) {
		String dateV = "null";
		VnWhite obj = new VnWhite();
		if(validTime>0){
			dateV = String.valueOf(System.currentTimeMillis()+validTime*1000);
			obj.setNtype(1);
		}else{
			obj.setNtype(0);
		}
		//存储null对象表示永久有效
		obj.setSid(sid);
		obj.setNum(num);
		obj.setCreatetime(new Date());
		obj.setValidtime(validTime);
		vnService.saveWhiteNum(obj);
		return jedisClusterUtil.HASH.hset(VN_WHITE+sid, num, dateV) == 1;
	}

	/**
	 * 删除白名单-标准接口使用
	 */
	public boolean delWhite(String sid,String num) {
		return jedisClusterUtil.HASH.hdel(VN_WHITE+sid, num) == 1;
	}

}
