package com.stackroute.config;

import com.stackroute.domain.Activity;
import com.stackroute.domain.SentimentDomain;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ConsumConfig {

    @Bean
    public ConsumerFactory<String, SentimentDomain> consumerFactory(){
        Map<String,Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"json");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES,"com.stackroute.domain");
        config.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.stackroute.domain.Activity");
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, SentimentDomain> kafkaListener(){
        ConcurrentKafkaListenerContainerFactory<String, SentimentDomain> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}
