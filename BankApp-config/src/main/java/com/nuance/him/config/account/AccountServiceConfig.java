/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.config.customer.CustomerServiceConfig;
import com.nuance.him.dao.account.SpringAccountDao;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.account.AccountServiceImpl;
import com.nuance.him.service.customer.CustomerService;

/**
 * AccountServiceConfig class.
 */
@Configuration
@Import({ AccountDaoConfig.class, CustomerServiceConfig.class })
public class AccountServiceConfig {

    @Autowired
    private SpringAccountDao springAccountDAO;
    @Autowired
    private CustomerService customerService;

    /**
     * {@link AccountService}.
     *
     * @return bean {@link AccountServiceImpl}
     */
    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl(springAccountDAO, customerService);
    }
}
