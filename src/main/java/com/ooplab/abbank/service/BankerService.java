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

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BankerService implements BankerServiceINF {

    private final BankAccountRepository bankAccountRepository;
    private final LogRepository logRepository;
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
}
