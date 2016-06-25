package com.efun.core.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * BaseController
 *
 * @author Galen
 * @since 2016/5/30
 */
public abstract class BaseController {
    protected final Logger logger = LogManager.getLogger(this.getClass());
}
