package com.ooplab.abbank.service;

import com.ooplab.abbank.*;
import com.ooplab.abbank.dao.BankAccountRepository;
import com.ooplab.abbank.dao.LogRepository;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.serviceinf.BankAccountServiceINF;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
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
    public BankAccount getAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    @Override
    public Log createLog(LogType logType, String[] logMessage) {
        Log log = new Log(logType, logMessage);
        logRepository.save(log);
        return log;
    }

    @Override
    public Map<LogType, List<String>> getStatement(BankAccount account){
        List<String> logs = new ArrayList<>();
        Map<LogType, List<String>> statement = new HashMap<>();
        account.getLogs().forEach((l) -> {
            if( l.getLogType() == LogType.DEPOSIT ||
                l.getLogType() == LogType.TRANSFER ||
                l.getLogType() == LogType.WITHDRAW ) {
                logs.add(l.getLogMessage());
                statement.put(l.getLogType(), logs);
            }
        });
        return statement;
    }

    @Override
    public void createAccount(String username, String accountType) {
        User user = getUser(username);
        if(user == null) return;
        BankAccount bankAccount = new BankAccount(accountType);
        user.getAccounts().add(bankAccount);
        String fullName = String.format("Mr/s. %s %s", user.getFirstName(), user.getLastName());
        Log out = createLog(LogType.REQUEST_ACCOUNT, new String[]{fullName, accountType, bankAccount.getAccountNumber()});
        List<Log> logs = new ArrayList<>(bankAccount.getLogs());
        logs.add(out);
        bankAccount.setLogs(logs);
        bankAccountRepository.save(bankAccount);
        userRepository.save(user);
    }

    @Override
    public String enableAccount(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
        if(account == null) return "No account exists with the given account number!";
        account.setAccountStatus("active");
        bankAccountRepository.save(account);
        return "Successfully enabled bank account! [Changed status -> `active`]";
    }

    @Override
    public String transferMoney(String senderAccount, String receiverAccount, BigDecimal amount) throws InSufficientFunds{
        BankAccount sAccount = getAccount(senderAccount);
        BankAccount rAccount = getAccount(receiverAccount);
        if(sAccount == null || rAccount == null) return null;
        BigDecimal sBalance = sAccount.getAccountBalance();
        BigDecimal diff = sBalance.subtract(amount);
        if(diff.compareTo(BigDecimal.ZERO) < 0)
            throw new InSufficientFunds();
        sAccount.setAccountBalance(diff);
        rAccount.setAccountBalance(rAccount.getAccountBalance().add(amount));
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Log out = createLog(LogType.TRANSFER, new String[]{df.format(amount), sAccount.getAccountNumber(), rAccount.getAccountNumber()});
        List<Log> Slogs = new ArrayList<>(sAccount.getLogs());
        Slogs.add(out);
        sAccount.setLogs(Slogs);
        List<Log> Rlogs = new ArrayList<>(rAccount.getLogs());
        Rlogs.add(out);
        rAccount.setLogs(Rlogs);
        bankAccountRepository.save(sAccount);
        bankAccountRepository.save(rAccount);
        return out.getLogMessage();
    }

    @Override
    public BigDecimal getDebt(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
        if(account == null) return BigDecimal.ZERO;
        return account.getAccountDebt();
    }

    @Override
    public List<Loan> getLoans(String accountNumber) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
        if(account == null) return new ArrayList<>();
        return account.getLoans();
    }

    @Override
    public String requestLoan(String accountNumber, BigDecimal amounts) {
        BankAccount account = bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
        if(account == null) return "No account exists with the given account number!";
        // TODO: Business logic regarding loan request eligibility
        // Loan loan = new Loan();
        return null;
    }
}

