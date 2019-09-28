package com.stackroute.config;

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

@EnableKafka
@Configuration
public class ConsumConfig {
    //Kafka configaration for the CGI topic.
    @Bean
    public ConsumerFactory<String, Domain> consumerFactory() throws NullPointerException {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Domain.class));
    }

    @Bean(name = "kafkaListener")
    public ConcurrentKafkaListenerContainerFactory<String, Domain> kafkaListener() throws NullPointerException {
        ConcurrentKafkaListenerContainerFactory<String, Domain> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    //Kafka configaration for the Disaster topic.
    @Bean
    public ConsumerFactory<String, Domain> disasterConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Domain.class));
    }

    @Bean(name = "kafkaListenerDisaster")
    public ConcurrentKafkaListenerContainerFactory<String, Domain> kafkaListenerDisaster() {
        ConcurrentKafkaListenerContainerFactory<String, Domain> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(disasterConsumerFactory());
        return factory;
    }

    //Kafka configaration for the NDA topic.
    @Bean
    public ConsumerFactory<String, Domain> ndaconsumerFactory() throws NullPointerException {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "sample-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Domain.class));
    }

    @Bean(name = "kafkaListenerNdaContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, Domain> kafkaListenerNdaContainerFactory() throws NullPointerException {
        ConcurrentKafkaListenerContainerFactory<String, Domain> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(ndaconsumerFactory());
        return factory;
    }

    //Kafka configaration for the finance topic.
    @Bean
    public ConsumerFactory<String, Finance> financeconsumerFactory() throws NullPointerException {
        Map<String, Object> config = new HashMap<>();
        // list of host:port pairs used for establishing the initial connections to the Kakfa cluster
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "finance");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "com.stackroute.domain");
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(),
                new JsonDeserializer<>(Finance.class));
    }

    @Bean(name = "kafkaListenerfinance")
    public ConcurrentKafkaListenerContainerFactory<String, Finance> kafkaListenerfinance() throws NullPointerException {
        ConcurrentKafkaListenerContainerFactory<String, Finance> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(financeconsumerFactory());
        return factory;
    }

}
