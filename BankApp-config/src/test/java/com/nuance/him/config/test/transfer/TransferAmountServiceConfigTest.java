/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.transfer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.config.account.AccountControllerConfig;
import com.nuance.him.config.transfer.TransferAmountServiceConfig;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.transfer.TransferAmountService;
import static org.testng.Assert.assertNotNull;

/**
 * {@link TransferAmountServiceConfigTest}.
 */
@ContextConfiguration(classes ={ TransferAmountServiceConfig.class, AccountControllerConfig.class })
public class TransferAmountServiceConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TransferAmountService transferAmountService;
    @Autowired
    private AccountService accountService;

    /**
     * Tests the transferAmountService bean.
     */
    @Test
    public void testTransactionServices() {assertNotNull(transferAmountService);        assertNotNull(accountService);

    }
}
