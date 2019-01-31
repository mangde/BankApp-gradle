/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.transfer;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.transfer.SpringTransferDao;
import com.nuance.him.dao.transfer.SpringTransferDaoImpl;
import static org.springframework.util.Assert.notNull;

/**
 * {@link TransferAmountDaoConfig}.
 */
@Configuration
@Import(DatabaseConfig.class)
@PropertySource("classpath:sql-queries.xml")
public class TransferAmountDaoConfig {

    private static final String TRANSFER_AMOUNT = "transfer";
    private static final String TRANSACTION_HISTORY = "transactionHistory";
    @Value("${" + TRANSFER_AMOUNT + "}")
    private String getTransferAmount;
    @Value("${" + TRANSACTION_HISTORY + "}")
    private String getTransactionHistory;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Validates namedParameterJdbcTemplate and database queries.
     */
    @PostConstruct
    public void postConstruct() {
        notNull(getTransferAmount, "missing getTransferAmount query");
        notNull(getTransactionHistory, "missing getTransactionHistory query");
    }

    /**
     * bean for transactionDAO.
     *
     * @return TransactionDAOImpl
     */
    @Bean
    public SpringTransferDao transactionDAO() {
        return new SpringTransferDaoImpl(namedParameterJdbcTemplate, getTransferAmount, getTransactionHistory);
    }
}
