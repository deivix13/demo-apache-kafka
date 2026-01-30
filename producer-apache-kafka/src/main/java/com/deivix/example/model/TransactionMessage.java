package com.deivix.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionMessage {

    private Long transactionID;
    private Event event;
    private Double amount;
    private Status status;

    public enum Status {
        SUBMITED, STARTED, PENDING, FINISHED, TERMINATED
    }

    public enum Event {
        WITHDRAW, DEPOSIT
    }
}
