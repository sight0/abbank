package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.LogType;
import com.ooplab.abbank.User;
import com.ooplab.abbank.service.InSufficientFunds;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CustomerServiceINF {

    User getUser(String JWT);

    BigDecimal getDebt(String JWT);
    void requestBankAccount(String JWT, String accountType);

    void transferMoney(String JWT, String senderAccount, String receiverAccount, BigDecimal amount) throws InSufficientFunds;

    List<BankAccount> getBankAccounts(String JWT);
    Map<String,Map<LogType, List<String>>> requestStatement(String JWT, String accountNumber);
}
