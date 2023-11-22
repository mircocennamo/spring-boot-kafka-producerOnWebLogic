/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.avanade.producer;

import com.avanade.model.Rilevazione;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author mirco.cennamo on 20/10/2023
 * @project spring-boot-kafka-producer
 */
@Configuration
public class Sender {


    private KafkaTemplate<String, Rilevazione> kafkaTemplate;
    Logger logger = LoggerFactory.getLogger(Sender.class);


    @Autowired
    Sender(@Qualifier("templateKafka")KafkaTemplate<String, Rilevazione> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    public void sendMessage(Rilevazione rilevazione, String topicName) {
        logger.info("Sending message : {}", rilevazione);
        logger.info("--------------------------------");
        kafkaTemplate.send(topicName, rilevazione);
        logger.info("Sent message  : {}", rilevazione);
        logger.info("--------------------------------");
    }
}
