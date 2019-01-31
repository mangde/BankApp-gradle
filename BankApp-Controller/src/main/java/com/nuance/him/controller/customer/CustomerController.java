/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.controller.customer;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nuance.him.controller.exception.CustomErrorType;
import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.customer.CustomerService;
import com.nuance.him.service.exception.CustomerServiceException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CustomerController.
 */
@RestController
@RequestMapping("${baseURL}")
public class CustomerController {

    private static final String ADD_CUSTOMER = "/addCustomer";
    private static final String DISPLAY_CUSTOMERS = "/displayCustomer";
    private final CustomerService customerService;
    private static final String GET_CUSTOMER_BY_ID = "/getCustomerById";

    /**
     * constructor for assign bean of {@link CustomerService}.
     *
     * @param customerService instance of {@link CustomerService}
     */
    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Request Method to add New Customer.
     *
     * @param customer instance of class {@link Customer}
     * @return new generated CustomerId
     */
    @RequestMapping(value = ADD_CUSTOMER, method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public ResponseEntity addNewCustomer(@RequestBody final Customer customer) {
        try {
            final int customerId = customerService.addCustomer(customer);
            return new ResponseEntity("Customer is registered successfully\n" + " customerID: " + customerId, HttpStatus.CREATED);
        }
        catch (final CustomerServiceException customerServiceException) {
            return new ResponseEntity(new CustomErrorType("Error in  Add customer"), HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Displays all customers.
     *
     * @return all customer details
     */
    @RequestMapping(value = DISPLAY_CUSTOMERS, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Customer> displayCustomers() {
        final List<Customer> customerList;
        try {
            customerList = customerService.getAllCustomers();
        }
        catch (final CustomerServiceException customerServiceException) {
            return new ResponseEntity(customerServiceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(customerList, HttpStatus.OK);
    }

    /**
     * get CustomerById.
     *
     * @param customerId customerId for search
     * @return customer Details
     */
    @RequestMapping(value = GET_CUSTOMER_BY_ID, method = RequestMethod.GET)
    public ResponseEntity<Object> getCustomerById(@RequestParam("customerId") final int customerId) {
        final Customer customer;
        try {
            customer = customerService.findCustomerById(customerId);
        }
        catch (final CustomerServiceException customerServiceException) {
            return new ResponseEntity(new CustomErrorType("customer with id " + customerId + " not found " + customerServiceException.getCause().getMessage()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customer, HttpStatus.OK);
    }

    /**
     * exception validation handler.
     *
     * @param exception exception
     * @return message
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handle(final ConstraintViolationException exception) {
        String message = "";
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        for (final ConstraintViolation<?> violation : violations) {
            message += violation.getMessage().concat(";");
        }
        return message;
    }

    /**
     * @param ex instance of exception class
     * @return exception message
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<String> handleValidationExceptions(final MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());
    }
}
