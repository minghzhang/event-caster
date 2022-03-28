package com.lewis.event.eventcaster.domainevent;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:32
 * @Description:
 */
public interface DomainEvent {

    long getEventTime();

    String getEventName();

    String getTraceId();

    String getTaskId();

    void generateTaskId();

    void generateTraceId();
}
