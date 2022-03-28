package com.lewis.event.eventcaster.domaineventhandler;

import com.google.common.eventbus.Subscribe;
import com.lewis.event.eventcaster.domainevent.OrderCompleted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:50
 * @Description:
 */
@Slf4j
@Component
public class OrderCompletedHandler implements DomainEventHandler {

    @Subscribe
    public void handleOrderCompleted(OrderCompleted orderCompleted) {
        log.info("received orderCompleted : {}", orderCompleted);
    }

    @Subscribe
    public void handleOrderCompleted1(OrderCompleted orderCompleted) {
        log.info("received orderCompleted clear cache: {}", orderCompleted);
    }
}
