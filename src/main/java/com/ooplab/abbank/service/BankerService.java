package com.ooplab.abbank.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.LogType;
import com.ooplab.abbank.User;
import com.ooplab.abbank.dao.BankAccountRepository;
import com.ooplab.abbank.dao.LogRepository;
import com.ooplab.abbank.dao.UserRepository;
import com.ooplab.abbank.serviceinf.BankerServiceINF;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class BankerService implements BankerServiceINF {

    private final BankAccountRepository bankAccountRepository;
    private final BankAccountService bankAccountService;
    private final LogRepository logRepository;
    private final CustomerService customerService;
    private final UserRepository userRepository;

    @Override
    public String getBankerInfo(String header) {
        String token = header.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
        JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) return "Invalid header while attempting to get banker's information";
        return String.format("[Signature - `%s %s` | ID %s]", user.getFirstName(),user.getLastName(), user.getId());
    }

    @Override
    public BankAccount getBankAccount(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).orElse(null);
    }

    @Override
    public Log createLog(LogType logType, String[] logMessage) {
        Log log = new Log(logType, logMessage);
        logRepository.save(log);
        return log;
    }

    @Override
    public String approveBankAccounts(String header, String accountNumber) {
        BankAccount account = getBankAccount(accountNumber);
        account.setAccountStatus("Active");
        account.setApprovalDate(LocalDateTime.now());
        Log out = createLog(LogType.APPROVE_ACCOUNT, new String[]{getBankerInfo(header), account.getAccountType(), account.getAccountNumber()});
        account.getLogs().add(out);
        bankAccountRepository.save(account);
        return out.getLogMessage();
    }

    @Override
    public List<Map<String, String>> getAccountsByName(String header, String firstname, String lastname) {
        User user = userRepository.findByFirstNameAndLastName(firstname, lastname).orElse(null);
        if(user == null)
            return new ArrayList<>();
        List<BankAccount> accounts = user.getAccounts();
        if(accounts.size()==0)
            return new ArrayList<>();
        List<Map<String, String>> active = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        accounts.forEach((a) -> {
            if(Objects.equals(a.getAccountStatus(), "Active")){
                Map<String, String> account = new HashMap<>();
                account.put("accountNumber", a.getAccountNumber());
                account.put("accountType", a.getAccountType());
                account.put("accountBalance", df.format(a.getAccountBalance()).concat(" AED"));
                account.put("accountDebt", df.format(a.getAccountDebt()).concat(" AED"));
                account.put("accountApproval", a.getApprovalDate().toString());
                account.put("accountCreation", a.getCreationDate().toString());
                account.put("bankerSignature", a.getBankerID());
                active.add(account);
            }
        });
        return active;
    }

    @Override
    public Map<String, String> getAccountByNumber(String header, String number) {
        BankAccount Baccount = bankAccountRepository.findByAccountNumber(number).orElse(null);
        if(Baccount==null||!Baccount.getAccountStatus().equals("Active"))
            return new HashMap<>();
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, String> account = new HashMap<>();
        account.put("accountNumber", Baccount.getAccountNumber());
        account.put("accountType", Baccount.getAccountType());
        account.put("accountBalance", df.format(Baccount.getAccountBalance()).concat(" AED"));
        account.put("accountDebt", df.format(Baccount.getAccountDebt()).concat(" AED"));
        account.put("accountApproval", Baccount.getApprovalDate().toString());
        account.put("accountCreation", Baccount.getCreationDate().toString());
        account.put("bankerSignature", Baccount.getBankerID());
        return account;
    }

    @Override
    public List<Map<String, String>> requestStatement(String header, String accountNumber) {
        List<Map<String, String>> statement = new ArrayList<>();
        if(Objects.equals(accountNumber, "")){
            BankAccount account = bankAccountService.getAccount(accountNumber);
            account.getLogs().forEach((log) -> {
                Map<String, String> r = new HashMap<>();
                r.put("logType", log.getLogType().toString());
                r.put("logMessage", log.getLogMessage());
                r.put("logDate", log.getLogDate().toString());
                statement.add(r);
            });
        }
        return statement;
    }


}
