/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nuance.him.config.account.AccountControllerConfig;
import com.nuance.him.config.atm.AtmServiceConfig;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.atm.AtmService;
import static org.testng.Assert.assertNotNull;

/**
 * {@link AtmServiceConfigTest}.
 */
@ContextConfiguration(classes = {AtmServiceConfig.class, AccountControllerConfig.class})
public class AtmServiceConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AtmService atmService;
    @Autowired
    private AccountService accountService;


    /**
     * Tests the AtmService bean.
     */
    @Test
    public void testAtmService() {
        Assert.assertNotNull(atmService);        assertNotNull(accountService);

    }
}
