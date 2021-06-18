package com.l.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * FanoutConsumer
 *
 * @author luliangyu
 * @date 2021-06-17 17:28
 */
@Slf4j
@Component
public class FanoutConsumer {
    private final RabbitTemplate rabbitTemplate;

    public FanoutConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(), // 临时队列
                    exchange = @Exchange(name = "order_exchange", type = "fanout") // 交换机与类型
            )
    })
    public void fanoutQueue1(String message) {
        System.out.println("FanoutConsumer.fanoutQueue1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(), // 临时队列
                    exchange = @Exchange(name = "order_exchange", type = "fanout") // 交换机与类型
            )
    })
    public void fanoutQueue2(String message) {
        System.out.println("FanoutConsumer.fanoutQueue2 = " + message);
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend("order_exchange", "", message);
    }
}
