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
import com.nuance.him.config.account.AccountServiceConfig;
import com.nuance.him.service.account.AccountService;

/**
 * customer class of AccountService bean.
 */
@ContextConfiguration(classes = AccountServiceConfig.class)
public class AccountServiceConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountService accountService;

    /**
     * Tests the accountService bean.
     */
    @Test
    public void testCustomerServices() {
        Assert.assertNotNull(accountService);
    }
}
