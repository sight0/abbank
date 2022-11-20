package com.ooplab.abbank;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Document(collection = "accounts")
public class BankAccount {
    @Id
    private String id;
    private String accountNumber;
    private String accountType;
    @Field(targetType = DECIMAL128)
    private BigDecimal accountBalance;
    @Field(targetType = DECIMAL128)
    private BigDecimal accountDebt;
    private String accountStatus;
    private LocalDateTime creationDate;
    private LocalDateTime approvalDate;
    private String bankerID;
    @DBRef
    private List<Log> logs;
    @DBRef
    private List<Loan> loans;
    private final String bankCode = "4321";
    private final String branchCode = "1234";

    public BankAccount(String accountType) {
        String random = String.format("%030d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
        this.accountNumber = bankCode.concat(branchCode).concat(random);
        this.accountType = accountType;
        this.accountBalance = new BigDecimal("0.0");
        this.accountDebt = new BigDecimal("0.0");
        this.accountStatus = "Pending";
        this.creationDate = LocalDateTime.now();
    }
}
