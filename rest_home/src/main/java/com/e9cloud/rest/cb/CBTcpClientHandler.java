package com.e9cloud.rest.cb;

import com.e9cloud.core.application.InitApp;
import com.e9cloud.core.application.SpringContextHolder;
import com.e9cloud.core.support.SysPropertits;
import com.e9cloud.mongodb.base.MongoDBDao;
import com.e9cloud.mybatis.domain.AppInfoExtra;
import com.e9cloud.mybatis.service.AppInfoService;
import com.e9cloud.redis.util.JedisClusterUtil;
import com.e9cloud.rest.obt.CallNotifyHttp;
import com.e9cloud.util.*;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class CBTcpClientHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(CBTcpClientHandler.class);

	AppInfoService appInfoService = SpringContextHolder.getBean(AppInfoService.class);
	SysPropertits sysPropertits = SpringContextHolder.getBean(SysPropertits.class);
	JedisClusterUtil jedisClusterUtil = SpringContextHolder.getBean(JedisClusterUtil.class);
	MongoDBDao mongoDBDao = SpringContextHolder.getBean(MongoDBDao.class);
	// 定义客户端没有收到服务端的pong消息的最大次数
	private static int max_un_rec_pong_times = Utils
			.convertToInt(InitApp.getInstance().getValue("cb.max.un.rec.pong.times", "3"), 3);
	private static String version = InitApp.getInstance().getValue("rest.version", "");
	// 客户端连续N次没有收到服务端的pong消息 计数器
	private int unRecPongTimes = 0;

	private static Gson gson = new Gson();


	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		CBMsg msg = new CBMsg();
		msg.getHbMsg(version, sysPropertits.getSnCode());
		ctx.channel().writeAndFlush(msg);
		// ctx.writeAndFlush(firstMessage);
		logger.info("channelActive 首次连接发送心跳报文 {}",msg.toString());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		try {
			if (msg != null && (msg instanceof CBMsg)) {
				CBMsg cbMsg = (CBMsg) msg;
				// 解析报文并相应操作
				// 只处理正常报文头的报文，用以操作响应还是通知，其中心跳报文待定处理
				if (cbMsg.getHeader() != null && cbMsg.getHeader().size() >= 4) {
					// 解析CB报文头
					String version = null;// 版本号
					String tranno = null;// 流水号
					String respCode = null;// 消息类型
					String callSid = null;// 32位标识号
					String snCode = null;//设备码
					for (TVObject tv : cbMsg.getHeader()) {
						if (tv.getType() == 1) {
							version = tv.getValue();
						} else if (tv.getType() == 2) {
							tranno = tv.getValue();
						} else if (tv.getType() == 3) {
							callSid = tv.getValue();
						} else if (tv.getType() == 4) {
							respCode = tv.getValue();
						} else if (tv.getType() == 5) {
							snCode = tv.getValue();
						}
					}

					if (ConstantsEnum.REST_RESPONSE_CODE.getStrValue().equals(respCode)) {
						logger.info("响应报文，不做任何处理");
					} else if (ConstantsEnum.REST_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_MASK_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_SFHH_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_VIRTUAL_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_VOICE_NOTIFY_CODE.getStrValue().equals(respCode)
							||ConstantsEnum.REST_VOICE_VALIDATE_NOTIFY_CODE.getStrValue().equals(respCode)) {
						logger.info("---CB响应通知报文,报文内容{}", msg.toString());
						logger.info("通知报文，进行AS回调通知");
						//添加消息推送任务
						CallNotifyHttp callNotifyHttp = gson.fromJson(cbMsg.getBody(), CallNotifyHttp.class);
						callNotifyHttp.setCreateDateTime(new Date());
						if ("Hangup".equals(callNotifyHttp.getAction())||"VoiceNotify".equals(callNotifyHttp.getAction())) {
							// 挂机后去掉对应redis临时存储
							logger.info("callSid挂机删除callSid:{}",callNotifyHttp.getCallSid());
							jedisClusterUtil.KEYS.del(callNotifyHttp.getCallSid());
							//mongoDBDao.save(callNotifyHttp);
						}
						AppIdTreadManager.getInstance().addParseMsg(callNotifyHttp);
					} else if (ConstantsEnum.REST_SIPPHONE_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_SIPPHONE_DIRECT_NOTIFY_CODE.getStrValue().equals(respCode)
							|| ConstantsEnum.REST_SIPPHONE_CALLBACK_NOTIFY_CODE.getStrValue().equals(respCode)) {
						//sipPhone通知AS的回调地址
						logger.info("CB回调SipPhone通知报文{}", msg.toString());
						String remoteAddress = ctx.channel().remoteAddress().toString();
						remoteAddress = remoteAddress.substring(1, remoteAddress.length());
						//解析CB通知报文体
						CallNotifyHttp callNotifyHttp = gson.fromJson(cbMsg.getBody(), CallNotifyHttp.class);
						callNotifyHttp.setCreateDateTime(new Date());
						//添加消息推送任务
						AppIdTreadManager.getInstance().addParseMsg(callNotifyHttp);
						//判断此回调应用是否是云话机挂机
						if ("Hangup".equals(callNotifyHttp.getAction())) {
							jedisClusterUtil.STRINGS.incr("cbmonitor:res:"+ DateUtil.dateToStr(new Date(),DateUtil.NO_SLASH)+":"+snCode);
							jedisClusterUtil.STRINGS.incr("cbmonitor:res:"+ DateUtil.dateToStr(new Date(),DateUtil.NO_SLASH)+":"+snCode+":"+remoteAddress);
							logger.info("进入sipphone Hangup 回调 callId {}",callNotifyHttp.getCallSid());
							AppInfoExtra appInfoExtra = appInfoService.findAppInfoExtraByAppid(callNotifyHttp.getAppId());
							// 挂机后去掉对应redis临时存储
							logger.info("callSid挂机删除callSid:{}",callNotifyHttp.getCallSid());
							jedisClusterUtil.KEYS.del(callNotifyHttp.getCallSid());
							//mongoDBDao.save(callNotifyHttp);
							if (Utils.notEmpty(appInfoExtra) && Utils.notEmpty(appInfoExtra.getFeeUrl())) {
								//添加消息推送任务
								logger.info("将任务添加至sipphone专用挂机回调 url {} callId {}", appInfoExtra.getFeeUrl(), callNotifyHttp.getCallSid());
								AppIdTreadManager.getInstance().addParseMsg(callNotifyHttp, appInfoExtra.getFeeUrl());
							}
						}
					} else if (ConstantsEnum.REST_HERT_CODE.getStrValue().equals(respCode)) {
						unRecPongTimes = 0;
						logger.info("CB心跳报文已响应:地址{}", ctx.channel().remoteAddress());
					}
				}

			} else {
				logger.error("---CB响应报文为空 " + msg.toString());
			}
		}catch (Exception e){
			logger.info("将数据添加到队列中出现error{}",e );
		}
		// ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {

		super.channelInactive(ctx);
		/** 重连 */
		String remoteAddress = ctx.channel().remoteAddress().toString();
		logger.info("Client close remoteAddress {}",remoteAddress);
		remoteAddress = remoteAddress.substring(1, remoteAddress.length());
		InitApp.getInstance().getSchedu().stopServer(remoteAddress);
		InitApp.getInstance().connServerBySocketAddress(remoteAddress);
		unRecPongTimes = 0;

	}

	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				/* 写超时 */
				if (unRecPongTimes < max_un_rec_pong_times) {
					CBMsg msg = new CBMsg();
					msg.getHbMsg(version, sysPropertits.getSnCode());
					logger.info("CB心跳报文发送至:{} 内容:{}", ctx.channel().remoteAddress(), msg.toString());
					ctx.channel().writeAndFlush(msg);
					unRecPongTimes++;
				} else {
					ctx.channel().close();
				}
			}
		}
	}

}