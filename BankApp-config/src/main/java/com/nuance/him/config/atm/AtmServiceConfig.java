/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.config.account.AccountServiceConfig;
import com.nuance.him.dao.atm.SpringAtmDao;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.account.AccountServiceImpl;
import com.nuance.him.service.atm.AtmService;
import com.nuance.him.service.atm.AtmServiceImpl;

/**
 * AccountServiceConfig class.
 */
@Configuration
@Import({AtmDaoConfig.class, AccountServiceConfig.class})
public class AtmServiceConfig {

    @Autowired
    private SpringAtmDao springAtmDao;
    @Autowired
    private AccountService accountService;

    /**
     * {@link AccountService}.
     *
     * @return bean {@link AccountServiceImpl}
     */
    @Bean
    public AtmService atmService() {
        return new AtmServiceImpl(springAtmDao,accountService);
    }
}
