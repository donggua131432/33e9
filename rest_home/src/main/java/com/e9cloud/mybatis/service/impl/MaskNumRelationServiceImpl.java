package com.e9cloud.mybatis.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.CityAreaCode;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.domain.MaskNumRelation;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.MaskNumRelationService;

@Service
public class MaskNumRelationServiceImpl extends BaseServiceImpl implements MaskNumRelationService {

	@Override
	public List<MaskNumRelation> select(MaskNumRelation maskNumRelation) {
		return this.findObjectList(Mapper.Mask_Mapper.select, maskNumRelation);
	}

	@Override
	public void insert(MaskNumRelation maskNumRelation) {
		this.save(Mapper.Mask_Mapper.insert, maskNumRelation);
	}

	@Override
	public void update(MaskNumRelation maskNumRelation) {
		this.update(Mapper.Mask_Mapper.update, maskNumRelation);

	}

	@Override
	public void delete(MaskNumRelation maskNumRelation) {
		this.delete(Mapper.Mask_Mapper.delete, maskNumRelation);

	}

	@Override
	public List<MaskNum> getMaskNumListByStatus(String status) {
		return this.findObjectList(Mapper.Mask_Mapper.selectMaskNumberByStatus, status);
	}

	@Override
	public CityAreaCode selectByPrimiry(String id) {
		return this.findObjectByPara(Mapper.Mask_Mapper.selectCityAreaCode, id);
	}
}