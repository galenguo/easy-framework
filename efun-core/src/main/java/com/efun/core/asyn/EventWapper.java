package com.efun.core.asyn;

/**
 * EventWapper
 * 事件外壳
 *
 * @author Galen
 * @since 2016/9/16
 */
public class EventWapper {

    private Event actualEvent;

    public Event getActualEvent() {
        return actualEvent;
    }

    public void setActualEvent(Event actualEvent) {
        this.actualEvent = actualEvent;
    }
}
