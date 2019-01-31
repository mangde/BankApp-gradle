/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.customer;

import com.nuance.him.dao.daoexception.CustomerDaoException;
import com.nuance.him.model.customer.Customer;
import java.util.List;

/**
 * {@link SpringCustomerDao} interface.
 */
public interface SpringCustomerDao {

    /**
     * To add the customer model to table.
     *
     * @param customer object of the Customer class
     * @return int 1 if the customer model is added else 0
     * @throws CustomerDaoException exception thrown by the JdbcTemplate update method
     */
    int addCustomer(Customer customer) throws CustomerDaoException;

    /**
     * get all customer.
     *
     * @return list of customers
     * @throws CustomerDaoException exception
     */
    List<Customer> getAllCustomers() throws CustomerDaoException;

    /**
     * GetCustomer from customerId.
     * @param customerId customer id
     * @return instance of {@link Customer}
     * @throws CustomerDaoException exception
     */
    Customer findCustomerById(int customerId) throws CustomerDaoException;

    /**
     * Add Address.
     * @param customer customer instance
     * @return 1integer
     * @throws CustomerDaoException exception
     */
    int addAddress(Customer customer)throws CustomerDaoException;
}
