/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.nuance.him.config.account.AccountControllerConfig;
import com.nuance.him.config.customer.CustomerControllerConfig;
import com.nuance.him.config.transfer.TransferAmountControllerConfig;

/**
 * Start point for application.
 */
@Import(BankAppRunner.Config.class)
@SpringBootApplication
public class BankAppRunner {

    /**
     * @param args method arguments String array.
     */
    public static void main(final String[] args) {
        SpringApplication.run(BankAppRunner.class, args);
    }

    /**
     * Aggregates all application Spring config into one.
     */
    @Configuration
    @Import({ CustomerControllerConfig.class, AccountControllerConfig.class, TransferAmountControllerConfig.class })
    public static class Config {}
}
