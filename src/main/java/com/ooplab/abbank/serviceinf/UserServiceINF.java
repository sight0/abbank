package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.User;

public interface UserServiceINF {
    void registerUser(User user);
    void updateUser(String id, User user);
    void deleteUser(String id);
}
