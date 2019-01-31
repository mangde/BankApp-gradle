/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.account;

import com.nuance.him.model.account.Account;
import com.nuance.him.service.exception.AccountServiceException;

/**
 * {@link AccountService} interface.
 */
public interface AccountService {

    /**
     * Add new customer account.
     *
     * @param account instance of {@link Account}
     * @return account number
     * @throws AccountServiceException serviceException
     */
    int addAccount(Account account) throws AccountServiceException;

    /**
     * Deposite amount into account.
     *
     * @param accountNumber account number
     * @param amount total amount to be deposited
     * @return successful deposited message
     * @throws AccountServiceException exception
     */
    double depositeAmount(int accountNumber, double amount) throws AccountServiceException;

    /**
     * get Balance.
     *
     * @param accountNumber account number
     * @return current balance
     * @throws AccountServiceException exception
     */
    double getCurrentBalance(int accountNumber) throws AccountServiceException;

    /**
     * Get Account details.
     *
     * @param accountNumber accNumber
     * @return instance of {@link Account}
     * @throws AccountServiceException exception
     */
    Account getAccountDetail(int accountNumber) throws AccountServiceException;

    /**
     * withDraw amount .
     *
     * @param accountNumber account number
     * @param amount total amount to be withdraw
     * @return successful withdraw message
     * @throws AccountServiceException exception
     */
    double withDrawAmount(int accountNumber, double amount) throws AccountServiceException;

    /**
     * get AccountType Id .
     * @param accountType type of account
     * @return Id
     * @throws AccountServiceException exception
     */
    int getAccountTypeId(String accountType)throws AccountServiceException;
}
