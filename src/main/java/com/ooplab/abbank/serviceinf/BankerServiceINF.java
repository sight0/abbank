package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.LogType;
import com.ooplab.abbank.User;

import java.util.List;
import java.util.Map;

public interface BankerServiceINF {
    //User getUser(String username);
    BankAccount getBankAccount(String accountNumber);
    String getBankerInfo(String JWT);
    Log createLog(LogType logType, String[] logMessage);
    String approveBankAccounts(String JWT, String accountNumber);
    //String approveLoan(Loan loan);

    List<Map<String,String>> getAccountsByName(String JWT, String fullname);
    Map<String, String> getAccountByNumber(String JWT, String number);
    //String holdBankAccount(BankAccount bankAccount);
    //List<Log> getBankAccountLog(BankAccount bankAccount);

}
