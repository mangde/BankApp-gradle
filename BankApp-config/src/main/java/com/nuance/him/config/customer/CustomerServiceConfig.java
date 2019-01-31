/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.dao.customer.SpringCustomerDao;
import com.nuance.him.service.customer.CustomerService;
import com.nuance.him.service.customer.CustomerServiceImpl;

/**
 * Configuration for CustomerServices.
 */
@Configuration
@Import(CustomerDaoConfig.class)
public class CustomerServiceConfig {

    @Autowired
    private SpringCustomerDao springCustomerDao;

    /**
     * Factory for CustomerServices.
     *
     * @return CustomerServicesImpl object
     */
    @Bean
    public CustomerService customerService() {
        return new CustomerServiceImpl(springCustomerDao);
    }
}
