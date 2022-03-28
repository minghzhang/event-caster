package com.lewis.event.eventcaster.publisher;

import com.alibaba.fastjson.JSON;
import com.google.common.eventbus.EventBus;
import com.lewis.event.eventcaster.common.DomainEventContainer;
import com.lewis.event.eventcaster.domainevent.BaseDomainEvent;
import com.lewis.event.eventcaster.domainevent.DomainEvent;
import com.lewis.event.eventcaster.domaineventhandler.DomainEventHandler;
import com.lewis.event.eventcaster.producer.SimpleMQProducer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:38
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventPublisher {

    private EventBus eventBus;

    @Resource
    private List<DomainEventHandler> domainEventHandlers;

    private final SimpleMQProducer simpleMQProducer;

    @PostConstruct
    public void init() {
        eventBus = new EventBus();

        for (DomainEventHandler domainEventHandler : domainEventHandlers) {
            eventBus.register(domainEventHandler);
        }
    }

    public void publishDomainEvent(BaseDomainEvent event) {
        DomainEventContainer domainEventContainer = createDomainEventContainer(event);
        simpleMQProducer.sendMQ(domainEventContainer);
    }

    private DomainEventContainer createDomainEventContainer(DomainEvent event) {
        DomainEventContainer domainEventContainer = new DomainEventContainer();
        domainEventContainer.setDomainEventClassName(event.getClass().getName());
        domainEventContainer.setEventName(event.getEventName());
        domainEventContainer.setEventTime(System.currentTimeMillis());
        domainEventContainer.setEventData(JSON.toJSONString(event));
        domainEventContainer.setTraceId(event.getTraceId());
        domainEventContainer.setTraceId(event.getTaskId());
        return domainEventContainer;
    }

    public void onEvent(DomainEvent baseDomainEvent) {
        eventBus.post(baseDomainEvent);
        log.info("publish domain event,name: {}, body: {}", baseDomainEvent.getEventName(), baseDomainEvent);
    }
}
