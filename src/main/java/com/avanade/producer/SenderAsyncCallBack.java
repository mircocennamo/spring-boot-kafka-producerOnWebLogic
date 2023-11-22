/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.avanade.producer;

import com.avanade.model.Rilevazione;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author mirco.cennamo on 20/10/2023
 * @project spring-boot-kafka-producer-OnWebLogic
 */
@Configuration
public class SenderAsyncCallBack {


    private KafkaTemplate<String, Rilevazione> kafkaTemplate;


    @Autowired
    SenderAsyncCallBack(@Qualifier("templateKafkaAsyncCallBack") KafkaTemplate<String, Rilevazione> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendMessage(Rilevazione rilevazione, String topicName) {
        ListenableFuture<SendResult<String, Rilevazione>> future = kafkaTemplate.send(topicName, rilevazione);
    }
}
