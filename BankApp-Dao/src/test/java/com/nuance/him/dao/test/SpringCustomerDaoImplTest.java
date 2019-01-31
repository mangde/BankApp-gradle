/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.customer.SpringCustomerDao;
import com.nuance.him.dao.customer.SpringCustomerDaoImpl;
import com.nuance.him.model.customer.Customer;
import java.util.List;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test class {@link SpringCustomerDaoImplTest}.
 */
@Transactional
@ContextConfiguration(classes = DatabaseConfig.class)
@TestPropertySource("classpath:sql-queries.xml")
public class SpringCustomerDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String INSERT_CUSTOMER = "addCustomer";
    private static final String INSERT_CUSTOMER_ADDRESS = "addAddress";
    private static final String SELECT_ALL_CUSTOMER = "selectAllCustomers";
    private static final String GET_CUSTOMER_BY_ID = "getCustomerById";
    private static final String NAME = "yogi";
    private static final long PHONE = 9545090850L;
    private static final String ADDRESS = "Mumbai";
    private static final String CITY = "Dadar";
    @Value("${" + INSERT_CUSTOMER + "}")
    private String insertCustomer;
    @Value("${" + INSERT_CUSTOMER_ADDRESS + "}")
    private String insertAddress;
    @Value("${" + SELECT_ALL_CUSTOMER + "}")
    private String getSelectAll;
    @Value("${" + GET_CUSTOMER_BY_ID + "}")
    private String getGetCustomerById;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SpringCustomerDao springCustomerDao;
    private Customer customer;

    /**
     * Initial setup.
     */
    @BeforeMethod
    public void setUp() {
        springCustomerDao = new SpringCustomerDaoImpl(namedParameterJdbcTemplate, insertCustomer, insertAddress, getSelectAll, getGetCustomerById);
        customer = new Customer(NAME, PHONE, ADDRESS, CITY);
        customer.setId(55);
    }

    /**
     * testAddCustomer.
     *
     * @throws Exception exception
     */
    @Test
    @Transactional
    public void testAddCustomer() throws Exception {
        assertNotNull(springCustomerDao,"springCustomerDao should not null");
        final int customerId = springCustomerDao.addCustomer(customer);
        assertNotEquals(customerId, 0,"customerId should not equal to 0");
    }

    /**
     * test DisplayCustomer.
     *
     * @throws Exception exception
     */
    @Test
    @Transactional
    public void testDisplayCustomer() throws Exception {
        assertNotNull(springCustomerDao,"springCustomerDao should not null");
        final List<Customer> customers = springCustomerDao.getAllCustomers();
        assertNotNull(customers, "customer list Should Not null");
    }

    /**
     * test findCustomerById.
     *
     * @throws Exception exception if Not Available
     */
    @Test
    @Transactional
    public void testGetCustomerById() throws Exception {
        assertNotNull(springCustomerDao,"springCustomerDao should not null");
        final Customer customerDetail = springCustomerDao.findCustomerById(customer.getId());
        assertNotNull(customerDetail, "customer  Should Not null");
    }
}
