/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import com.nuance.him.controller.customer.CustomerController;
import com.nuance.him.service.customer.CustomerService;

/**
 * CustomerController configuration.
 */
@Configuration
@Import(CustomerServiceConfig.class)
public class CustomerControllerConfig {

    @Autowired
    private CustomerService customerService;

    /**
     * creating controller bean.
     *
     * @return bean controller
     */
    @Bean
    public CustomerController customerController() {
        return new CustomerController(customerService);
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

    /**
     * bean  property resource.
     *
     * @return bean
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}

