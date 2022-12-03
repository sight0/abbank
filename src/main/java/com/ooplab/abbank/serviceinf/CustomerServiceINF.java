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

    Map<String, Object> getInformation(String JWT);

    BigDecimal getDebt(String JWT);
    String payDebt(String JWT, String accountNumber, BigDecimal amount) throws InSufficientFunds;
    String requestLoan(String JWT, String accountNumber, BigDecimal amount);
    void requestBankAccount(String JWT, String accountType);

    String transferMoney(String JWT, String senderAccount, String receiverAccount, BigDecimal amount) throws InSufficientFunds;

    List<BankAccount> getBankAccounts(String JWT);
    Map<String,List<Map<String, String>>> requestStatement(String JWT, String accountNumber);
}
