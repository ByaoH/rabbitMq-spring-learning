package com.l.controller;

import com.l.consumer.FanoutConsumer;
import com.l.consumer.PollingConsumer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author luliangyu
 * @date 2021-06-17 14:29
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {
    private final RabbitTemplate rabbitTemplate;
    private final PollingConsumer pollingConsumer;
    private final FanoutConsumer fanoutConsumer;

    public RabbitController(RabbitTemplate rabbitTemplate, PollingConsumer pollingConsumer, FanoutConsumer fanoutConsumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.pollingConsumer = pollingConsumer;
        this.fanoutConsumer = fanoutConsumer;
    }

    @GetMapping("/sendQueue")
    public void sendQueue(String msg) {
        rabbitTemplate.convertAndSend("simple_queue", msg);
    }

    @GetMapping("/sendWorkQueue/{message}")
    public void sendWorkQueue(@PathVariable String message) {
        pollingConsumer.send(message);
    }

    @GetMapping("/sendFanoutQueue/{message}")
    public void sendFanoutQueue(@PathVariable String message) {
        fanoutConsumer.send(message);
    }
}
