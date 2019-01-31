/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.account;

import com.nuance.him.dao.daoexception.AccountDaoException;
import com.nuance.him.model.account.Account;

/**
 * AccountDAO interface.
 */
public interface SpringAccountDao {

    /**
     * Add Account.
     *
     * @param account instance of {@link Account}
     * @param accTypeId accountTypeId
     * @return account number
     * @throws AccountDaoException exception if Not add
     */
    int addAccount(Account account, int accTypeId) throws AccountDaoException;

    /**
     * Deposite amount into account.
     *
     * @param accountNumber account number
     * @param amount total amount to be deposited
     * @return successful deposited message
     * @throws AccountDaoException exception if fail to deposite
     */
    double depositeAmount(int accountNumber, double amount) throws AccountDaoException;

    /**
     * get Balance.
     *
     * @param accountNumber account number
     * @return current balance
     * @throws AccountDaoException exception if AcNumber Not exits
     */
    double getCurrentBalance(int accountNumber) throws AccountDaoException;

    /**
     * Get Account details.
     *
     * @param accountNumber accNumber
     * @return instance of {@link Account}
     * @throws AccountDaoException exception if AcNumber Not exits
     */
    Account getAccountDetail(int accountNumber) throws AccountDaoException;

    /**
     * withDraw amount .
     *
     * @param accountNumber account number
     * @param amount total amount to be withdraw
     * @return successful withdraw message with value 1
     * @throws AccountDaoException exception if insufficient balance
     */
    double withDrawAmount(int accountNumber, double amount) throws AccountDaoException;

    /**
     * getAccountType Id.
     *
     * @param accountType type of account
     * @return accountTypeId
     * @throws AccountDaoException DaoException
     */
    int getAccountTypeId(String accountType) throws AccountDaoException;
}
