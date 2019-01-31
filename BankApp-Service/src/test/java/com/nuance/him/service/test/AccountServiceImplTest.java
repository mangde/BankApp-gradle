/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.dao.account.SpringAccountDao;
import com.nuance.him.dao.daoexception.AccountDaoException;
import com.nuance.him.model.account.Account;
import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.account.AccountServiceImpl;
import com.nuance.him.service.customer.CustomerService;
import com.nuance.him.service.exception.AccountServiceException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test {@link AccountServiceImpl}.
 */
public class AccountServiceImplTest {

    private static final String ACC_TYPE = "saving";
    private static final double BALANCE = 500.00;
    private static final int CUSTOMER_ID = 1;
    private static final int ACCOUNT_NUMBER =3;
    private static final double AMOUNT = 500.00;
    private static final String ACCOUNT_TYPE="saving";
    @Mock
    private SpringAccountDao springAccountDAO;
    @Mock
    private CustomerService customerService;
    private AccountService accountService;
    private Account account;
    private Customer customer;

    /**
     * initial setup.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountServiceImpl(springAccountDAO, customerService);
        account = new Account(ACC_TYPE, BALANCE, CUSTOMER_ID);
    }

    /**
     * test OpenAccount method.
     *
     * @throws Exception exception {@link AccountServiceException}
     */
    @Test
    public void testOpenAccount() throws Exception {
        when(springAccountDAO.addAccount(any(Account.class),anyInt())).thenReturn(1);
        when(customerService.findCustomerById(CUSTOMER_ID)).thenReturn(customer);
        final int accId = accountService.addAccount(account);
        assertNotNull(accId,"account id should not be Null");
        verify(springAccountDAO, atMost(2)).addAccount(account,1);
    }

    /**
     * test exception thrown by method openAccount.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test(expectedExceptions = AccountServiceException.class)
    public void testOpenAccountException() throws Exception {
        doThrow(AccountDaoException.class).when(springAccountDAO).addAccount(any(Account.class),anyInt());
        doThrow(AccountDaoException.class).when(springAccountDAO).getAccountTypeId(ACCOUNT_TYPE);
        try {
            final int result = accountService.addAccount(account);
        }
        catch (final AccountServiceException accountServiceException) {
            assertEquals(accountServiceException.getCause().getClass(), AccountDaoException.class);
            verify(springAccountDAO).getAccountTypeId(ACCOUNT_TYPE);
            verify(springAccountDAO,atMost(2)).addAccount(account,1);
            throw accountServiceException;
        }
    }

    /**
     * test method getCurrentAvailable balance.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test
    public void testGetBalance() throws Exception {
        when(springAccountDAO.getCurrentBalance(anyInt())).thenReturn(BALANCE);
        final double balance = accountService.getCurrentBalance(ACCOUNT_NUMBER);
        assertNotNull(balance,"balance should not be null");
        verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
    }

    /**
     * test exception thrown by method GetCurrentBalance.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test(expectedExceptions = AccountServiceException.class)
    public void testGetBalanceException() throws Exception {
        doThrow(AccountDaoException.class).when(springAccountDAO).getCurrentBalance(anyInt());
        try {
            final double balance = accountService.getCurrentBalance(ACCOUNT_NUMBER);
        }
        catch (final AccountServiceException accountServiceException) {
            assertEquals(accountServiceException.getCause().getClass(), AccountDaoException.class);
            verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
            throw accountServiceException;
        }
    }

    /**
     * test  method depositeAmount.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test
    public void testDeposite() throws Exception {
        when(springAccountDAO.depositeAmount(anyInt(), anyDouble())).thenReturn(BALANCE);
        when(springAccountDAO.getCurrentBalance(anyInt())).thenReturn(BALANCE);
        final double amount = accountService.depositeAmount(ACCOUNT_NUMBER, AMOUNT);
        assertNotNull(amount,"amount should not be null");
        verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
        verify(springAccountDAO, atMost(2)).depositeAmount(ACCOUNT_NUMBER, AMOUNT);
    }

    /**
     * test exception thrown by method depositAmount.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test(expectedExceptions = AccountServiceException.class)
    public void testDepositException() throws Exception {
        doThrow(AccountDaoException.class).when(springAccountDAO).getCurrentBalance(anyInt());
        doThrow(AccountDaoException.class).when(springAccountDAO).depositeAmount(anyInt(), anyDouble());
        try {
            final double amount = accountService.depositeAmount(ACCOUNT_NUMBER, AMOUNT);
        }
        catch (final AccountServiceException accountServiceException) {
            assertEquals(accountServiceException.getCause().getClass(), AccountDaoException.class);
            verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
            verify(springAccountDAO, atMost(2)).depositeAmount(ACCOUNT_NUMBER, AMOUNT);
            throw accountServiceException;
        }
    }

    /**
     * test method getAccountDetails.
     *
     * @throws Exception is {@link AccountServiceException}
     */
    @Test
    public void testGetAccountDetail() throws Exception {
        when(springAccountDAO.getAccountDetail(anyInt())).thenReturn(account);
        final Account accountDetail = accountService.getAccountDetail(ACCOUNT_NUMBER);
        assertNotNull(accountDetail,"accountDetail not be null");
        verify(springAccountDAO).getAccountDetail(ACCOUNT_NUMBER);
    }

    /**
     * test exception thrown by method GetAccountDetails.
     *
     * @throws Exception {@link AccountServiceException}
     */
    @Test(expectedExceptions = AccountServiceException.class)
    public void testGetAccountDetailException() throws Exception {
        doThrow(AccountDaoException.class).when(springAccountDAO).getAccountDetail(anyInt());
        try {
            final Account accountDetail = accountService.getAccountDetail(ACCOUNT_NUMBER);
        }
        catch (final AccountServiceException accountServiceException) {
            assertEquals(accountServiceException.getCause().getClass(), AccountDaoException.class);
            verify(springAccountDAO).getAccountDetail(ACCOUNT_NUMBER);
            throw accountServiceException;
        }
    }

    /**
     * test  method withDraw.
     *
     * @throws Exception {@link AccountServiceException}
     */
    @Test
    public void testWithdraw() throws Exception {
        when(springAccountDAO.getCurrentBalance(anyInt())).thenReturn(BALANCE);
        when(springAccountDAO.withDrawAmount(anyInt(), anyDouble())).thenReturn(BALANCE);
        final double amount = accountService.withDrawAmount(ACCOUNT_NUMBER, AMOUNT);
        assertNotNull(amount,"amount should not be null");
        verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
        verify(springAccountDAO, atMost(2)).withDrawAmount(ACCOUNT_NUMBER, AMOUNT);
    }

    /**
     * test exception thrown by method withDraw.
     *
     * @throws Exception {@link AccountServiceException}
     */
    @Test(expectedExceptions = AccountServiceException.class)
    public void testWithDrawAmountException() throws Exception {
        doThrow(AccountDaoException.class).when(springAccountDAO).withDrawAmount(anyInt(), anyDouble());
        doThrow(AccountDaoException.class).when(springAccountDAO).getCurrentBalance(anyInt());
        try {
            final double balance = accountService.getCurrentBalance(ACCOUNT_NUMBER);
            final double amount = accountService.withDrawAmount(ACCOUNT_NUMBER, AMOUNT);
        }
        catch (final AccountServiceException accountServiceException) {
            assertEquals(accountServiceException.getCause().getClass(), AccountDaoException.class);
            verify(springAccountDAO).getCurrentBalance(ACCOUNT_NUMBER);
            verify(springAccountDAO, atMost(2)).withDrawAmount(ACCOUNT_NUMBER, AMOUNT);
            throw accountServiceException;
        }
    }
}
