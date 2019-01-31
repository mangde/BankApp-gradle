/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.transfer;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.nuance.him.dao.daoexception.TransferDaoException;
import com.nuance.him.dao.transfer.SpringTransferDao;
import com.nuance.him.model.transfer.TransferAmount;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.exception.AccountServiceException;
import com.nuance.him.service.exception.TransferAmountServiceException;
import java.util.List;

/**
 * {@link TransferAmountService}.
 */
public class TransferAmountServiceImpl implements TransferAmountService {

    /**
     * instance of {@link SpringTransferDao}.
     */
    private final SpringTransferDao springTransferDAO;
    private final AccountService accountService;

    /**
     * Constructor of {@link TransferAmountServiceImpl}.
     *
     * @param springTransferDAO instance of {@link SpringTransferDao}
     * @param accountService instance of  {@link AccountService}
     */
    public TransferAmountServiceImpl(final SpringTransferDao springTransferDAO, final AccountService accountService) {
        this.springTransferDAO = springTransferDAO;
        this.accountService = accountService;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = false, timeout = 100, rollbackFor = Exception.class)
    @Override
    public int transferAmount(final TransferAmount transferAmount) throws TransferAmountServiceException {
        try {
            int transactionId = 0;
            final int debitAc = transferAmount.getAccIdFrom();
            final int creditAc = transferAmount.getAccIdTo();
            if (debitAc == creditAc) {
                throw new RuntimeException("Impossible operation: account id must be different");
            }
            else {
                accountService.getAccountDetail(creditAc);
                accountService.withDrawAmount(debitAc, transferAmount.getAmount());
                accountService.depositeAmount(creditAc, transferAmount.getAmount());
                transactionId = springTransferDAO.transferAmount(transferAmount);
            }
            return transactionId;
        }
        catch (final RuntimeException runtimeException) {
            throw new TransferAmountServiceException(" AccountServiceException in transferAmount ", runtimeException);
        }
        catch (final AccountServiceException accountServiceException) {
            throw new TransferAmountServiceException(" AccountServiceException in transferAmount ", accountServiceException);
        }
        catch (final TransferDaoException transferDaoException) {
            throw new TransferAmountServiceException("serviceException in transferAmount", transferDaoException);
        }
    }

    @Override
    public List<TransferAmount> getTransactionHistory(final int accountNumber) throws TransferAmountServiceException {
        try {
            return springTransferDAO.getTransactionHistory(accountNumber);
        }
        catch (final TransferDaoException transferDaoException) {
            throw new TransferAmountServiceException("serviceException in transaction", transferDaoException);
        }
    }
}

