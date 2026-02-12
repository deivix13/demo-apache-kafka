package com.deivix.producer;

import com.deivix.event.CustomKafkaEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<UUID, Object> kafkaTemplate;

    /**
     * Sends an event to Kafka with validations and detailed logging.
     *
     * @param <T> Type of event that implements CustomKafkaEvent
     * @param topicName the Kafka topic name
     * @param key the message key for partitioning
     * @param event the event to send
     */
    public <T extends CustomKafkaEvent> void send(String topicName, UUID key, T event) {
        // Validate input parameters
        Objects.requireNonNull(topicName, "Topic cannot be null");
        Objects.requireNonNull(key, "Key cannot be null");
        Objects.requireNonNull(event, "Event cannot be null");

        if (topicName.isBlank()) {
            throw new IllegalArgumentException("Topic cannot be empty");
        }

        String eventType = event.getClass().getSimpleName();

        LOGGER.debug("Starting to send event [{}] to topic [{}] with key [{}]",
                eventType, topicName, key);

        try {
            var future = kafkaTemplate.send(topicName, key, event);

            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    // Message sent successfully - log metadata
                    long offset = result.getRecordMetadata().offset();
                    int partition = result.getRecordMetadata().partition();
                    long timestamp = result.getRecordMetadata().timestamp();

                    LOGGER.info("Event [{}] sent successfully. Topic: [{}], Partition: [{}], Offset: [{}], Timestamp: [{}], Key: [{}]",
                            eventType, topicName, partition, offset, timestamp, key);
                } else {
                    // Message send failed - log error details
                    LOGGER.error("Error sending event [{}] to topic [{}] with key [{}]. Message: {}",
                            eventType, topicName, key, exception.getMessage(), exception);

                    // - Send to Dead Letter Queue (DLQ)
                    // - Send alerts or notifications
                }
            });
        } catch (Exception e) {
            LOGGER.error("Fatal error processing event [{}] for topic [{}]", eventType, topicName, e);
            throw new RuntimeException("Error sending event: " + eventType, e);
        }
    }
}