package com.deivix.example.config;

import com.deivix.example.model.TransactionMessage;
import lombok.NonNull;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration // Marks this class as a Spring configuration class
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<@NonNull UUID, @NonNull TransactionMessage> producerFactory() {

        // Map that holds all Kafka producer configuration properties
        Map<String, Object> configProps = new HashMap<>();

        // Address of the Kafka broker the producer will connect to
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092"
        );

        // Serializer used to convert the message KEY (UUID) into bytes
        // Kafka can only send byte[], so this is mandatory
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                UUIDSerializer.class
        );

        // Serializer used to convert the message VALUE (TransactionMessage)
        // into JSON and then into bytes before sending to Kafka
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JacksonJsonSerializer.class
        );

        // Creates a ProducerFactory with the given configuration.
        // Spring will use this factory to create Kafka producers when needed.
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    // Creates a KafkaTemplate bean that can be injected and used to send messages.
    // It uses the ProducerFactory defined above to obtain producers with the correct
    // configuration (broker address, key/value serializers).
    // KafkaTemplate handles sending messages, serializing them, and managing the producer lifecycle internally.
    @Bean
    KafkaTemplate<@NonNull UUID, @NonNull TransactionMessage> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
