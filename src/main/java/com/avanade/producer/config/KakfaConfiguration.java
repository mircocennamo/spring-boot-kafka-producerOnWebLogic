package com.avanade.producer.config;

import com.avanade.model.Rilevazione;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;


/**
 * @author mirco.cennamo on 20/10/2023
 * @project spring-boot-kafka-producer
 * classe di configurazione dove sono censiti due tipologie di kafkaTemplate
 * uno con listener su invio e l'altro senza
 */
@Configuration
public class KakfaConfiguration {
    Logger logger = LoggerFactory.getLogger(KakfaConfiguration.class);
    @Value("${bootstrap.servers}")
    private String bootstrapServers;



    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }


    @Bean
    public ProducerFactory<String, Rilevazione> producerFactory() {
      return new DefaultKafkaProducerFactory<>(producerConfigs());
    }


    @Bean(name="templateKafka")
    public KafkaTemplate<String, Rilevazione> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean(name="templateKafkaAsyncCallBack")
    public KafkaTemplate<String, Rilevazione> kafkaTemplateAsyncCallBack() {
            KafkaTemplate kt = new KafkaTemplate<>(producerFactory());
            kt.setProducerListener(new ProducerListener() {

            @Override
            public void onSuccess(ProducerRecord record, RecordMetadata recordMetadata) {
                if(logger.isDebugEnabled()){
                    logger.debug("message sended  on topic {} ; partition = {} with offset= {} ; Timestamp : {} ; Message Size = {}" ,
                            recordMetadata.topic() , recordMetadata.partition() , recordMetadata.offset()
                            , recordMetadata.timestamp() , recordMetadata.serializedValueSize());
                }

            }

            @Override
            public void onError(ProducerRecord producerRecord, Exception exception) {
                if(logger.isDebugEnabled()){
                    logger.debug("error send message on Topic = {} ; Message = {} ; exception = {} " ,
                            producerRecord.topic() , producerRecord.value() , exception.getMessage());
                }
                exception.printStackTrace();
            }
        });
        return kt;
    }
}

