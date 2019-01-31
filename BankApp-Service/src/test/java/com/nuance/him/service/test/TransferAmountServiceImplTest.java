/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.service.test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.dao.daoexception.TransferDaoException;
import com.nuance.him.dao.transfer.SpringTransferDao;
import com.nuance.him.model.transfer.TransferAmount;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.exception.TransferAmountServiceException;
import com.nuance.him.service.transfer.TransferAmountService;
import com.nuance.him.service.transfer.TransferAmountServiceImpl;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/***
 * {@link TransferAmountService} Test class implementation.
 */
public class TransferAmountServiceImplTest {

    private static final double AMOUNT = 500.00;
    private static final int FROM_ACC = 1;
    private static final int TO_ACC = 2;
    private static final String DESCRIPTION = "Loan";
    @Mock
    private SpringTransferDao springTransferDAO;
    @Mock
    private AccountService accountService;
    private TransferAmountService transactionService;
    private TransferAmount transferAmount;

    /**
     * initial setup.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransferAmountServiceImpl(springTransferDAO, accountService);
        transferAmount = new TransferAmount(FROM_ACC, TO_ACC, AMOUNT, DESCRIPTION);
    }

    /**
     * test TransferAmount.
     *
     * @throws Exception exception {@link TransferAmountServiceException}
     */
    @Test
    public void testTransferAmount() throws Exception {
        when(springTransferDAO.transferAmount(any(TransferAmount.class))).thenReturn(1);
        final int result = transactionService.transferAmount(transferAmount);
        assertNotNull(result,"result must be Not null");
        verify(springTransferDAO).transferAmount(transferAmount);
    }

    /**
     * check exception of method transferAmount.
     *
     * @throws Exception exception {@link TransferAmountServiceException}
     */
    @Test(expectedExceptions = TransferAmountServiceException.class)
    public void testTransferAmountException() throws Exception {
        doThrow(TransferDaoException.class).when(springTransferDAO).transferAmount(any(TransferAmount.class));
        try {
            final int result = transactionService.transferAmount(transferAmount);
        }
        catch (final TransferAmountServiceException transferAmountServiceException) {
            assertEquals(transferAmountServiceException.getCause().getClass(), TransferDaoException.class);
            verify(springTransferDAO).transferAmount(any(TransferAmount.class));
            throw transferAmountServiceException;
        }
    }

    /**
     * Test TransferAmount History of account.
     *
     * @throws Exception exception
     */
    @Test
    public void testTransactionHistory() throws Exception {
        when(springTransferDAO.getTransactionHistory(anyInt())).thenReturn(anyListOf(TransferAmount.class));
        final List<TransferAmount> transferAmounts = transactionService.getTransactionHistory(FROM_ACC);
        assertNotNull(transferAmounts,"transferAmount should not be null");
        verify(springTransferDAO).getTransactionHistory(FROM_ACC);
    }

    /**
     * test exception thrown by method transactionHistory.
     *
     * @throws Exception exception {@link TransferAmountServiceException}
     */
    @Test(expectedExceptions = TransferAmountServiceException.class)
    public void testTransactionHistoryException() throws Exception {
        doThrow(TransferDaoException.class).when(springTransferDAO).getTransactionHistory(anyInt());
        try {
            final List<TransferAmount> transferAmounts = transactionService.getTransactionHistory(FROM_ACC);
        }
        catch (final TransferAmountServiceException transferAmountServiceException) {
            assertEquals(transferAmountServiceException.getCause().getClass(), TransferDaoException.class);
            verify(springTransferDAO).getTransactionHistory(FROM_ACC);
            throw transferAmountServiceException;
        }
    }
}
