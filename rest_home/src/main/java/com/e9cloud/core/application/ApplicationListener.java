package com.e9cloud.core.application;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.e9cloud.cache.CacheManager;
import com.e9cloud.rest.cb.AppIdTreadManager;
import com.e9cloud.rest.cb.StatisMsgManager;
import com.e9cloud.rest.cb.UDPServer;
import com.e9cloud.rest.mask.MakeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.debug("contextInitialized start.");
		//new UDPServer();
		CacheManager.init();
		InitApp.getInstance().initToApp();
		AppIdTreadManager.getInstance().init();
		MakeService.getInstance().sysMysql();
		StatisMsgManager.getInstance().init();
		MakeService.getInstance().syncVoiceNotify();

	}

}
