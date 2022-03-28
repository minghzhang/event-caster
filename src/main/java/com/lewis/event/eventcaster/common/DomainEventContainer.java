package com.lewis.event.eventcaster.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/25/2022 14:36
 * @Description:
 */
@Data
@NoArgsConstructor
public class DomainEventContainer {

    private String domainEventClassName;
    private String eventName;
    private long eventTime;
    private String eventData;
    private String traceId;
    private String taskId;
}
