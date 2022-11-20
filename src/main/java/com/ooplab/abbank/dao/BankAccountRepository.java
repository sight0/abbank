package com.ooplab.abbank.dao;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountRepository extends MongoRepository<BankAccount, String> {
}
