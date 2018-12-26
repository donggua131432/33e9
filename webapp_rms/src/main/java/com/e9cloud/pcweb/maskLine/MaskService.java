package com.e9cloud.pcweb.maskLine;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.e9cloud.core.application.AppConfig;
import com.e9cloud.mybatis.domain.MaskNum;
import com.e9cloud.mybatis.service.MaskLineService;
import com.e9cloud.redis.base.RedisOperator;

@Service
public class MaskService {

	private List<MaskNum> maskNumq = new LinkedList<>();
	@Autowired
	private MaskLineService maskLineService;
	@Autowired
	private RedisOperator redisOperator;
	@Autowired
	private AppConfig appConfig;

	private static final Logger logger = LoggerFactory.getLogger(MaskService.class);

	/**
	 * 定时修改隐私号码状态
	 */
	@PostConstruct
	public void fixUpdateNumState() {
		Timer timer = new Timer("Update NumState", false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// 根据状态查找隐私号列表
				logger.info("开始定时扫描锁定隐私号记录");
				maskNumq.clear();
				List<MaskNum> maskNums = maskLineService.getMaskNumListByStatus("02");
				maskNumq.addAll(maskNums);
				logger.info("扫描锁定隐私号记录数" + maskNumq.size());
			}
		};
		timer.scheduleAtFixedRate(task, 60 * 1000, Integer.parseInt(appConfig.getIntervalSecond()) * 1000);

		List<MaskNum> temp = new ArrayList<>();
		Runnable runn = new Runnable() {
			public void run() {
				while (true) {
					try {
						int size = maskNumq.size();
						if (size > 0) {
							temp.clear();
							for (int i = 0; i < size; i++) {
								MaskNum num = maskNumq.get(i);
								String key = new StringBuffer("mask*").append(num.getNumber()).append("*").toString();
								if (redisOperator.existKeys(key) == false) {
									num.setAppid("");
									num.setStatus("03");
									maskLineService.updateMaskNumber(num);
									temp.add(num);
								}
							}
							if (temp.isEmpty() == false) {
								maskNumq.removeAll(temp);
							}
						}
						Thread.sleep(5000);
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}
			}
		};
		new Thread(runn, "Update Thread").start();

	}
}
