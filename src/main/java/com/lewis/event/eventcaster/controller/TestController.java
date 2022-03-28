package com.lewis.event.eventcaster.controller;

import com.lewis.event.eventcaster.domainevent.OrderCompleted;
import com.lewis.event.eventcaster.producer.SimpleMQProducer;
import com.lewis.event.eventcaster.publisher.DomainEventPublisher;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.client.producer.MQProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/24/2022 17:52
 * @Description:
 */
@RequestMapping("/domain-event")
@RestController
@RequiredArgsConstructor
public class TestController {

    private final DomainEventPublisher domainEventPublisher;

    private final SimpleMQProducer simpleMQProducer;

    @GetMapping("/sendEventBus")
    public String publishOrderCompleted() {
        OrderCompleted orderCompleted = new OrderCompleted();
        orderCompleted.setOrderId(UUID.randomUUID().toString());
        domainEventPublisher.onEvent(orderCompleted);
        return "ok";
    }

    @GetMapping("/sendMQ")
    public String sendMQ() {
        simpleMQProducer.sendMQ("the order is completed. orderId: " + UUID.randomUUID().toString());
        //OrderCompleted orderCompleted = OrderCompleted.builder().orderId(UUID.randomUUID().toString()).build();
        //domainEventPublisher.onEvent(orderCompleted);
        return "ok";
    }

    @GetMapping("/publishDomainEvent")
    public String publishDomainEvent() {
        OrderCompleted orderCompleted = new OrderCompleted();
        orderCompleted.setOrderId(UUID.randomUUID().toString());
        domainEventPublisher.publishDomainEvent(orderCompleted);
        return "ok";
    }

}
