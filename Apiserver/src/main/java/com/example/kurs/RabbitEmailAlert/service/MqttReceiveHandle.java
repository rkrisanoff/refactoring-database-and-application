package com.example.kurs.RabbitEmailAlert.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class MqttReceiveHandle {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void handle(Message<?> message){
        rabbitTemplate.convertAndSend("directExchange", "EmailSend",message);

    }
}
