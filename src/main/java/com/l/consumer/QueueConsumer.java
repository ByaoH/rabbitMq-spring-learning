package com.l.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 简单队列模式
 *
 * @author luliangyu
 * @value 队列名
 * @durable 持久化消息队列 RabbitMQ 重启后，队列仍存在，默认 true
 * @exclusive 表示该消息队列是否只在当前 Connection 生效，默认是 false
 * @autoDelete 表示消息队列没有在使用时将被自动删除，默认是 false
 * @date 2021-06-17 14:25
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "simple_queue", durable = "true", exclusive = "false", autoDelete = "false"))
public class QueueConsumer {
    @RabbitHandler
    public void test(String message) {
        System.out.println("message = " + message);
    }
}
