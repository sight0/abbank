package com.ooplab.abbank.serviceinf;

import com.ooplab.abbank.Address;
import com.ooplab.abbank.Salary;
import com.ooplab.abbank.User;

import java.time.LocalDate;
import java.util.HashMap;

public interface UserServiceINF {
    User getUser(String username);

    String registerUser(User user);
    String deleteUser(String username);

    String setPassword(String username, String password);
    String setPin(String username, String pin);

    String setRole(String username, String role);
    String setEnabled(String username, Boolean enabled);

    void sendVerificationEmail(String username); // TODO: implement abstract method

    void verifyEmail(User user); // TODO: implement abstract method

    String setAddress(String username, Address address);
    String setDOB(String username, LocalDate DOB);
    String setSalary(String username, Salary salary);

    String getProfile(String username);

//  List<Notification> getNotifications();
//  void pushNotification(Notification notification);
//  void popNotification(Notification notification);
}
