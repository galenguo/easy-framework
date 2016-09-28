package com.efun.core.asyn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * EventHandler
 *
 * @author Galen
 * @since 2016/9/16
 */
public abstract class EventHandler<T extends Event> {

    protected Logger logger = LogManager.getLogger(this.getClass());

    protected abstract void onEvent(T event);
}
