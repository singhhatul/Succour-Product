package com.stackroute.config;

import com.stackroute.domain.SentimentDomain;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
@EnableKafka
@PropertySource(value = "classpath:application.properties")
public class KafkaConfig{
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

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
    public ProducerFactory<String,SentimentDomain> producerFactory() {
        return new DefaultKafkaProducerFactory<String,SentimentDomain>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, SentimentDomain> kafkaTemplate() {

        return new KafkaTemplate<String, SentimentDomain>(producerFactory());
    }
}
