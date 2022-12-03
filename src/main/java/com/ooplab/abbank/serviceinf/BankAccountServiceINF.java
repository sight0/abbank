package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.*;
import com.ooplab.abbank.service.InSufficientFunds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface BankAccountServiceINF {

    User getUser(String username);

    BankAccount getAccount(String accountNumber);

    Log createLog(LogType logType, String[] logMessage);
    List<Map<String, String>> getStatement(BankAccount bankAccount);

    void createAccount(String username, String accountType);

    String enableAccount(String accountNumber);
    String transferMoney(String senderAccount, String receiverAccount, BigDecimal amount) throws InSufficientFunds;

    String payDebt(String accountNumber, BigDecimal amount) throws InSufficientFunds;

    BigDecimal getDebt(String accountNumber);
    List<Loan> getLoans(String accountNumber);
    String requestLoan(String accountNumber, BigDecimal amount);

}
