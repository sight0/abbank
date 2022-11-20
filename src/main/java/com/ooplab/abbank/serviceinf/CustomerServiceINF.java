package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.User;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerServiceINF {

    User getUser(String username);

    BigDecimal getDebt(String username);
    String requestBankAccount(String username, BankAccount bankAccount);

    List<BankAccount> getBankAccounts(String username);
    List<Log> requestStatement(String username);
}
