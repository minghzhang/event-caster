package com.lewis.event.eventcaster.domainevent;

import java.util.UUID;
import org.slf4j.MDC;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:31
 * @Description:
 */
public abstract class BaseDomainEvent implements DomainEvent {

    private long eventTime = System.currentTimeMillis();
    private String traceId;
    private String taskId;

    @Override
    public long getEventTime() {
        return eventTime;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getTraceId() {
        return traceId;
    }

    @Override
    public void generateTaskId() {
        this.taskId = UUID.randomUUID().toString();
    }

    @Override
    public void generateTraceId() {
        this.traceId = MDC.get("x-traceId");
    }
}
