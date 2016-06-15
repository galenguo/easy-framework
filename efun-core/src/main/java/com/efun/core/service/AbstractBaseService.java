package com.efun.core.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AbstractBaseService
 * 基础抽象类
 *
 * @author Galen
 * @since 2016/6/1
 */
public class AbstractBaseService implements BaseService {

    protected final Logger logger = LogManager.getLogger(this.getClass());

}
