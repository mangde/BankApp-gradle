/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.account;

import com.nuance.him.dao.account.SpringAccountDao;
import com.nuance.him.dao.daoexception.AccountDaoException;
import com.nuance.him.model.account.Account;
import com.nuance.him.service.customer.CustomerService;
import com.nuance.him.service.exception.AccountServiceException;
import com.nuance.him.service.exception.CustomerServiceException;
import com.nuance.him.service.exception.InsufficientBalanceException;

/**
 * {@link AccountService} implementation.
 */
public class AccountServiceImpl implements AccountService {

    private final SpringAccountDao springAccountDao;
    private final CustomerService customerService;
    private static final double WITHDRAW_LIMIT = 4500.50;

    /**
     * {@link SpringAccountDao }constructor.
     *
     * @param springAccountDAO instance of {@link SpringAccountDao}
     * @param customerService instance of {@link CustomerService}
     */
    public AccountServiceImpl(final SpringAccountDao springAccountDAO, final CustomerService customerService) {
        springAccountDao = springAccountDAO;
        this.customerService = customerService;
    }

    @Override
    public int addAccount(final Account account) throws AccountServiceException {
        try {
            customerService.findCustomerById(account.getCustomerId());
        }
        catch (final CustomerServiceException customerServiceException) {
            throw new AccountServiceException("Customer NOt Found", customerServiceException);
        }
        try {
            final int accTypeId = getAccountTypeId(account.getType());
            return springAccountDao.addAccount(account, accTypeId);
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service", accountDaoException);
        }
    }

    @Override
    public synchronized double depositeAmount(final int accountNumber, final double amount) throws AccountServiceException {
        try {
            final double currentBalance = amount + getCurrentBalance(accountNumber);
            return springAccountDao.depositeAmount(accountNumber, currentBalance);
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service deposite amount", accountDaoException);
        }
    }

    @Override
    public double getCurrentBalance(final int accountNumber) throws AccountServiceException {
        try {
            final Account account = springAccountDao.getAccountDetail(accountNumber);
            return springAccountDao.getCurrentBalance(accountNumber);
        }
        catch (final RuntimeException runtimeException) {
            throw new AccountServiceException(" Unable to get current balance ", runtimeException);
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service getCurrentBalance", accountDaoException);
        }
    }

    @Override
    public Account getAccountDetail(final int accountNumber) throws AccountServiceException {
        try {
            return springAccountDao.getAccountDetail(accountNumber);
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service getAccountDetail", accountDaoException);
        }
    }

    @Override
    public synchronized double withDrawAmount(final int accountNumber, final double amount) throws AccountServiceException {
        try {
            final double currentAvailableBalance = springAccountDao.getCurrentBalance(accountNumber);
            if (currentAvailableBalance < amount) {
                throw new InsufficientBalanceException("insufficient balance ");
            }
            else if (amount > WITHDRAW_LIMIT) {
                throw new InsufficientBalanceException("you have entered amount exceed than daily limit");
            }
            final double availableBalance = currentAvailableBalance - amount;
            springAccountDao.withDrawAmount(accountNumber, availableBalance);
            return availableBalance;
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service withDrawAmount", accountDaoException);
        }
        catch (final InsufficientBalanceException insufficientBalanceException) {
            throw new AccountServiceException("insufficient Balance Exception", insufficientBalanceException);
        }
    }

    @Override
    public int getAccountTypeId(final String accountType) throws AccountServiceException {
        try {
            return springAccountDao.getAccountTypeId(accountType);
        }
        catch (final AccountDaoException accountDaoException) {
            throw new AccountServiceException("exception in service getAccountDetail", accountDaoException);
        }
    }
}
