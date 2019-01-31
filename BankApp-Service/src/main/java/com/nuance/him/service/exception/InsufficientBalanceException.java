/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.service.exception;

/**
 * Insufficient Balance Exception.
 */
public class InsufficientBalanceException extends Exception {

    /**
     * return message.
     */
    private static String message;

    /**
     * construct for {@link InsufficientBalanceException}.
     *
     * @param insufficientBalance message
     */
    public InsufficientBalanceException(final String insufficientBalance) {
        message = insufficientBalance;
    }

    /**
     * Gets the exception messages.
     */
    @Override
    public String getMessage() {
        return InsufficientBalanceException.message;
    }
}
