/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import com.nuance.him.controller.account.AccountController;
import com.nuance.him.service.account.AccountService;

/**
 * Account Config controller class.
 */
@Configuration
@Import(AccountServiceConfig.class)
public class AccountControllerConfig {

    @Autowired
    private AccountService accountService;

    /**
     * bean  property resource for read properties file.
     *
     * @return bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * creating controller bean.
     *
     * @return bean controller
     */
    @Bean
    public AccountController accountController() {
        return new AccountController(accountService);
    }

    /**
     * bean for validation.
     *
     * @return validation bean
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
