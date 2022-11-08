package com.yiran.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
    @Bean
    public CustomExchange delayed(){
        Map<String,Object> args = new HashMap<>(1);
        args.put("x-delayed-type","direct");
        return new CustomExchange("yiran.delayed.exchange","x-delayed-message",true,false,args);
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
        public Queue delayedOrderQueue(){
            return new Queue("order.create.delayed");
        }
        @Bean
        public Queue couponFailureQueue(){
            return new Queue("coupon.failure.delayed");
        }
        @Bean
        public Binding bindingOrderPayStatusQueue(TopicExchange topic, Queue orderPayStatusQueue){
            return BindingBuilder.bind(orderPayStatusQueue).to(topic).with("order.pay.finish.#");
        }
        @Bean
        public Binding bindingFlowCreateQueue(TopicExchange topic, Queue flowCreateQueue){
            return BindingBuilder.bind(flowCreateQueue).to(topic).with("order.pay.finish.#");
        }
        @Bean
        public Binding bindingDelayedOrderQueue(CustomExchange delayed,Queue delayedOrderQueue){
            return BindingBuilder.bind(delayedOrderQueue).to(delayed).with("order.create").noargs();
        }
        @Bean
        public Binding bindingCouponFailureQueue(CustomExchange delayed,Queue couponFailureQueue){
            return BindingBuilder.bind(couponFailureQueue).to(delayed).with("coupon.failure").noargs();
        }
    }
}
