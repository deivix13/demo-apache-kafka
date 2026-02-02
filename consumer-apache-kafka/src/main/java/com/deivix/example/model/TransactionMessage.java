package com.deivix.example.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMessage {

    @NonNull
    private Long transactionID;

    private Event event;

    @Positive
    private Double amount;

    private Status status;

    public enum Event {
        WITHDRAW, DEPOSIT
    }
    public enum Status {
        SUBMITTED, STARTED, PENDING, FINISHED, TERMINATED
    }

}
