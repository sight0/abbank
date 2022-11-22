package com.ooplab.abbank.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.User;
import com.ooplab.abbank.serviceinf.CustomerServiceINF;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService implements CustomerServiceINF {

    private final BankAccountService bankAccountService;

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public BigDecimal getDebt(String username) {
        return null;
    }

    @Override
    public String requestBankAccount(String header, String accountType) {
        // TODO: Push Notification Logic to Bankers
        // TODO: Possibly send email for confirmation

        String token = header.substring("Bearer ".length());
        Algorithm algorithm = Algorithm.HMAC256("SECRET".getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();

        return bankAccountService.createAccount(username, accountType);
    }

    @Override
    public List<BankAccount> getBankAccounts(String username) {
        return null;
    }

    @Override
    public List<Log> requestStatement(String username) {
        return null;
    }
}
