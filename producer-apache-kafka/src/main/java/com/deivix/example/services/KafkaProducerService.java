package com.deivix.example.services;

import com.deivix.example.model.TransactionMessage;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class KafkaProducerService {

    // Logger for tracking message sending results
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    KafkaTemplate<@NonNull UUID, @NonNull TransactionMessage> kafkaTemplate;

    /**
     * Sends a transaction message to the specified Kafka topic asynchronously.
     *
     * @param topicName          the Kafka topic to send the message to
     * @param key                the key used for partitioning in Kafka
     * @param transactionMessage the message payload
     */
    public void send(String topicName, UUID key, TransactionMessage transactionMessage) {
        // Send the message asynchronously
        var future = kafkaTemplate.send(topicName, key, transactionMessage);

        // Add a callback to handle success or failure
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                // Message sent successfully, log transaction ID, status, and Kafka offset
                LOGGER.info("Transaction ID: {}, status: {}, offset: {}",
                        transactionMessage.getTransactionID(),
                        transactionMessage.getStatus(),
                        result.getRecordMetadata().offset());
            } else {
                // Message failed to send, log the error
                LOGGER.error("Unable to send message=[{}] due to: {}", transactionMessage, exception.getMessage());
            }
        });
    }
}
