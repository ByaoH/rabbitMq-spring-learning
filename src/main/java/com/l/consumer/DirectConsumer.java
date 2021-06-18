package com.l.consumer;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 路由模式
 *
 * @author luliangyu
 * @date 2021-06-18 09:51
 */
@Slf4j
@Component
public class DirectConsumer {
    private final RabbitTemplate rabbitTemplate;

    public DirectConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "direct_exchange", type = "direct"),//交换机和类型
            key = {"info", "warn", "error"}
    ))
    public void directQueue(String message) {
        log.info("DirectConsumer.directQueue = {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "direct_exchange", type = "direct"),//交换机和类型
            key = {"warn"}
    ))
    public void directWarn(String message) {
        log.info("DirectConsumer.directWarn = {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue,
            exchange = @Exchange(name = "direct_exchange", type = "direct"),//交换机和类型
            key = {"error"}
    ))
    public void directError(String message) {
        log.info("DirectConsumer.directError = {}", message);
    }

    public void send(String key, String message) {
        rabbitTemplate.convertAndSend("direct_exchange", key, message);
    }

    public void send(RoutingKey key, String message) {
        send(key.getKey(), message);
    }

    @Getter
    public enum RoutingKey {
        INFO("info"),
        WARN("warn"),
        ERROR("error");
        private String key;

        RoutingKey(String key) {
            this.key = key;
        }
    }
}
