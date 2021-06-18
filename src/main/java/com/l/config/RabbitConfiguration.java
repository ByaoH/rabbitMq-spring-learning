package com.l.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitConfig
 *
 * @author luliangyu
 * @date 2021-06-18 11:10
 */
@Configuration
public class RabbitConfiguration {


    /**
     * 交换机名
     */
    public static final String TOPIC_EXCHANGE = "topic_config_exchange";
    /**
     * 队列名
     */
    public static final String TOPIC_QUEUE_NAME_1 = "topic_config_queue_1";
    public static final String TOPIC_QUEUE_NAME_2 = "topic_config_queue_2";
    /**
     * 路由key
     */
    public static final String TOPIC_KEY_GET = "get.*";
    public static final String TOPIC_KEY_POST = "post.*";

    @Bean

    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE_NAME_1);
    }


    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE_NAME_2);
    }

    @Bean
    public Binding bindingTopic1() {
        return BindingBuilder.bind(topicQueue1())
                .to(topicExchange())
                .with(TOPIC_KEY_GET);
    }

    @Bean
    public Binding bindingTopic2() {
        return BindingBuilder.bind(topicQueue2())
                .to(topicExchange())
                .with(TOPIC_KEY_POST);
    }
}
