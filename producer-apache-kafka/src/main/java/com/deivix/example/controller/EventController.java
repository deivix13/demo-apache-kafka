package com.deivix.example.controller;

import com.deivix.example.model.TransactionMessage;
import com.deivix.example.services.KafkaProducerService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class EventController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/event")
    ResponseEntity<@NonNull String> eventResponse(@Valid @RequestBody TransactionMessage transactionMessage) {

        UUID uuid = UUID.randomUUID();
        log.info("Transaction received with key: " + uuid);
        kafkaProducerService.send("transaction-cool-topic", uuid, transactionMessage);

        return ResponseEntity.ok("Sent");
    }

}
