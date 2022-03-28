package com.lewis.event.eventcaster.consume;

import com.lewis.event.eventcaster.common.DomainEventContainer;
import com.lewis.event.eventcaster.constant.TopicConstant;
import com.lewis.event.eventcaster.domainevent.DomainEvent;
import com.lewis.event.eventcaster.publisher.DomainEventPublisher;
import com.lewis.event.eventcaster.util.JsonUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/25/2022 14:14
 * @Description:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageConsumer {

    private final DomainEventPublisher domainEventPublisher;

    @PostConstruct
    public void init() {
        try {
            // 实例化消费者
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");
            // 设置NameServer的地址
            consumer.setNamesrvAddr("localhost:9876");
            // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
            consumer.subscribe(TopicConstant.ORDER_COMPLETED_TOPIC, "*");
            // 注册回调实现类来处理从broker拉取回来的消息
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    log.info("{} Receive New Messages,: {}", Thread.currentThread().getName(), msgs);
                    for (MessageExt msg : msgs) {
                        String messageBody = new String(msg.getBody(), StandardCharsets.UTF_8);
                        log.info("{} parse message 1 is : {}", Thread.currentThread().getName(), messageBody);
                        try {
                            DomainEventContainer domainEventContainer = JsonUtil.fromJson(messageBody, DomainEventContainer.class);
                            log.info("{} parse message 2 is : {}", Thread.currentThread().getName(), domainEventContainer);

                            DomainEvent domainEvent = compatibleConvert(domainEventContainer);
                            log.info("{} parse message 3 is : {}", Thread.currentThread().getName(), domainEvent);

                            domainEventPublisher.onEvent(domainEvent);
                        } catch (Exception ex) {
                            log.error("parseObject failed", ex);
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    // 标记该消息已经被成功消费
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 启动消费者实例
            consumer.start();
        } catch (MQClientException e) {
            log.error("MessageConsumer start failed! ", e);
            e.printStackTrace();
        }
        log.info("MessageConsumer start success.");
    }


    private static DomainEvent compatibleConvert(DomainEventContainer eventContainer) throws IOException {
        try {
            Class<?> eventClass = Class.forName(eventContainer.getDomainEventClassName());
            return (DomainEvent) JsonUtil.fromJson(eventContainer.getEventData(), eventClass);
            //   return (DomainEvent) CommonJsonUtils.fromJson(eventContainer.getEventData(), eventClass);
        } catch (Exception e) {
            log.info("convert message to event failed.", e);
            //  Class<? extends DomainEvent> eventClass = DomainEventHolder.get(eventContainer.getEventName());
            //  return CommonJsonUtils.fromJson(eventContainer.getEventData(), eventClass);
            return null;
        }
    }
}
