package com.ooplab.abbank.dao;

import com.ooplab.abbank.Salary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryRepository extends MongoRepository<Salary, String> {
}
