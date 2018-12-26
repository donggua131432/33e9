package com.e9cloud.core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 *  Log基类,所有的类默认继承此类,可以直接使用 logger 记录日志,例如 logger.error("error");
 * @author wangq
 */
public class BaseLogger {
	public final static Logger logger = LoggerFactory.getLogger(BaseLogger.class);

}
