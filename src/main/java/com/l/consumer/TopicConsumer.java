package com.l.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 主题模式
 *
 * @author luliangyu
 * @date 2021-06-18 10:24
 */
@Slf4j
@Component
public class TopicConsumer {
    private final RabbitTemplate rabbitTemplate;

    public TopicConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "topic_exchange", type = "topic"),
            key = {"get.*"}
    ))
    public void topicGet(String message) {
        log.info("TopicConsumer.topicGet = {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "topic_exchange", type = "topic"),
            key = {"post.*"}
    ))
    public void topicPost(String message) {
        log.info("TopicConsumer.topicPost = {}", message);

    }

    public void send(String key, String message) {
        rabbitTemplate.convertAndSend("topic_exchange", key, message);
    }
}
