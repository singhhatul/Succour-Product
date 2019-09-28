package com.stackroute.config;


import com.stackroute.domain.Email;
import com.stackroute.domain.Notifier;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


import java.util.HashMap;
import java.util.Map;

import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public class KafkaProduceConfig{

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<String,Object>();

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, Email> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Email> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    @Bean
    public ProducerFactory<String, Notifier> producerFactory1() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, Notifier> kafkaTemplate1() {
        return new KafkaTemplate<>(producerFactory1());
    }
}
