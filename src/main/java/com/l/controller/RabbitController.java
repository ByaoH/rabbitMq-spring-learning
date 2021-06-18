package com.l.controller;

import com.l.config.RabbitConfiguration;
import com.l.consumer.DirectConsumer;
import com.l.consumer.FanoutConsumer;
import com.l.consumer.PollingConsumer;
import com.l.consumer.TopicConsumer;
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
    private final DirectConsumer directConsumer;
    private final TopicConsumer topicConsumer;

    public RabbitController(RabbitTemplate rabbitTemplate, PollingConsumer pollingConsumer, FanoutConsumer fanoutConsumer, DirectConsumer directConsumer, TopicConsumer topicConsumer) {
        this.rabbitTemplate = rabbitTemplate;
        this.pollingConsumer = pollingConsumer;
        this.fanoutConsumer = fanoutConsumer;
        this.directConsumer = directConsumer;
        this.topicConsumer = topicConsumer;
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

    @GetMapping("/sendDirectQueue/{key}/{message}")
    public void sendDirectQueue(@PathVariable String key, @PathVariable String message) {
        directConsumer.send(key, message);
    }

    @GetMapping("/sendTopicQueue/{key}/{message}")
    public void sendTopicQueue(@PathVariable String key, @PathVariable String message) {
        topicConsumer.send(key, message);
    }

    @GetMapping("/configTopicQueue/{key}/{message}")
    public void configTopicQueue(@PathVariable String key, @PathVariable String message) {
        rabbitTemplate.convertSendAndReceive(RabbitConfiguration.TOPIC_EXCHANGE, key, message);
    }
}
