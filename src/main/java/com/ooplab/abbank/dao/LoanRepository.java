package com.ooplab.abbank.dao;

import com.ooplab.abbank.Loan;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {

    @DeleteQuery
    void deleteById(String id);
}
