//package com.example.kurs.RabbitEmailAlert.config;
//
//
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class MqttDataRabbitConfig {
//
//    final static String TOPIC_NAME = "topic.mqtt.EmailSend";
//
//
//    @Bean
//    public Queue OneQueue() {
//        return new Queue(MqttDataRabbitConfig.TOPIC_NAME);
//    }
//
//    @Bean
//    DirectExchange directExchange() {
//        return new DirectExchange("directExchange");
//    }
//
//    @Bean
//    Binding bindingYihonWqmMessage(Queue OneQueue, DirectExchange directExchange) {
//        return BindingBuilder.bind(OneQueue).to(directExchange).with("EmailSend");
//    }
//
//}
