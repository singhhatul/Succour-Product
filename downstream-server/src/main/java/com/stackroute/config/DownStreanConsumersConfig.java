package com.stackroute.config;


import com.stackroute.domain.Activities;
import com.stackroute.domain.Domain;
import com.stackroute.domain.Finance;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class DownStreanConsumersConfig {

    @Bean
    public ConsumerFactory<String, Activities> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.stackroute.domain.Activities");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Activities.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Activities> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Activities>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, Domain> processedDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "processed-data");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain1");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.stackroute.domain1.Domain");
//        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS,true);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Domain.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Domain> kafkaListener() {
        ConcurrentKafkaListenerContainerFactory<String, Domain>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(processedDataConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, Finance> financeDataConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "finance-data");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain1");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,"com.stackroute.domain1.Domain");
//        props.put(JsonDeserializer.REMOVE_TYPE_INFO_HEADERS,true);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Finance.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Finance> kafkaFinanceListener() {
        ConcurrentKafkaListenerContainerFactory<String, Finance>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(financeDataConsumerFactory());
        return factory;
    }

}
