package com.stackroute.config;
import com.stackroute.domain.Notifier;
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

@EnableKafka
@Configuration
public class NotificationConsumeConfig {

    @Bean
    public ConsumerFactory<String, Notifier> notificationConsumerFactoryCGI() throws NullPointerException {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Notifier.class));
    }

    @Bean(name = "NotificationkafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, Notifier> kafkaListener() throws NullPointerException {
        ConcurrentKafkaListenerContainerFactory<String, Notifier> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(notificationConsumerFactoryCGI());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, Notifier> notificationDisasterConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Notifier.class));
    }

    @Bean(name = "NotificationkafkaListenerDisaster")
    public ConcurrentKafkaListenerContainerFactory<String, Notifier> kafkaListenerDisaster() {
        ConcurrentKafkaListenerContainerFactory<String, Notifier> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(notificationDisasterConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, Notifier> notificationNDAconsumerFactory() throws NullPointerException {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Notifier.class));
    }

    @Bean(name = "NotificationkafkaListenerNdaContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Notifier> kafkaListenerNdaContainerFactory() throws NullPointerException {
        ConcurrentKafkaListenerContainerFactory<String, Notifier> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(notificationNDAconsumerFactory());
        return factory;
    }
}
