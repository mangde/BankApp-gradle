/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.customer;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.customer.SpringCustomerDao;
import com.nuance.him.dao.customer.SpringCustomerDaoImpl;
import static org.springframework.util.Assert.notNull;

/**
 * SpringCustomerDao configuration.
 */
@Configuration
@Import(DatabaseConfig.class)
@PropertySource("classpath:sql-queries.xml")
public class CustomerDaoConfig {

    private static final String INSERT_CUSTOMER = "addCustomer";
    private static final String INSERT_CUSTOMER_ADDRESS = "addAddress";
    private static final String SELECT_ALL_CUSTOMERS = "selectAllCustomers";
    private static final String GET_CUSTOMER_BY_ID="getCustomerById";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Value("${" + INSERT_CUSTOMER + "}")
    private String insertCustomer;
    @Value("${" + INSERT_CUSTOMER_ADDRESS + "}")
    private String insertAddress;
    @Value("${" + SELECT_ALL_CUSTOMERS + "}")
    private String selectAllCustomers;
    @Value("${" + GET_CUSTOMER_BY_ID + "}")
    private String getGetCustomerById;

    /**
     * Validates jdbcTemplate and database queries.
     */
    @PostConstruct
    public void postConstruct() {
        notNull(INSERT_CUSTOMER, "null value : INSERT_CUSTOMER");
        notNull(INSERT_CUSTOMER_ADDRESS, "null value : INSERT_CUSTOMER_ADDRESS");
        notNull(SELECT_ALL_CUSTOMERS, "null value : SELECT_ALL_CUSTOMERS");
        notNull(GET_CUSTOMER_BY_ID,"missing bean : GET_CUSTOMER_BY_ID");
    }

    /**
     * Factory for SpringCustomerDao.
     *
     * @return SpringCustomerDaoImpl object
     */
    @Bean
    public SpringCustomerDao customerDao() {
        return new SpringCustomerDaoImpl(namedParameterJdbcTemplate, insertCustomer, insertAddress, selectAllCustomers,getGetCustomerById);
    }
}
