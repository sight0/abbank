package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.Loan;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.LogType;
import com.ooplab.abbank.User;

import java.math.BigDecimal;
import java.util.List;

public interface BankAccountServiceINF {

    User getUser(String username);

    Log createLog(LogType logType, String[] logMessage);
    List<Log> getLogs(String accountNumber);
    List<Log> getStatement(String email, String accountNumber);

    String createAccount(String username, String accountType);

    String enableAccount(String accountNumber);
    String transferMoney(String senderAccount, String receiverAccount, BigDecimal amount);

    BigDecimal getDebt(String accountNumber);
    List<Loan> getLoans(String accountNumber);
    String requestLoan(String accountNumber);

}
