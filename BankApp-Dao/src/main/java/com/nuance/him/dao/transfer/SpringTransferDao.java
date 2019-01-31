/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.transfer;

import com.nuance.him.dao.daoexception.TransferDaoException;
import com.nuance.him.model.transfer.TransferAmount;
import java.util.List;

/**
 * SpringTransferDao interface.
 */
public interface SpringTransferDao {

    /**
     * transfer amount to self or other account.
     *
     * @param transferAmount instance of class {@link TransferAmount} hold values
     * @return success or failure message
     * @throws TransferDaoException daoException
     */
    int transferAmount(TransferAmount transferAmount) throws TransferDaoException;

    /**
     * TransferAmount history.
     *
     * @param accountNumber accNo
     * @return list of transaction record
     * @throws TransferDaoException daoException
     */
    List<TransferAmount> getTransactionHistory(int accountNumber) throws TransferDaoException;
}
