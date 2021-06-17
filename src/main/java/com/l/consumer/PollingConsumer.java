package com.l.consumer;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * 工作队列模式
 *
 * @author luliangyu
 * @date 2021-06-17 16:31
 */
@Component
public class PollingConsumer {
    private final RabbitTemplate rabbitTemplate;

    public PollingConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queuesToDeclare = @Queue("work_queue"))
    public void workQueue(String message) {
        System.out.println("WorkConsumer.workQueue = " + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("work_queue"))
    public void workQueue2(String message) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("WorkConsumer.workQueue2 = " + message);
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend("work_queue", message);
    }
}
