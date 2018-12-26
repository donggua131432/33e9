package com.e9cloud.mybatis.service;

import java.util.List;

import com.e9cloud.mybatis.base.IBaseService;
import com.e9cloud.mybatis.domain.AppointLink;

/**
 *
 */
public interface AppointLinkService extends IBaseService {

	/**
	 * 查出所有指定链路
	 *
	 */
	public List<AppointLink> getAll();

}
