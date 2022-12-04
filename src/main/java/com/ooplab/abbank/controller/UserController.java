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
            @RequestParam String firstname,
            @RequestParam String lastname,
            @RequestParam String email,
            @RequestParam String password
    ){
        User user = new User(username, email, password);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        String response = userService.registerUser(user);
        if(response.equals("Username is unavailable!"))
            return ResponseEntity.status(502).body(response);
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

    @PostMapping("/customer/requestLoan")
    public ResponseEntity<String> requestLoan(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount
    ){
        String response = customerService.requestLoan(auth, accountNumber, amount);
        return ResponseEntity.ok().body(response);
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

    @PostMapping(value = "/customer/payDebt", produces = "application/json")
    public ResponseEntity<Object> payDebt(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount
    ) throws InSufficientFunds {
        String response = customerService.payDebt(auth, accountNumber, amount);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping(value = "/customer/editProfile", produces = "application/json")
    public ResponseEntity<Object> editProfile(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String pin,
            @RequestParam(required = false) String password
    ) {
        String response = customerService.editProfile(auth, email, pin, password);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping(value = "/customer/getInformation", produces = "application/json")
    public ResponseEntity<Object> getInformation(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ){
        Map<String, Object> info = customerService.getInformation(auth);
        return ResponseEntity.ok().body(info);
    }

    @GetMapping(value = "/customer/getNotifications", produces = "application/json")
    public ResponseEntity<Object> getNotifications(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam Boolean all
    ){
        List<Log> notification = customerService.getNotifications(auth, all);
        return ResponseEntity.ok().body(notification);
    }

    @PostMapping(value = "/customer/seeNotifications", produces = "application/json")
    public ResponseEntity<Object> seeNotifications(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth
    ){
        customerService.seeNotifications(auth);
        return ResponseEntity.ok().body("Cleared notifications!");
    }

    @GetMapping(value = "/customer/requestStatement", produces = "application/json")
    public ResponseEntity<Object> requestStatement(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String auth,
            @RequestParam(required = false) String accountNumber
    ){
//        Map<String,Map<LogType, List<String>>> logs;
        Map<String,List<Map<String, String>>> logs;
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
        String response = customerService.transferMoney(auth, senderAccount, receiverAccount, amount);
        return ResponseEntity.ok().body(response);
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
