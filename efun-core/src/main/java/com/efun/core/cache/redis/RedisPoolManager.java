package com.efun.core.cache.redis;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class RedisPoolManager extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2073786379363537242L;
	protected static final Logger logger = LogManager.getLogger(RedisPoolManager.class);

	public RedisPoolManager() {
		super();
	}

	public void destroy() {
		super.destroy();
		Redis.destroyPool();
		logger.info("destroy redis pool");
	}

	public void init() throws ServletException {
		Redis.initPool();
		logger.info("init redis pool");
	}

}
