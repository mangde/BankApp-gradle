/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.customer;

import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.exception.CustomerServiceException;
import java.util.List;

/**
 * CustomerService interface.
 */
public interface CustomerService {

    /**
     * To add the customer model to table.
     *
     * @param customer object of the Customer class
     * @return int 1 if the customer model is added else 0
     * @throws CustomerServiceException exception thrown by the jdbc Template update method
     */
    int addCustomer(Customer customer) throws CustomerServiceException;

    /**
     * To get all the customers.
     *
     * @return List list of the all customers
     * @throws CustomerServiceException exception
     */
    List<Customer> getAllCustomers() throws CustomerServiceException;

    /**
     * GetCustomer from customerId.
     * @param customerId customer id
     * @return instance of {@link Customer}
     * @throws CustomerServiceException exception
     */
    Customer findCustomerById(int customerId) throws CustomerServiceException;

    /**
     * Add address of customer.
     * @param customer instance of class Customer
     * @return 1 if success
     * @throws CustomerServiceException exception
     */
    int addAddress(Customer customer)throws CustomerServiceException;
}
