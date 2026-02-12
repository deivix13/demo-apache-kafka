package com.deivix.dto;

import com.deivix.event.CustomKafkaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCreatedEventDTO implements CustomKafkaEvent {

    @NonNull
    @JsonProperty("transaction_id")
    private Long transactionId;

    @NonNull
    @JsonProperty("amount")
    private Double amount;

    @NonNull
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @NonNull
    @JsonProperty("correlation_id")
    private String correlationId;
}