/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nuance.him.config.account.AccountDaoConfig;
import com.nuance.him.dao.account.SpringAccountDao;


/**
 * Test AccountDaoBean Class.
 */
@ContextConfiguration(classes = AccountDaoConfig.class)
public class SpringAccountDaoConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SpringAccountDao springAccountDAO;

    /**
     * customer  bean class of accountDao.
     */
    @Test
    public void testAccountDao() {
        Assert.assertNotNull(springAccountDAO);
    }
}
