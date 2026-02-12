package com.deivix.config;

import lombok.NonNull;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;


    // Defines how Kafka consumers are created.
    @Bean
    public ConsumerFactory<@NonNull UUID, Object> consumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServers);

        props.put(ConsumerConfig.GROUP_ID_CONFIG, this.groupId);

        // Wrap deserializers to handle errors without crashing the consumer
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, UUIDDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, true);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.deivix.*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, Object.class.getName());
        props.put(JsonDeserializer.TYPE_MAPPINGS,
                "transaction_created:com.deivix.dto.TransactionCreatedEventDTO, " +
                        "transaction_finished:com.deivix.dto.TransactionFinishedEventDTO");
        return new org.springframework.kafka.core.DefaultKafkaConsumerFactory<>(props);
    }

    /**
     * Creates Kafka listener containers used by @KafkaListener.
     * Supports concurrent message consumption.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<@NonNull UUID, Object>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<@NonNull UUID, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        // Use the consumer factory defined above
        factory.setConsumerFactory(consumerFactory());

        return factory;
    }
}
