package com.deivix.consumer;

import com.deivix.model.TransactionMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumerService {

    /**
     * This method listens to the "transaction-cool-topic" Kafka topic.
     * Every time a message is published, Spring calls this method automatically.
     * The groupId ensures multiple consumers can coordinate for load balancing.
     */
    @KafkaListener(topics = {"transaction-cool-topic"}, groupId = "AA-DEMO")
    public void consumer(TransactionMessage transactionMessage) {
        // Log the received transaction for demo purposes
        log.info("Transaction received with the ID: {} status: {}",
                transactionMessage.getTransactionID(), transactionMessage.getStatus());
    }
}
