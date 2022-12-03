package com.ooplab.abbank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Document(collection = "loans")
public class Loan {
    @Id
    private String id;
    private BigDecimal loanAmount;
    private String loanStatus;
    private LocalDateTime creationDate;
    private LocalDateTime approvalDate;
    private String bankerSignature;

    public Loan(BigDecimal amount) {
        this.loanAmount = amount;
        this.loanStatus = "Pending";
        this.creationDate = LocalDateTime.now();
    }
}
