/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nuance.him.config.transfer.TransferAmountDaoConfig;
import com.nuance.him.dao.transfer.SpringTransferDao;

/**
 * {@link SpringTransferDaoConfigTest}.
 */
@ContextConfiguration(classes = TransferAmountDaoConfig.class)
public class SpringTransferDaoConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SpringTransferDao springTransferDAO;

    /**
     * for bean class of transactionDao.
     */
    @Test
    public void testTransactionDao() {
        Assert.assertNotNull(springTransferDAO);
    }
}
