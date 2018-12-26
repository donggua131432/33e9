package com.e9cloud.mybatis.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AreacodePcode;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AreacodePcodeService;

@Service
public class AreacodePcodeServiceImpl extends BaseServiceImpl implements AreacodePcodeService {

	@Override
	public List<AreacodePcode> getAll() {
		return this.findList(Mapper.Telno_Mapper.findAllAreacodePcode);
	}
}