/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nuance.him.config.customer.CustomerServiceConfig;
import com.nuance.him.service.customer.CustomerService;

/**
 * Test class for CustomerServiceConfig.
 */
@ContextConfiguration(classes = CustomerServiceConfig.class)
public class CustomerServicesConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CustomerService customerServices;

    /**
     * Tests the customerServices bean.
     */
    @Test
    public void testCustomerServices() {
        Assert.assertNotNull(customerServices);
    }
}
