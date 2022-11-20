package com.ooplab.abbank.controller;

import com.ooplab.abbank.User;
import com.ooplab.abbank.service.BankerService;
import com.ooplab.abbank.service.CustomerService;
import com.ooplab.abbank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class UserController {

    // User Service
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password
    ){
        User user = new User(username, email, password);
        String response = userService.registerUser(user);
        return ResponseEntity.ok().body(response);
    }

    // Customer Service
    //private CustomerService customerService;


    // Banker Service
    private BankerService bankerService;


    // Bank Accounts Service


}
