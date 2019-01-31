/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.controller.transfer;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.nuance.him.controller.exception.CustomErrorType;
import com.nuance.him.model.transfer.TransferAmount;
import com.nuance.him.service.exception.TransferAmountServiceException;
import com.nuance.him.service.transfer.TransferAmountService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TransferAmountController class.
 */
@RestController
@RequestMapping("${baseURL}")
public class TransferAmountController {

    private static final String TRANSFER_AMOUNT = "/transferAmount";
    private static final String TRANSACTION_HISTORY = "/transactionHistory";
    private final TransferAmountService transferAmountService;

    /**
     * constructor of class {@link TransferAmountController}.
     *
     * @param transferAmountService instance of class {@link TransferAmountService}
     */
    public TransferAmountController(final TransferAmountService transferAmountService) {
        this.transferAmountService = transferAmountService;
    }

    /**
     * Request method for transfer Amount form one account to another.
     *
     * @param transferAmount instance of class {@link TransferAmount}
     * @return transactionId
     */
    @RequestMapping(value = TRANSFER_AMOUNT, method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<TransferAmount> transferAmountApi(@Valid @RequestBody final TransferAmount transferAmount) {
        try {
            final int transactionId = transferAmountService.transferAmount(transferAmount);
            return new ResponseEntity("TransferAmount successfully  TransactionId: " + transactionId, HttpStatus.CREATED);
        }
        catch (final TransferAmountServiceException transferAmountServiceException) {
            return new ResponseEntity(new CustomErrorType(" transfer Amount failed " + transferAmountServiceException.getCause().getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * TransferAmount History Method.
     *
     * @param accNumber accountNumber
     * @return List of transactions
     */
    @RequestMapping(value = TRANSACTION_HISTORY, method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<TransferAmount> transactionHistory(@RequestParam(name = "accNumber") final int accNumber) {
        try {
            final List<TransferAmount> transferAmounts = transferAmountService.getTransactionHistory(accNumber);
            return new ResponseEntity(transferAmounts, HttpStatus.OK);
        }
        catch (final TransferAmountServiceException transferAmountServiceException) {
            return new ResponseEntity(new CustomErrorType("getting transfer history failed " + transferAmountServiceException.getCause().getMessage()), HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Handle Validation Exception.
     *
     * @param exception exception
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
