package com.ooplab.abbank.dao;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {

    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
