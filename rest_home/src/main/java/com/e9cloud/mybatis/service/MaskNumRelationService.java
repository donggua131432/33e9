package com.e9cloud.mybatis.service;

import java.util.Date;
import java.util.List;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumRelation;
import com.e9cloud.mybatis.domain.TelnoInfo;
import com.e9cloud.rest.obt.MaskNumber;

/**
 *
 */
public interface MaskNumRelationService extends IBaseService {

	/**
	 * 根据条件查询映射关系
	 *
	 */
	public List<MaskNumRelation> select(MaskNumRelation maskNumRelation);

	/**
	 * 插入映射关系
	 *
	 */
	public void insert(MaskNumRelation maskNumRelation);

	/**
	 * 修改映射关系
	 *
	 */
	public void update(MaskNumRelation maskNumRelation);

	/**
	 * 删除映射关系
	 *
	 */
	public void delete(MaskNumRelation maskNumRelation);

	List<MaskNum> getMaskNumListByStatus(String status);

	CityAreaCode selectByPrimiry(String id);
}
