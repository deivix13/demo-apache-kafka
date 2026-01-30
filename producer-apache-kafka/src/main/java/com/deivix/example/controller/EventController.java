package com.deivix.example.controller;

import com.deivix.example.model.TransactionMessage;
import com.deivix.example.services.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class EventController {

    @Autowired
    KafkaProducerService kafkaProducerService;

    @PostMapping("/event")
    ResponseEntity<String> eventResponse(@RequestBody TransactionMessage transactionMessage) {

        UUID uuid = UUID.randomUUID();
        log.info("Transaction recieved with key: " + uuid);
        kafkaProducerService.send("transaction-cool-topic", uuid, transactionMessage);

        return ResponseEntity.ok("Sent");
    }

}
