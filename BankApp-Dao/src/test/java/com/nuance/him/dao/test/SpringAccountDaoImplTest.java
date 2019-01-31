/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.account.SpringAccountDao;
import com.nuance.him.dao.account.SpringAccountDaoImpl;
import com.nuance.him.model.account.Account;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * test class for {@link SpringAccountDao}.
 */
@TestPropertySource("classpath:sql-queries.xml")
@ContextConfiguration(classes = DatabaseConfig.class)
@Transactional
public class SpringAccountDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String OPEN_ACCOUNT = "addAccount";
    private static final String GET_BALANCE = "getBalance";
    private static final String DEPOSITE_AMOUNT = "depositeAmount";
    private static final String ACCOUNT_DETAILS = "accountDetail";
    private static final String WITHDRAW_AMOUNT = "withDraw";
    private static final String ACC_TYPE = "saving";
    private static final double BALANCE = 5025.00;
    private static final int CUSTOMER_ID = 1;
    private static final int ACCOUNT_NUMBER = 3;
    private static final double AMOUNT = 500.00;
    private static final String ACC_TYPE_ID = "getAccTypeId";
    @Value("${" + OPEN_ACCOUNT + "}")
    private String getOpenAccount;
    @Value("${" + ACC_TYPE_ID + "}")
    private String getAccType;
    @Value("${" + GET_BALANCE + "}")
    private String getGetBalance;
    @Value("${" + DEPOSITE_AMOUNT + "}")
    private String getDepositeAmount;
    @Value("${" + ACCOUNT_DETAILS + "}")
    private String getAccountDetails;
    @Value("${" + WITHDRAW_AMOUNT + "}")
    private String getWithdrawAmount;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SpringAccountDao springAccountDAO;
    private Account account;

    /**
     * initial setup.
     */
    @BeforeMethod
    public void setUp() {
        springAccountDAO = new SpringAccountDaoImpl(namedParameterJdbcTemplate, getOpenAccount, getAccType, getGetBalance, getDepositeAmount, getAccountDetails, getWithdrawAmount);
        account = new Account(ACC_TYPE, BALANCE, CUSTOMER_ID);
    }

    /**
     * test open New Account.
     *
     * @throws Exception exception if any.
     */
    @Test
    @Transactional
    public void testOpenAccount() throws Exception {
        assertNotNull(springAccountDAO,"springAccountDAO should not be null");
        final int accId = springAccountDAO.addAccount(account, 1);
        assertNotEquals(accId, 0,"accountId not be 0");
    }

    /**
     * test Current Available balance.
     *
     * @throws Exception exception if accNumber Not exits
     */
    @Test
    @Transactional
    public void testGetBalance() throws Exception {
        assertNotNull(springAccountDAO,"springAccountDAO should not be null");
        final double balance = springAccountDAO.getCurrentBalance(ACCOUNT_NUMBER);
        assertNotNull(balance,"balance should not be null");
    }

    /**
     * test Deposite amount.
     *
     * @throws Exception exception if fail to deposite.
     */
    @Test
    @Transactional
    public void testDeposite() throws Exception {
        assertNotNull(springAccountDAO,"springAccountDAO should not be null");
        final double balance = springAccountDAO.depositeAmount(ACCOUNT_NUMBER, AMOUNT);
        assertNotEquals(balance, 0,"balance Not equal to 0");
    }

    /**
     * test AccountDetails.
     *
     * @throws Exception exception if account not exits
     */
    @Test
    @Transactional
    public void testGetAccountDetails() throws Exception {
        assertNotNull(springAccountDAO,"springAccountDAO should not be null");
        final Account accountDetail = springAccountDAO.getAccountDetail(ACCOUNT_NUMBER);
        assertNotNull(accountDetail,"accountDetail should not be null");
    }

    /**
     * test WithDraw amount.
     *
     * @throws Exception exception if insufficient balance or wrong accNumber
     */
    @Test
    @Transactional
    public void testWithDrawAmount() throws Exception {
        assertNotNull(springAccountDAO,"springAccountDAO should not be null");
        final double balance = springAccountDAO.withDrawAmount(ACCOUNT_NUMBER, AMOUNT);
        assertNotNull(balance,"balance should not be null");
    }
}
