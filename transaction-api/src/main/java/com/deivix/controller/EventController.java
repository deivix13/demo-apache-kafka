package com.deivix.controller;

import com.deivix.dto.TransactionCreatedEventDTO;
import com.deivix.producer.KafkaProducerService;
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
    ResponseEntity<@NonNull String> eventResponse(@Valid @RequestBody TransactionCreatedEventDTO transactionCreated) {

        UUID uuid = UUID.randomUUID();
        log.info("Transaction received with key: " + uuid);
        kafkaProducerService.send("transaction-created", uuid, transactionCreated);

        return ResponseEntity.ok("Sent");
    }

}
