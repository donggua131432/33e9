package com.e9cloud.mybatis.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.e9cloud.mybatis.base.BaseServiceImpl;
import com.e9cloud.mybatis.domain.AppointLink;
import com.e9cloud.mybatis.mapper.Mapper;
import com.e9cloud.mybatis.service.AppointLinkService;

@Service
public class AppointLinkServiceImpl extends BaseServiceImpl implements AppointLinkService {

	@Override
	public List<AppointLink> getAll() {
		return this.findList(Mapper.Telno_Mapper.findAllAppointLink);
	}
}