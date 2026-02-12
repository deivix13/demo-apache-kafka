package com.deivix.consumer;

import com.deivix.dto.TransactionCreatedEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

    /**
     * This method listens to the "transaction-cool-topic" Kafka topic.
     * Every time a message is published, Spring calls this method automatically.
     * The groupId ensures multiple consumers can coordinate for load balancing.
     */
    @KafkaListener(topics = {"transaction-created"}, groupId = "AA-DEMO")
    public void consumer(@Payload TransactionCreatedEventDTO transactionCreated) {
        // Log the received transaction for demo purposes
        log.info("Transaction received with the ID: {}",
                transactionCreated.getTransactionId());
    }
}
