/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.service.test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.dao.atm.SpringAtmDao;
import com.nuance.him.dao.daoexception.AtmDaoException;
import com.nuance.him.model.account.Account;
import com.nuance.him.model.atm.AtmDetail;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.atm.AtmService;
import com.nuance.him.service.atm.AtmServiceImpl;
import com.nuance.him.service.exception.AtmServiceException;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * test class of {@link AtmServiceImpl}.
 */
public class AtmServiceImplTest {

    private static final int ATM_NUMBER = 123456;
    private static final int ACC_NUMBER = 3;
    private static final int CVV_NUMBER = 723;
    private static final String CARD_TYPE = "visa";
    @Mock
    private SpringAtmDao springAtmDao;
    @Mock
    private AccountService accountService;
    private AtmService atmService;
    private AtmDetail atmDetail;
    private Account account;

    /**
     * Initial setup for testing.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        atmService = new AtmServiceImpl(springAtmDao, accountService);
        atmDetail = new AtmDetail(ATM_NUMBER, ACC_NUMBER,CVV_NUMBER,  CARD_TYPE);
    }

    /**
     * test atmIssueService method.
     *
     * @throws Exception serviceException
     */
    @Test
    public void testIssueAtm() throws Exception {
        when(springAtmDao.issueAtmCard(any(AtmDetail.class))).thenReturn(atmDetail);
        when(accountService.getAccountDetail(ACC_NUMBER)).thenReturn(account);
        final AtmDetail atmDetailReturn = atmService.issueAtmCard(atmDetail);
        assertNotNull(atmDetailReturn,"atmDetailResult should not null");
        verify(springAtmDao, atMost(2)).issueAtmCard(atmDetail);
    }

    /**
     * test Exception thrown by method issueAtm.
     *
     * @throws Exception expectedExceptions
     */
    @Test(expectedExceptions = AtmServiceException.class)
    public void testIssueAtmException() throws Exception {
        doThrow(AtmDaoException.class).when(springAtmDao).issueAtmCard(any(AtmDetail.class));
        try {
            final AtmDetail atmDetailResult = atmService.issueAtmCard(atmDetail);
        }
        catch (final AtmServiceException accountServiceException) {
            assertEquals(AtmDaoException.class, accountServiceException.getCause().getClass());
            verify(springAtmDao).issueAtmCard(atmDetail);
            throw accountServiceException;
        }
    }

    /**
     * test IsAlreadyAtmTaken method.
     *
     * @throws Exception serviceException
     */
    @Test
    public void testIsAtmAlreadyTaken() throws Exception {
        when(springAtmDao.isAlreadyAtmTaken(anyInt())).thenReturn(atmDetail);
        final AtmDetail atmDetailResult = atmService.isAlreadyAtmTaken(ACC_NUMBER);
        assertNotNull(atmDetailResult,"atmDetailResult should not null");
        verify(springAtmDao).isAlreadyAtmTaken(ACC_NUMBER);
    }


    /**
     * test Exception thrown by method IsAtmAlreadyTaken.
     *
     * @throws Exception expectedExceptions
     */
    @Test(expectedExceptions = AtmServiceException.class)
    public void testIsAtmAlreadyTakenException() throws Exception {
        doThrow(AtmDaoException.class).when(springAtmDao).isAlreadyAtmTaken(anyInt());
        try {
            final AtmDetail atmDetailResult = atmService.isAlreadyAtmTaken(ACC_NUMBER);
        }
        catch (final AtmServiceException accountServiceException) {
            assertEquals(AtmDaoException.class, accountServiceException.getCause().getClass());
            verify(springAtmDao).isAlreadyAtmTaken(ACC_NUMBER);
            throw accountServiceException;
        }
    }
}
