package com.lewis.event.eventcaster.producer;

import com.alibaba.fastjson.JSON;
import com.lewis.event.eventcaster.constant.TopicConstant;
import com.lewis.event.eventcaster.util.JsonUtil;
import java.nio.charset.StandardCharsets;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: Louis
 * @ModuleOwner: Louis
 * @Date:03/25/2022 14:20
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SimpleMQProducer {

    DefaultMQProducer producer;

    private final RocketMQTemplate rocketMQTemplate;

    @PostConstruct
    public void init() {
        // 实例化消息生产者Producer
      /*  producer = new DefaultMQProducer("please_rename_unique_group_name");
        // 设置NameServer的地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动Producer实例
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("SimpleMQProducer start failed. ", e);
        }*/
        log.info("SimpleMQProducer start success.");
    }

    public void sendMQ(Object messageBody) {
        try {

            rocketMQTemplate.convertAndSend(TopicConstant.ORDER_COMPLETED_TOPIC,messageBody);

            String message = JsonUtil.toJson(messageBody);
          //  Message msg = new Message(TopicConstant.ORDER_COMPLETED_TOPIC, message.getBytes(StandardCharsets.UTF_8));
            //SendResult sendResult = producer.send(msg);
          //  log.info("sendResult: {}, message: {}", sendResult, messageBody);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("sendMQ failed: message: {}", messageBody, e);
        }
    }

}
