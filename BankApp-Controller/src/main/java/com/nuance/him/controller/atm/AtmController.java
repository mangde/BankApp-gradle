/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.controller.atm;

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
import com.nuance.him.dao.daoexception.AccountDaoException;
import com.nuance.him.model.atm.AtmDetail;
import com.nuance.him.service.atm.AtmService;
import com.nuance.him.service.exception.AtmServiceException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Atm controller class.
 */
@RestController
@RequestMapping("${baseURL}")
public class AtmController {

    private final AtmService atmService;
    private static final String ISSUE_ATM = "/issueAtm";
    private static final String DISPLAY_ALL_ATM_DETAIL = "/displayAllAtmDetail";
    private static final String CHECK_IS_ALREADY_ATM_TAKEN = "/isAtmAlreadyTaken";

    /**
     * initialize atmService.
     *
     * @param atmService instance of class {@link AtmService}
     */
    public AtmController(final AtmService atmService) {
        this.atmService = atmService;
    }

    /**
     * @param atmDetail instance of {@link AtmDetail}.
     * @return newly issue atm details.
     */
    @RequestMapping(value = ISSUE_ATM, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<AtmDetail> addNewAtm(@RequestBody final AtmDetail atmDetail) {
        try {
            final AtmDetail issueAtmCard = atmService.issueAtmCard(atmDetail);
            return new ResponseEntity("Issue Atm successfully\n customer Atm details: " + issueAtmCard, HttpStatus.CREATED);
        }
        catch (final AtmServiceException atmServiceException) {
            if (atmServiceException.getCause().getCause().getClass().equals(AccountDaoException.class)) {
                return new ResponseEntity(new CustomErrorType("Exception From  Invalid AccountNumber " + atmDetail.getAccNumber()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity(new CustomErrorType("Not issue atm" + atmServiceException.getCause().getCause()), HttpStatus.EXPECTATION_FAILED);
        }
    }

    /**
     * Displays all Atm Details.
     *
     * @return all Atm details issued to customer.
     */
    @RequestMapping(value = DISPLAY_ALL_ATM_DETAIL, method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<AtmDetail> displayAllAtm() {
        final List<AtmDetail> atmDetails;
        try {
            atmDetails = atmService.displayAllAtmDetail();
        }
        catch (final AtmServiceException atmServiceException) {
            return new ResponseEntity(atmServiceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(atmDetails, HttpStatus.OK);
    }

    /**
     * check is Already Atm Taken or not.
     *
     * @param accNumber customerId for search
     * @return customer Details
     */
    @RequestMapping(value = CHECK_IS_ALREADY_ATM_TAKEN, method = RequestMethod.GET,produces = "application/json")
    @ResponseBody
    public ResponseEntity<AtmDetail> checkAtmIssue(@RequestParam("accNumber") final int accNumber) {
        final AtmDetail atmDetail;
        try {
            atmDetail = atmService.isAlreadyAtmTaken(accNumber);
        }
        catch (final AtmServiceException atmServiceException) {
            return new ResponseEntity(new CustomErrorType("customer with AccountNumber " + accNumber + " not Issue Atm " ), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity("Atm Already Taken Details:" + atmDetail, HttpStatus.OK);
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
