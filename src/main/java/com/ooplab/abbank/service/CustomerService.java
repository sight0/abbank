package com.ooplab.abbank.service;

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
    public String requestBankAccount(String username, BankAccount bankAccount) {

        return null;
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
