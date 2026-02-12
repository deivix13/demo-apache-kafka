package com.deivix.dto;

import com.deivix.event.CustomKafkaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

import java.time.LocalDateTime;

public record TransactionCreatedEventDTO(

        @NonNull
        @JsonProperty("transaction_id")
        Long transactionId,

        @NonNull
        @JsonProperty("amount")
        Double amount,

        @NonNull
        @JsonProperty("timestamp")
        LocalDateTime timestamp,

        @NonNull
        @JsonProperty("correlation_id")
        String correlationId

) implements CustomKafkaEvent {
}