/*
 *
 *  * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 *  *
 *
 */
package com.nuance.him.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.transfer.SpringTransferDao;
import com.nuance.him.dao.transfer.SpringTransferDaoImpl;
import com.nuance.him.model.transfer.TransferAmount;
import java.util.List;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * {@link SpringTransferDaoImplTest}.
 */
@TestPropertySource("classpath:sql-queries.xml")
@ContextConfiguration(classes =DatabaseConfig.class)
@Transactional
public class SpringTransferDaoImplTest extends AbstractTestNGSpringContextTests {

    private static final String TRANSFER_AMOUNT = "transfer";
    private static final String TRANSACTION_HISTORY = "transactionHistory";
    private static final double AMOUNT = 500.00;
    private static final int FROM_ACC = 1;
    private static final int TO_ACC = 3;
    private static final String DESCRIPTION = "Loan";
    @Value("${" + TRANSFER_AMOUNT + "}")
    private String getTransferAmount;
    @Value("${" + TRANSACTION_HISTORY + "}")
    private String getTransactionHistory;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private TransferAmount transferAmount;
    private SpringTransferDao springTransferDAO;

    /**
     * initial setup for test.
     */
    @BeforeMethod
    public void setUp() {
        springTransferDAO = new SpringTransferDaoImpl(namedParameterJdbcTemplate, getTransferAmount, getTransactionHistory);
        transferAmount = new TransferAmount(FROM_ACC, TO_ACC, AMOUNT, DESCRIPTION);
    }

    /**
     * test TransferAmount.
     *
     * @throws Exception exception
     */
    @Test
    @Transactional
    public void testTransferAmount() throws Exception {
        assertNotNull(springTransferDAO,"SpringTransferDao should not be null");
        final int transactionId = springTransferDAO.transferAmount(transferAmount);
        assertNotEquals(transactionId, 0,"transactionId should not equal to 0");
    }

    /**
     * test TransferAmount statement.
     *
     * @throws Exception exception
     */
    @Test
    @Transactional
    public void testTransactionHistory() throws Exception {
        assertNotNull(springTransferDAO,"SpringTransferDao should not be null");
        final List<TransferAmount> transferAmounts = springTransferDAO.getTransactionHistory(FROM_ACC);
        assertNotNull(transferAmounts, "transferAmounts should not null");
    }
}
