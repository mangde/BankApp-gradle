/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.controller.account;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
import com.nuance.him.dao.daoexception.CustomerDaoException;
import com.nuance.him.model.account.Account;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.exception.AccountServiceException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;



/**
 * Account controller.
 */
@RestController
@RequestMapping("${baseURL}")
public class AccountController {

    private static final String OPEN_ACCOUNT = "/addAccount";
    private static final String GET_BALANCE = "/getBalance";
    private static final String DEPOSITE_AMOUNT = "/deposite";
    private static final String ACCOUNT_DETAILS = "/accountDetail";
    private static final String WITHDRAW_AMOUNT = "/withDraw";
    private final AccountService accountService;

    /**
     * Constructor for initialize accountService.
     *
     * @param accountService instance of {@link AccountService}
     */
    public AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * @param account instance of {@link Account}.
     * @return newly generated accountId.
     */
    @RequestMapping(value = OPEN_ACCOUNT, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Account> addNewAccount(@Valid @RequestBody final Account account) {
        try {
            final int accountId = accountService.addAccount(account);
            return new ResponseEntity("Customer Account  is Open successfully\n customer Account ID: " + accountId, HttpStatus.CREATED);
        }
        catch (final AccountServiceException accountServiceException) {
            if (accountServiceException.getCause().getCause().getClass().equals(CustomerDaoException.class)) {
                return new ResponseEntity(new CustomErrorType("Exception From  Invalid CustomerID " + account.getCustomerId()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new CustomErrorType("Error in  Add customer Account " + accountServiceException.getCause().getCause()), HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Request Method For check current balance.
     *
     * @param accNumber account Number
     * @return Current Available Balance
     */
    @RequestMapping(value = GET_BALANCE, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Double> getCurrentBalance(@RequestParam("accNumber") final int accNumber) {
        try {
            final double currentBalance = accountService.getCurrentBalance(accNumber);
            return new ResponseEntity("Current Available Balance is : " + currentBalance, HttpStatus.OK);
        }
        catch (final AccountServiceException accountServiceException) {
            return new ResponseEntity(new CustomErrorType("current balance exception account Id Not found " + accountServiceException.getCause().getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Request Method for Deposite Amount.
     *
     * @param accNumber account Number to be credited
     * @param amount Amount
     * @return new Updated Balance
     */
    @RequestMapping(value = DEPOSITE_AMOUNT, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Account> deposite(@RequestParam("accNumber") final int accNumber, @RequestParam("amount") final double amount) {
        try {
            final double newBalance = accountService.depositeAmount(accNumber, amount);
            return new ResponseEntity("Amount deposited successfully \n Current Available Balance is : " + newBalance, HttpStatus.OK);
        }
        catch (final AccountServiceException accountServiceException) {
            return new ResponseEntity(new CustomErrorType("Failed to Deposite balance " + accountServiceException.getCause().getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * Check Account Details.
     *
     * @param accNumber Account Number
     * @return Account Details
     */
    @RequestMapping(value = ACCOUNT_DETAILS, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Account> getAccountDetails(@RequestParam("accNumber") final int accNumber) {
        try {
            final Account account = accountService.getAccountDetail(accNumber);
            return new ResponseEntity("Account Details : " + account, HttpStatus.OK);
        }
        catch (final AccountServiceException accountServiceException) {
            return new ResponseEntity(new CustomErrorType("Account Not Found" + accountServiceException.getCause().getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Request Method for withDraw Amount.
     *
     * @param accNumber account Number to be debited
     * @param amount Amount
     * @return new Updated Balance
     */
    @RequestMapping(value = WITHDRAW_AMOUNT, method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<Double> withDrawAmount(@RequestParam("accNumber") final int accNumber, @RequestParam("amount") final double amount) {
        try {
            final double currentAvailableBalance = accountService.withDrawAmount(accNumber, amount);
            return new ResponseEntity("Amount withDraw successfully " + amount + " \n Current Available Balance is : " + currentAvailableBalance, HttpStatus.OK);
        }
        catch (final AccountServiceException accountServiceException) {
            return new ResponseEntity(new CustomErrorType("Failed to Withdraw balance " + accountServiceException.getCause().getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * handle validation.
     *
     * @param exception constraint violation exception
     * @return exception message
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

