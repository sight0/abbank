package com.ooplab.abbank.service;

public class InSufficientFunds extends Exception {
    public InSufficientFunds() {
        super("Insufficient funds to transfer money!");
    }
}
