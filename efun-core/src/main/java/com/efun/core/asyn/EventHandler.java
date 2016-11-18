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

    /**
     * 处理成功返回true，失败返回false。
     * @param event
     * @return
     */
    protected abstract boolean onEvent(T event);
}
