/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.test;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.dao.customer.SpringCustomerDao;
import com.nuance.him.dao.daoexception.CustomerDaoException;
import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.customer.CustomerService;
import com.nuance.him.service.customer.CustomerServiceImpl;
import com.nuance.him.service.exception.CustomerServiceException;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Customer Service Test.
 */
public class CustomerServiceImplTest {

    private static final String NAME = "Yo";
    private static final long PHONE = 9545090850L;
    private static final String ADDRESS = "Pune";
    private static final String CITY = "pune";
    @Mock
    private SpringCustomerDao springCustomerDao;
    @Captor
    private final ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);


    private CustomerService customerService;
    private Customer customer;
    private List<Customer> customers;


    /**
     * initial setup.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(springCustomerDao);
        customer = new Customer(NAME, PHONE, ADDRESS, CITY);
    }

    /**
     * Test AddCustomer.
     *
     * @throws Exception exception
     */
    @Test
    public void testAddCustomer() throws Exception {
        when(springCustomerDao.addCustomer(any(Customer.class))).thenReturn(1);
        final int customerId = customerService.addCustomer(customer);
        assertNotNull(customerId,"customerId should not null");
        verify(springCustomerDao).addCustomer(captor.capture());
        final Customer actual=captor.getValue();
        assertEquals(actual,customer);
    }

    /**
     * Test AddCustomer Exception.
     *
     * @throws Exception {@link CustomerServiceException}
     */
    @Test(expectedExceptions = CustomerServiceException.class)
    public void testAddCustomerException() throws Exception {
        doThrow(CustomerDaoException.class).when(springCustomerDao).addCustomer(any(Customer.class));
        try {
            final int customerId = customerService.addCustomer(customer);
        }
        catch (final CustomerServiceException customerServiceException) {
            assertEquals(customerServiceException.getCause().getClass(), CustomerDaoException.class);
            verify(springCustomerDao).addCustomer(captor.capture());
            throw customerServiceException;
        }
    }

    /**
     * test Display customer.
     *
     * @throws Exception exception
     */
    @Test
    public void testDisplayCustomer() throws Exception {
        when(springCustomerDao.getAllCustomers()).thenReturn(customers);
        assertEquals(customers, customerService.getAllCustomers(), "Actual Different then expected");
        verify(springCustomerDao).getAllCustomers();
    }

    /**
     * Test Exception thrown by method testDisplayCustomerException.
     *
     * @throws Exception {@link CustomerServiceException}
     */
    @Test(expectedExceptions = CustomerServiceException.class)
    public void testDisplayCustomerException() throws Exception {
        doThrow(CustomerDaoException.class).when(springCustomerDao).getAllCustomers();
        try {
            customerService.getAllCustomers();
        }
        catch (final CustomerServiceException customerServiceException ) {
            assertEquals(customerServiceException.getCause().getClass(), CustomerDaoException.class, "Exception mismatch");
            verify(springCustomerDao).getAllCustomers();
            throw customerServiceException;
        }
    }


    /**
     * test Find customer.
     *
     * @throws Exception exception
     */
    @Test
    public void testFindCustomer() throws Exception {
        when(springCustomerDao.findCustomerById(anyInt())).thenReturn(customer);
        assertEquals(customer, customerService.findCustomerById(customer.getId()), "Actual Different then expected");
        verify(springCustomerDao).findCustomerById(anyInt());
    }

    /**
     * Test Exception thrown by method testFindCustomerById.
     *
     * @throws Exception {@link CustomerServiceException}
     */
    @Test(expectedExceptions = CustomerServiceException.class)
    public void testFindCustomerException() throws Exception {
        doThrow(CustomerDaoException.class).when(springCustomerDao).findCustomerById(anyInt());
        try {
            customerService.findCustomerById(customer.getId());
        }
        catch (final CustomerServiceException customerServiceException ) {
            assertEquals(customerServiceException.getCause().getClass(), CustomerDaoException.class, "Exception mismatch");
            verify(springCustomerDao).findCustomerById(anyInt());
            throw customerServiceException;
        }
    }
}
