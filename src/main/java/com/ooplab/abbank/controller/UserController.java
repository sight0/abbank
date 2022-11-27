package com.ooplab.abbank.controller;

import com.ooplab.abbank.BankAccount;
import com.ooplab.abbank.Log;
import com.ooplab.abbank.LogType;
import com.ooplab.abbank.User;
import com.ooplab.abbank.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(path = "/")
@AllArgsConstructor
public class UserController {

    // Services
    private final UserService userService;
    private final CustomerService customerService;
    private final BankerService bankerService;
    private final BankAccountService bankAccountService;

    // User Service

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

    @PostMapping("/customer/requestAccount")
    public ResponseEntity<String> requestAccount(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam String accountType
    ){
        customerService.requestBankAccount(auth, accountType);
        return ResponseEntity.ok().body("Submitted your request to open a bank account!");
    }

    @GetMapping(value = "/customer/getAccounts", produces = "application/json")
    public ResponseEntity<Object> getAccounts(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ){
        List<BankAccount> accountList = customerService.getBankAccounts(auth);
        Map<String, String> map = new HashMap<>();
        accountList.forEach((a) -> map.put(a.getAccountNumber(), a.getAccountType()));
        return ResponseEntity.ok().body(map);
    }

    @GetMapping(value = "/customer/getDebt", produces = "application/json")
    public ResponseEntity<Object> getDebt(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ){
        BigDecimal debt = customerService.getDebt(auth);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        Map<String, String> map = new HashMap<>();
        map.put("Total Debt", df.format(debt).concat(" AED"));
        return ResponseEntity.ok().body(map);
    }

    @GetMapping(value = "/customer/requestStatement", produces = "application/json")
    public ResponseEntity<Object> requestStatement(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam(required = false) String accountNumber
    ){
        Map<String,Map<LogType, List<String>>> logs;
        logs = customerService.requestStatement(auth, Objects.requireNonNullElse(accountNumber, ""));
        return ResponseEntity.ok().body(logs);
    }

    @PostMapping("/customer/transfer")
    public ResponseEntity<String> transfer(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam String senderAccount,
            @RequestParam String receiverAccount,
            @RequestParam BigDecimal amount
    ) throws InSufficientFunds {
        if(amount.compareTo(BigDecimal.ZERO) <= 0)
            return ResponseEntity.badRequest().body("Invalid amount!");
        customerService.transferMoney(auth, senderAccount, receiverAccount, amount);
        return ResponseEntity.ok().body("Successfully transferred amount!");
    }

    // Banker Service

    @PostMapping("/banker/approveAccount")
    public ResponseEntity<String> approveAccount(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam String accountNumber
    ){
        bankerService.approveBankAccounts(auth, accountNumber);
        return ResponseEntity.ok().body("Successfully approved account!");
    }

}
