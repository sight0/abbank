package com.ooplab.abbank.dao;

import com.ooplab.abbank.User;
import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    Optional<User> findByFirstNameAndLastName(String firstname, String lastname);

    @DeleteQuery
    void deleteByUsername(String username);
}
