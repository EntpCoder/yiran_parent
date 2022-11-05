package com.yiran.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yang Song
 * @date 2022/11/5 9:29
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    public TopicExchange topic(){
        return new TopicExchange("yiran.order.topic");
    }
    private static class ReceiverConfig{
        @Bean
        public Queue orderPayStatusQueue() {
            return new Queue("order.pay.status");
        }
        @Bean
        public Queue flowCreateQueue() {
            return new Queue("flow.create");
        }
        @Bean
        public Binding bindingOrderPayStatusQueue(TopicExchange topic, Queue orderPayStatusQueue){
            return BindingBuilder.bind(orderPayStatusQueue).to(topic).with("order.pay.finish.#");
        }
        @Bean
        public Binding bindingFlowCreateQueue(TopicExchange topic, Queue flowCreateQueue){
            return BindingBuilder.bind(flowCreateQueue).to(topic).with("order.pay.finish.#");
        }
    }
}
