package com.ooplab.abbank.service;

import com.ooplab.abbank.*;
import com.ooplab.abbank.dao.BankAccountRepository;
import com.ooplab.abbank.dao.LogRepository;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.serviceinf.BankAccountServiceINF;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class BankAccountService implements BankAccountServiceINF {

    private final LogRepository logRepository;
    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Log createLog(LogType logType, String[] logMessage) {
        Log log = new Log(logType, logMessage);
        logRepository.save(log);
        return log;
    }

    @Override
    public List<Log> getLogs(String accountNumber) {
        return null;
    }

    @Override
    public List<Log> getStatement(String email, String accountNumber) {
        return null;
    }

    @Override
    public String createAccount(String username, String accountType) {
        User user = getUser(username);
        if(user == null) return "No user exists with the given username!";
        BankAccount bankAccount = new BankAccount(accountType);
        bankAccountRepository.save(bankAccount);
        Log out = createLog(LogType.REQUEST_ACCOUNT, new String[]{username, accountType, bankAccount.getAccountNumber()});
        return out.getLogMessage();
    }

    @Override
    public String enableAccount(String accountNumber) {
        return null;
    }

    @Override
    public String transferMoney(String senderAccount, String receiverAccount, BigDecimal amount) {
        return null;
    }

    @Override
    public BigDecimal getDebt(String accountNumber) {
        return null;
    }

    @Override
    public List<Loan> getLoans(String accountNumber) {
        return null;
    }

    @Override
    public String requestLoan(String accountNumber) {
        return null;
    }
}
