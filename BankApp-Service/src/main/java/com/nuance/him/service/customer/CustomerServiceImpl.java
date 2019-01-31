/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.customer;

import com.nuance.him.dao.customer.SpringCustomerDao;
import com.nuance.him.dao.daoexception.CustomerDaoException;
import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.exception.CustomerServiceException;
import java.util.List;

/**
 * implements {@link CustomerService}.
 */
public class CustomerServiceImpl implements CustomerService {

    private final SpringCustomerDao springCustomerDao;

    /**
     * constructor of {@link CustomerServiceImpl}.
     *
     * @param springCustomerDao instance of SpringCustomerDao.
     */
    public CustomerServiceImpl(final SpringCustomerDao springCustomerDao) {
        this.springCustomerDao = springCustomerDao;
    }

    @Override
    public int addCustomer(final Customer customer) throws CustomerServiceException {
        try {
            final int customerId = springCustomerDao.addCustomer(customer);
            customer.setId(customerId);
            addAddress(customer);
            return customerId;
        }
        catch (final CustomerDaoException customerDaoException) {
            throw new CustomerServiceException(customerDaoException.getMessage(), customerDaoException);
        }
    }

    @Override
    public List<Customer> getAllCustomers() throws CustomerServiceException {
        try {
            return springCustomerDao.getAllCustomers();
        }
        catch (final CustomerDaoException customerDaoException) {
            throw new CustomerServiceException(customerDaoException.getMessage(), customerDaoException);
        }
    }

    @Override
    public Customer findCustomerById(final int customerId) throws CustomerServiceException {
        try {
            return springCustomerDao.findCustomerById(customerId);
        }
        catch (final CustomerDaoException customerDaoException) {
            throw new CustomerServiceException("Customer Not Found", customerDaoException);
        }
    }

    @Override
    public int addAddress(final Customer customer) throws CustomerServiceException {
        try {
            return springCustomerDao.addAddress(customer);
        }
        catch (final CustomerDaoException customerDaoException) {
            throw new CustomerServiceException("Customer Address Not register", customerDaoException);
        }
    }
}
