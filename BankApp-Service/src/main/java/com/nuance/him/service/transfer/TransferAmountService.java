/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.transfer;

import com.nuance.him.model.transfer.TransferAmount;
import com.nuance.him.service.exception.TransferAmountServiceException;
import java.util.List;

/**
 * {@link TransferAmountService}.
 */
public interface TransferAmountService {

    /**
     * transfer amount to self or other account.
     *
     * @param transferAmount instance of class {@link TransferAmount} hold values
     * @return success or failure message
     * @throws TransferAmountServiceException serviceException
     */
    int transferAmount(TransferAmount transferAmount) throws TransferAmountServiceException;

    /**
     * Get TransferAmount detail of account.
     *
     * @param accountNumber accountId
     * @return List of  transaction History
     * @throws TransferAmountServiceException serviceException
     */
    List<TransferAmount> getTransactionHistory(int accountNumber) throws TransferAmountServiceException;
}
