/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.model.account;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Account model class.
 */
public class Account {

    private int accNumber;
    private @NotNull @Pattern(regexp = "[a-z A-z]+", message = "AccountType should contain Alphabets only") String type;
    private @Digits(integer = 6, fraction = 2, message = "wrong balance inputs") double balance;
    private @NotNull int customerId;

    /**
     * Constructor of  Account.
     *
     * @param type Account type
     * @param balance balance
     * @param customerId customerID
     */
    public Account(final String type, final double balance, final int customerId) {
        this.type = type;
        this.balance = balance;
        this.customerId = customerId;
    }

    /**
     * default constructor.
     */
    public Account() {
    }

    /**
     * getAccountType.
     *
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * GetBalance.
     *
     * @return balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * getCustomerId.
     *
     * @return customerID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * setAccountNumber.
     *
     * @param accountNo accountNumber
     */
    public void setAccountNo(final int accountNo) {
        accNumber = accountNo;
    }

    /**
     * getAccountNumber.
     *
     * @return accountNumber
     */
    public int getAccountNo() {
        return accNumber;
    }

    @Override
    public String toString() {
        return "Account{" +
            "accountNo=" + accNumber +
            ", type='" + type + '\'' +
            ", balance=" + balance +
            ", customerId=" + customerId +
            '}';
    }
}
