package com.deivix.example.config;

import com.deivix.example.model.TransactionMessage;
import lombok.NonNull;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class KafkaProducerConfig {

    // Inject Kafka producer properties from application configuration
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.producer.key-serializer}")
    private Class<?> keySerializer;

    @Value("${spring.kafka.producer.value-serializer}")
    private Class<?> valueSerializer;

    // Define how Kafka producers are created.
    @Bean
    public ProducerFactory<@NonNull UUID, @NonNull TransactionMessage> producerFactory() {

        Map<String, Object> configProps = new HashMap<>();

        // Kafka broker address
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);

        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, this.keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, this.valueSerializer);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<@NonNull UUID, @NonNull TransactionMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
