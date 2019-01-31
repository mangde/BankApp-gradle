/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.config.account.AccountControllerConfig;
import com.nuance.him.controller.account.AccountController;
import static org.testng.Assert.assertNotNull;

/**
 * Test Account Controller bean class.
 */
@ContextConfiguration(classes = AccountControllerConfig.class)
public class AccountControllerConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AccountController accountController;

    /**
     * customer AccountController bean creation.
     */
    @Test
    public void testAccountDAO() {
        assertNotNull(accountController);
    }
}
