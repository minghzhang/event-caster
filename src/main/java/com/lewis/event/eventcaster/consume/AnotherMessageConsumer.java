package com.lewis.event.eventcaster.consume;

import com.lewis.event.eventcaster.common.DomainEventContainer;
import com.lewis.event.eventcaster.constant.TopicConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/25/2022 15:37
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = TopicConstant.ORDER_COMPLETED_TOPIC, consumerGroup = "group-01",nameServer = "localhost:9876")
public class AnotherMessageConsumer implements RocketMQListener<DomainEventContainer> {

    @Override
    public void onMessage(DomainEventContainer message) {
        log.info("receive message: {}", message);
    }
}
