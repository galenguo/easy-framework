package com.efun.core.manager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * AbstractBaseManager
 *
 * @author Galen
 * @since 2017/5/15
 */
public abstract class AbstractBaseManager {
    protected final Logger logger = LogManager.getLogger(getClass().getName());
}
