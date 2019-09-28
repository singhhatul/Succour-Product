package com.stackroute.config;

import com.stackroute.domain.Activities;
import com.stackroute.domain.TransactionDetails;
import org.apache.kafka.clients.producer.ProducerConfig;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;


import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;


@Configuration
@PropertySource(value = "classpath:application.properties")

//This consumer consumes messages from the Kafka Producer.Applications that need to read data from Kafka
// use a KafkaConsumer to subscribe to Kafka topics and receive messages from these topics.
public class KafkaConfig{
    @Value("${kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, Activities> producerFactory(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }


    @Bean
    public ProducerFactory<String, TransactionDetails> producerFactory1(){
        Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(config);
    }
    //Apache Kafka is a distributed and fault-tolerant stream processing system. ...
    // Spring Kafka brings the simple and typical Spring
    // template programming model with a KafkaTemplate and Message-driven POJOs via @KafkaListener annotation.
    @Bean
    public KafkaTemplate<String,Activities> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }


    @Bean
    public KafkaTemplate<String,TransactionDetails> kafkaTemplate1(){
        return new KafkaTemplate<>(producerFactory1());
    }

}
